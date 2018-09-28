package RecepcionDatos;

import Paquetes.*;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;
import java.util.jar.Pack200;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.ICMPPacket;

public class Receptor implements PacketReceiver {

    JTable tabla = null;
    int numero;
    long tiempoAnterior;
    Packet paquete = null;

    Receptor(JTable tabla) {
        this.tabla = tabla;
    }

    public Packet getPaquete() {
        return paquete;
    }

    public void setPaquete(Packet paquete) {
        this.paquete = paquete;
    }

    public void insertarEnTablaPrimeraSeccion(Packet packet) {

        if (packet instanceof IPPacket) {
            IPPacket aux = (IPPacket) packet;

            if (aux.version == 4) {
                PaqueteETHERNET ethernet = null;
                ethernet = captureETHERNETFields(packet);//se crea paquete ethernet
                PaqueteIP ip = null;
                ip = captureIPFields(aux);

                if (packet instanceof ICMPPacket) {
                   ICMPPacket auxICMP = (ICMPPacket) packet;
                   PaqueteICMP paqueteICMP = null;
                   paqueteICMP = captureICMPFields(auxICMP);
                   
                   
                }

            }

        }

        if (packet instanceof ARPPacket || packet instanceof ICMPPacket || packet instanceof IPPacket) {

            paquete = packet;

            //System.out.println("-----------------\n 1 "+packet + "\n 2 " + paquete+"\n-----------------\n");
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Vector row = new Vector();
            row.add(numero);//contador
            numero++;

            // PaqueteIP paqueteIp=captureIPFields(paquete);
            row.add((double) Math.round((System.currentTimeMillis() - this.tiempoAnterior) * 1 * 100d) / 100d);//tiempo
            byte[] areglo = {paquete.header[26], paquete.header[27], paquete.header[28], paquete.header[29]};
            int[] direccionOrigenBytes = bytearray2intarray(areglo);
            String direccionOrigenString = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
            row.add(direccionOrigenString);

            byte[] areglo2 = {paquete.header[30], paquete.header[31], paquete.header[32], paquete.header[33]};
            direccionOrigenBytes = bytearray2intarray(areglo2);
            String direccionOrigenString2 = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
            row.add(direccionOrigenString2);

            byte[] arregloProtocolo = {paquete.header[23]};
            int[] protocolo = bytearray2intarray(arregloProtocolo);
            row.add(protocolo[0]);

            //------------
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(paquete.header[17]);
            bb.put(paquete.header[16]);
            short tamanioDecimal = bb.getShort(0);
            row.add(tamanioDecimal);

            /* short TypeOfService=bb.getShort(0);
            row.add(TypeOfService);
            row.add("lol");
            row.add("que hubo");
             */
            model.addRow(row);

        }

        //IP
        //-----------------------------------------------------------------------------------
    }

    public int[] bytearray2intarray(byte[] barray) {
        int[] iarray = new int[barray.length];
        int i = 0;
        for (byte b : barray) {
            iarray[i++] = b & 0xff;
        }
        return iarray;
    }

    public String byteToBinaryString(byte b) {

        String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        System.out.println(s1);
        return s1;
    }

    public String transformadorAHexa(byte[] arreglo) {

        StringBuilder sb = new StringBuilder();
        for (byte b : arreglo) {
            sb.append(String.format("%02X", b));
        }
        return "0x" + sb.toString();
    }

