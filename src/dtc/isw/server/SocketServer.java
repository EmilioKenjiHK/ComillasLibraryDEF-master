package dtc.isw.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.controler.CustomerControler;
import dtc.isw.domain.*;
import dtc.isw.message.Message;

public class SocketServer extends Thread {
    public static final int PORT_NUMBER = 8081;

    protected Socket socket;

    private SocketServer(Socket socket) {
        this.socket = socket;
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
        start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            //first read the object that has been sent
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Message mensajeIn= (Message)objectInputStream.readObject();
            //Object to return informations
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            Message mensajeOut = new Message();
            CustomerControler cc = new CustomerControler();
            HashMap<String,Object> session = new HashMap<String,Object>();
            String biblioteca;
            String planta;
            String mesa;
            int id;

            switch (mensajeIn.getContext()) {
                case "/getCustomer":
                    CustomerControler customerControler=new CustomerControler();
                    ArrayList<Customer> lista=new ArrayList<Customer>();
                    customerControler.getCustomer(lista);
                    mensajeOut.setContext("/getCustomerResponse");
                    session=new HashMap<String, Object>();
                    session.put("Customer",lista);
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/loginUser":
                    String user = (String) mensajeIn.getSession().get("u");
                    String password = (String) mensajeIn.getSession().get("p");
                    cc = new CustomerControler();
                    boolean b = cc.checkLogin(user,password);
                    if(b)
                    {
                        Boolean l = cc.checkAdmin(user);
                        if(l)
                        {
                            session = new HashMap<String,Object>();
                            session.put("Respuesta",2);
                        }
                        else{
                            session = new HashMap<String,Object>();
                            session.put("Respuesta",1);
                        }
                        mensajeOut.setContext("/loginUserEnd");
                        mensajeOut.setSession(session);
                        objectOutputStream.writeObject(mensajeOut);
                        break;
                    }
                    else
                    {
                        mensajeOut.setContext("/loginUserEnd");
                        session = new HashMap<String,Object>();
                        session.put("Respuesta",0);
                        mensajeOut.setSession(session);
                        objectOutputStream.writeObject(mensajeOut);
                        break;
                    }

                case "/getPerfil":
                    String username = ((String) mensajeIn.getSession().get("id"));
                    Usuario usuario = cc.getPerfil(username);
                    session = new HashMap<String,Object>();
                    session.put("u",usuario);
                    mensajeOut.setContext("/getPerfilEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getBibliotecas":
                    cc = new CustomerControler();
                    session = cc.getBibliotecas();
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getPlantas":
                    biblioteca = (String) mensajeIn.getSession().get("b");
                    cc = new CustomerControler();
                    session = cc.getPlantas(biblioteca);
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getMesas":
                    biblioteca = (String) mensajeIn.getSession().get("b");
                    planta = (String) mensajeIn.getSession().get("p");
                    cc = new CustomerControler();
                    session = cc.getMesas(biblioteca,planta);
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/checkUsuario":
                    user = (String) mensajeIn.getSession().get("u");
                    cc = new CustomerControler();
                    b = cc.checkUsuario(user);
                    session = new HashMap<String,Object>();
                    session.put("Respuesta",b);
                    mensajeOut.setContext("/checkUsuarioEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;


                case "/getIDReserva":
                    int inicial = (Integer) mensajeIn.getSession().get("id");
                    cc = new CustomerControler();
                    int fin = cc.getID(inicial);
                    session = new HashMap<String,Object>();
                    session.put("id",fin);
                    mensajeOut.setContext("/getIDReservaEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/updateAsiento":
                    biblioteca = (String) mensajeIn.getSession().get("b");
                    planta = (String) mensajeIn.getSession().get("p");
                    mesa = (String) mensajeIn.getSession().get("m");
                    id = (Integer) mensajeIn.getSession().get("id");
                    cc = new CustomerControler();
                    cc.updateAsiento(biblioteca,planta,mesa,id);
                    mensajeOut.setContext("/updateAsientoEnd");
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/insertReserva":
                    Reserva r = (Reserva) mensajeIn.getSession().get("r");
                    user = (String) mensajeIn.getSession().get("u");
                    cc = new CustomerControler();
                    cc.insertReserva(r,user);
                    mensajeOut.setContext("/insertReservaEnd");
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getReservas":
                    user = (String) mensajeIn.getSession().get("u");
                    cc = new CustomerControler();
                    session = cc.getReservas(user);
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/borrarReserva":
                    id = (Integer) mensajeIn.getSession().get("id");
                    cc = new CustomerControler();
                    cc.deleteReserva(id);
                    mensajeOut.setContext("/borrarReservaEnd");
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/liberarAsiento":
                    id = (Integer) mensajeIn.getSession().get("id");
                    cc = new CustomerControler();
                    cc.liberarAsiento(id);
                    mensajeOut.setContext("/updateAsientoEnd");
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/infoBibliotecas":
                    cc = new CustomerControler();
                    session = cc.infoBibliotecas();
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/infoPlantas":
                    biblioteca = (String) mensajeIn.getSession().get("b");
                    cc = new CustomerControler();
                    session = cc.infoPlantas(biblioteca);
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/infoReservas":
                    cc = new CustomerControler();
                    session = cc.infoReservas();
                    mensajeOut.setContext("/getInfoEnd");
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                case "/getCompras":
                    user = (String) mensajeIn.getSession().get("u");
                    cc = new CustomerControler();
                    ArrayList<String> result = new ArrayList<>();
                    break;


            }

            //Lógica del controlador
            //System.out.println("\n1.- He leído: "+mensaje.getContext());
            //System.out.println("\n2.- He leído: "+(String)mensaje.getSession().get("Nombre"));



            //Prueba para esperar
		    /*try {
		    	System.out.println("Me duermo");
				Thread.sleep(15000);
				System.out.println("Me levanto");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
            // create an object output stream from the output stream so we can send an object through it
			/*ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

			//Create the object to send
			String cadena=((String)mensaje.getSession().get("Nombre"));
			cadena+=" añado información";
			mensaje.getSession().put("Nombre", cadena);
			//System.out.println("\n3.- He leído: "+(String)mensaje.getSession().get("Nombre"));
			objectOutputStream.writeObject(mensaje);*
			*/

        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("Unable to get streams from client");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("SocketServer Example");
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT_NUMBER);
            while (true) {
                /**
                 * create a new {@link SocketServer} object for each connection
                 * this will allow multiple client connections
                 */
                new SocketServer(server.accept());
            }
        } catch (IOException ex) {
            System.out.println("Unable to start server.");
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}