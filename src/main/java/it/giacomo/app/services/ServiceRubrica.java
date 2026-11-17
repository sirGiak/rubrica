package it.giacomo.app.services;

import java.util.Scanner;
import java.util.Vector;

import it.giacomo.app.Singleton;
import it.giacomo.app.Utils;
import it.giacomo.app.models.Persona;

public class ServiceRubrica {
    private Vector<Persona> listaPersone;

    public ServiceRubrica(){
        this.listaPersone= new Vector<>();
    }

    public void aggiungi(Persona p){
		listaPersone.add(p);
	}
	public void elimina(int index){
		listaPersone.remove(index);
	}    
    public void elimina(String telefono){ //Elimina persona su lista e su file 
        Persona p;
        Singleton singleton=Singleton.getInstance();
        singleton.svuotaFile();
        for(int i=0; i<listaPersone.size(); i++){
            if (listaPersone.get(i).getTelefono().equals(telefono)) {
                p=listaPersone.remove(i);
                System.out.println("Persona eliminata: "+p.toString());
            }else{
                Utils.println(listaPersone.get(i).toString(), singleton.printWriter);
            }
        }
    }
    public void caricaLista(){
        Scanner scanner= Singleton.getInstance().scanner;
        String stringPersona="";
        String[] arrayString=null;

        if (scanner!=null) {
            while (scanner.hasNextLine()) {
                stringPersona=scanner.nextLine();
                arrayString=stringPersona.split(";");
                int eta=0;
                try {
                    eta=Integer.parseInt(arrayString[4]);
                } catch (NumberFormatException e) {
                    System.err.println("Il seguente campo 'Età' non è un numero intero: "+arrayString[4]+". Valore dell'età sostituito con 0, del contatto: "+arrayString[3]+".");
                }
                aggiungi(new Persona(arrayString[0],arrayString[1],arrayString[2],arrayString[3],eta));
            }
            scanner.close();
            System.out.println("Lista delle persone caricata. Persone inserite: "+listaPersone.size());
        }
    }
    public int getSize(){
        return listaPersone.size();
    }
    public Vector<Persona> getLista(){
        return listaPersone;
    }
    public void updateOrAddPersona(Persona oldPersona, Persona newPersona){ //Aggiorna lista e file (il file viene riscritto da 0 tramite la lista)
        int i=0;
        boolean aggiornato=false;
        Singleton singleton=Singleton.getInstance();

        singleton.svuotaFile();

        for(i=0; i<listaPersone.size(); i++){
            if (oldPersona.getTelefono().equals(listaPersone.get(i).getTelefono())) {
                //UPDATE Persona
                listaPersone.set(i, newPersona);
                Utils.println(newPersona.toString(), singleton.printWriter);
                aggiornato=true;
                System.out.println("Modifica di: "+oldPersona.toString()+" - in: "+newPersona.toString());
            }else{
                Utils.println(listaPersone.get(i).toString(), singleton.printWriter);
            }
        }
        if (!aggiornato){
            //CREATE Persona
            listaPersone.add(newPersona);
            Utils.println(newPersona.toString(),singleton.printWriter);
            System.out.println("Nuova persona aggiunta: "+ newPersona.toString());
        }
    }
    public Persona getPersona(String telefono){
        int i=0;
        boolean trovato=false;
        Persona persona=new Persona();
        while (i<listaPersone.size() && !trovato) {
            if (listaPersone.get(i).getTelefono().equals(telefono)) {
                persona=listaPersone.get(i);
                trovato=true;
            }else{
                i++;
            }
        }
        return persona;
    }
}
