package ClienteServidor;
    
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author Carlos Tule
 */
public class Cliente extends javax.swing.JFrame {

    final int puerto = 1234;
    private int n;
    Carpetas carpetas;
    Socket cliente;
    
    public Cliente() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ip = new javax.swing.JLabel();
        ipText = new javax.swing.JTextField();
        buttonConectar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tree = new javax.swing.JTree();
        buttonSubir = new javax.swing.JButton();
        buttonDescargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        ip.setText("Direcion IP");

        ipText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTextActionPerformed(evt);
            }
        });

        buttonConectar.setText("Conectar");
        buttonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ip, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(buttonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ip)
                    .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConectar))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        Tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        Tree.setEnabled(false);
        jScrollPane1.setViewportView(Tree);

        buttonSubir.setText("Subir");
        buttonSubir.setEnabled(false);
        buttonSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSubirActionPerformed(evt);
            }
        });

        buttonDescargar.setText("Descargar ");
        buttonDescargar.setEnabled(false);
        buttonDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDescargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonDescargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSubir)
                        .addGap(18, 18, 18)
                        .addComponent(buttonDescargar)
                        .addGap(0, 70, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Carpetas leerCarpeta(){
        try{
            String host = ipText.getText();
            cliente = new Socket(host,puerto);
            System.out.println("Se ha conectado con el servidor ONEDRIVE");
            
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            
            String solicitud = "Mostrar";
            Solicitud aux = new Solicitud(solicitud,0);
            oos.writeObject(aux);
            oos.flush();
            
            carpetas = (Carpetas)ois.readObject();
            
            System.out.println("Se ha recibido objeto del servidor ONEDRIVE");
            oos.close();
            ois.close();
            cliente.close();
           
        }catch(Exception e){System.out.println(e);}
        
        return carpetas;
    }
    
    private void fillDataTree(DefaultMutableTreeNode t){
        DefaultTreeModel dtm = new DefaultTreeModel(t);
        this.Tree.setModel(dtm);
        dtm.reload();
    }
    
    private DefaultMutableTreeNode Leer(Carpetas carpetas){
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode((carpetas.getRuta()).getAbsolutePath());
        Vector <Carpetas>hijos = carpetas.getHijos();
        if(!(hijos).isEmpty()){
           for(int i=0;i<hijos.size();i++){
               raiz.add(Leer(hijos.get(i)));
           }
        }
        return raiz;
    }
    
    
    private void buttonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConectarActionPerformed
        // TODO add your handling code here:
        System.out.println("CONECTANDO...");
        
        Carpetas c = leerCarpeta();
        
        fillDataTree(Leer(c));
        
        buttonConectar.setEnabled(false);
        ipText.setEnabled(false);
        // usuarioText.setEnabled(false);
        // pass.setEnabled(false);
        ip.setEnabled(false);
        // usuario.setEnabled(false);
        // contra.setEnabled(false);
            
        // buttonCargar.setEnabled(true);
        buttonSubir.setEnabled(true);
        // buttonEliminar.setEnabled(true);
        buttonDescargar.setEnabled(true);
        Tree.setEnabled(true);
    }//GEN-LAST:event_buttonConectarActionPerformed

    private void ipTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipTextActionPerformed

    private void Descargar(Carpetas carpetas,DefaultMutableTreeNode node,String dir){
        String nuevadir="";
        int recibidos = 0;
        try {
                cliente = new Socket(ipText.getText(), puerto);
                System.out.println("Se ha conectado con el servidor ONEDRIVE");

                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                String solicitud = "Descargar";
                Solicitud aux = new Solicitud(solicitud,0);
                oos.writeObject(aux);
                oos.flush();

                oos.writeObject(carpetas);
                oos.flush();
                if(node.getChildCount()==0){ // Archivo
                    byte[] receivedData = new byte[65535];
                    int in;
                    BufferedInputStream bis = new BufferedInputStream(cliente.getInputStream());
                    DataInputStream dis=new DataInputStream(cliente.getInputStream());
                    //Recibimos el nombre del fichero
                    String file = dis.readUTF();
                    file = dir+file;
                    //Para guardar fichero recibido
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    while ((in = bis.read(receivedData)) != -1){
                        bos.write(receivedData,0,in);
                    }
                    bos.close();
                    dis.close();
                    bis.close();
                }
                else{ // Directorio
                    Solicitud s = (Solicitud)ois.readObject();
                    nuevadir = dir + s.getReq()+"/";
                    File nuevo = new File(nuevadir);
                    nuevo.mkdir();
                }
                oos.close();
                ois.close();
                cliente.close();
            } catch (Exception ex) {            }
            for(int i=0;i<node.getChildCount();i++){
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)node.getChildAt(i);
                Carpetas solicitarDescarga = new Carpetas((String)selectedNode.getUserObject());
                Descargar(solicitarDescarga,selectedNode,nuevadir);
            }
            
    }
    
    private void buttonDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDescargarActionPerformed
        // TODO add your handling code here:
        String dir ="/Users/carlostule/Desktop/ESCOM/Redes2/SocketMultipleFiles/ArchivosCliente/";
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Tree.getSelectionPath().getLastPathComponent();
        Carpetas solicitarDescarga = new Carpetas((String)selectedNode.getUserObject());
        Descargar(solicitarDescarga,selectedNode,dir);
        
    }//GEN-LAST:event_buttonDescargarActionPerformed

    private void subirArchivos(Carpetas c, String direccion){
        try{
            int enviados = 0;
            String host = ipText.getText();
            cliente = new Socket(host, puerto);
            System.out.println("Se ha conectado con el servidor ONEDRIVE");
            
            if((c.getRuta()).isDirectory()){ // Es un directorio
                System.out.println("Subiendo directorio");
                direccion = direccion+"/"+c.ruta.getName();
                System.out.println(direccion);
                
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                String solicitud = "Subir";
                Solicitud aux = new Solicitud(solicitud,0);
                oos.writeObject(aux);
                oos.flush();
                
                String tipo = "Directorio";
                Solicitud t = new Solicitud(tipo,0);
                oos.writeObject(t);
                oos.flush();
                
                Solicitud d = new Solicitud(direccion,0);
                oos.writeObject(d);
                oos.flush();
                
                cliente.close();
                oos.close();
                ois.close();
                
                for(int i=0;i<(c.getHijos()).size();i++){
                    subirArchivos((c.getHijos()).elementAt(i),direccion);
                }
            }else{ // Es un archivo
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                String solicitud = "Subir";
                Solicitud aux = new Solicitud(solicitud,0);
                oos.writeObject(aux);
                oos.flush();
                
                String clase = "Archivo";
                Solicitud auxiliar = new Solicitud(clase,0);
                oos.writeObject(auxiliar);
                oos.flush();
                
                System.out.println("Se enviara archivo");
                
                String dirPath = direccion;
                Solicitud a = new Solicitud(dirPath,0);
                oos.writeObject(a);
                oos.flush();
                
                System.out.println("Se envio la direccion");
                
                String path = c.getRuta().getAbsolutePath();
                
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                
                System.out.println("Se sube archivo");
                dos.writeUTF(c.getRuta().getName());
                dos.flush();
                dos.writeLong(c.getRuta().length());
                dos.flush();
                
                while (enviados < c.getRuta().length()) {
                    byte [] b = new byte [1024];
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    enviados += n;
                    System.out.println("\rEnviados: " + (int)((enviados * 100) / c.getRuta().length()) + "%");
                }
                
                dos.close();
                dis.close();
                
                oos.close();
                ois.close();
                
                cliente.close();
            }
            
        }catch(Exception e){System.out.println(""+e);}
    }
    
    private void buttonSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSubirActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Tree.getSelectionPath().getLastPathComponent();
        Carpetas solicitarSubir = new Carpetas((String)selectedNode.getUserObject());
        try{
            JFileChooser jf = new JFileChooser();
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(jf);
            
            if(r == JFileChooser.APPROVE_OPTION){
                File[] files = jf.getSelectedFiles();
                for(File f : files){
                    String dir = solicitarSubir.getRuta().getAbsolutePath();
                    System.out.println(dir);
                    Carpetas c = new Carpetas(f.getAbsolutePath());
                    if(f.isDirectory()){
                        listarFicherosPorCarpeta(c,f);
                    }
                    subirArchivos(c,dir);
                }
            }
       }catch(Exception e){System.out.println("Chooser wrong : "+e);} 
        fillDataTree(Leer(leerCarpeta()));
    }//GEN-LAST:event_buttonSubirActionPerformed

    public static void listarFicherosPorCarpeta(Carpetas c,File carpeta) {
        for (File ficheroEntrada : carpeta.listFiles()) {
            c.setHijo(ficheroEntrada.getAbsolutePath());
            if (ficheroEntrada.isDirectory())
                listarFicherosPorCarpeta((c.hijos).lastElement(),ficheroEntrada);
        }
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree Tree;
    private javax.swing.JButton buttonConectar;
    private javax.swing.JButton buttonDescargar;
    private javax.swing.JButton buttonSubir;
    private javax.swing.JLabel ip;
    private javax.swing.JTextField ipText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
