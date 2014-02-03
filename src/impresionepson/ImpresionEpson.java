/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionepson;

import java.util.Properties;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

/**
 *
 * @author Pablo Velez
 */
public final class ImpresionEpson {

    /**
     * @param args the command line arguments
     */
    private PrintService service;
    //Le decimos el tipo de datos que vamos a enviar a la impresora
    //Tipo: bytes Subtipo: autodetectado
    private DocFlavor flavor;
    //Creamos un trabajo de impresi?n
    private DocPrintJob pj;
    //Nuestro trabajo de impresi?n env?a una cadena de texto
    private String ss;
    byte[] bytes;
    //Transformamos el texto a bytes que es lo que soporta la impresora         
    //Creamos un documento (Como si fuese una hoja de Word para imprimir)
    private Doc doc;

    //Constructor que toma la impresora predeteminada del sistema. 
    public ImpresionEpson() {
        this.service = PrintServiceLookup.lookupDefaultPrintService();
        this.flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        this.pj = service.createPrintJob();
        this.ss = "";
    }
    //Constructor para definir la impresora 

    public ImpresionEpson(String impresora) {
        establecerImpresora(impresora);
        this.flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        this.pj = service.createPrintJob();
        this.ss = "";
    }

    /**
     * @return the ss
     */
    public String getSs() {
        return ss;
    }

    /**
     * @param ss the ss to set
     */
    public void setSs(String ss) {
        this.ss = ss;
    }

    /**
     * @return the pj
     */
    public DocPrintJob getPj() {
        return pj;
    }

    /**
     * @param pj the pj to set
     */
    public void setPj(DocPrintJob pj) {
        this.pj = pj;
    }

    /**
     * @return the doc
     */
    public Doc getDoc() {
        return doc;
    }

    /**
     * @param doc the doc to set
     */
    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    /**
     * @return the service
     */
    public PrintService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(PrintService service) {
        this.service = service;
    }

    /**
     * @return the flavor
     */
    public DocFlavor getFlavor() {
        return flavor;
    }

    /**
     * @param flavor the flavor to set
     */
    public void setFlavor(DocFlavor flavor) {
        this.flavor = flavor;
    }

    public void dibujaLinea() {
        adicionarLinea("----------------------------------------");
    }

    public void saltoDeLinea(int n) {
        for (int i = 0; i < n; i++) {
            adicionarLinea("");
        }
    }

    public void adicionarLinea(String texto) {
        setSs(getSs() + texto + "\n");
    }

    public void adicionarTexto(String texto) {
        setSs(getSs() + texto);
    }

    public void correrEspacios(int n) {
        for (int i = 1; i <= n; i++) {
            adicionarTexto(" ");
        }
    }

    public void centrar(String texto) {

        int size = texto.length();
        int espacios = 40 - size;
        int espaciosIni = espacios / 2;
        int espaciosFin = 40 - (espaciosIni + size);
        String txtAux = "";
        //Establecemos los espacios iniciales
        for (int i = 1; i < espaciosIni; i++) {
            txtAux += " ";
        }
        //Adicionamos el texto centrado
        txtAux += texto;

        //Espacios Finales
        for (int i = 1; i < espaciosFin; i++) {
            txtAux += " ";
        }

        adicionarLinea(txtAux);
    }

    public void imprimir() {

        this.bytes = getSs().getBytes();
        this.setDoc(new SimpleDoc(bytes, getFlavor(), null));
        try {
            //Mandamos a impremir el documento
            getPj().print(getDoc(), null);
        } catch (PrintException e) {
            System.out.println("Error al imprimir: " + e.getMessage());
        }
    }

    public void establecerEncabezadoFactura(String datos[]) {

        //Capturamos los datos de la empresa para crear el encabezado de la factura.
        String razonSocial = datos[0];
        String nit = datos[1];
        String regimen = datos[2];
        String direccion = datos[3];
        String telefono = datos[4];
        String celular = datos[5];
        String ciudad = datos[6];
        String dpto = datos[7];

        dibujaLinea();
        centrar(razonSocial);
        centrar("NIT " + nit + " " + regimen);
        centrar(direccion);
        if (celular.equals("")) {
            centrar("TEL. " + telefono);
        } else {
            centrar("TEL. " + telefono + " - CEL " + celular);
        }
        centrar(ciudad + " - " + dpto);
        dibujaLinea();
    }