    public String obtenerMac(byte[] arreglo) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arreglo.length - 1; i++) {

            sb.append(String.format("%02X:", arreglo[i]));
        }
        sb.append(String.format("%02X:", arreglo[arreglo.length - 1]));
        return "0x" + sb.toString();
    }

    public short byteToShort(byte[] arreglo) {
        ByteBuffer bb = ByteBuffer.allocate(arreglo.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for(int i=arreglo.length-1;i>=0;i--){
            bb.put(arreglo[i]);
        }
        return bb.getShort(0);
    }

    public PaqueteIP captureIPFields(IPPacket paquete) {

        //---------CREACION DE VARIABLES DE IP--------------------------------------------
        PaqueteIP paqueteIp = new PaqueteIP();
        paqueteIp.setVersion(paquete.version);//VERSION DEL PROTOCOLO IP(14-primer cuarteto)
        paqueteIp.setHeaderLength((short) 20);//TAMAÑO DE LA TRAMA ETHERNET(14-segundo cuarteto)
        //DIFFERENTIATED SERVICECS FIELD(15) ---------------------------------------
        byte[] arreglo={paquete.header[16],paquete.header[17]};
        paqueteIp.setTotalLength(byteToShort(arreglo));//TAMAÑO TOTAL DEL CAMPO IP(16-17)
        short Identification= (short)paquete.ident;//IDENTIFICACION DEL PAQUETE(18-19)
        paqueteIp.setIdentification(Identification);
        byte[] arreglo2 = {paquete.header[18], paquete.header[19]};
        String IdentificationHexa=transformadorAHexa(arreglo2);//---------------------------------
        paqueteIp.setIdentificationHexa(IdentificationHexa);
        byte[] arreglo3={paquete.header[20],paquete.header[21]};
        String FlagsHexa=transformadorAHexa(arreglo3);//INDICA FRAGMENTACION DEL PAQUETE(20-21)
        paqueteIp.setFlagsHexa(FlagsHexa);
        String flagCuartetoPrimerByteBinario;//PARA ANALIZAR LAS POSIBLES FRAGMENTACIONES DEL PAQUETE(20-primer cuarteto)
        if(paquete.more_frag){
            flagCuartetoPrimerByteBinario="Hay más fragmentos";
        }else{
            flagCuartetoPrimerByteBinario="No hay más fragmentos";
        }
        paqueteIp.setFlagCuartetoPrimerByteBinario(flagCuartetoPrimerByteBinario);
        
        
                
                
                
                
                
                
        
        int[] num;
        byte[] ar={paquete.header[22]};
        short TimeToLive=byteToShort(arreglo);//TIEMPO DE VIDA DEL PAQUETE(22)
        paqueteIp.setTimeToLive(TimeToLive);
        //paqueteIp.setTimeToLive(TimeToLive);
        short Protocol=paquete.header[23];//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE(23)
        paqueteIp.setProtocol(Protocol);
        String ProtocolName;//NOMBRE DEL PROTOCOLO ANTERIOR
        ProtocolName = hallarNombreProtocolo(Protocol);
        paqueteIp.setProtocolName(ProtocolName);
        byte[] arreglo4={paquete.header[24],paquete.header[25]};
        String HeaderChecksumHexa = transformadorAHexa(arreglo4);//verificacion(24-25)
        
        
        
        byte[] areglo = {paquete.header[26], paquete.header[27], paquete.header[28], paquete.header[29]};
        int[] direccionOrigenBytes = bytearray2intarray(areglo);
        String Source = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];

        byte[] areglo2 = {paquete.header[30], paquete.header[31], paquete.header[32], paquete.header[33]};
        direccionOrigenBytes = bytearray2intarray(areglo2);
        String Destination = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
        
        paqueteIp.setSource(Source);//DIRECCION IP ORIGEN(26-29)
        paqueteIp.setDestination(Destination);//DIRECCION IP DESTINO(30-33)
        

        /*int version = paquete.version;//VERSION DEL PROTOCOLO IP
        int Protocol = paquete.header[23];//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE
        int HeaderLength = 20;//TAMAÑO DE LA TRAMA ETHERNET
        int Identification = paquete.ident;
        byte[] arreglo = {paquete.header[18], paquete.header[19]};
        String IdentificationHexa = transformadorAHexa(arreglo);*/

        //----------------------IMPRESION PARA CONTROL-----------------------------------------
        //String TotalLength=transformador();
        /*System.out.println("Paquete IP------------------------"
                + "\nVersion " + version
                + "\nProtocolo " + Protocol
                + "\nHeaderLength " + HeaderLength
                + "\nIdentificationInt " + Identification
                + "\nIdentificationHexa " + IdentificationHexa
                + "\n");
         */
        //ASIGNACION DE VARIABLES A LA CLASE----------------------------------------------------
        /*paqueteIp.setHeaderLength(HeaderLength);                //= HeaderLength;
        paqueteIp.setIdentification(Identification);            //= Identification;
        paqueteIp.setProtocol(Protocol);                        // = Protocol;
        paqueteIp.setversion(version);                          // = version; 
        paqueteIp.setIdentificationHexa(IdentificationHexa);    // = IdentificationHexa;*/
        return paqueteIp;
    }
     
    public PaqueteICMP captureICMPFields(ICMPPacket paquete) {

        //---------CREACION DE VARIABLES DE IP--------------------------------------------
        PaqueteICMP paqueteICMP = new PaqueteICMP();
     /*   short TypeOfMessage;//TIPO DEL MENSAJE(34)
 
    short Code;//NO SE QUE ES(35)
    String CheckSumHexa;//(36-37)
    short IdentifierBE;//IDENTIFICADOR(38-39)
    String IdentifierBEHexa;//------------(38-39)
    short IdentifierLE;//------------(39-38)
    String IdentifierLEHexa;//----------(39-38)
    
    short SequenceNumberBE;//IDENTIFICADOR(40-41)
    String SequenceNumberBEHexa;//------------(40-41)
    short SequenceNumberLE;//------------(41-40)
    String SequenceNumberLEHexa;//----------(41-40)
    
    String Data; */
        return paqueteICMP;
    }

    @Override
    public void receivePacket(Packet packet) {

        insertarEnTablaPrimeraSeccion(packet);

    }
    
    private String hallarNombreProtocolo(short num){
        String nombre = " ";
        switch(num){        
            case 1:
                nombre = "ICMP";
                break;
            case 4:
                nombre = "IPv4";
                break;
            case 6:
                nombre = "TCP";
                break;
            case 17:
                nombre = "UDP";
                break;
            case 41:
                nombre = "IPv6";
                break; 
        };
        
        return nombre;
    }

    private PaqueteETHERNET captureETHERNETFields(Packet packet) {
        PaqueteETHERNET ethernet = new PaqueteETHERNET();
        ethernet.setType("IPv4");
        byte[] arreglo={packet.header[0],packet.header[1],packet.header[2],packet.header[3],packet.header[4],packet.header[5]};
        ethernet.setMACDestination(obtenerMac(arreglo));
        byte[] arreglo2={packet.header[6],packet.header[7],packet.header[8],packet.header[9],packet.header[10],packet.header[11]};
        ethernet.setMACSource(obtenerMac(arreglo2));
        

        return ethernet;
    }

    

    

}
