package it.giacomo.app.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import it.giacomo.app.Singleton;
import it.giacomo.app.Utils;
import it.giacomo.app.models.Persona;
import it.giacomo.app.services.ServiceRubrica;

public class EditorPersonaGUI extends JFrame implements ActionListener {
    private JPanel panel, panelBottoni;
    private PanelCampoModulo panelNome, panelCognome, panelIndirizzo, panelTelefono, panelEta;
    private JButton buttonSalva, buttonAnnulla;
    private ServiceRubrica serviceRubrica=Singleton.getInstance().serviceRubrica;
    private Persona persona;
    private DefaultTableModel tableModel;
    private int rigaSelezionata;

    public EditorPersonaGUI(String titolo, DefaultTableModel tableModel){
        init(titolo, tableModel, -1);//Per aggiungere un contatto
    }
    public EditorPersonaGUI(String titolo, DefaultTableModel tableModel, int rigaSelezionata){
        init(titolo, tableModel, rigaSelezionata);//Per modificare un contatto
    }

    public void init(String titolo, DefaultTableModel tableModel, int rigaSelezionata){
        setTitle(titolo);
        setSize(290,340);
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getContentPane().add(panel);

        this.persona=new Persona();
        if (rigaSelezionata>=0) {
            persona=serviceRubrica.getPersona((String)tableModel.getValueAt(rigaSelezionata, 2));
        }

        panelNome= new PanelCampoModulo("Nome", persona.getNome());
        panel.add(panelNome);
        panelCognome= new PanelCampoModulo("Cognome", persona.getCognome());
        panel.add(panelCognome);
        panelIndirizzo= new PanelCampoModulo("Indirizzo", persona.getIndirizzo());
        panel.add(panelIndirizzo);
        panelTelefono= new PanelCampoModulo("Telefono", persona.getTelefono());
        panel.add(panelTelefono);
        panelEta= new PanelCampoModulo("Eta", String.valueOf(persona.getEta()));
        panel.add(panelEta);

        panelBottoni= new JPanel();
        panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER, 20,5));
        getContentPane().add(panelBottoni);
        
        buttonSalva=new JButton("Salva");
        buttonSalva.addActionListener(this);
        panelBottoni.add(buttonSalva);

        buttonAnnulla=new JButton("Annulla");
        buttonAnnulla.addActionListener(this);
        panelBottoni.add(buttonAnnulla); 

        this.tableModel=tableModel;
        this.rigaSelezionata=rigaSelezionata;

        setStyle();
    }

    public void setStyle(){
        panel.setBackground(Colori.getInstance().primaryColor);
        panelBottoni.setBackground(Colori.getInstance().primaryColor);
        buttonSalva.setBackground(Colori.getInstance().secondaryColor);
        buttonAnnulla.setBackground(Colori.getInstance().secondaryColor);
    }
    public void pulisciInput(){
        panelNome.setValore(Utils.pulisciString(panelNome.getValore()));
        panelCognome.setValore(Utils.pulisciString(panelCognome.getValore()));
        panelIndirizzo.setValore(Utils.pulisciString(panelIndirizzo.getValore()));
        panelTelefono.setValore(Utils.pulisciString(panelTelefono.getValore()));
        panelEta.setValore(Utils.pulisciString(panelEta.getValore()));

    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        String e= evt.getActionCommand();
        if (e.equals("Salva")){
            pulisciInput();
            if (!panelTelefono.getValore().equals("")) {
                int newEta=0;
                try {
                    newEta= Integer.parseInt(panelEta.getValore());
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(this, "L'età inserità non è un numero intero. Età impostata a 0");
                    System.out.println("Valore età non è un numero intero. Età impostata a 0");
                }
                
                Persona newPersona= new Persona(
                    panelNome.getValore(),
                    panelCognome.getValore(),
                    panelIndirizzo.getValore(),
                    panelTelefono.getValore(),
                    newEta
                );

                serviceRubrica.updateOrAddPersona(persona, newPersona);
                if (rigaSelezionata>=0) { //Ovvero se è in modalità modifica elimina la riga selezionata
                    tableModel.removeRow(rigaSelezionata);                
                }
                tableModel.addRow(new Object[]{newPersona.getNome(), newPersona.getCognome(), newPersona.getTelefono()}); //nuova riga

                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Inserisci numero di telefono!");
            }
        }
        if (e.equals("Annulla")) {
            dispose();
        }
    }   
}
