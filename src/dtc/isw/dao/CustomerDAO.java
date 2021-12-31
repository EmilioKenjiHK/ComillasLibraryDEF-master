package dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.domain.*;

public class CustomerDAO {


    public static void getClientes(ArrayList<Customer> lista) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM prueba");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(new Customer(rs.getString(0), rs.getString(1)));
            }

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {


        ArrayList<Customer> lista = new ArrayList<Customer>();
        CustomerDAO.getClientes(lista);


        for (Customer customer : lista) {
            System.out.println("He leído el id: " + customer.getUser() + " con contrasenya: " + customer.getPassword());
        }


    }

    public static boolean checkLogin(String user, String password) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT username,password FROM usuarios");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (user.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
                    System.out.println("Encontrado");
                    return true;
                }
                /*else {
                    System.out.println("NO encontrado");
                    return false;
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("NO encontrado");
        return false;
    }

    public static boolean checkAdmin(String user) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT administrador FROM usuarios WHERE username = '" + user + "'");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (rs.getString(1).equals("t")) {
                    System.out.println("Admin");
                    return true;
                }
                /*else {
                    System.out.println("NO admin");
                    return false;
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("NO admin");
        return false;
    }

    public static Usuario getPerfil(String username) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        Usuario usuario = new Usuario("", "", "", 0);
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM usuarios WHERE username = '" + username + "'");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                usuario.setUsername(rs.getString(1));
                usuario.setPassword(rs.getString(2));
                usuario.setCorreo(rs.getString(3));
                usuario.setPuntos(Integer.parseInt(rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usuario;
    }

    public static HashMap<String, Object> infoTienda() {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Producto> a = new ArrayList<Producto>();
        HashMap<String, Object> res = new HashMap<String, Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM tienda where cantidad != 0");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i, new Producto(rs.getString(3),Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2))));
                i += 1;
            }
            for (Integer j = 0; j < a.size(); j++) {
                res.put(j.toString(), a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void updateTienda(String objeto, int cantidad)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String condicion = "objeto = '" + objeto + "'";
        try (PreparedStatement pst = con.prepareStatement("UPDATE tienda SET cantidad = " + cantidad + " WHERE " + condicion);
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void insertProducto(Producto producto,String username)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String valor = "'" + producto.getObjeto() + "','" + username + "'";
        try(PreparedStatement pst = con.prepareStatement("INSERT INTO producto VALUES (" + valor + ")");
            ResultSet rs = pst.executeQuery()) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static HashMap<String, Object> getBibliotecas() {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Biblioteca> a = new ArrayList<Biblioteca>();
        HashMap<String, Object> res = new HashMap<String, Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM asientos WHERE ocupado = false ORDER BY biblioteca asc");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i, new Biblioteca(rs.getString(1)));
                i += 1;
            }
            for (Integer j = 0; j < a.size(); j++) {
                res.put(j.toString(), a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static HashMap<String, Object> getPlantas(String biblioteca) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Planta> a = new ArrayList<Planta>();
        HashMap<String, Object> res = new HashMap<String, Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM asientos WHERE ocupado = false AND biblioteca = '" + biblioteca + "' ORDER BY planta asc");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i, new Planta(rs.getString(2)));
                i += 1;
            }
            for (Integer j = 0; j < a.size(); j++) {
                res.put(j.toString(), a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static HashMap<String, Object> getMesas(String biblioteca, String planta) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Mesa> a = new ArrayList<Mesa>();
        HashMap<String, Object> res = new HashMap<String, Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM asientos WHERE ocupado = false AND biblioteca = '" + biblioteca + "' AND planta = '" + planta + "' ORDER BY mesa asc");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i, new Mesa(rs.getString(3)));
                i += 1;
            }
            for (Integer j = 0; j < a.size(); j++) {
                res.put(j.toString(), a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static boolean checkUsuario(String user) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT username FROM usuarios");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (user.equals(rs.getString(1))) {
                    System.out.println("Encontrado");
                    return true;
                }
                /*else {
                    System.out.println("NO encontrado");
                    return false;
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("NO encontrado");
        return false;
    }

    public static boolean checkSancion(String user) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT username FROM sanciones");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (user.equals(rs.getString(1))) {
                    System.out.println("Encontrado");
                    return true;
                }
                /*else {
                    System.out.println("NO encontrado");
                    return false;
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("NO encontrado");
        return false;
    }

    public static Sancion getSancion(String user)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        Sancion s = new Sancion("",0);
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM sanciones WHERE username = '" + user + "'");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                s = new Sancion(rs.getString(2),Integer.parseInt(rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }

    public static int getID(int inicial)
    {
        Connection con=ConnectionDAO.getInstance().getConnection();
        int fin = inicial+1;
        try (PreparedStatement pst = con.prepareStatement("SELECT id FROM reservas");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Integer i = Integer.parseInt(rs.getString(1));
                if(inicial < i)
                {
                    fin = i+1;
                    inicial = i;
                }
            }
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        return fin;
    }

    public static void updateAsiento(String biblioteca,String planta, String mesa, int id)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String condicion = "biblioteca = '" + biblioteca + "' AND planta = '" + planta + "' AND mesa = '" + mesa + "'";
        try (PreparedStatement pst = con.prepareStatement("UPDATE asientos SET ocupado='true', idreserva = '" + id + "' WHERE " + condicion);
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void insertReserva(Reserva reserva,String username)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String valor = "'" + reserva.getIdreserva() + "','" + reserva.getHi() + "','" + reserva.getHf() + "','" + username + "'";
        try(PreparedStatement pst = con.prepareStatement("INSERT INTO reservas VALUES (" + valor + ")");
            ResultSet rs = pst.executeQuery()) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int getPuntos(String username)
    {
        Connection con=ConnectionDAO.getInstance().getConnection();
        int fin = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT puntos FROM usuarios WHERE username = '" + username + "'");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Integer i = Integer.parseInt(rs.getString(1));
                fin = i;
            }
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        return fin;
    }

    public static void updatePuntos(String username, int puntos)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String condicion = "username = '" + username + "'";
        try (PreparedStatement pst = con.prepareStatement("UPDATE usuarios SET puntos='"+ puntos + "' WHERE " + condicion);
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static HashMap<String,Object> getReservas(String username)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Reserva> a = new ArrayList<Reserva>();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM reservas WHERE username = '" + username + "' ORDER BY id asc");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i,new Reserva((Integer) Integer.parseInt(rs.getString(1)), rs.getString(2),rs.getString(3)));
                i +=1 ;
            }
            for(Integer j = 0; j<a.size();j++)
            {
                res.put(j.toString(),a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void deleteReserva(int id) //id de la Reserva
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try(PreparedStatement pst = con.prepareStatement("DELETE FROM reservas WHERE id = '" + id + "'");
            ResultSet rs = pst.executeQuery()) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void liberarAsiento(int id)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("UPDATE asientos SET ocupado='false', idreserva = null WHERE idreserva = '" + id + "'");
             ResultSet rs = pst.executeQuery()) {

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static HashMap<String,Object> infoBibliotecas()
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Biblioteca> a = new ArrayList<Biblioteca>();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT DISTINCT biblioteca FROM asientos ORDER BY biblioteca ASC;");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i,new Biblioteca(rs.getString(1)));
                i +=1 ;
            }
            for(Integer j = 0; j<a.size();j++)
            {
                res.put(j.toString(),a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static HashMap<String,Object> infoPlantas(String biblioteca)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Planta> a = new ArrayList<Planta>();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT DISTINCT planta FROM asientos WHERE ocupado = false AND biblioteca = '" + biblioteca + "' ORDER BY planta ASC");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                a.add(i,new Planta(rs.getString(1)));
                i +=1 ;
            }
            for(Integer j = 0; j<a.size();j++)
            {
                res.put(j.toString(),a.get(j));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static HashMap<String,Object> infoReservas()
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        ArrayList<Reserva> a = new ArrayList<Reserva>();
        ArrayList<String> b = new ArrayList<String>();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * from reservas ORDER BY username ASC");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                a.add(i,new Reserva(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3)));
                a.add(i+1,new Reserva(0,"00:00","00:00")); // Para evitar IndexOutOfBoundsException
                b.add(i,""); // Para evitar IndexOutOfBoundsException
                b.add(i+1,rs.getString(4));
                i +=2 ;
            }
            for(Integer j = 0; j<a.size();j+=2)
            {
                res.put(j.toString(),a.get(j));
                Integer k = j+1;
                res.put(k.toString(),b.get(k));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void setSancion(String usuario, Sancion sancion)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String valor = "'" + usuario + "','" + sancion.getRazon() + "','" + sancion.getLimite() + "'";
        try(PreparedStatement pst = con.prepareStatement("INSERT INTO sanciones VALUES (" + valor + ")");
            ResultSet rs = pst.executeQuery()) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}