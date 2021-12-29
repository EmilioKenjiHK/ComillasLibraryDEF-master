package ui;
import dtc.isw.client.Client;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class JAdminMenu extends JFrame {
    //Variables
    JButton mapas;
    JButton reservas;
    JButton sancion;
    JButton salir;

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public static void main(String argv[]) { new JAdminMenu("Admin"); }

    /**
     * Constructor of JAdminMenu class
     * @param administrador the administrator who logged in previously (JLogin)
     */
    public JAdminMenu(String administrador)
    {
        super("ComillasLibrary: " + administrador);

        //Instanciar variables
        mapas = new JButton("Subir planos de una biblioteca");
        reservas = new JButton("Ver lista de las reservas");
        sancion = new JButton("Sancionar un usuario");
        salir = new JButton("Logout");

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Norte
        pnlNorth.setLayout(new GridLayout(1, 3));
        pnlNorth.add(mapas);
        pnlNorth.add(reservas);
        pnlNorth.add(sancion);
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
        this.add(pnlSouth,BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH,MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        mapas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminMapas(administrador);
            }
        });
        
        reservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminReservas(administrador);
            }
        });
        
        sancion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminSancion();
            }
        });
        
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JLogin();
            }
        });
    }
}
