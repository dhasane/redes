package RecepcionDatos;


import Paquetes.PaqueteIP;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;
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
    Packet paquete;
    
    Sniffer pp; 
    /*
    public Receptor(JTable tabla) {
        this.tabla = tabla;
    }//*/
    
    Receptor(JTable tabla, Sniffer aThis) {
        this.tabla = tabla;
        this.pp = aThis;
    }

    public Packet getPaquete() {
        return paquete;
    }

    public void setPaquete(Packet paquete) {
        this.paquete = paquete;
    }

    

    public void insertarEnTabla(Packet packet) {

        //FILTRADO
      
    
        if (packet instanceof ARPPacket|| packet instanceof ICMPPacket ||  packet instanceof IPPacket ) 
        {
            //arp y ethernet

            // lo que llevo pensando es que los paquetes tienen que ser aggarrados desde este punto
            // pero para agregarlos a la lista esta que les digo 
            // entonces si entra a cualqueira de las categorias, lo agrega a la lista
            // y no hay forma de retornarlo despues :V "retornar"
            //
            System.out.println("-----------------");
            System.out.println(packet.caplen);
            System.out.println(packet.data);
            System.out.println(packet.header);
            System.out.println(packet.len);
            System.out.println(packet.sec);
            System.out.println(packet.usec);
            
            //pp.agregarPaquete(packet);
            paquete = packet;
            
            
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Vector row = new Vector();
            row.add(numero);//contador
            
           // PaqueteIP paqueteIp=captureIPFields(paquete);
            row.add((double)Math.round((System.currentTimeMillis() - this.tiempoAnterior)*1* 100d) / 100d);//tiempo
            byte[] areglo = { paquete.header[26], paquete.header[27], paquete.header[28],paquete.header[29]};
            int[] direccionOrigenBytes = bytearray2intarray(areglo);             
            String direccionOrigenString=""+direccionOrigenBytes[0]+"."+direccionOrigenBytes[1]+"."+direccionOrigenBytes[2]+"."+direccionOrigenBytes[3];
            row.add(direccionOrigenString);
            
            byte[] areglo2 = { paquete.header[30], paquete.header[31], paquete.header[32],paquete.header[33]};
            direccionOrigenBytes = bytearray2intarray(areglo2);             
            String direccionOrigenString2=""+direccionOrigenBytes[0]+"."+direccionOrigenBytes[1]+"."+direccionOrigenBytes[2]+"."+direccionOrigenBytes[3];
            row.add(direccionOrigenString2);
            
            byte[] arregloProtocolo = {paquete.header[23]};
            int[] protocolo = bytearray2intarray(arregloProtocolo); 
            row.add(protocolo[0]);
            
           
            
            
            
            //------------
            
            
             ByteBuffer bb=ByteBuffer.allocate(2);
             bb.order(ByteOrder.LITTLE_ENDIAN);
             bb.put(paquete.header[17]);
             bb.put(paquete.header[16]);
             short tamanioDecimal=bb.getShort(0);
             row.add(tamanioDecimal);
             
             
           
             
            /* short TypeOfService=bb.getShort(0);
            row.add(TypeOfService);
            row.add("lol");
            row.add("que hubo");
            */model.addRow(row);
             
        }

        //IP
        //-----------------------------------------------------------------------------------
    }

    public int[] bytearray2intarray(byte[] barray)
            {
              int[] iarray = new int[barray.length];
              int i = 0;
              for (byte b : barray)
                  iarray[i++] = b & 0xff;
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

    public PaqueteIP captureIPFields(IPPacket paquete) {
        //---------CREACION DE VARIABLES DE IP--------------------------------------------
        PaqueteIP paqueteIp = new PaqueteIP();
        int version = paquete.version;//VERSION DEL PROTOCOLO IP
        int Protocol = paquete.header[23];//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE
        int HeaderLength = 20;//TAMAÑO DE LA TRAMA ETHERNET
        int Identification = paquete.ident;
        byte[] arreglo = {paquete.header[18], paquete.header[19]};
        String IdentificationHexa = transformadorAHexa(arreglo);

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
        paqueteIp.setHeaderLength(HeaderLength);                //= HeaderLength;
        paqueteIp.setIdentification(Identification);            //= Identification;
        paqueteIp.setProtocol(Protocol);                        // = Protocol;
        paqueteIp.setversion(version);                          // = version; 
        paqueteIp.setIdentificationHexa(IdentificationHexa);    // = IdentificationHexa;
        return paqueteIp;
    }

    @Override
    public void receivePacket(Packet packet) {
        
        

        insertarEnTabla(packet);

    }

}
