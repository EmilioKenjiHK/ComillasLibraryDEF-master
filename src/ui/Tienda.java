package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Usuario;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Displays the shop page
 */
public class Tienda extends JFrame{

    JButton descuentos;

    public Tienda (String usuario) {

        super("ComillasLibrary: Tienda");

        Client cl = new Client();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",usuario);
        cl.enviar("/getTienda",map);
        Usuario u = (Usuario) map.get("Respuesta");

        //Extracting basic information
        String name = u.getUsername();
        String password = u.getPassword();
        String mail = u.getCorreo();
        String p = Integer.toString(u.getPuntos());
        ArrayList<String> a = new ArrayList<String>(Arrays.asList(name,password,mail,p));

        System.out.println("LIST USUARIOS " + u);
    }

    public static void main(String[] args) {
        new Tienda("default");
    }



}
