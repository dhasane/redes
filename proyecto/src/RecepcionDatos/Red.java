package RecepcionDatos;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTable;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import Paquetes.*;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author briam
 */
public class Red {

    Sniffer sniffer;

    public Red() {
        sniffer = new Sniffer();
    }

    public void setSniffer(Sniffer sniffer) {
        this.sniffer = sniffer;
    }

    public void llenarTabla(JTable TablaSniffer, String d, boolean modoDeCaptura) {
        sniffer.modificarInterfaceDeRed(d, modoDeCaptura);
        sniffer.setTabla(TablaSniffer);
        sniffer.startTask();
    }

    public void conseguirDaticos(int fila, JTable tablaDatos) {
        Vector<Packet> vectorPaquetes = sniffer.darVectoricito();
        Packet paquete = vectorPaquetes.get(fila);
        PaqueteETHERNET ethernet = crearPaqueteEthernet(paquete);

        llenarTablaDeDatos(tablaDatos, ethernet);

    }

    public void llenarComboBoxDispositivos(JComboBox dispositivosCB) {
        ArrayList<String> dispositivos = sniffer.getNombreDispositivos();
        dispositivos.forEach((string) -> {
            dispositivosCB.addItem(string);
        });
    }

    public void terminarLlenadoDeTabla() {
        sniffer.endTask();
        System.out.println("finalizó");
    }

    public void detenerLlenadoDeTabla() {
        sniffer.pause();
    }

    public void continuarLLenadoDeTabla() {
        sniffer.resume();
    }

    public PaqueteETHERNET crearPaqueteEthernet(Packet packet) {
        PaqueteETHERNET ethernet = null;
        if (packet instanceof IPPacket) {
            IPPacket aux = (IPPacket) packet;
            if (aux.version == 4) {

                ethernet = captureETHERNETFields(packet);//se crea paquete ethernet|
                PaqueteIP ip = captureIPFields(aux);

                if (packet instanceof ICMPPacket) {
                    ICMPPacket auxICMP = (ICMPPacket) packet;
                    PaqueteICMP paqueteICMP = captureICMPFields(auxICMP);
                    ip.setIcmp(paqueteICMP);
                }
                ethernet.setIp(ip);

            }

        }
        return ethernet;

    }

