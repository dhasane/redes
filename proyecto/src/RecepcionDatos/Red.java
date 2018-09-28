package RecepcionDatos;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import java.lang.String;
import jpcap.packet.EthernetPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

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
        //To change body of generated methods, choose Tools | Templates.

        sniffer.modificarInterfaceDeRed(d, modoDeCaptura);
        sniffer.setTabla(TablaSniffer);

        sniffer.startTask();
    }

    public Vector<String> conseguirDaticos(int fila) {
        Vector<Packet> vectorPaquetes = sniffer.darVectoricito();
        Packet paquete = vectorPaquetes.get(fila); // saco el valor necesario 

        //System.out.println("EL PAQUETE SELECCIONADO FUE:"+paquete+"\n-----------------------");
        Vector<String> texto = new Vector();

        System.out.print("tipo : (" + paquete.getClass() + ") ");
        if (paquete instanceof ARPPacket) {
            ARPPacket arp = (ARPPacket) paquete;

            System.out.println(" ARP (" + arp.getClass() + ") ");

            texto.add("" + arp);
        } else if (paquete instanceof IPPacket) {
            IPPacket ip = (IPPacket) paquete;
            if (ip.version == 4) {
                System.out.println(" IP (" + ip.getClass() + ")");

                texto.add("" + ip.datalink);
                texto.add("" + ip.data);
                if (paquete instanceof ICMPPacket) {
                    ICMPPacket icmp = (ICMPPacket) paquete;

                    System.out.println(" ICMP (" + icmp.getClass() + ") ");
                    texto.add("" + icmp);

                }
            }

        }

        return texto;
    }

    public void llenarComboBoxDispositivos(JComboBox dispositivosCB) {
        ArrayList<String> dispositivos = sniffer.getNombreDispositivos();
        for (String string : dispositivos) {
            dispositivosCB.addItem(string);
        }
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

}
