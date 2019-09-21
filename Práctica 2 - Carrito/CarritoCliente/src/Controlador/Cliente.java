
package Controlador;



import Modelo.DAOProducto;
import java.awt.Desktop;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    
    private String host;
    private int puerto;
    
    public Cliente(String host, int puerto){
        this.host = host;
        this.puerto = puerto;
    }
    
    public void conectar(){
        
        int i;
        String id;
        String nombre;
        String descripcion;
        String marca;
        Double precio;
        int cantidad;
        String direccion;
        ArrayList<Producto> productos = new ArrayList();
        
        try{
            Socket cl = new Socket(host,puerto);
            System.out.println("Enviando petición");
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            dos.writeUTF("Productos");
            dos.flush();
            
            System.out.println("Preparado para recibir productos");
            /*Para recibir datos no serializados*/
            //DataInputStream dis = new DataInputStream(cl.getInputStream());
            /*Para recibir datos serializados*/
            ObjectInputStream d = new ObjectInputStream(cl.getInputStream());
            /*Para recibir datos no serializados*/
            //int tam = dis.readInt();            
            /*Para recibir datos serializados*/
            int tam = d.readInt();
            
            
            for(i = 0;i < tam; i++){
                /*Cuando no este serializado el objeto*/
                /*id = dis.readUTF();
                nombre = dis.readUTF();
                descripcion = dis.readUTF();
                marca = dis.readUTF();
                precio = dis.readDouble();
                cantidad = dis.readInt();
                direccion = dis.readUTF();
                Producto p = new Producto(id,nombre,descripcion,marca,precio,cantidad,direccion);
                productos.add(p);*/
                /*Cuando este serializado el objeto*/
                Producto p1 = (Producto) d.readObject();
                productos.add(p1);
                /*Pruebas*/
                /*System.out.println("id " + id);
                System.out.println("nombre " + nombre);
                System.out.println("descripcion " + descripcion);
                System.out.println("marca " + marca);
                System.out.println("precio " + precio);
                System.out.println("cantidad " + cantidad);
                System.out.println("direccion " + direccion);*/
            }
            
            System.out.println("Productos recibidos");
            /*Cuando no este serializado*/
            //dis.close();
            /*Cuando este serializado*/
            d.close();
            dos.close();
            cl.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        /*Cuando este serializado el objeto*/
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        llenarTabla(productos,host,puerto);
        
    }
    
    public void llenarTabla(ArrayList<Producto> productos,String host, int puerto){
        DAOProducto dao = new DAOProducto(productos,host,puerto);
    }
    
    public void enviarPedido(ArrayList<Pedido> pedidos){
        
        int i;        
        try {

            Socket cl = new Socket(host, puerto);
            System.out.println("Preparado para enviar pedido");

            System.out.println("Enviando petición");
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            dos.writeUTF("Pedido");
            dos.flush();
            dos.writeInt(pedidos.size());
            dos.flush();

            for (i = 0; i < pedidos.size(); i++) {
                dos.writeUTF(pedidos.get(i).getId());
                dos.flush();
                dos.writeInt(pedidos.get(i).getCantidad());
                dos.flush();
            }

            System.out.println("Pedido enviado");

            DataInputStream dis = new DataInputStream(cl.getInputStream());
            String nombre = dis.readUTF();
            System.out.println("Preparando para recibir el pdf " + nombre);
            byte[] b = new byte[1024];
            long tam = dis.readLong();
            System.out.println("tam " + tam);

            DataOutputStream dos1 = new DataOutputStream(new FileOutputStream(nombre));
            int n, porcentaje;
            long recibido = 0;
            System.out.println("Recibiendo");
            while (recibido < tam) {
                n = dis.read(b);
                //System.out.println("n " + n);
                dos1.write(b, 0, n);
                dos1.flush();
                recibido = recibido + n;
                porcentaje = (int) ((recibido * 100) / tam);
                System.out.print("\r Completado: " + porcentaje + "%");
            }//while

            System.out.println("Pdf recibido");
            dis.close();
            dos1.close();
            dos.close();
            cl.close();
            
            String path = new File(" ").getAbsolutePath();
            
            //abrimos pdf
            File arch = new File(path.trim() + "archivo.pdf");
            Desktop.getDesktop().open(arch);

            //conectar();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
}
