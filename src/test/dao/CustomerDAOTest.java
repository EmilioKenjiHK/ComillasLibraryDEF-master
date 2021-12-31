package test.dao;

import dtc.isw.dao.CustomerDAO;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import dtc.isw.domain.Usuario;
import dtc.isw.domain.Mesa;
import dtc.isw.domain.Reserva;


class CustomerDAOTest extends TestCase {
    private static CustomerDAO  dao = null;

    @Test
    public void checkLogin() {
        String fakeu = "aDmIn";
        String fakep = "PassworD";
        String fakep2 = "passw0rd";
        String username = "Admin";
        String password = "password";

        assertEquals(false,dao.checkLogin(fakeu,fakep));
        assertEquals(false,dao.checkLogin(username,fakep));
        assertEquals(false,dao.checkLogin(username,fakep2));
        assertEquals(false,dao.checkLogin(fakeu,password));
        assertEquals(true,dao.checkLogin(username,password));
    }

    @Test
    public void checkAdmin() {
        String u = "1"; //User
        String a = "Admin"; //Admin

        assertEquals(false,dao.checkAdmin(u));
        assertEquals(true,dao.checkAdmin(a));
    }

    @Test
    public void getPerfil() {
        String username = "Admin";
        Usuario res = dao.getPerfil(username);

        assertEquals("Admin", res.getUsername());
        assertEquals("password",res.getPassword());
        assertEquals("default@comillas.edu",res.getCorreo());
        assertEquals(0,res.getPuntos());
    }

    @Test
    public void getMesas() {
        String biblioteca = "test";
        String planta = "test";
        HashMap<String,Object> res = dao.getMesas(biblioteca,planta);
        Mesa m = (Mesa) res.get("0");

        assertEquals("1A",m.getNombre());
    }

    @Test
    public void checkSancion() {
        String a = "Admin"; //Admin nunca puede recibir sancion (no pongo usuarios ya que se pueden asignarles)
        assertEquals(false,dao.checkSancion(a));
    }

    @Test
    public void getReservas(){
        String username = "Admin";
        HashMap<String,Object> res = dao.getReservas(username);
        Reserva r = (Reserva) res.get("0");

        assertEquals(0,r.getIdreserva());
        assertEquals("08:00",r.getHi());
        assertEquals("08:30",r.getHf());
    }
}