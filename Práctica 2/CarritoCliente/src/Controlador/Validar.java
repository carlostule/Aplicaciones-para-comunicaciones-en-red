package Controlador;

import javax.swing.JOptionPane;


public class Validar {
    
    public Validar(){}
    
    public int ValidarNumero(String numero){
        
        int num = -105;
        try{
            num = Integer.parseInt(numero);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ingresa un número");
        }
        return num;
        
    }
    
    public int ValidarSigno(int num){
        if(num <= 0){
            JOptionPane.showMessageDialog(null, "Ingresa un número positivo o mayor a cero");
            return 0;
        }
        return 1;
    }
    
    public int ValidarCantidad(int cantidadR, int cantidadS){
        
        if(cantidadR > cantidadS){
            JOptionPane.showMessageDialog(null, "La cantidad pedida debe ser menor o igual a la existente");
            return 0;
        }
        return 1;
    }
    
}
