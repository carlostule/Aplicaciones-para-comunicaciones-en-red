
package Controlador;

/**
 *
 * @author Carlos Tule
 */

import Modelo.DAOProducto;
import Modelo.PDF;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Servidor {
    
   //    ServerSocket s = new ServerSocket(9090);
    
    ServerSocket s = (ServerSocket)null;
    int puerto;
    
    public Servidor(int puerto) {
        this.puerto = puerto;
    }    
    
    public void escuchar(){
        int i;
        String solicitud;
        try {
            s = new ServerSocket(puerto);
            System.out.println("Servidor iniciado, esperado cliente...");
            for (;;) {
                //se conecta cliente
                Socket cl = s.accept();
                System.out.println("Cliente conectado desde : " + cl.getInetAddress() + ":" + cl.getPort());

                //primero verifica lo que el cliente desea
                System.out.println("Recibiendo solicitud");
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                solicitud = dis.readUTF();
                System.out.println("El cliente solicito " + solicitud);

                if (solicitud.compareTo("Productos") == 0) {
                    //enviamos los productos al cliente
                    System.out.println("Enviando productos al cliente...");
                    ArrayList<Producto> productos = new ArrayList();
                    /*se descomentará cuando no este serializado el objeto*/ 
                    //DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                    
                    /*Este objeto se descomentará para serializar el objeto*/    
                    ObjectOutputStream d = new ObjectOutputStream(cl.getOutputStream());
                    productos = Producto.getP();
                    /*se decomentará para serialzar*/
                    d.writeInt(productos.size());
                    d.flush();
                    /*se decomentará para no serialzar el objeto*/
                    //dos.writeInt(productos.size());
                    //dos.flush();
                    for (i = 0; i < productos.size(); i++) {
                        /*datos serializados*/
                        d.writeObject(productos.get(i));
                        d.flush();
                        /*datos no serializados*/
                        /*dos.writeUTF(productos.get(i).getId());
                        dos.flush();
                        dos.writeUTF(productos.get(i).getNombre());
                        dos.flush();
                        dos.writeUTF(productos.get(i).getDescripcion());
                        dos.flush();
                        dos.writeUTF(productos.get(i).getMarca());
                        dos.flush();
                        dos.writeDouble(productos.get(i).getPrecio());
                        dos.flush();
                        dos.writeInt(productos.get(i).getCantidad());
                        dos.flush();
                        dos.writeUTF(productos.get(i).getDireccion());
                        dos.flush();*/                                            
                    }
                    System.out.println("Productos enviados");
                    /*datos no serializados*/
                    //dos.close();
                    /*Cuando se serialize el objeto*/
                    d.close();
                } else {
                    //procesa pedido
                    System.out.println("Procesando pedido...");
                    ArrayList<Pedido> pedidos = new ArrayList();
                    int tamanio = dis.readInt();
                    String id;
                    int cantidad;

                    for (i = 0; i < tamanio; i++) {
                        id = dis.readUTF();
                        cantidad = dis.readInt();
                        Pedido p = new Pedido(id, cantidad);
                        pedidos.add(p);
                        /*System.out.println("id " + id);
                        System.out.println("cantidad " + cantidad);*/
                    }

                    //obtenemos productos
                    /*ArrayList<Producto> productos = new ArrayList();
                    productos = DAOProducto.getProductoW(pedidos);
                    
                    System.out.println("Actualizando información");
                    DAOProducto pro = new DAOProducto();
                    for(i = 0; i < productos.size(); i++){
                        int canPr = productos.get(i).getCantidad();
                        int canPe = pedidos.get(i).getCantidad();
                        productos.get(i).setCantidad(canPr - canPe);
                        pro.actualizarProducto(productos.get(i), productos.get(i).getId());
                    }*/
                    
                    System.out.println("Creando ticket...");
                    PDF pdf = new PDF(pedidos);
                    pdf.createPDF();

                    System.out.println("Enviando ticket...");
                    String path = new File(" ").getAbsolutePath();
                    File archivo = new File(path.trim() + "archivo.pdf");
                    
                    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                    DataInputStream di = new DataInputStream(new FileInputStream(archivo));
                    long tam = archivo.length();
                    dos.writeUTF(archivo.getName());
                    dos.flush();
                    dos.writeLong(tam);
                    dos.flush();

                    int porcentaje, n;
                    long enviados = 0;
                    byte[] b = new byte[1024];
                    System.out.print("tam: " + tam);
                    while (enviados < tam) {
                        n = di.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        enviados = enviados + n;
                        porcentaje = (int) ((enviados * 100) / tam);
                        System.out.print("\r Enviado: " + porcentaje + "%");
                    }//while
                    System.out.println("\rPedido enviado...");
                    dos.close();
                    di.close();

                }

                dis.close();
                cl.close();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
