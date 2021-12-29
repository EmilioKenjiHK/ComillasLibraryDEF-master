package ui;

import dtc.isw.client.Client;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dtc.isw.domain.*;

/**
 * In this page, the user can specify the global information for his reservation
 */
public class JAdminReservas extends JFrame{
    //Variables
    JButton guardar;
    JButton salir;
    JList reservas;
    JLabel info;

    Client client = new Client();
    HashMap<String,Object> session = new HashMap<>();

    // Hashmap donde pondremos el resultado de la request a la base de datos
    HashMap<String,Object> h = new HashMap<String,Object>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public void main(String argv[]){ new JAdminReservas("Admin");}

    public JAdminReservas(String administrador)
    {
        super("ComillasLibrary: Lista de Reservas");

        //Instanciar variables
        salir = new JButton("Volver a Opciones");
        guardar = new JButton("Guardar informacion en un .txt");

        info = new JLabel("Lista de Reservas",SwingConstants.CENTER);
        Font fTexto = new Font("Arial", Font.BOLD, 12);
        Font fTitulo = new Font("Arial",Font.BOLD, 25);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        info.setFont(fTitulo);

        info.setOpaque(true);
        info.setBackground(Color.CYAN);

        //Norte
        pnlNorth.add(info);
        this.add(pnlNorth,BorderLayout.NORTH);

        //Centro

        h = (HashMap<String, Object>) session.get("Respuesta");
        List list = new List();
        for(Integer i = 0;i<h.size();i+=2)
        {
            Reserva r = (Reserva) h.get(i.toString());
            Integer k = i+1;
            String u = (String) h.get(k.toString());
            list.add(u + " " + k);
        }
        reservas.add(list);
        pnlCenter.add(reservas);
        pnlCenter.add(guardar);

        this.add(pnlCenter,BorderLayout.CENTER);

        //Sur
        pnlSouth.add(salir);
        this.add(pnlSouth,BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminMenu(administrador);
            }
        });
    }

}
