package ui;

import dtc.isw.client.Client;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dtc.isw.domain.*;
import io.IOImagen;
import util.SpringUtilities;

/**
 * In this page, the admnistrator can change the map of a chosen library and level
 */

public class JAdminMapas extends JFrame{

    //Variables
    JComboBox biblioteca;
    JComboBox planta;
    JButton mapa;
    JButton confirmar;
    JButton salir;
    // biblioteca
    JLabel b;
    // planta
    JLabel p;
    //mapa
    JLabel m;
    //introducir datos
    JLabel intro;

    Client client = new Client();
    HashMap<String,Object> session = new HashMap<>();

    // Hashmap donde pondremos el resultado de la request a la base de datos
    HashMap<String,Object> h = new HashMap<String,Object>();

    ArrayList<Object> noRepeat = new ArrayList<Object>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 300;

    public void main(String argv[])
    {
        new JAdminMapas("Admin");
    }

    /**
     * Contructor of JAdminMapas
     */
    public JAdminMapas(String administrador)
    {
        super("ComillasLibrary: Actualizar Mapa");

        //Instanciar variables
        biblioteca = new JComboBox();
        planta = new JComboBox();
        mapa = new JButton("Nuevo Mapa");
        confirmar = new JButton("Confirmar actualizacion de Mapa");
        salir = new JButton("Volver a Opciones");
        b = new JLabel("Biblioteca:", SwingConstants.CENTER);
        p = new JLabel("Planta:", SwingConstants.CENTER);
        m = new JLabel("", SwingConstants.CENTER);
        intro = new JLabel("Introduzca los datos, por favor.",SwingConstants.CENTER);
        Font fTexto = new Font("Arial", Font.BOLD, 12);
        Font fTitulo = new Font("Arial",Font.BOLD, 25);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        b.setFont(fTitulo);
        p.setFont(fTitulo);
        m.setFont(fTitulo);
        intro.setFont(fTexto);

        intro.setOpaque(true);
        intro.setBackground(Color.CYAN);

        //Configuracion ComboBoxes
        //Valores nulos
        biblioteca.addItem("");
        planta.addItem("");

        //biblioteca
        client.enviar("/infoBibliotecas",session);
        h = (HashMap<String, Object>) session.get("Respuesta");
        ArrayList<Object> noRepeat = new ArrayList<Object>();
        for(Integer i=0;i<h.size();i++)
        {
            Biblioteca bb = (Biblioteca) h.get(i.toString());
            if(!noRepeat.contains(bb.getNombre()))
            {
                biblioteca.addItem(bb.getNombre());
                noRepeat.add(bb.getNombre());
            }
        }

        //planta
        //planta.addItem("3ª Planta");
        //planta.addItem("4ª Planta");
        //planta.addItem("5ª Planta");

        biblioteca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Previous options eliminated and replaced
                switch(planta.getItemCount())
                {
                    case 1:
                        System.out.println("No hay valores guardados");
                        break;

                    default:
                        planta.removeAllItems();
                        planta.addItem("");
                        break;
                }

                session = new HashMap<String,Object>();
                session.put("b",biblioteca.getSelectedItem());
                client.enviar("/infoPlantas",session);
                h = (HashMap<String,Object>) session.get("Respuesta");
                ArrayList<Object> noRepeat = new ArrayList<Object>();
                for(Integer i = 0; i< h.size();i++)
                {
                    Planta pp = (Planta) h.get(i.toString());
                    if(!noRepeat.contains(pp.getNombre()))
                    {
                        planta.addItem(pp.getNombre());
                        noRepeat.add(pp.getNombre());
                    }
                }
            }
        });


        //Centro
        SpringLayout layout = new SpringLayout();
        pnlCenter.setLayout(layout);
        pnlCenter.add(b);
        pnlCenter.add(biblioteca);
        pnlCenter.add(p);
        pnlCenter.add(planta);
        pnlCenter.add(mapa);
        pnlCenter.add(m);
        SpringUtilities.makeCompactGrid(pnlCenter,3,2,5,5,5,5);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(confirmar);
        pnlSouth.add(salir);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);




        //Funciones botones
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(biblioteca.getSelectedItem().equals("") || planta.getSelectedItem().equals("") || m.getText().equals(""))
                {
                    JInfoBox.infoBox("Error","Error: Debe rellenar todo los campos para aplicar el cambio");
                }
                else
                {
                    File f = new File(m.getText());
                    String direccion = "src/Recursos/Bibliotecas/"+biblioteca.getSelectedItem()+"/"+planta.getSelectedItem()+".png";
                    IOImagen.setImagen(f,direccion);
                    JInfoBox.infoBox("Aviso","Mapa actualizado.");
                    dispose();
                    new JAdminMenu(administrador);
                }
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminMenu(administrador);
            }
        });

        mapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInfoBox.infoBox("Aviso","No hemos podido hacer que pueda extraer un documento de nuestros archivos. Dejamos un ejemplo para comprobar su funcionalidad (se recomienda aplicar en biblioteca test y planta test).");
                m.setText("src/Recursos/Ejemplo.png");
            }
        });
    }
}
