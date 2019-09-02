package Controlador;

import Modelo.DAOProducto;
import java.io.Serializable;
import java.util.ArrayList;


//Serializando el objeto
public class Producto implements Serializable{
//public class Producto{
    
    private String id;
    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private int cantidad;
    private String direccion;
    
    public Producto(String id, String nombre, String descripcion, String marca, Double precio,
            int cantidad, String direccion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precio = precio;
        this.direccion = direccion;
    }
        
    public static ArrayList<Producto> getP(){
        DAOProducto dao = new DAOProducto();
        return dao.getProductos();
    }
        
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public Double getPrecio() {
        return precio;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }
}
