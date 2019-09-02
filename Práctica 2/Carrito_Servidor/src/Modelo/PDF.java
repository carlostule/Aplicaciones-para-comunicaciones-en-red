
package Modelo;

/**
 *
 * @author Carlos Tule
 */
import Controlador.Pedido;
import Controlador.Producto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;
import java.io.*;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PDF {
    
    ArrayList<Pedido> pedidos = new ArrayList();
    
    public PDF(ArrayList<Pedido> pedidos){
        this.pedidos = pedidos;
    }
    
    public void createPDF() {
        
        //obtenemos productos
        ArrayList<Producto> productos = new ArrayList();
        DAOProducto dao = new DAOProducto();
        productos = dao.getProductoW(pedidos);
        int i;
        double total = 0;
        String path = new File(" ").getAbsolutePath();
        
        try {
            FileOutputStream archivo = new FileOutputStream(path.trim() + "archivo.pdf");
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivo).setInitialLeading(20);
            //se abre el documento
            documento.open();
 
            documento.add(new Paragraph("Resumen de pedido",
                FontFactory.getFont("arial",   // fuente
                14,                            // tamaño
                Font.BOLD,                     // estilo
                BaseColor.BLACK)));            // color
            
            for (i = 0; i < productos.size(); i++) {
                documento.add(new Paragraph(" "));

                try {
                    Image foto = Image.getInstance(productos.get(i).getDireccion());
                    foto.scaleToFit(100, 100);
                    foto.setAlignment(Chunk.ALIGN_MIDDLE);
                    documento.add(foto);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Paragraph parrafo = new Paragraph(productos.get(i).getNombre(),
                        FontFactory.getFont("arial", // fuente
                                14, // tamaño
                                Font.BOLD, // estilo
                                BaseColor.BLACK));             // color
                parrafo.setAlignment(Element.ALIGN_CENTER);

                documento.add(parrafo);
                documento.add(new Paragraph(" "));

                //creamos tabla con 5 columnas
                PdfPTable tabla = new PdfPTable(5);
                PdfPCell cell = new PdfPCell(new Phrase("Descripción"));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("Marca"));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("Precio unitario"));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("Cantidad"));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("Subtotal"));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase(productos.get(i).getDescripcion()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase(productos.get(i).getMarca()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("$" + productos.get(i).getPrecio()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                cell = new PdfPCell(new Phrase("" + pedidos.get(i).getCantidad()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                DecimalFormat df = new DecimalFormat("0.00"); 
                double precioS = productos.get(i).getPrecio() * pedidos.get(i).getCantidad();
                cell = new PdfPCell(new Phrase("$" + df.format(precioS)));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);

                total = total + precioS;
                
                documento.add(tabla);
            }
            
            documento.add(new Paragraph(" "));
            
            PdfPTable tabla = new PdfPTable(2);
            
            PdfPCell cell2 = new PdfPCell(new Phrase("Total "));
            cell2.setVerticalAlignment(Element.ALIGN_RIGHT);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(cell2);
            
            DecimalFormat df = new DecimalFormat("0.00");
            cell2 = new PdfPCell(new Phrase("$" + df.format(total)));
            cell2.setVerticalAlignment(Element.ALIGN_RIGHT);
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(cell2);
            
            tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
            documento.add(tabla);            
            
            
            documento.close();       
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }      
    }
    
}
