package dtc.isw.controler;

import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.dao.CustomerDAO;
import dtc.isw.domain.*;

public class CustomerControler {

    public void getCustomer(ArrayList<Customer> lista) {
        CustomerDAO.getClientes(lista);
    }
    public static boolean checkLogin(String user, String password) {
        boolean b = CustomerDAO.checkLogin(user,password);
        return b;
    }

    public static boolean checkAdmin(String user) {
        boolean b = CustomerDAO.checkAdmin(user);
        return b;
    }

    public Usuario getPerfil(String username) {
        Usuario u = CustomerDAO.getPerfil(username);
        return u;
    }

    public static HashMap<String, Object> infoTienda()
    {
        HashMap<String,Object> h = CustomerDAO.infoTienda();
        return h;
    }

    public static void updateTienda(String objeto, int cantidad)
    {
        CustomerDAO.updateTienda(objeto,cantidad);
    }

    public static void insertProducto(Producto producto,String username)
    {
        CustomerDAO.insertProducto(producto,username);
    }

    public static HashMap<String,Object> getBibliotecas()
    {
        HashMap<String,Object> h = CustomerDAO.getBibliotecas();
        return h;
    }

    public static HashMap<String,Object> getPlantas(String biblioteca)
    {
        HashMap<String,Object> h = CustomerDAO.getPlantas(biblioteca);
        return h;
    }

    public static HashMap<String,Object> getMesas(String biblioteca,String planta)
    {
        HashMap<String,Object> h = CustomerDAO.getMesas(biblioteca,planta);
        return h;
    }

    public static boolean checkUsuario(String user)
    {
        boolean b = CustomerDAO.checkUsuario(user);
        return b;
    }

    public static boolean checkSancion(String user)
    {
        boolean b = CustomerDAO.checkSancion(user);
        return b;
    }

    public static Sancion getSancion(String user)
    {
        Sancion s = CustomerDAO.getSancion(user);
        return s;
    }

    public static int getID(int inicial)
    {
        int i = CustomerDAO.getID(inicial);
        return i;
    }

    public static void updateAsiento(String biblioteca,String planta, String mesa, int id)
    {
        CustomerDAO.updateAsiento(biblioteca,planta,mesa,id);
    }

    public static void insertReserva(Reserva reserva,String username)
    {
        CustomerDAO.insertReserva(reserva,username);
    }

    public static int getPuntos(String username){
        Integer i = CustomerDAO.getPuntos(username);
        return i;
    }

    public static void updatePuntos(String username, int puntos){
        CustomerDAO.updatePuntos(username,puntos);
    }

    public static HashMap<String,Object> getReservas(String username)
    {
        HashMap<String,Object> h = CustomerDAO.getReservas(username);
        return h;
    }

    public static void deleteReserva(int id)
    {
        CustomerDAO.deleteReserva(id);
    }

    public static void liberarAsiento(int id)
    {
        CustomerDAO.liberarAsiento(id);
    }

    public static HashMap<String,Object> infoBibliotecas()
    {
        HashMap<String,Object> h = CustomerDAO.infoBibliotecas();
        return h;
    }

    public static HashMap<String,Object> infoPlantas(String biblioteca)
    {
        HashMap<String,Object> h = CustomerDAO.infoPlantas(biblioteca);
        return h;
    }

    public static HashMap<String,Object> infoReservas()
    {
        HashMap<String,Object> h = CustomerDAO.infoReservas();
        return h;
    }
    public static void setSancion(String usuario, Sancion sancion)
    {
        CustomerDAO.setSancion(usuario,sancion);
    }

}