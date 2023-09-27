package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MesuresImperiales.MesuresImperiales ;

public class FenetrePrincipale {
    private JTextField textField1;
    private JTextField textField2;
    private JPanel fenetre;
    private JLabel labelImperial;
    private JLabel titre;
    private JLabel erreurImperial;
    private JLabel erreurDouble;
    private JComboBox precisionBox;

    private int precision = 128 ;

    public FenetrePrincipale() {
        erreurImperial.setVisible(false);
        erreurDouble.setVisible(false);


        textField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saisie = textField2.getText() ;

                erreurImperial.setVisible(false);
                erreurDouble.setVisible(false);

                try {
                    MesuresImperiales nombre = MesuresImperiales.value_of(saisie) ;
                    double resultat = nombre.to_double() ;
                    System.out.print(resultat);
                    textField1.setText(String.valueOf(resultat));
                }
                catch (NumberFormatException exc) {
                    erreurImperial.setVisible(true);
                    textField2.setText("");
                    textField1.setText("");
                }
            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saisie = textField1.getText() ;

                erreurImperial.setVisible(false);
                erreurDouble.setVisible(false);

                try {
                    double nombre = Double.parseDouble(saisie) ;
                    MesuresImperiales resultat = new MesuresImperiales(nombre, precision) ;
                    textField2.setText(resultat.to_string());
                }
                catch (NumberFormatException exc) {
                    erreurDouble.setVisible(true);
                    textField2.setText("");
                    textField1.setText("");
                }
            }
        });

        precisionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String precisionString = (String) precisionBox.getSelectedItem();
                if (precisionString == null) return ;
                precision = Integer.parseInt(precisionString) ;
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Convertisseur") ;
        frame.setContentPane(new FenetrePrincipale().fenetre);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
