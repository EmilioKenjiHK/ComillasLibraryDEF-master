package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Customer;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import util.SpringUtilities;

/**
 * Provides the user interface for the user to log in into the application
 */
public class JLogin extends JFrame {
    //Variables
    JTextField usuario0;
    JTextField password0;
    JLabel usuario1;
    JLabel password1;
    JLabel intro;
    JButton confirmar;
    JButton volver;

    public static int MAXWIDTH = 600;
    public static int MAXHEIGHT = 200;

    public static void main(String argv[]) {
        new JLogin();
    }

    /**
     * Constructor of JLogin class : creates the window with all its components
     */
    public JLogin() {
        super("ComillasLibrary: Login");

        //Instanciar variables
        usuario0 = new JTextField(50);
        usuario1 = new JLabel("Usuario: ",SwingConstants.CENTER);
        password0 = new JTextField(50);
        password1 = new JLabel("Password: ",SwingConstants.CENTER);
        confirmar = new JButton("Confirmar");
        volver = new JButton("Volver atras");
        intro = new JLabel("Introduzca vuestros datos, por favor.",SwingConstants.CENTER);
        Font fTexto = new Font("Arial", Font.PLAIN, 12);
        Font fTitulo = new Font("Arial",Font.BOLD, 25);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        usuario1.setFont(fTexto);
        password1.setFont(fTexto);
        intro.setFont(fTitulo);

        intro.setOpaque(true);
        intro.setBackground(Color.CYAN);

        //Norte
        pnlNorth.setLayout(new GridLayout(1,1));
        pnlNorth.add(intro);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Centro
        SpringLayout layout = new SpringLayout();
        pnlCenter.setLayout(layout);
        pnlCenter.add(usuario1);
        pnlCenter.add(usuario0);
        pnlCenter.add(password1);
        pnlCenter.add(password0);
        SpringUtilities.makeCompactGrid(pnlCenter,2,2,5,5,5,5);
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
            public void actionPerformed(ActionEvent e)
            {
                Client client = new Client();
                HashMap<String,Object> session = new HashMap<>();
                String u = usuario0.getText();
                String p = password0.getText();
                session.put("u",u);
                session.put("p",p);
                client.enviar("/loginUser",session);

                int resultado = (Integer) session.get("Respuesta");
                //System.out.println(resultado);
                if(resultado == 1) // Usuario
                {
                    new JOpciones(u);
                    dispose();
                }
                else if(resultado == 2) // Administrador
                {
                    new JAdminMenu(u);
                    dispose();
                }
                else //Username/Password incorrecto
                {
                    password0.setText("");
                    JInfoBox.infoBox("Error","Error: Login o Contrasenya incorrecta.");

                }
            }
        });

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new JMenu();
            }
        });
    }
}
