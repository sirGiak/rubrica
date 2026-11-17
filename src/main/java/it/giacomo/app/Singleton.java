package it.giacomo.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import it.giacomo.app.services.ServiceRubrica;

public class Singleton {
    private static Singleton instance;
    public Scanner scanner=null; 
    private FileWriter fileWriter=null;
    public PrintWriter printWriter=null;
    private File file=null;
    public ServiceRubrica serviceRubrica=null;

    private Singleton() {
        serviceRubrica=new ServiceRubrica();
        String nomeFile="informazioniRubrica.txt";
        file=new File(System.getProperty("user.home"),nomeFile);
        System.out.println("Cartella file di memoria: "+System.getProperty("user.home")+" - Nome file: "+nomeFile);
        try{
            scanner= new Scanner(file);
            fileWriter=new FileWriter(file, true); 
            printWriter=new PrintWriter(fileWriter);
        }catch(FileNotFoundException fe){
            try {
                fileWriter=new FileWriter(file, true);  
                printWriter=new PrintWriter(fileWriter);
                scanner=null;
            } catch (Exception e) {
                System.err.println("Creazione file: "+e);
                System.exit(1);
            }
            System.out.println("File di memoria creato");
        }
        catch(Exception e){
            System.err.println("Apertura file di memoria "+e);
            System.exit(1);
        }    
    
        System.out.println("File di memoria aperto correttamente");

    }
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    public void closeFile(){
        try {
            printWriter.close();
            fileWriter.close();
            scanner.close();
            System.out.println("File di memoria chiuso correttamente");
        } catch (Exception e) {
            System.err.println("Chiusura file: "+e);
            System.exit(1);
        }
    }
    public void svuotaFile(){
        try {
            printWriter.close();
            fileWriter.close();
            fileWriter=new FileWriter(file,false);//false sovrascrive il file
            printWriter= new PrintWriter(fileWriter);

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
