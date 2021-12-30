package dtc.isw.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

//import dtc.isw.domain.Customer;
import dtc.isw.domain.Sancion;
import org.apache.log4j.Logger;

import dtc.isw.configuration.PropertiesISW;
import dtc.isw.message.Message;

public class Client {
    private String host;
    private int port;
    final static Logger logger = Logger.getLogger(Client.class);

    public void enviar(String situacion, HashMap<String,Object> session) {
        //Configure connections
        String host;
        host = PropertiesISW.getInstance().getProperty("host");
        int port = Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
        Logger.getRootLogger().info("Host: "+host+" port"+port);
        //Create a cliente class
        Client cliente=new Client(host, port);
        HashMap<String,Object> h;

        //session.put("/getCustomer","");

        Message mensajeEnvio=new Message();
        Message mensajeVuelta=new Message();
        mensajeEnvio.setContext(situacion);
        mensajeEnvio.setSession(session);
        cliente.sent(mensajeEnvio,mensajeVuelta);
        
        switch (mensajeVuelta.getContext()) {
           /* case "/getCustomerResponse":
                ArrayList<Customer> customerList=(ArrayList<Customer>)(mensajeVuelta.getSession().get("Customer"));
                for (Customer customer : customerList) {
                    System.out.println("He leído el id: "+customer.getId()+" con nombre: "+customer.getName());
                }
                break;
            */
            case "/loginUserEnd":
                System.out.println("\nEntrada comprobada");
                int res = (Integer) mensajeVuelta.getSession().get("Respuesta");
                session.put("Respuesta",res);
                break;

            case "/getPerfilEnd":
                h = (HashMap<String, Object>) mensajeVuelta.getSession();
                session.put("Respuesta",h.get("u"));
                System.out.println("Usuario obtenido");
                break;

            case "/getComprasEnd":
                ArrayList<String> a = (ArrayList<String>) mensajeVuelta.getSession().get("Respuesta");
                session.put("Respuesta",a);
                System.out.println("Compras Obtenidas");
                break;

            case "/getInfoEnd":
                h = (HashMap<String, Object>) mensajeVuelta.getSession();
                session.put("Respuesta",h);
                System.out.println("Informacion obtenida");
                break;

            case "/checkUsuarioEnd":
                System.out.println("\nEntrada comprobada");
                boolean b = (Boolean) mensajeVuelta.getSession().get("Respuesta");
                session.put("Respuesta",b);
                break;

            case "/checkSancionEnd":
                System.out.println("\nEntrada comprobada");
                b = (Boolean) mensajeVuelta.getSession().get("Respuesta");
                session.put("Respuesta",b);
                break;

            case "/getSancionEnd":
                System.out.println("Sancion obtenida");
                Sancion s = (Sancion) mensajeVuelta.getSession().get("Respuesta");
                session.put("Respuesta",s);
                break;

            case "/getIDReservaEnd":
                int i = (Integer) mensajeVuelta.getSession().get("id");
                session.put("Respuesta",i);
                break;

            case "/updateAsientoEnd":
                System.out.println("Valor actualizado");
                break;

            case "/insertReservaEnd":
                System.out.println("Nueva Reserva guardada");
                break;

            case "/borrarReservaEnd":
                System.out.println("Reserva eliminada");
                break;

            case "/setSancionEnd":
                System.out.println("Sancion impuesta");
                break;

            default:
                Logger.getRootLogger().info("Option not found");
                System.out.println("\nError a la vuelta");
                break;

        }
        //System.out.println("3.- En Main.- El valor devuelto es: "+((String)mensajeVuelta.getSession().get("Nombre")));
    }


    public Client(){}

    public Client(String host, int port) {
        this.host=host;
        this.port=port;
    }


    public void sent(Message messageOut, Message messageIn) {
        try {

            System.out.println("Connecting to host " + host + " on port " + port + ".");

            Socket echoSocket = null;
            OutputStream out = null;
            InputStream in = null;


            try {
                echoSocket = new Socket(host, port);

                in = echoSocket.getInputStream();

               out = echoSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

                //Create the object to send
                objectOutputStream.writeObject(messageOut);

                // create a DataInputStream so we can read data from it.
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                Message msg=(Message)objectInputStream.readObject();
                messageIn.setContext(msg.getContext());
                messageIn.setSession(msg.getSession());
		        /*System.out.println("\n1.- El valor devuelto es: "+messageIn.getContext());
		        String cadena=(String) messageIn.getSession().get("Nombre");
		        System.out.println("\n2.- La cadena devuelta es: "+cadena);*/

            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + host);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);

           }

            /** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}