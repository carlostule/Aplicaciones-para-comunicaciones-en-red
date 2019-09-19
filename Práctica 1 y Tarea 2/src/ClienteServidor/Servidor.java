/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClienteServidor;


import java.net.*;
import java.io.*;
/**
 *
 * @author Carlos Tule
 */
public class Servidor {
    
    public static void main(String[] args) {
        
        ServerSocket server;
        Socket connection;
        Carpetas carpetas;
        File onedrive = new File("/Users/carlostule/Desktop/ESCOM/Redes2/SocketMultipleFiles/ArchivosServidor");
        int enviados = 0, n;
        
        try{
           
           server = new ServerSocket(1234);
           server.setReuseAddress(true);
           System.out.println("Servidor iniciado, esperando cliente");
           
           while(true){
                connection = server.accept();
                System.out.println("Se ha conectado un cliente");
                
                ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                
                Solicitud aux = (Solicitud)ois.readObject();
                String solicitud = aux.getReq();//Que accion
                
                if("Mostrar".equals(solicitud) == true){
                    carpetas = new Carpetas(onedrive.getAbsolutePath());
                    listarFicherosPorCarpeta(carpetas,onedrive);
                    oos.writeObject(carpetas);
                    oos.flush();        
                }else if (solicitud.compareToIgnoreCase("Subir") == 0){
                    System.out.println("La solicitud ha sido "+solicitud);
                    Solicitud clase = (Solicitud)ois.readObject();
                    String sClase = clase.getReq();
                    System.out.println("De tipo " + sClase);
                    if(sClase.equalsIgnoreCase("Directorio")){
                        aux = (Solicitud)ois.readObject();
                        solicitud = aux.getReq();
                        File a = new File(solicitud);
                        System.out.println(solicitud);
                        try{
                            a.mkdir();
                        }catch(Exception e){System.out.println("Error al crear carpeta "+e);}
                    }else{
                        System.out.println("El cliente esta subiendo archivos");
                        Solicitud nDir = (Solicitud)ois.readObject();
                        String sdir = nDir.getReq();
                        System.out.println(""+sdir);
                        byte[] receivedData = new byte[65535];
                        int in;
                        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                        DataInputStream dis=new DataInputStream(connection.getInputStream());
                        //Recibimos el nombre del fichero
                        String file = dis.readUTF();
                        file = sdir+"/"+file;
                        //Para guardar fichero recibido
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                        while ((in = bis.read(receivedData)) != -1){
                            bos.write(receivedData,0,in);
                        }
                        bos.close();
                        dis.close();
                        bis.close();
                    }
                    
                }else if(solicitud.compareToIgnoreCase("Eliminar") == 0){
                    carpetas = (Carpetas)ois.readObject();
                    String path = carpetas.getRuta().getAbsolutePath();
                    File a = new File(path);
                    eliminar(a);
                }else if(solicitud.compareToIgnoreCase("Descargar") == 0){
                    carpetas = (Carpetas)ois.readObject();
                    String path = carpetas.getRuta().getAbsolutePath();
                    File a = new File(path);
                    if(!a.isDirectory()){
                        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                        DataInputStream dis = new DataInputStream(new FileInputStream(path));
                        System.out.println("Se envia");
                        dos.writeUTF(carpetas.getRuta().getName());
                        dos.flush();
                        dos.writeLong(carpetas.getRuta().length());
                        dos.flush();
                        
                        while (enviados < carpetas.getRuta().length()) {
                            byte [] b = new byte [1024];
                            n = dis.read(b);
                            dos.write(b, 0, n);
                            dos.flush();
                            enviados += n;
                            System.out.println("\rEnviados: " + (int)((enviados * 100) / carpetas.getRuta().length()) + "%");
                        }
                        
                    }else{
                        Solicitud s = new Solicitud(a.getName(),0);
                        oos.writeObject(s);
                        oos.flush();
                    }
                   
                }
                oos.close();
                ois.close();
                connection.close();
           }
           
       }catch(Exception e){System.out.println(e);}
    }
    
    public static void eliminar(File f){
        for(File file:f.listFiles()){
            if(file.isDirectory()){
                if((file.listFiles().length)==0){file.delete();}
                else{eliminar(file);}
            }else{file.delete();}
        }
    }
    
    public static void listarFicherosPorCarpeta(Carpetas c,File carpeta) {
        for (File ficheroEntrada : carpeta.listFiles()) {
            c.setHijo(ficheroEntrada.getAbsolutePath());
            if (ficheroEntrada.isDirectory())
                listarFicherosPorCarpeta((c.hijos).lastElement(),ficheroEntrada);
        }
    }
    
}
