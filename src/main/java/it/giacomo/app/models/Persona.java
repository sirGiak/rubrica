package it.giacomo.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter  @AllArgsConstructor
public class Persona {
    String nome;
    String cognome;
    String indirizzo;
    String telefono;
    int eta;

    public Persona(){
        nome=cognome=indirizzo=telefono="";
        eta=0;
    }
    public String toString(){
        return nome+";"+cognome+";"+indirizzo+";"+telefono+";"+eta;
    } 
}
