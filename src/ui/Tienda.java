package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Producto;
import dtc.isw.domain.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Displays the shop page
 */
public class Tienda extends JFrame{

    //Variables
    JLabel p;
    JLabel s;
    JComboBox seleccionar;
    JButton comprar;
    JButton volver;

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 800;

    public Tienda (String usuario) {

        super("ComillasLibrary: Tienda");

        //Instanciar variables
        s = new JLabel("Selecciona un objeto a comprar:");
        seleccionar = new JComboBox();
        comprar = new JButton("Comprar objeto");
        volver = new JButton("Volver atras");

        Client cl = new Client();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();

        //Extraemos puntos de Usuario
        map.put("u",usuario);
        cl.enviar("/getPerfil",map);
        Usuario u = (Usuario) map.get("Respuesta");
        p = new JLabel("Puntos: " + ((Integer) u.getPuntos()).toString());

        //Extraemos info de Tienda
        map = new HashMap<String,Object>();
        cl.enviar("/infoTienda",map);
        response = (HashMap<String, Object>) map.get("Respuesta");

        //paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Configuracion ComboBox
        for(Integer i=0;i<response.size();i++)
        {
            Producto p = (Producto) response.get("Respuesta");

        }


        //Norte

        //Centro

        //Sur
        pnlSouth.add(comprar);
        pnlSouth.add(volver);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH,MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        comprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JPerfil(usuario);
            }
        });
    }

    public static void main(String[] args) {
        new Tienda("default");
    }

}