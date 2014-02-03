package impresionepson;

import java.io.IOException;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pablo Velez
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ImpresionEpson imp = new ImpresionEpson("Generic / Text Only");
        //Cogemos el servicio de impresi?n por defecto (impresora por defecto)
        String datos[] = {"PLASTIASEO ROZO", "1114820980-6", "REGIMEN SIMPLIFICADO", "CL 10 #11-18", "2685399", "3147491822", "ROZO", "VALLE DEL CAUCA"};
        imp.establecerEncabezadoFactura(datos);
        String datosFactura[]={"Cotizacion","000001","2014/02/02"};
        imp.establecerDatosFactura(datosFactura);
        String datosCliente[]={"Rest. Siga la Vaca","16682914-0","Rozo",""};
        imp.establecerDatosCliente(datosCliente);
        imp.establecerEncabezadoProductos();        
        String productos[][]={{"bolsa-032","Bol. bas. 65x90","100","20000"},
            {"bolsa-034","Bol. bas. 90x110","100","45000"},
            {"des-041","Gte. plast. par","150","7500"},
            {"des-071","Palo Pnch. Mad.","1","2000"},
            {"icopor-008","Contenedor 24Oz","400","100000"},
            {"icopor-012","J1","200","28000"},
            {"vaso-005","Vaso 10Oz x50","100","17000"},
            {"vaso-008","Vaso 14Oz x25","37","55500"}        
        };
        imp.enviarProductos(productos);
        String datosFnDetProd[]={"428000","0","428000"};
        imp.establecerFinDetalleProductos(datosFnDetProd);
        imp.saltoDeLinea(2);
        
        /*String productos[][]={{"bolsa-032","Bolsa basura 65x90","100","20000"},
            {"bolsa-034","Bolsa basura 90 x 110","100","45000"},
            {"des-041","Guante plastico par","150","7500"},
            {"des-071","Palo Pincho Madera","1","2000"},
            {"icopor-008","Contenedor 24 Oz","400","100000"},
            {"icopor-012","J1","200","28000"},
            {"vaso-005","Vaso 10 Oz x 50","100","17000"},
            {"vaso-008","Vaso 14 Oz x 25","37","55500"}        
        };*/
        imp.imprimir();
        
        imp.setSs("");

    }
}