    public PaqueteIP captureIPFields(IPPacket paquete) {

        //---------CREACION DE VARIABLES DE IP--------------------------------------------
        PaqueteIP paqueteIp = new PaqueteIP();
        //--------------------------------------------------------------------------------------------------
        paqueteIp.setVersion(paquete.version);//VERSION DEL PROTOCOLO IP(14-primer cuarteto)
        //--------------------------------------------------------------------------------------------------
        paqueteIp.setHeaderLength((short) 20);//TAMAÑO DE LA TRAMA ETHERNET(14-segundo cuarteto)
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo = {paquete.header[16], paquete.header[17]};
        paqueteIp.setTotalLength(Utils.byteToShort(arreglo));//TAMAÑO TOTAL DEL CAMPO IP(16-17)
        //--------------------------------------------------------------------------------------------------
        short Identification = (short) paquete.ident;//IDENTIFICACION DEL PAQUETE(18-19)
        paqueteIp.setIdentification(Identification);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo2 = {paquete.header[18], paquete.header[19]};
        String IdentificationHexa = Utils.transformadorAHexa(arreglo2);//---------------------------------
        paqueteIp.setIdentificationHexa(IdentificationHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo3 = {paquete.header[20], paquete.header[21]};
        String FlagsHexa = Utils.transformadorAHexa(arreglo3);//INDICA FRAGMENTACION DEL PAQUETE(20-21)
        paqueteIp.setFlagsHexa(FlagsHexa);
        //--------------------------------------------------------------------------------------------------
        String flagCuartetoPrimerByteBinario;//PARA ANALIZAR LAS POSIBLES FRAGMENTACIONES DEL PAQUETE(20-primer cuarteto)
        if (paquete.more_frag) {
            flagCuartetoPrimerByteBinario = "Hay más fragmentos";
        } else {
            flagCuartetoPrimerByteBinario = "No hay más fragmentos";
        }
        paqueteIp.setFlagCuartetoPrimerByteBinario(flagCuartetoPrimerByteBinario);
        //--------------------------------------------------------------------------------------------------       
        short TimeToLive = (short) Utils.AbyteToAInt(paquete.header[22]);//TIEMPO DE VIDA DEL PAQUETE(22)
        paqueteIp.setTimeToLive(TimeToLive);
        //--------------------------------------------------------------------------------------------------
        short Protocol = paquete.header[23];//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE(23)
        paqueteIp.setProtocol(Protocol);
        //--------------------------------------------------------------------------------------------------
        String ProtocolName;//NOMBRE DEL PROTOCOLO ANTERIOR
        ProtocolName = Utils.hallarNombreProtocolo(Protocol);
        paqueteIp.setProtocolName(ProtocolName);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo4 = {paquete.header[24], paquete.header[25]};
        String HeaderChecksumHexa = Utils.transformadorAHexa(arreglo4);//verificacion(24-25)
        paqueteIp.setHeaderChecksumHexa(HeaderChecksumHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] areglo = {paquete.header[26], paquete.header[27], paquete.header[28], paquete.header[29]};
        int[] direccionOrigenBytes = Utils.bytearray2intarray(areglo);
        String Source = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
        paqueteIp.setSource(Source);//DIRECCION IP ORIGEN(26-29)
        //--------------------------------------------------------------------------------------------------
        byte[] areglo2 = {paquete.header[30], paquete.header[31], paquete.header[32], paquete.header[33]};
        direccionOrigenBytes = Utils.bytearray2intarray(areglo2);
        String Destination = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
        paqueteIp.setDestination(Destination);//DIRECCION IP DESTINO(30-33)
        //--------------------------------------------------------------------------------------------------

        return paqueteIp;
    }

    public PaqueteICMP captureICMPFields(ICMPPacket paquete) {

        //---------CREACION DE VARIABLES DE IP--------------------------------------------
        PaqueteICMP paqueteICMP = new PaqueteICMP();
        //--------------------------------------------------------------------------------------------------
        short TypeOfMessage = paquete.type;//TIPO DEL MENSAJE(34)
        paqueteICMP.setTypeOfMessage(TypeOfMessage);
        //--------------------------------------------------------------------------------------------------
        short Code = paquete.code;//NO SE QUE ES(35)
        paqueteICMP.setCode(Code);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo = {paquete.header[36], paquete.header[37]};
        String CheckSumHexa = Utils.transformadorAHexa(arreglo);//(36-37)
        paqueteICMP.setCheckSumHexa(CheckSumHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo2 = {paquete.header[38], paquete.header[39]};
        short IdentifierBE = Utils.byteToShort(arreglo2);//IDENTIFICADOR(38-39) 
        paqueteICMP.setIdentifierBE(IdentifierBE);
        //--------------------------------------------------------------------------------------------------
        String IdentifierBEHexa = Utils.transformadorAHexa(arreglo2);//------------(38-39)
        paqueteICMP.setIdentifierBEHexa(IdentifierBEHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo3 = {paquete.header[39], paquete.header[38]};
        short IdentifierLE = Utils.byteToShort(arreglo3);//------------(39-38)
        paqueteICMP.setIdentifierLE(IdentifierLE);
        //--------------------------------------------------------------------------------------------------
        String IdentifierLEHexa = Utils.transformadorAHexa(arreglo3);//----------(39-38)
        paqueteICMP.setIdentifierLEHexa(IdentifierLEHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo4 = {paquete.header[40], paquete.header[41]};
        short SequenceNumberBE = Utils.byteToShort(arreglo4);//IDENTIFICADOR(40-41)
        paqueteICMP.setSequenceNumberBE(SequenceNumberBE);
        //--------------------------------------------------------------------------------------------------
        String SequenceNumberBEHexa = Utils.transformadorAHexa(arreglo4);//------------(40-41)
        paqueteICMP.setSequenceNumberBEHexa(SequenceNumberBEHexa);
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo5 = {paquete.header[41], paquete.header[40]};
        short SequenceNumberLE = Utils.byteToShort(arreglo5);//------------(41-40)
        paqueteICMP.setSequenceNumberLE(SequenceNumberLE);
        //--------------------------------------------------------------------------------------------------
        String SequenceNumberLEHexa = Utils.transformadorAHexa(arreglo5);//----------(41-40)
        paqueteICMP.setSequenceNumberLEHexa(SequenceNumberLEHexa);
        //--------------------------------------------------------------------------------------------------
        String Data = Utils.obtenerData(paquete.data);
        paqueteICMP.setData(Data);
        //--------------------------------------------------------------------------------------------------
        return paqueteICMP;
    }

    private PaqueteETHERNET captureETHERNETFields(Packet packet) {
        //--------------------------------------------------------------------------------------------------
        PaqueteETHERNET ethernet = new PaqueteETHERNET();
        //--------------------------------------------------------------------------------------------------
        ethernet.setType("IPv4");
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo = {packet.header[0], packet.header[1], packet.header[2], packet.header[3], packet.header[4], packet.header[5]};
        ethernet.setMACDestination(Utils.obtenerMac(arreglo));
        //--------------------------------------------------------------------------------------------------
        byte[] arreglo2 = {packet.header[6], packet.header[7], packet.header[8], packet.header[9], packet.header[10], packet.header[11]};
        ethernet.setMACSource(Utils.obtenerMac(arreglo2));
        //--------------------------------------------------------------------------------------------------
        return ethernet;
    }

    private void llenarTablaDeDatos(JTable tablaDatos, PaqueteETHERNET ethernet) {
        System.out.println(ethernet);
        PaqueteIP ip = ethernet.getIp();
        DefaultTableModel model = (DefaultTableModel) tablaDatos.getModel();
        Vector row = new Vector();
        row.add("---TRAMA ETHERNET---");
        row.add("--------------------");
        model.addRow(row);

        row = new Vector();
        row.add("Mac de destino");
        row.add(ethernet.getMACDestination());
        model.addRow(row);

        row = new Vector();
        row.add("Mac de origen");
        row.add(ethernet.getMACSource());
        model.addRow(row);

        row = new Vector();
        row.add("Tipo");
        row.add(ethernet.getType());
        model.addRow(row);
        //-------------------------------------------------------------------------------------------------------------------

        if (ip != null) {
            System.out.println(ip);
            row = new Vector();
            row.add("---TRAMA IP---");
            row.add("----------------");
            model.addRow(row);

            row = new Vector();
            row.add("Versión");
            row.add(ip.getVersion());
            model.addRow(row);

            row = new Vector();
            row.add("Tamaño de cabecera");
            row.add(ip.getHeaderLength());
            model.addRow(row);

            row = new Vector();
            row.add("Tamaño total");
            row.add(ip.getTotalLength());
            model.addRow(row);

            row = new Vector();
            row.add("Identificación");
            row.add(ip.getIdentificationHexa() + " ("+String.valueOf(ip.getIdentification())+")");
            model.addRow(row);

            row = new Vector();
            row.add("Banderas");
            row.add("(" + ip.getFlagsHexa() + ")" + " " + ip.getFlagCuartetoPrimerByteBinario());
            model.addRow(row);

            row = new Vector();
            row.add("TTL");
            row.add(ip.getTimeToLive());
            model.addRow(row);

            row = new Vector();
            row.add("Protocolo");
            row.add(String.valueOf(ip.getProtocol()) + " (" + ip.getProtocolName() + ")");
            model.addRow(row);

            row = new Vector();
            row.add("IP Check Sum");
            row.add(ip.getHeaderChecksumHexa());
            model.addRow(row);

            row = new Vector();
            row.add("IP ORIGEN");
            row.add(ip.getSource());
            model.addRow(row);

            row = new Vector();
            row.add("IP DESTINO");
            row.add(ip.getDestination());
            model.addRow(row);

            PaqueteICMP icmp = ip.getIcmp();
            if (icmp != null) {
                System.out.println(icmp);
                row = new Vector();
                row.add("---TRAMA ICMP---");
                row.add("----------------");
                model.addRow(row);

                row = new Vector();
                row.add("Código");
                row.add(icmp.getCode());
                model.addRow(row);
                
                row = new Vector();
                row.add("ICMP Check Sum");
                row.add(icmp.getCheckSumHexa());
                model.addRow(row);
                
                row = new Vector();
                row.add("Identificador BE");
                row.add(icmp.getIdentifierBEHexa()+"("+String.valueOf(icmp.getIdentifierBE())+")");
                model.addRow(row);
                
                row = new Vector();
                row.add("Identificador LE");
                row.add(icmp.getIdentifierLEHexa()+"("+String.valueOf(icmp.getIdentifierLE())+")");
                model.addRow(row);
                
                row = new Vector();
                row.add("Numero de secuencia BE");
                row.add(icmp.getSequenceNumberBEHexa()+"("+String.valueOf(icmp.getSequenceNumberBE())+")");
                model.addRow(row);
                
                row = new Vector();
                row.add("Numero de secuencia LE");
                row.add(icmp.getSequenceNumberLEHexa()+"("+String.valueOf(icmp.getSequenceNumberLE())+")");
                model.addRow(row);
                
                row = new Vector();
                row.add("Datos");
                row.add(icmp.getData());
                model.addRow(row);
                
            }
        }

        //model.addRow(row);
    }

}
