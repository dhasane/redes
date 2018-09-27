
import java.io.UnsupportedEncodingException;
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
    JTable tabla=null; 
    int numero;
    PaqueteETHERNET ethernet;
   public Receptor(JTable tabla) {
       this.tabla=tabla;
    }
   public void insertarEnTabla(Packet packet){
       
       //FILTRADO
       if (packet instanceof ARPPacket) {
            //arp y ethernet
            ethernet=new PaqueteETHERNET();
            System.out.println("ARP"+numero);
            
        }
        if (packet instanceof IPPacket) {
            ethernet=new PaqueteETHERNET();
            System.out.println("IP");
            //Ip
            IPPacket paquete = (IPPacket) packet;
            PaqueteIP paqueteIp=captureIPFields(paquete);
            
            
            
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Vector row = new Vector();
            row.add(numero);
            row.add(paqueteIp.Identification);
            row.add(paqueteIp.IdentificationHexa);
            row.add(paqueteIp.HeaderLength);
            row.add(paqueteIp.Protocol);
            row.add(paqueteIp.version);
            row.add("que hubo");
            model.addRow(row);
            

            
        }
        if (packet instanceof ICMPPacket) {
                //icmp
                System.out.println("ICMP"+numero);
            }
        else{
            System.out.println("OTRO *************************************");
        }
        //-----------------------------------------------------------------------------------
      
        
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
        paqueteIp.HeaderLength=HeaderLength;
        paqueteIp.Identification=Identification;
        paqueteIp.Protocol=Protocol;
        paqueteIp.version=version;
        paqueteIp.IdentificationHexa=IdentificationHexa;
        return paqueteIp;
    }

    @Override
    public void receivePacket(Packet packet) {  
        
        insertarEnTabla(packet);
   
    }

}
