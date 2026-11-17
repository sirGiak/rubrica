package it.giacomo.app.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfermaGUI extends JFrame implements ActionListener {
    private JPanel panel, panelBottoni;
    private JLabel label;
    private JButton buttonConferma, buttonAnnulla;
    public Azione azione;
    public ConfermaGUI(String titolo, String messaggio, String textButtonA, String textButtonB, Azione azione){
        setTitle(titolo);

        panel=new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        label=new JLabel(messaggio, SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        panelBottoni= new JPanel();
        panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER,30,15));
        panel.add(panelBottoni, BorderLayout.SOUTH);

        buttonConferma=new JButton(textButtonA);
        panelBottoni.add(buttonConferma);
        buttonConferma.addActionListener(this);
        buttonAnnulla=new JButton(textButtonB);
        panelBottoni.add(buttonAnnulla);
        buttonAnnulla.addActionListener(this);
        
        this.azione=azione;

        setSize(400,130);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setStyle();
    }

    public void setStyle(){
        panel.setBackground(Colori.getInstance().primaryColor);

        panelBottoni.setBackground(Colori.getInstance().primaryColor);

        buttonConferma.setBackground(Colori.getInstance().secondaryColor);
        buttonAnnulla.setBackground(Colori.getInstance().secondaryColor);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String e = evt.getActionCommand();
        azione.esegui(e);
    }

}