    public void establecerDatosFactura(String datosFactura[]) {
        String tipoFact = datosFactura[0];
        String numFact = datosFactura[1];
        String fechaFact = datosFactura[2];

        adicionarLinea("Fecha de Expedicion: " + fechaFact);
        adicionarLinea(tipoFact + " No. " + numFact);

    }

    public void establecerDatosCliente(String datosCliente[]) {
        String nombre = datosCliente[0];
        String nit = datosCliente[1];
        String direccion = datosCliente[2];
        String telefono = datosCliente[3];

        adicionarLinea("Cliente: " + nombre);
        adicionarLinea("CC/NIT: " + nit);
        if (!direccion.equals("")) {
            adicionarLinea("Direccion: " + direccion);
        }
        if (!telefono.equals("")) {
            adicionarLinea("Telefono: " + telefono);
        }


    }

    public void establecerEncabezadoProductos() {
        //10-1-16-1-3-1-9
        adicionarLinea("---------- ---------------- --- --------");
        adicionarLinea("  Codigo     Descripcion    Cnt   Valor ");
        adicionarLinea("---------- ---------------- --- --------");
    }

    public void enviarProductos(String[][] productos) {
        for (int i = 0; i < productos.length; i++) {
            String codigo = productos[i][0];
            String desc = productos[i][1];
            String cnt = productos[i][2];
            String val = productos[i][3];
            correrEspacios(10 - codigo.length());
            adicionarTexto(codigo);
            correrEspacios(1);
            adicionarTexto(desc);
            correrEspacios(16 - desc.length());
            correrEspacios(1);
            correrEspacios(3 - cnt.length());
            adicionarTexto(cnt);
            correrEspacios(1);
            correrEspacios(8 - val.length());
            adicionarTexto(val);
            adicionarTexto("\n");
        }
        saltoDeLinea(1);
    }

    public void establecerFinDetalleProductos(String datosFnDetProd[]) {
        
        String subtotal=datosFnDetProd[0];
        String descuentos=datosFnDetProd[1];
        String total=datosFnDetProd[2];
        correrEspacios(18);
        adicionarTexto("Subtotal:");
        correrEspacios(5);
        correrEspacios(8-subtotal.length());
        adicionarTexto(subtotal);
        adicionarTexto("\n");
        correrEspacios(17);
        adicionarTexto("Descuento:");
        correrEspacios(5);
        correrEspacios(8-descuentos.length());
        adicionarTexto(descuentos);
        adicionarTexto("\n");
        correrEspacios(21);
        adicionarTexto("Total:");
        correrEspacios(5);
        correrEspacios(8-total.length());
        adicionarTexto(total);
        adicionarTexto("\n");
        dibujaLinea();
    }

    public void printAvailable() {

        // busca los servicios de impresion...
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        // -- ver los atributos de las impresoras...
        for (PrintService printService : services) {

            System.out.println(" ---- IMPRESORA: " + printService.getName());

            PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();

            System.out.println("--- atributos");

            // todos los atributos de la impresora
            Attribute[] a = printServiceAttributeSet.toArray();
            for (Attribute unAtribute : a) {
                System.out.println("atributo: " + unAtribute.getName());
            }

            System.out.println("--- viendo valores especificos de los atributos ");

            // valor especifico de un determinado atributo de la impresora
            System.out.println("PrinterLocation: " + printServiceAttributeSet.get(PrinterLocation.class));
            System.out.println("PrinterInfo: " + printServiceAttributeSet.get(PrinterInfo.class));
            System.out.println("PrinterState: " + printServiceAttributeSet.get(PrinterState.class));
            System.out.println("Destination: " + printServiceAttributeSet.get(Destination.class));
            System.out.println("PrinterMakeAndModel: " + printServiceAttributeSet.get(PrinterMakeAndModel.class));
            System.out.println("PrinterIsAcceptingJobs: " + printServiceAttributeSet.get(PrinterIsAcceptingJobs.class));

        }

    }

    public void printDefault() {

        // tu impresora por default
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println("Tu impresora por default es: " + service.getName());

    }

    public void establecerImpresora(String printName) {

        // buscar por el nombre de la impresora (nombre que le diste en tu S.O.)
        // en "aset" puedes agregar mas atributos de busqueda
        AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(printName, null));
        //aset.add(ColorSupported.SUPPORTED); // si quisieras buscar ademas las que soporten color

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, aset);
        if (services.length == 0) {
            System.out.println("No se encontro impresora con nombre " + printName);
            System.exit(1);
        }
        for (PrintService printService : services) {
            if (printService.getName().equals(printName)) {
                setService(printService);
                break;
            }
        }
    }
}
