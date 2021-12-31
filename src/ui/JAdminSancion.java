package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Sancion;
import util.JInfoBox;
import util.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JAdminSancion extends JFrame {

    //Constantes
    public ArrayList<Integer> LIMITES = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));


    //Variables
    JLabel info;
    JTextField usuario;
    JTextField razon;
    JComboBox limite;
    JLabel u;
    JLabel r;
    JLabel l;
    JButton sancionar;
    JButton volver;

    Client client = new Client();
    HashMap<String,Object> session = new HashMap<>();

    public static int MAXWIDTH = 800;
    public static int MAXHEIGHT = 300;

    public void main(String argv[]){new JAdminSancion("Admin");}

    public JAdminSancion(String administrador)
    {
        super("ComillasLibrary: Sancion");

        //Instanciar variables
        info = new JLabel("Sancionar Usuario", SwingConstants.CENTER);
        usuario = new JTextField(50);
        razon = new JTextField(50);
        limite = new JComboBox();
        u = new JLabel("Usuario:");
        r = new JLabel("Razon (Opcional):");
        l = new JLabel("Limite sin poder Reservas (horas):");
        sancionar = new JButton("Sancionar usuario");
        volver = new JButton("Volver a opciones");
        Font fTitulo = new Font("Arial",Font.BOLD, 25);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        info.setFont(fTitulo);

        info.setOpaque(true);
        info.setBackground(Color.CYAN);

        //Configuracion ComboBoxes
        limite.addItem("");

        //Limites
        for(int i = 0; i<LIMITES.size()-1;i++)
        {
            limite.addItem(LIMITES.get(i));
        }

        //Norte
        pnlNorth.setLayout(new GridLayout(1,1));
        pnlNorth.add(info);
        this.add(pnlNorth,BorderLayout.NORTH);

        //Centro
        SpringLayout layout = new SpringLayout();
        pnlCenter.setLayout(layout);
        pnlCenter.add(u);
        pnlCenter.add(usuario);
        pnlCenter.add(r);
        pnlCenter.add(razon);
        pnlCenter.add(l);
        pnlCenter.add(limite);
        SpringUtilities.makeCompactGrid(pnlCenter,3,2,5,5,5,5);
        this.add(pnlCenter,BorderLayout.CENTER);

        //Sur
        pnlSouth.add(sancionar);
        pnlSouth.add(volver);
        this.add(pnlSouth,BorderLayout.SOUTH);

        //Ventana
        this.setSize(MAXWIDTH, MAXHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones

        sancionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(usuario.getText().equals("") || limite.getSelectedItem().equals("")) // Campo Vacio ---> OJO: Razon puede estar vacio
                {
                    JInfoBox.infoBox("Error","Error: Debe rellenar toda la informacion");
                }
                else {
                    session.put("u", usuario.getText());
                    client.enviar("/checkUsuario", session); // Debemos comprobar que Usuario ha sido bien insertado
                    Boolean b = (Boolean) session.get("Respuesta");
                    if (b) {
                        String s0 = razon.getText().replace(" ","");
                        if(s0.equals(""))
                        {
                            razon.setText("Razon Desconocido");
                        }
                        Sancion s = new Sancion(razon.getText(),(Integer) limite.getSelectedItem());
                        session = new HashMap<String,Object>();
                        session.put("u",usuario.getText());
                        session.put("s", s);
                        client.enviar("/setSancion",session);
                        JInfoBox.infoBox("Aviso", "Sancion implementado correctamente.");
                        dispose();
                        new JAdminMenu(administrador);

                    } else {
                        JInfoBox.infoBox("Error", "Error: Usuario Desconocido. Comprueba que ha puesto bien el Usuario.");
                    }
                }

            }
        });

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JAdminMenu(administrador);
            }
        });

    }
}
