package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JConfirmacion extends JFrame {
    //Variables
    JLabel info;
    JButton si;
    JButton no;

    public void main(String argv[])
    {
        new JConfirmacion("null");
    }

    public JConfirmacion(String titulo)
    {
        super("Confirmacion");

        //Instanciar variables
        info = new JLabel(titulo);
        si = new JButton("Si");
        no = new JButton("No");

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();

        //Norte
        pnlNorth.add(info);
        this.add(pnlNorth,BorderLayout.NORTH);

        //Centro
        pnlCenter.add(si);
        pnlCenter.add(no);
        this.add(pnlCenter,BorderLayout.SOUTH);

        //Ventana
        this.setSize(200,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        si.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



    }
}
