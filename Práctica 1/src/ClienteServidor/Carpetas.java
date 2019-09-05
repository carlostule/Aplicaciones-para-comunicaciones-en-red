package ClienteServidor;

import java.io.*;
import java.util.*;

/**
 *
 * @author Carlos Tule
 */

public class Carpetas implements Serializable {
    
    public File ruta;
    public Vector<Carpetas> hijos;
    
    public Carpetas(String raiz){
        ruta  = new File(raiz);
        hijos = new Vector();
    }
    
    public void setHijo(String hijo){
        Carpetas aux = new Carpetas(hijo);
        hijos.add(aux);
    }
    
    public Vector<Carpetas> getHijos(){
        return hijos;
    }
    
    public File getRuta(){
        return ruta;
    }
}
