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

    public Usuario getPerfil(String username) {
        Usuario u = CustomerDAO.getPerfil(username);
        return u;
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
}