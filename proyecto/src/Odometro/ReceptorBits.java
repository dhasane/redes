package Odometro;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class ReceptorBits implements PacketReceiver {

    ContadorBits cb;
    int pos;
    
    ReceptorBits(ContadorBits aThis,int pos) {
        this.pos = pos;
        this.cb = aThis;
    }

    @Override
    public void receivePacket(Packet packet) {
        
        int len = 8 * packet.header.length;
        cb.contarBitsSegundo(len,this.pos);
    }
}