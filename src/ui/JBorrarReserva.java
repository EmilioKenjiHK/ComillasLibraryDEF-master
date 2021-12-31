package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.client.Client;
import dtc.isw.domain.*;
import util.JInfoBox;
import util.SpringUtilities;

public class JBorrarReserva extends JFrame {
    JLabel titulo;
    JComboBox reservas;
    JButton confirmar;
    JButton volver;
    int id;

    Client client = new Client();
    HashMap<String,Object> session = new HashMap<String,Object>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 200;

    public void main(String argv[])
    {
        new JBorrarReserva("default");
    }

    public JBorrarReserva(String usuario)
    {
        super("ComillasLibrary: Borrar Reserva");

        //Instanciar variables
        titulo = new JLabel("Reservas guardadas del usuario "+usuario, SwingConstants.CENTER);
        reservas = new JComboBox();
        confirmar = new JButton("Borrar Reserva Elegida");
        volver = new JButton("Volver a Opciones");
        Font fTexto = new Font("Arial", Font.BOLD, 12);
        Font fTitulo = new Font("Arial",Font.BOLD, 25);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        titulo.setFont(fTitulo);
        reservas.setFont(fTexto);

        //Configuracion ComboBoxes
        //Valores nulos
        reservas.addItem("");

        //Reserva
        session.put("u",usuario);
        client.enviar("/getReservas",session);
        HashMap<String,Object> h = (HashMap<String, Object>) session.get("Respuesta");

        for(Integer i=0;i<h.size();i++)
        {
            Reserva r = (Reserva) h.get(i.toString());
            reservas.addItem(r);
        }

        reservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(reservas.getSelectedItem().equals(""))
                {
                    id = 0;
                }
                else {
                    Reserva rv = (Reserva) reservas.getSelectedItem();
                    id = rv.getIdreserva();
                }
            }
        });

        //Norte
        pnlNorth.setLayout(new GridLayout(1,1));
        pnlNorth.add(titulo);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Centro
        SpringLayout layout = new SpringLayout();
        pnlCenter.setLayout(layout);
        pnlCenter.add(reservas);
        SpringUtilities.makeCompactGrid(pnlCenter,1,1,5,5,5,5);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(confirmar);
        pnlSouth.add(volver);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(reservas.getSelectedItem().equals(""))
                {
                    JInfoBox.infoBox("Error", "No ha elegido una reserva a borrar");
                }
                else
                {
                    session = new HashMap<String,Object>();
                    session.put("id",id);
                    client.enviar("/borrarReserva",session);
                    client.enviar("/liberarAsiento",session);
                    JInfoBox.infoBox("Aviso", "Se ha borrado la reserva elegida");
                    dispose();
                    new JOpciones(usuario);
                }
            }
        });

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JOpciones(usuario);
            }
        });

    }
}
