package Modelo;

/**
 *
 * @author Carlos Tule
 */

import Controlador.Pedido;
import Controlador.Producto;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class DAOProducto {
        
    //obtenemos la dirección del proyecto
    String path = new File(" ").getAbsolutePath();
    
    /*  id,nombre,descripcion,marca,precio,cantidad,direccionImagen */
    ArrayList<String> P1 = new ArrayList(Arrays.asList("bala1","Balancín","Balancín para niño","acme","300.66","7",path.trim() + "balancin.jpg"));
    ArrayList<String> P2 = new ArrayList(Arrays.asList("bote2","Botella","Botella para vino","Desconocido","7.21","47",path.trim() + "botella.jpg"));
    ArrayList<String> P3 = new ArrayList(Arrays.asList("cam3","Cama","Cama matrimonial base de madera","Troncoso","1429.32","10",path.trim() + "cama.jpeg"));
    ArrayList<String> P4 = new ArrayList(Arrays.asList("cami4","Camisa","Camisa para hombre","Zara","129.87","20",path.trim() + "camisa.jpeg"));
    ArrayList<String> P5 = new ArrayList(Arrays.asList("cham5","Chamarra","Chamarra para hombre","Zara","429.99","12",path.trim() + "chamarra.jpeg"));
    ArrayList<String> P6 = new ArrayList(Arrays.asList("comp6","Computadora","Computadora de escritorio","Mac","17429.32","15",path.trim() + "computadora.jpeg"));
    ArrayList<String> P7 = new ArrayList(Arrays.asList("cort7","Cortina","Cortina de 2x2m","Cortinas Guadalajara","2999.99","10",path.trim() + "cortina.jpeg"));
    ArrayList<String> P8 = new ArrayList(Arrays.asList("estu8","Estufa","Estufa gris claro","Mabe","4998","5",path.trim() + "estufa.jpeg"));
    ArrayList<String> P9 = new ArrayList(Arrays.asList("lava9","Lavadora","Lavadora gris oscuro","Whirlpool","5997","5",path.trim() + "lavadora.jpeg"));
    ArrayList<String> P10 = new ArrayList(Arrays.asList("refr10","Refrigerador","Refrigerador gris oscuro","mabe","5000","10",path.trim() + "refrigerador.jpeg"));
    ArrayList<String> P11 = new ArrayList(Arrays.asList("pant11","Pantalon","Pantalon de vestir gris claro","Zara","250","50",path.trim() + "pantalon.jpeg"));
    ArrayList<String> P12 = new ArrayList(Arrays.asList("sil12","Sillon","Sillon rojo","Troncoso","1429","60",path.trim() + "sillon.jpeg"));
    ArrayList<String> P13 = new ArrayList(Arrays.asList("pro13","Producto de limpieza","Pino de 10litros","Pinol","1429.32","10",path.trim() + "pinol.jpeg"));
    ArrayList<String> P14 = new ArrayList(Arrays.asList("del14","Delineador","Delineador para ojos","Kejel Jabibe","150","37",path.trim() + "DelineadorOjos.jpeg"));
    ArrayList<String> P15 = new ArrayList(Arrays.asList("lab15","Labial","Labial Indeleble rosa claro","Kejel Jabibe","200","40",path.trim() + "labial-indeleble.jpeg"));
    ArrayList<String> P16 = new ArrayList(Arrays.asList("som16","Sombras para ojos","Set de Sombras para ojos","Kejel Jabibe","700","28",path.trim() + "PaletasSombras.jpeg"));
    ArrayList<String> P17 = new ArrayList(Arrays.asList("esco17","Escoba","Escoba","Sabilo","70","22",path.trim() + "Escoba.jpeg"));
    ArrayList<String> P18 = new ArrayList(Arrays.asList("trap18","Trapeador","Trapeador","Sabilo","90","10",path.trim() + "Trapeador.jpeg"));
    ArrayList<String> P19 = new ArrayList(Arrays.asList("sar19","Sarten","Sarten","La Vasconia","1000","30",path.trim() + "sarten.jpeg"));
    ArrayList<String> P20 = new ArrayList(Arrays.asList("oll20","Olla Express","Olla Express","La Vasconia","32","10",path.trim() + "ollaExpress.jpeg"));
    
    
    public DAOProducto(){}    
        
    public ArrayList<Producto> getProductos(){
        
        ArrayList<Producto> productos = new ArrayList<>();
        Producto p = (Producto)null;
        
        String id;
        String nombre;
        String descripcion;
        String marca;
        double precio;
        int cantidad;
        String direccion;
        
        id = P1.get(0);
        nombre = P1.get(1);
        descripcion = P1.get(2);
        marca = P1.get(3);
        precio = Double.parseDouble(P1.get(4));
        cantidad = Integer.parseInt(P1.get(5));
        direccion = P1.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P2.get(0);
        nombre = P2.get(1);
        descripcion = P2.get(2);
        marca = P2.get(3);
        precio = Double.parseDouble(P2.get(4));
        cantidad = Integer.parseInt(P2.get(5));
        direccion = P2.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P3.get(0);
        nombre = P3.get(1);
        descripcion = P3.get(2);
        marca = P3.get(3);
        precio = Double.parseDouble(P3.get(4));
        cantidad = Integer.parseInt(P3.get(5));
        direccion = P3.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P4.get(0);
        nombre = P4.get(1);
        descripcion = P4.get(2);
        marca = P4.get(3);
        precio = Double.parseDouble(P4.get(4));
        cantidad = Integer.parseInt(P4.get(5));
        direccion = P4.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P5.get(0);
        nombre = P5.get(1);
        descripcion = P5.get(2);
        marca = P5.get(3);
        precio = Double.parseDouble(P5.get(4));
        cantidad = Integer.parseInt(P5.get(5));
        direccion = P5.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P6.get(0);
        nombre = P6.get(1);
        descripcion = P6.get(2);
        marca = P6.get(3);
        precio = Double.parseDouble(P6.get(4));
        cantidad = Integer.parseInt(P6.get(5));
        direccion = P6.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P7.get(0);
        nombre = P7.get(1);
        descripcion = P7.get(2);
        marca = P7.get(3);
        precio = Double.parseDouble(P7.get(4));
        cantidad = Integer.parseInt(P7.get(5));
        direccion = P7.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P8.get(0);
        nombre = P8.get(1);
        descripcion = P8.get(2);
        marca = P8.get(3);
        precio = Double.parseDouble(P8.get(4));
        cantidad = Integer.parseInt(P8.get(5));
        direccion = P8.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P9.get(0);
        nombre = P9.get(1);
        descripcion = P9.get(2);
        marca = P9.get(3);
        precio = Double.parseDouble(P9.get(4));
        cantidad = Integer.parseInt(P9.get(5));
        direccion = P9.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P10.get(0);
        nombre = P10.get(1);
        descripcion = P10.get(2);
        marca = P10.get(3);
        precio = Double.parseDouble(P10.get(4));
        cantidad = Integer.parseInt(P10.get(5));
        direccion = P10.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P11.get(0);
        nombre = P11.get(1);
        descripcion = P11.get(2);
        marca = P11.get(3);
        precio = Double.parseDouble(P11.get(4));
        cantidad = Integer.parseInt(P11.get(5));
        direccion = P11.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P12.get(0);
        nombre = P12.get(1);
        descripcion = P12.get(2);
        marca = P12.get(3);
        precio = Double.parseDouble(P12.get(4));
        cantidad = Integer.parseInt(P12.get(5));
        direccion = P12.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P13.get(0);
        nombre = P13.get(1);
        descripcion = P13.get(2);
        marca = P13.get(3);
        precio = Double.parseDouble(P13.get(4));
        cantidad = Integer.parseInt(P13.get(5));
        direccion = P13.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P14.get(0);
        nombre = P14.get(1);
        descripcion = P14.get(2);
        marca = P14.get(3);
        precio = Double.parseDouble(P14.get(4));
        cantidad = Integer.parseInt(P14.get(5));
        direccion = P14.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P15.get(0);
        nombre = P15.get(1);
        descripcion = P15.get(2);
        marca = P15.get(3);
        precio = Double.parseDouble(P15.get(4));
        cantidad = Integer.parseInt(P15.get(5));
        direccion = P15.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P16.get(0);
        nombre = P16.get(1);
        descripcion = P16.get(2);
        marca = P16.get(3);
        precio = Double.parseDouble(P16.get(4));
        cantidad = Integer.parseInt(P16.get(5));
        direccion = P16.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P17.get(0);
        nombre = P17.get(1);
        descripcion = P17.get(2);
        marca = P17.get(3);
        precio = Double.parseDouble(P17.get(4));
        cantidad = Integer.parseInt(P17.get(5));
        direccion = P17.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P18.get(0);
        nombre = P18.get(1);
        descripcion = P18.get(2);
        marca = P18.get(3);
        precio = Double.parseDouble(P18.get(4));
        cantidad = Integer.parseInt(P18.get(5));
        direccion = P18.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P19.get(0);
        nombre = P19.get(1);
        descripcion = P19.get(2);
        marca = P19.get(3);
        precio = Double.parseDouble(P19.get(4));
        cantidad = Integer.parseInt(P19.get(5));
        direccion = P19.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        id = P20.get(0);
        nombre = P20.get(1);
        descripcion = P20.get(2);
        marca = P20.get(3);
        precio = Double.parseDouble(P20.get(4));
        cantidad = Integer.parseInt(P20.get(5));
        direccion = P20.get(6);
       
        p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
        productos.add(p);
        
        return productos;
    }
    
    public ArrayList<Producto> getProductoW(ArrayList<Pedido> pedidos){
        
        ArrayList<Producto> productos = new ArrayList();
        int tam = pedidos.size();
        int i;
        
        System.out.println("getW");
        for(i = 0; i < tam; i++){
            Producto p = getProducto(pedidos.get(i).getId());
            productos.add(p);
        }
        
        return productos;
        
    }
    
    
    public Producto getProducto(String idI){
        
        Producto p = (Producto)null; 
        
        String id;
        String nombre;
        String descripcion;
        String marca;
        double precio;
        int cantidad;
        String direccion;
        
        if (P1.get(0).compareTo(idI) == 0) {
            id = P1.get(0);
            nombre = P1.get(1);
            descripcion = P1.get(2);
            marca = P1.get(3);
            precio = Double.parseDouble(P1.get(4));
            cantidad = Integer.parseInt(P1.get(5));
            direccion = P1.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P2.get(0).compareTo(idI) == 0) {
            id = P2.get(0);
            nombre = P2.get(1);
            descripcion = P2.get(2);
            marca = P2.get(3);
            precio = Double.parseDouble(P2.get(4));
            cantidad = Integer.parseInt(P2.get(5));
            direccion = P2.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P3.get(0).compareTo(idI) == 0) {
            id = P3.get(0);
            nombre = P3.get(1);
            descripcion = P3.get(2);
            marca = P3.get(3);
            precio = Double.parseDouble(P3.get(4));
            cantidad = Integer.parseInt(P3.get(5));
            direccion = P3.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P4.get(0).compareTo(idI) == 0) {
            id = P4.get(0);
            nombre = P4.get(1);
            descripcion = P4.get(2);
            marca = P4.get(3);
            precio = Double.parseDouble(P4.get(4));
            cantidad = Integer.parseInt(P4.get(5));
            direccion = P4.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P5.get(0).compareTo(idI) == 0) {
            id = P5.get(0);
            nombre = P5.get(1);
            descripcion = P5.get(2);
            marca = P5.get(3);
            precio = Double.parseDouble(P5.get(4));
            cantidad = Integer.parseInt(P5.get(5));
            direccion = P5.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P6.get(0).compareTo(idI) == 0) {
            id = P6.get(0);
            nombre = P6.get(1);
            descripcion = P6.get(2);
            marca = P6.get(3);
            precio = Double.parseDouble(P6.get(4));
            cantidad = Integer.parseInt(P6.get(5));
            direccion = P6.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P7.get(0).compareTo(idI) == 0) {
            id = P7.get(0);
            nombre = P7.get(1);
            descripcion = P7.get(2);
            marca = P7.get(3);
            precio = Double.parseDouble(P7.get(4));
            cantidad = Integer.parseInt(P7.get(5));
            direccion = P7.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P8.get(0).compareTo(idI) == 0) {
            id = P8.get(0);
            nombre = P8.get(1);
            descripcion = P8.get(2);
            marca = P8.get(3);
            precio = Double.parseDouble(P8.get(4));
            cantidad = Integer.parseInt(P8.get(5));
            direccion = P8.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P9.get(0).compareTo(idI) == 0) {
            id = P9.get(0);
            nombre = P9.get(1);
            descripcion = P9.get(2);
            marca = P9.get(3);
            precio = Double.parseDouble(P9.get(4));
            cantidad = Integer.parseInt(P9.get(5));
            direccion = P9.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P10.get(0).compareTo(idI) == 0) {
            id = P10.get(0);
            nombre = P10.get(1);
            descripcion = P10.get(2);
            marca = P10.get(3);
            precio = Double.parseDouble(P10.get(4));
            cantidad = Integer.parseInt(P10.get(5));
            direccion = P10.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P11.get(0).compareTo(idI) == 0) {
            id = P11.get(0);
            nombre = P11.get(1);
            descripcion = P11.get(2);
            marca = P11.get(3);
            precio = Double.parseDouble(P11.get(4));
            cantidad = Integer.parseInt(P11.get(5));
            direccion = P11.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P12.get(0).compareTo(idI) == 0) {
            id = P12.get(0);
            nombre = P12.get(1);
            descripcion = P12.get(2);
            marca = P12.get(3);
            precio = Double.parseDouble(P12.get(4));
            cantidad = Integer.parseInt(P12.get(5));
            direccion = P12.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P13.get(0).compareTo(idI) == 0) {
            id = P13.get(0);
            nombre = P13.get(1);
            descripcion = P13.get(2);
            marca = P13.get(3);
            precio = Double.parseDouble(P13.get(4));
            cantidad = Integer.parseInt(P13.get(5));
            direccion = P13.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P14.get(0).compareTo(idI) == 0) {
            id = P14.get(0);
            nombre = P14.get(1);
            descripcion = P14.get(2);
            marca = P14.get(3);
            precio = Double.parseDouble(P14.get(4));
            cantidad = Integer.parseInt(P14.get(5));
            direccion = P14.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P15.get(0).compareTo(idI) == 0) {
            id = P15.get(0);
            nombre = P15.get(1);
            descripcion = P15.get(2);
            marca = P15.get(3);
            precio = Double.parseDouble(P15.get(4));
            cantidad = Integer.parseInt(P15.get(5));
            direccion = P15.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P16.get(0).compareTo(idI) == 0) {
            id = P16.get(0);
            nombre = P16.get(1);
            descripcion = P16.get(2);
            marca = P16.get(3);
            precio = Double.parseDouble(P16.get(4));
            cantidad = Integer.parseInt(P16.get(5));
            direccion = P16.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P17.get(0).compareTo(idI) == 0) {
            id = P17.get(0);
            nombre = P17.get(1);
            descripcion = P17.get(2);
            marca = P17.get(3);
            precio = Double.parseDouble(P17.get(4));
            cantidad = Integer.parseInt(P17.get(5));
            direccion = P17.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P18.get(0).compareTo(idI) == 0) {
            id = P18.get(0);
            nombre = P18.get(1);
            descripcion = P18.get(2);
            marca = P18.get(3);
            precio = Double.parseDouble(P18.get(4));
            cantidad = Integer.parseInt(P18.get(5));
            direccion = P18.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else if (P19.get(0).compareTo(idI) == 0) {
            id = P19.get(0);
            nombre = P19.get(1);
            descripcion = P19.get(2);
            marca = P19.get(3);
            precio = Double.parseDouble(P19.get(4));
            cantidad = Integer.parseInt(P19.get(5));
            direccion = P19.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        } else {
            id = P20.get(0);
            nombre = P20.get(1);
            descripcion = P20.get(2);
            marca = P20.get(3);
            precio = Double.parseDouble(P20.get(4));
            cantidad = Integer.parseInt(P20.get(5));
            direccion = P20.get(6);
            p = new Producto(id, nombre, descripcion, marca, precio, cantidad, direccion);
            return p;
        }      
    }    
}
