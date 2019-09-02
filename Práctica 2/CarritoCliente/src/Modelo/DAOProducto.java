package Modelo;

import Controlador.Producto;
import Vista.Compras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class DAOProducto {
        
    Compras ventana = (Compras)null;
    
    public DAOProducto(){}
    
    public DAOProducto(ArrayList<Producto> productos,String host, int puerto){
          
         ventana = new Compras();
         ventana.setHostPuerto(host, puerto);
         insertarProductos(productos);
         ventana.setVisible(true);
    }
    
    /*Estos metodos no los usamos, sin embargo los necesitamos para que la clase Persona sea 
    igual a la del servidor*/
    
    public void insertarProducto(Producto p){  }
    
    public int actualizarProducto(Producto p, String id){return 0;}
    
    public ArrayList<Producto> getProductos(){ return null;  }
    
    public void insertarProductos(ArrayList<Producto> productos){
        
        ventana.jTable1.setModel(new DefaultTableModel());
        DefaultTableModel dtm = new DefaultTableModel();
        ventana.jTable1.setModel(dtm);
        int i;
        
        dtm.addColumn("id_producto");
        dtm.addColumn("nombre");
        dtm.addColumn("descripción");
        dtm.addColumn("marca");
        dtm.addColumn("precio");
        dtm.addColumn("cantidad");
        dtm.addColumn("direccion"); 
        
        for(i = 0; i < productos.size(); i++){
            Object []fila = new Object[7];//número de columnas
            fila[0] = productos.get(i).getId();
            fila[1] = productos.get(i).getNombre();
            fila[2] = productos.get(i).getDescripcion();
            fila[3] = productos.get(i).getMarca();
            fila[4] = productos.get(i).getPrecio();
            fila[5] = productos.get(i).getCantidad();
            fila[6] = productos.get(i).getDireccion();
            dtm.addRow(fila);
        }
        
        //ocultamos la última columna estableciendo su ancho a 0
        ventana.jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
        ventana.jTable1.getColumnModel().getColumn(6).setMinWidth(0);
        ventana.jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
        
    }
}
