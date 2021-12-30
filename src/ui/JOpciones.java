package ui;
import dtc.isw.client.Client;
import dtc.isw.domain.Sancion;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Displays the options for the user to choose
 */
public class JOpciones extends JFrame
{
    //Variables
    JButton reservar;
    JButton cancelar;
    JButton perfil;
    JButton salir;

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public static void main(String argv[])
    {
        new JOpciones("default");
    }

    /**
     * Constructor of JOpciones class
     * @param usuario the user who logged in previously (JLogin)
     */
    public JOpciones(String usuario)
    {
        super("ComillasLibrary: " + usuario);

        //Instanciar variables
        reservar = new JButton("Reserva un asiento");
        cancelar = new JButton("Cancelar una reserva");
        perfil = new JButton("Perfil");
        salir = new JButton("Logout");

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        //titulo.setFont(fontTitulo);

        //Norte
        pnlNorth.setLayout(new GridLayout(1, 3));
        pnlNorth.add(reservar);
        pnlNorth.add(cancelar);
        pnlNorth.add(perfil);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Centro
        double d = 0.8*MAXHEIGHT;
        int h = (int) d;
        Image logo = new ImageIcon("src/Recursos/Inspiracion.png").getImage();
        ImageIcon ii = new ImageIcon(logo.getScaledInstance(MAXWIDTH,h,java.awt.Image.SCALE_SMOOTH));
        JLabel im = new JLabel(ii);
        pnlCenter.add(im);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(salir);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH,MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones

        reservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                HashMap<String, Object> session = new HashMap<String,Object>();
                session.put("u",usuario);
                client.enviar("/getReservas",session);
                HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                if(h.size() == 4) {
                    JInfoBox.infoBox("Error","Error: Ya ha hecho el maximo numero de reservas");
                }
                else
                {
                    session = new HashMap<String,Object>();
                    session.put("u",usuario);
                    client.enviar("/checkSancion",session);
                    Boolean s = (Boolean) session.get("Respuesta");
                    if(s) // Comprobando que usuario no esta sancionado
                        {
                            session = new HashMap<String,Object>();
                            session.put("u",usuario);
                            client.enviar("/getSancion",session);
                            Sancion sancion = (Sancion) session.get("Respuesta");
                            System.out.println(sancion);
                            JInfoBox.infoBox("Error","Error: No puedes reservar por esta Sancion: \n Razon: " + sancion.getRazon() + "\n Horas a esperar: " + sancion.getLimite());
                        }
                    else {
                        dispose();
                        new JReservaInformacion(usuario);
                    }
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                HashMap<String, Object> session = new HashMap<String,Object>();
                session.put("u",usuario);
                client.enviar("/getReservas",session);
                HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");
                if(h.size() == 0) {
                    JInfoBox.infoBox("Error","Error: No tienes Reservas hechas");
                }
                else {
                    dispose();
                    new JBorrarReserva(usuario);
                }
            }
        });

        perfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JPerfil(usuario);
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new JLogin();
            }
        });


    }
}