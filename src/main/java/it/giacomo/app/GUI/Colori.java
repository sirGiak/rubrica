package it.giacomo.app.GUI;

import java.awt.Color;


public class Colori {
    private static Colori instance;
    public Color primaryColor, secondaryColor, thirdColor;

    private Colori() {
        primaryColor=new Color(191,235,178);
        secondaryColor=new Color(255,255,240);
        thirdColor=new Color(0,160,0);
    }
    public static Colori getInstance(){
        if(instance == null) {
            instance = new Colori();
        }
        return instance;
    }

}
