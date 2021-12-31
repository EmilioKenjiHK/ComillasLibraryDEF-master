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
import util.SpringUtilities;

/**
 * In this page, the user can specify the global information for his reservation
 */
public class JReservaInformacion extends JFrame {

    //Constantes
    public static ArrayList<String> HORAS = new ArrayList<String>(Arrays.asList("08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00"));

    //Variables
    JComboBox biblioteca;
    JComboBox planta;
    JComboBox horaIn;
    JComboBox horaFin;
    JButton buscar;
    JButton grupo;
    JButton salir;
    // biblioteca
    JLabel b;
    // planta
    JLabel p;
    //horario inicial
    JLabel hi;
    //horario final
    JLabel hf;
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
        new JReservaInformacion("default");
    }

    /**
     * Contructor of JReservaInformacion
     * @param usuario the current user
     */
    public JReservaInformacion(String usuario)
    {
        super("ComillasLibrary: Reservacion (Informacion)");

        //Instanciar variables
        biblioteca = new JComboBox();
        planta = new JComboBox();
        horaIn = new JComboBox();
        horaFin = new JComboBox();
        buscar = new JButton("Buscar Mesas");
        grupo = new JButton("Reservar como Grupo");
        salir = new JButton("Volver a Opciones");
        b = new JLabel("Biblioteca:", SwingConstants.CENTER);
        p = new JLabel("Plantas Libres:", SwingConstants.CENTER);
        hi = new JLabel("Horario Inicial:", SwingConstants.CENTER);
        hf = new JLabel("Horario Final:", SwingConstants.CENTER);
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
        hi.setFont(fTitulo);
        hf.setFont(fTitulo);
        intro.setFont(fTexto);

        intro.setOpaque(true);
        intro.setBackground(Color.CYAN);

        //Configuracion ComboBoxes
        //Valores nulos
        biblioteca.addItem("");
        planta.addItem("");
        horaIn.addItem("");
        horaFin.addItem("");

        //biblioteca
        client.enviar("/getBibliotecas",session);
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
                client.enviar("/getPlantas",session);
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

        //Hora inicio
        for(int i = 0; i<HORAS.size()-1;i++)
        {
            horaIn.addItem(HORAS.get(i));
        }

        //Hora fin

        horaIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch(horaFin.getItemCount()) {
                    case 1:
                        System.out.println("No hay valores guardados");
                        break;

                    default:
                        horaFin.removeAllItems();
                        horaFin.addItem("");
                        break;
                }

                int index = HORAS.indexOf(horaIn.getSelectedItem());
                if(index < HORAS.size()-4)
                {
                    for(int j = 1; j < 5; j++) // Siempre 4 opciones
                    {
                        horaFin.addItem(HORAS.get(index+j));
                    }
                }
                else
                {
                    for(int j = index+1; j < HORAS.size();j++)
                    {
                        System.out.println(j);
                        horaFin.addItem(HORAS.get(j));
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
        pnlCenter.add(hi);
        pnlCenter.add(horaIn);
        pnlCenter.add(hf);
        pnlCenter.add(horaFin);
        SpringUtilities.makeCompactGrid(pnlCenter,4,2,5,5,5,5);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(buscar);
        pnlSouth.add(grupo);
        pnlSouth.add(salir);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);




        //Funciones botones
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(biblioteca.getSelectedItem().equals("") || planta.getSelectedItem().equals("") || horaIn.getSelectedItem().equals("") || horaFin.getSelectedItem().equals("") )
                {
                    JInfoBox.infoBox("Error","Error: Debe rellenar toda la informacion");
                }
                else {
                    dispose();
                    ArrayList<String> valores = new ArrayList<String>();
                    valores.add((String) biblioteca.getSelectedItem());
                    valores.add((String) planta.getSelectedItem());
                    valores.add((String) horaIn.getSelectedItem());
                    valores.add((String) horaFin.getSelectedItem());
                    new JReservaMesa(usuario, valores);
                }
            }
        });

        grupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(biblioteca.getSelectedItem().equals("") || planta.getSelectedItem().equals("") || horaIn.getSelectedItem().equals("") || horaFin.getSelectedItem().equals("") )
                {
                    JInfoBox.infoBox("Error","Error: Debe rellenar toda la informacion");
                }
                else {
                    dispose();
                    ArrayList<String> valores = new ArrayList<String>();
                    valores.add((String) biblioteca.getSelectedItem());
                    valores.add((String) planta.getSelectedItem());
                    valores.add((String) horaIn.getSelectedItem());
                    valores.add((String) horaFin.getSelectedItem());
                    JInfoBox.infoBox("Aviso", "Puedes rellenar entre 1-4 mesas (1 para cada persona).");
                    new JReservaGrupo(usuario, valores);
                }
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JOpciones(usuario);
            }
        });
    }
}
