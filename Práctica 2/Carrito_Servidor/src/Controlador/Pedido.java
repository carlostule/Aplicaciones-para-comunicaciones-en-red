package Controlador;

/**
 *
 * @author Carlos Tule
 */
public class Pedido {

    private String id;
    private int cantidad;
    
    public Pedido(String id, int cantidad){
        this.id = id;
        this.cantidad = cantidad;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    } 
    
}
