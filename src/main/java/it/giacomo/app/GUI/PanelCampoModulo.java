package it.giacomo.app.GUI;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelCampoModulo extends JPanel {
    private JLabel label;
    private JTextField textField;
    public PanelCampoModulo(String nomeCampo, String valore){
        label=new JLabel(nomeCampo, SwingConstants.CENTER);
        textField=new JTextField(valore);
        init();
    }
    public PanelCampoModulo(String nomeCampo){
        label=new JLabel(nomeCampo, SwingConstants.CENTER);
        textField=new JTextField();
        init();
    }
    private void init(){
        
        setBackground(Colori.getInstance().primaryColor);
        setLayout(new GridLayout(1, 2, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(label);
        add(textField);
    }
    public String getValore(){
        return textField.getText();
    }

    public void setValore(String s){
        textField.setText(s);
    }
    
}
