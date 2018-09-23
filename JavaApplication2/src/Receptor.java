import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
 import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
 import jpcap.packet.ICMPPacket;
/**
 *
 * @author Orlando
 */
public class Receptor implements PacketReceiver {
 
    public Receptor() {
       
    }
   
    @Override
    public void receivePacket(Packet packet) {
        //System.out.println("Nombre del paquete"+packet.getClass().getName());
        if(packet instanceof ICMPPacket){
            ICMPPacket icmp= (ICMPPacket) packet;
            System.out.println(icmp.toString());
            System.out.println("data "+icmp.data);
            byte[]b=icmp.data;
            String data="";
            for(int i=0;i<b.length;i++)	
                data=data+b;

            System.out.println("RECEIVED PACKET's DATA :"+data);
    
            
        }
         if(packet instanceof ARPPacket)
        {
           /*ARPPacket arp= (ARPPacket) packet;
           System.out.println("To string "+arp.toString()); 
           System.out.println("Data "+arp.data);
           System.out.println("caplen "+arp.caplen);
           System.out.println("datalink "+arp.datalink);
           System.out.println("hardtype "+arp.hardtype);
           System.out.println("header "+arp.header);
           System.out.println("hlen "+arp.hlen);
           System.out.println("len "+arp.len);
           System.out.println("operation "+arp.operation);
           System.out.println("plen "+arp.plen);
           System.out.println("prototype "+arp.prototype);
           System.out.println("sec "+arp.sec); 
           System.out.println("sender_hardaddr "+arp.getSenderHardwareAddress().getClass().toString());
           System.out.println("sender_protoaddr"+arp.getSenderProtocolAddress().getClass().toString());
           System.out.println("target_hardaddr "+arp.target_hardaddr);
           System.out.println("target_protoaddr "+arp.target_protoaddr);
           System.out.println("usec "+arp.usec);
            System.out.println("-----------------------------------------");*/
             
            
        }
        
        
 
        if (packet instanceof TCPPacket) {
           /* TCPPacket tcp = (TCPPacket) packet;
            System.out.println("IP fuente " + tcp.src_ip
            + "\nPuerto fuente " + tcp.src_port
            + "\nIp dest " + tcp.dst_ip
            + "\nPuerto dest " + tcp.dst_port
            + "\nLongitud " + tcp.length
            + "\nHeader " +tcp.header
            + "\nDatalink " + tcp.datalink
            + "\n-----------------------------------------------");
           if(tcp.ack){
               System.out.println("ACK "+ tcp.ack_num);
           }
            if(tcp.src_port == 80 || tcp.src_port == 443 || tcp.dst_port == 80 || tcp.dst_port == 443)
                System.err.println("Paquetes que pasan por web " + ++Sniffer.web);
            else if(tcp.src_port == 21 || tcp.dst_port == 21 || tcp.dst_port == 22 || tcp.src_port == 22)
                System.err.println("Paquetes que pasan por ftp " + ++Sniffer.ftp);
            else
                System.err.println("Paquetes otros " + ++Sniffer.otros);*/
               
        }
     
    }
   
}