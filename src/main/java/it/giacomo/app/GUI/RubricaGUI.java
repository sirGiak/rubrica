package it.giacomo.app.GUI;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import it.giacomo.app.Singleton;
import it.giacomo.app.models.Persona;
import it.giacomo.app.services.ServiceRubrica;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RubricaGUI extends JFrame implements ActionListener{
    private ServiceRubrica serviceRubrica=Singleton.getInstance().serviceRubrica;
    private JTable tabella;
    private DefaultTableModel tableModel;
    private JPanel panel, panelBottoni;
    private JButton buttonModifica, buttonElimina, buttonNuovo;
    private EditorPersonaGUI editorPersonaGUI;
    private ConfermaGUI confermaGUI;

    public RubricaGUI(String titolo){
        setTitle(titolo);
        
        panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getContentPane().add(panel);
        setStylePanel();

        creaTabella();
        creaBottoni();

        setSize(800,500);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // Schermo intero
        setVisible(true);

        editorPersonaGUI=null;
        confermaGUI=null;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String e=evt.getActionCommand();
		if(e.equals("Nuovo")){
            if (editorPersonaGUI==null) {//si evitano di creare così più editor di testo contemporaneamente  
                editorPersonaGUI=new EditorPersonaGUI("Nuovo Contatto", tableModel);
                editorPersonaGUI.setLocationRelativeTo(getContentPane());
                editorPersonaGUI.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosed(WindowEvent e) {
                        editorPersonaGUI = null;
                    }
                });
            }
        }else{
            int RigaDaConvertire=tabella.getSelectedRow();
            if(RigaDaConvertire==-1){
                JOptionPane.showMessageDialog(this, "Seleziona una riga");
            }else{
                int riga=tabella.convertRowIndexToModel(RigaDaConvertire);//Conversione indice riga selezionata in base a come è ordinata la tabella 
            
                if(e.equals("Modifica")){
                    if (editorPersonaGUI==null) {
                        editorPersonaGUI= new EditorPersonaGUI("Modifica Contatto", tableModel, riga);
                        editorPersonaGUI.setLocationRelativeTo(getContentPane());
                        editorPersonaGUI.addWindowListener(new WindowAdapter() {
                            @Override 
                            public void windowClosed(WindowEvent e) {
                                editorPersonaGUI = null;
                            }
                        });
                    }
                }
        
                if (e.equals("Elimina")) {
                    String mes="Eliminare la persona "+tableModel.getValueAt(riga, 0)+" "+tableModel.getValueAt(riga, 1)+"?";
                    
                    confermaGUI=new ConfermaGUI("Attenzione", mes, "Sì", "No", new Azione() {
                        @Override 
                        public void esegui(String stringActionEvent){
                            if (stringActionEvent.equals("Sì")) {
                                serviceRubrica.elimina((String) tableModel.getValueAt(riga, 2));
                                tableModel.removeRow(riga);
                                confermaGUI.dispose();
                            }
                            if (stringActionEvent.equals("No")) {
                                confermaGUI.dispose();
                            }
                        }
                    });
                    confermaGUI.setLocationRelativeTo(getContentPane());
                }
            }
        }
    }

    private void riempiTabella(){
        for (Persona p : serviceRubrica.getLista()) {
            tableModel.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
    }
    private void creaTabella(){
        tableModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Telefono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // celle non modificabili con doppio click
            }
        };
        tabella = new JTable(tableModel);
        
        tabella.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabella.getModel());
        tabella.setRowSorter(sorter);
        // ordina per Nome (colonna 0) in modo crescente
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        riempiTabella();

        JScrollPane scrollPane=new JScrollPane(tabella);
        panel.add(scrollPane);   
        setStyleTabella();
    }
    private void creaBottoni(){
        panelBottoni=new JPanel();
        panelBottoni.setLayout(new BoxLayout(panelBottoni, BoxLayout.X_AXIS));

        buttonNuovo=new JButton("Nuovo");
        buttonNuovo.addActionListener(this);
        panelBottoni.add(buttonNuovo);
        
        panelBottoni.add(Box.createHorizontalGlue()); //Crea una spaziatura automatica tra due elementi
        
        buttonModifica=new JButton("Modifica");
        buttonModifica.addActionListener(this);
        panelBottoni.add(buttonModifica);

        panelBottoni.add(Box.createHorizontalGlue());

        buttonElimina=new JButton("Elimina");
        buttonElimina.addActionListener(this);
        panelBottoni.add(buttonElimina);

        panel.add(panelBottoni);
        setStyleBottoni();
    }

    private void setStylePanel(){
        panel.setBackground(Colori.getInstance().primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void setStyleTabella(){
        tabella.setBackground(Colori.getInstance().secondaryColor);
        tabella.setFont(tabella.getFont().deriveFont(Font.BOLD));
        tabella.getTableHeader().setFont(tabella.getTableHeader().getFont().deriveFont(Font.BOLD));
        tabella.getTableHeader().setForeground(Colori.getInstance().thirdColor);
        tabella.getTableHeader().setBackground(Colori.getInstance().primaryColor);
        tabella.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void setStyleBottoni(){
        panelBottoni.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBottoni.setBackground(Colori.getInstance().primaryColor);
        buttonNuovo.setBackground(Colori.getInstance().secondaryColor);
        buttonModifica.setBackground(Colori.getInstance().secondaryColor);
        buttonElimina.setBackground(Colori.getInstance().secondaryColor);
    }
}
