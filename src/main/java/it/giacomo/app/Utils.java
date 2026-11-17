package it.giacomo.app;

import java.io.PrintWriter;
import java.util.Vector;

public class Utils {
    public static boolean println(String string, PrintWriter printWriter){
        try {
            printWriter.println(string);
            printWriter.flush();
            return true;
        } catch (Exception e) {
            System.err.println("scrittura su file: " +e);
            return false;
        }
    }

    public static Vector<String> getVectorColumnNames(){
        Vector <String> v=new Vector<>();
        v.add("Nome");
        v.add("Cognome");
        v.add("Telefono");
        return v;
    }

    public static String pulisciString(String s){
        if (s.length()>200)
            s=s.substring(0, 200);

        return s==null?"":s.replaceAll(";", ""); //vengono tolti eventuali separatori ( in questo caso il ;)

    }
    
  
}
