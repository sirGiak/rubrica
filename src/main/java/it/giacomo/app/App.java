package it.giacomo.app;

import it.giacomo.app.GUI.RubricaGUI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class App {
    public static void main(String[] args) {
        Singleton singleton=Singleton.getInstance();
        singleton.serviceRubrica.caricaLista();
        RubricaGUI rubrica =new RubricaGUI("MyRubrica");
        rubrica.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                singleton.closeFile();
                System.exit(0);
            }
        });
    }
    
}
