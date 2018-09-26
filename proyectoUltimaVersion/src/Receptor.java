
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.ICMPPacket;

public class Receptor implements PacketReceiver {

    public Receptor() {

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
        PaqueteIP paqueteIp = new PaqueteIP();
        int version = paquete.version;//VERSION DEL PROTOCOLO IP
        int Protocol = paquete.header[23];//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE
        int HeaderLength = 20;//TAMAÑO DE LA TRAMA ETHERNET
        int Identification = paquete.ident;
        byte[] arreglo = {paquete.header[18], paquete.header[19]};
        String IdentificationHexa = transformadorAHexa(arreglo);

        //String TotalLength=transformador();
        System.out.println("Paquete IP------------------------"
                + "\nVersion " + version
                + "\nProtocolo " + Protocol
                + "\nHeaderLength " + HeaderLength
                + "\nIdentificationInt " + Identification
                + "\nIdentificationHexa " + IdentificationHexa
                + "\n");

        return paqueteIp;
    }

    @Override
    public void receivePacket(Packet packet) {

        if (packet instanceof ARPPacket) {
            //arp y ethernet
        }
        if (packet instanceof IPPacket) {
            //Ip y ethernet
            IPPacket paqueteIP = (IPPacket) packet;
            captureIPFields(paqueteIP);

            if (packet instanceof ICMPPacket) {
                //icmp
            }
        }
    }

}
