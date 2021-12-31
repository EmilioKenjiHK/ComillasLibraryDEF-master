package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Producto;
import dtc.isw.domain.Usuario;
import util.JInfoBox;
import util.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        p = new JLabel("Objeto a comprar (Tienes " + ((Integer) u.getPuntos()).toString() + " puntos) :");

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
            Producto p = (Producto) response.get(i.toString());
            seleccionar.addItem(p);
        }

        //Norte
        pnlNorth.add(p);
        pnlNorth.add(seleccionar);
        this.add(pnlNorth,BorderLayout.NORTH);

        //Centro
        SpringLayout layout = new SpringLayout();
        pnlCenter.setLayout(layout);
        int numRows = 0;
        for(Integer i=0;i<response.size();i++)
        {
            Producto p = (Producto) response.get(i.toString());
            JLabel l = new JLabel(p.toString());
            pnlCenter.add(l);
            numRows+=1;
        }
        SpringUtilities.makeCompactGrid(pnlCenter,numRows,1,5,5,5,5);
        this.add(pnlCenter,BorderLayout.CENTER);


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
                //Comprobar que tiene suficientes puntos
                Producto p = (Producto) seleccionar.getSelectedItem();
                if(p.getPrecio() > u.getPuntos())
                {
                    JInfoBox.infoBox("Error","Error: No tienes los puntos para comprar el objeto");
                }
                else{
                    HashMap<String,Object> session = new HashMap<String,Object>();
                    // Restar puntos del usuario
                     session.put("u",usuario);
                     int i = u.getPuntos() - p.getPrecio();
                     session.put("p",i);
                     cl.enviar("/updatePuntos",session);

                    // Añadir a base de datos de producto
                    session = new HashMap<String,Object>();
                    session.put("u",usuario);
                    session.put("p",p);
                    cl.enviar("/insertProducto",session);

                    // Actualizar tienda
                    session = new HashMap<>();
                    session.put("o",p.getObjeto());
                    session.put("c",p.getCantidad()-1);
                    cl.enviar("/updateTienda",session);
                    JInfoBox.infoBox("Aviso","Se ha comprado el objeto. Reclama el objeto en la recepcion");
                    dispose();
                    new Tienda(usuario);
                }

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