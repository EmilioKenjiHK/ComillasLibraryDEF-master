package ui;

import dtc.isw.client.Client;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import dtc.isw.domain.*;


public class JReservaGrupo extends JFrame{
    //Variables
    JComboBox mesa0;
    JComboBox mesa1;
    JComboBox mesa2;
    JComboBox mesa3;
    JTextField usuario0;
    JTextField usuario1;
    JTextField usuario2;
    JTextField usuario3;
    JButton salir;
    JButton reservar;
    JLabel m0;
    JLabel m1;
    JLabel m2;
    JLabel m3;

    HashMap<String,Object> h;
    ArrayList<Object> noRepeat = new ArrayList<Object>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public void main(String argv[])
    {
        new JReservaGrupo("default",null);
    }

    // valores(0) = biblioteca
    // valores(1) = planta
    // valores(2) = horain;
    // valores(3) = horafin;

    /**
     * Contructor of JReservaMesa class
     * Initializes variables and describe the page and its elements
     * @param usuario The current user
     * @param valores Table with library,plant and times for the reservation
     */
    public JReservaGrupo(String usuario, ArrayList<String> valores)
    {
        super("ComillasLibrary: Reservacion (Grupo)");

        //Instanciar variables
        mesa0 = new JComboBox();
        mesa1 = new JComboBox();
        mesa2 = new JComboBox();
        mesa3 = new JComboBox();
        usuario0 = new JTextField(20);
        usuario1 = new JTextField(20);
        usuario2 = new JTextField(20);
        usuario3 = new JTextField(20);
        salir = new JButton("Volver Atras");
        reservar = new JButton("Reservar los asientos");
        m0 = new JLabel("Mesa 1");
        m1 = new JLabel("Mesa 2");
        m2 = new JLabel("Mesa 3");
        m3 = new JLabel("Mesa 4");
        Font fontTexto = new Font("Arial", Font.BOLD, 12);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        mesa0.setFont(fontTexto);
        mesa1.setFont(fontTexto);
        mesa2.setFont(fontTexto);
        mesa3.setFont(fontTexto);

        //Configuracion ComboBoxes
        //Valores nulos
        mesa0.addItem("");
        mesa1.addItem("");
        mesa2.addItem("");
        mesa3.addItem("");

        //mesa
        Client client = new Client();
        HashMap<String,Object> session = new HashMap<>();
        session.put("b",valores.get(0));
        session.put("p",valores.get(1));
        client.enviar("/getMesas",session);
        h = (HashMap<String,Object>) session.get("Respuesta");
        for(Integer i = 0; i< h.size();i++)
        {
            Mesa m = (Mesa) h.get(i.toString());
            if(!noRepeat.contains(m.getNombre()))
            {
                mesa0.addItem(m.getNombre());
                mesa1.addItem(m.getNombre());
                mesa2.addItem(m.getNombre());
                mesa3.addItem(m.getNombre());
                noRepeat.add(m.getNombre());
            }
        }


        //Norte
        pnlNorth.setLayout(new GridLayout(4, 3));
        pnlNorth.add(m0);
        pnlNorth.add(mesa0);
        pnlNorth.add(usuario0);
        pnlNorth.add(m1);
        pnlNorth.add(mesa1);
        pnlNorth.add(usuario1);
        pnlNorth.add(m2);
        pnlNorth.add(mesa2);
        pnlNorth.add(usuario2);
        pnlNorth.add(m3);
        pnlNorth.add(mesa3);
        pnlNorth.add(usuario3);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Centro (Mapa)
        double d = 0.8*MAXHEIGHT;
        int h = (int) d;
        Image logo = new ImageIcon("src/Recursos/Bibliotecas/"+valores.get(0)+"/"+valores.get(1)+".png").getImage();
        ImageIcon ii = new ImageIcon(logo.getScaledInstance(MAXWIDTH,h,java.awt.Image.SCALE_SMOOTH));
        JLabel im = new JLabel(ii);
        pnlCenter.add(im);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(salir);
        pnlSouth.add(reservar);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new JReservaInformacion(usuario);
            }
        });

        reservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s0 = usuario0.getText().replace(" ","");
                String s1 = usuario1.getText().replace(" ","");
                String s2 = usuario2.getText().replace(" ","");
                String s3 = usuario3.getText().replace(" ","");
                boolean b = true; // Determina si se puede crear las reservas
                int k = 0;
                HashMap<String,String> reservas = new HashMap<String,String>();
                Client client = new Client();
                HashMap<String,Object> session = new HashMap<String,Object>();

                if(s0.equals("") && s1.equals("") && s2.equals("") && s3.equals("")) // Todos Vacios
                {
                    JInfoBox.infoBox("Error","Error: No has asignado ningun usuario.");
                }
                else {

                    //s0
                    if(s0.equals("") || mesa0.getSelectedItem().equals(""))
                    {
                        if(s0.equals("") && mesa0.getSelectedItem().equals(""))
                        {
                            System.out.println("Se ignora"); //Se ignora s0
                        }
                        else //Un campo vacio --> no posible
                        {
                            JInfoBox.infoBox("Error","Error: Debes rellenar ambos campos si desea incluir este usuario en " + m0.getText() + ".");
                            b = false;
                        }
                    }
                    else //Ambos Campos rellenos
                    {
                        session = new HashMap<String,Object>();
                        session.put("u",s0);
                        client.enviar("/checkUsuario",session);
                        Boolean check = (Boolean) session.get("Respuesta");
                        if(check) //Usuario presente
                        {
                            session = new HashMap<String,Object>();
                            session.put("u",s0);
                            client.enviar("/getReservas",session);
                            HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                            if(h.size() == 4) { // Asegurando que no ha llegado a numero maximo de reservas
                                JInfoBox.infoBox("Error","Error: Usuario " + s0 + " ha hecho el maximo numero de reservas");
                                b = false;
                            }
                            else {
                                session = new HashMap<String,Object>();
                                session.put("u",s0);
                                client.enviar("/checkSancion",session);
                                Boolean s = (Boolean) session.get("Respuesta");
                                if(s) // Comprobando que usuario no esta sancionado
                                {
                                    JInfoBox.infoBox("Error","Error: Usuario " + s0 + " esta sancionado. No puede reservar");
                                    b = false;
                                }
                                else {
                                    reservas.put((String) mesa0.getSelectedItem(), s0);
                                    k+=5;
                                }
                            }
                        }
                        else
                        {
                            JInfoBox.infoBox("Error","Error: Usuario " + s0 + " puesto incorrecto");
                            b = false;
                        }
                    }

                    //s1
                    if(s1.equals("") || mesa1.getSelectedItem().equals(""))
                    {
                        System.out.println(s1);
                        System.out.println(mesa1.getSelectedItem());
                        if(s1.equals("") && mesa1.getSelectedItem().equals(""))
                        {
                            System.out.println("Se ignora"); //Se ignora s1
                        }
                        else //Un campo vacio --> no posible
                        {
                            JInfoBox.infoBox("Error","Error: Debes rellenar ambos campos si desea incluir este usuario en " + m1.getText() + ".");
                            b = false;
                        }
                    }
                    else //Ambos Campos rellenos
                    {
                        session = new HashMap<String,Object>();
                        session.put("u",s1);
                        client.enviar("/checkUsuario",session);
                        Boolean check = (Boolean) session.get("Respuesta");
                        if(check) //Usuario presente
                        {
                            session = new HashMap<String,Object>();
                            session.put("u",s1);
                            client.enviar("/getReservas",session);
                            HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                            if(h.size() == 4) { // Asegurando que no ha llegado a numero maximo de reservas
                                JInfoBox.infoBox("Error","Error: Usuario " + s1 + " ha hecho el maximo numero de reservas");
                                b = false;
                            }
                            else {
                                //A partir de aqui, se debe comprobar que 2 usuarios no tienen reservada la misma silla
                                Set key = reservas.keySet();
                                if (key.contains(mesa1.getSelectedItem())) {
                                    JInfoBox.infoBox("Error", "Error: Cada usuario debe ocupar un asiento diferente.");
                                    b = false;
                                } else {
                                    session = new HashMap<String,Object>();
                                    session.put("u",s1);
                                    client.enviar("/checkSancion",session);
                                    Boolean s = (Boolean) session.get("Respuesta");
                                    if(s) // Comprobando que usuario no esta sancionado
                                    {
                                        JInfoBox.infoBox("Error","Error: Usuario " + s1 + " esta sancionado. No puede reservar");
                                        b = false;
                                    }
                                    else {
                                        reservas.put((String) mesa1.getSelectedItem(), s1);
                                        k+=5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            JInfoBox.infoBox("Error","Error: Usuario " + s1 + " puesto incorrecto");
                            b = false;
                        }
                    }

                    //s2
                    if(s2.equals("") || mesa2.getSelectedItem().equals(""))
                    {
                        if(s2.equals("") && mesa2.getSelectedItem().equals(""))
                        {
                            System.out.println("Se ignora"); //Se ignora s2
                        }
                        else //Un campo vacio --> no posible
                        {
                            JInfoBox.infoBox("Error","Error: Debes rellenar ambos campos si desea incluir este usuario en " + m2.getText() + ".");
                            b = false;
                        }
                    }
                    else //Ambos Campos rellenos
                    {
                        session = new HashMap<String,Object>();
                        session.put("u",s2);
                        client.enviar("/checkUsuario",session);
                        Boolean check = (Boolean) session.get("Respuesta");
                        if(check) //Usuario presente
                        {
                            session = new HashMap<String,Object>();
                            session.put("u",s2);
                            client.enviar("/getReservas",session);
                            HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                            if(h.size() == 4) { // Asegurando que no ha llegado a numero maximo de reservas
                                JInfoBox.infoBox("Error","Error: Usuario " + s2 + " ha hecho el maximo numero de reservas");
                                b = false;
                            }
                            else {
                                //Se debe comprobar que 2 usuarios no tienen reservada la misma silla
                                Set key = reservas.keySet();
                                if (key.contains(mesa2.getSelectedItem())) {
                                    JInfoBox.infoBox("Error", "Error: Cada usuario debe ocupar un asiento diferente.");
                                    b = false;
                                } else {
                                    session = new HashMap<String,Object>();
                                    session.put("u",s2);
                                    client.enviar("/checkSancion",session);
                                    Boolean s = (Boolean) session.get("Respuesta");
                                    if(s) // Comprobando que usuario no esta sancionado
                                    {
                                        JInfoBox.infoBox("Error","Error: Usuario " + s2 + " esta sancionado. No puede reservar");
                                        b = false;
                                    }
                                    else {
                                        reservas.put((String) mesa2.getSelectedItem(), s2);
                                        k+=5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            JInfoBox.infoBox("Error","Error: Usuario " + s2 + " puesto incorrecto");
                            b = false;
                        }
                    }

                    //s3
                    if(s3.equals("") || mesa3.getSelectedItem().equals(""))
                    {
                        if(s3.equals("") && mesa3.getSelectedItem().equals(""))
                        {
                            System.out.println("Se ignora"); //Se ignora s3
                        }
                        else //Un campo vacio --> no posible
                        {
                            JInfoBox.infoBox("Error","Error: Debes rellenar ambos campos si desea incluir este usuario en " + m3.getText() + ".");
                            b = false;
                        }
                    }
                    else //Ambos Campos rellenos
                    {
                        session = new HashMap<String,Object>();
                        session.put("u",s3);
                        client.enviar("/checkUsuario",session);
                        Boolean check = (Boolean) session.get("Respuesta");
                        if(check) //Usuario presente
                        {
                            session = new HashMap<String,Object>();
                            session.put("u",s3);
                            client.enviar("/getReservas",session);
                            HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                            if(h.size() == 4) { // Asegurando que no ha llegado a numero maximo de reservas
                                JInfoBox.infoBox("Error","Error: Usuario " + s3 + " ha hecho el maximo numero de reservas");
                                b = false;
                            }
                            else {
                                //Se debe comprobar que 2 usuarios no tienen reservada la misma silla
                                Set key = reservas.keySet();
                                if (key.contains(mesa3.getSelectedItem())) {
                                    JInfoBox.infoBox("Error", "Error: Cada usuario debe ocupar un asiento diferente.");
                                    b = false;
                                } else {
                                    session = new HashMap<String,Object>();
                                    session.put("u",s3);
                                    client.enviar("/checkSancion",session);
                                    Boolean s = (Boolean) session.get("Respuesta");
                                    if(s) // Comprobando que usuario no esta sancionado
                                    {
                                        JInfoBox.infoBox("Error","Error: Usuario " + s3 + " esta sancionado. No puede reservar");
                                        b = false;
                                    }
                                    else {
                                        reservas.put((String) mesa3.getSelectedItem(), s3);
                                        k+=5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            JInfoBox.infoBox("Error","Error: Usuario " + s3 + " puesto incorrecto");
                            b = false;
                        }
                    }

                    if(b)
                    {
                        System.out.println("Todo Correcto");
                        ArrayList<String> array = new ArrayList<>(reservas.keySet());
                        for(Integer i=0; i<reservas.size();i++)
                        {
                            session.put("id",0); // Valor defecto
                            client.enviar("/getIDReserva",session);
                            int idres = (Integer) session.get("Respuesta");

                            //actualizar tabla asientos
                            session = new HashMap<String,Object>();
                            session.put("b",valores.get(0));
                            session.put("p",valores.get(1));
                            session.put("m",array.get(i));
                            session.put("id",idres);
                            client.enviar("/updateAsiento",session);

                            //crear Reserva
                            Reserva r = new Reserva(idres,valores.get(2),valores.get(3));
                            session = new HashMap<String,Object>();
                            session.put("r",r);
                            session.put("u",reservas.get(array.get(i)));
                            client.enviar("/insertReserva",session);
                        }
                        JInfoBox.infoBox("Aviso","Reservacion en Grupo hecho (+5 por persona)");
                        session = new HashMap<String,Object>();
                        session.put("u",usuario);
                        client.enviar("/getPuntos",session);
                        int puntos = (Integer) session.get("Respuesta");
                        session = new HashMap<String,Object>();
                        session.put("u",usuario);
                        session.put("p",puntos+k);
                        client.enviar("/addPuntos",session);
                        dispose();
                        new JOpciones(usuario);
                    }
                }
            }
        });
    }
}
