package Odometro;

import RecepcionDatos.*;
import Paquetes.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.IPPacket;
import jpcap.packet.ICMPPacket;

public class ReceptorBits implements PacketReceiver {

    Packet paquete;
    ContadorBits cb;
    int pos;
    
    ReceptorBits(ContadorBits aThis,int pos) {
        this.pos = pos;
        paquete = null;
        this.cb = aThis;
    }

    public Packet getPaquete() {
        return paquete;
    }

    public void setPaquete(Packet paquete) {
        this.paquete = paquete;
    }

    @Override
    public void receivePacket(Packet packet) {
        
        if (packet instanceof ICMPPacket || packet instanceof IPPacket) {

            
        }
        paquete = packet;
        int len = paquete.header.length;
        cb.contarBitsSegundo(len,this.pos);
    }

}
