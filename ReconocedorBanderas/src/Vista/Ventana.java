package Vista;

import Modelo.Bandera;

import javax.swing.*;

public class Ventana extends JFrame implements ViewInterface{
    private JLabel label;

    public Ventana(){
        Bandera b = new Bandera("Banderas\\Baleares.png");
        label.setIcon(b.GetImagen());
        add(label);
        pack();
        setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void abrirVentana() {
        setVisible(true);
    }
}