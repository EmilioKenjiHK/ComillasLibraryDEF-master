package ui;

import dtc.isw.client.Client;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.domain.*;


/**
 * Displays the page where the user can choose a table for his reservation
 */
public class JReservaMesa extends JFrame {
    //Variables
    JComboBox mesa;
    JButton salir;
    JButton reservar;
    JLabel m;

    HashMap<String,Object> h;
    ArrayList<Object> noRepeat = new ArrayList<Object>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public void main(String argv[])
    {
        new JReservaMesa("default",null);
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
    public JReservaMesa(String usuario, ArrayList<String> valores)
    {
        super("ComillasLibrary: Reservacion (Opciones)");

        //Instanciar variables
        mesa = new JComboBox();
        salir = new JButton("Volver Atras");
        reservar = new JButton("Reservar el asiento");
        m = new JLabel("Mesa:");
        Font fontTexto = new Font("Arial", Font.BOLD, 12);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        mesa.setFont(fontTexto);

        //Configuracion ComboBoxes
        //Valores nulos
        mesa.addItem("");

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
                mesa.addItem(m.getNombre());
                noRepeat.add(m.getNombre());
            }
        }


        //Norte
        //pnlNorth.setLayout(new GridLayout(1, 2));
        pnlNorth.add(m);
        pnlNorth.add(mesa);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Centro
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
                if(mesa.getSelectedItem().equals(""))
                {
                    JInfoBox.infoBox("Error","Error: No has elegido una mesa");
                }
                else {
                    Client client = new Client();
                    HashMap<String,Object> session = new HashMap<String,Object>();

                    //dar un valor a idreserva
                    session.put("id",0); // Valor defecto
                    client.enviar("/getIDReserva",session);
                    int idres = (Integer) session.get("Respuesta");

                    //actualizar tabla asientos
                    session = new HashMap<String,Object>();
                    session.put("b",valores.get(0));
                    session.put("p",valores.get(1));
                    session.put("m",mesa.getSelectedItem());
                    session.put("id",idres);
                    client.enviar("/updateAsiento",session);

                    //crear Reserva
                    Reserva r = new Reserva(idres,valores.get(2),valores.get(3));
                    session = new HashMap<String,Object>();
                    session.put("r",r);
                    session.put("u",usuario);
                    client.enviar("/insertReserva",session);
                    JInfoBox.infoBox("Aviso", "Se ha completado la reserva (+5 puntos).");
                    session = new HashMap<String,Object>();
                    session.put("u",usuario);
                    client.enviar("/getPuntos",session);
                    int puntos = (Integer) session.get("Respuesta");
                    session = new HashMap<String,Object>();
                    session.put("u",usuario);
                    session.put("p",puntos+5);
                    client.enviar("/addPuntos",session);
                    dispose();
                    new JOpciones(usuario);
                }
            }
        });
    }
}
