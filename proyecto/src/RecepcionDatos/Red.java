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
        sniffer=new Sniffer();
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

    public Vector<String> conseguirDaticos(int fila)
    {
        Vector<Packet> vectorPaquetes = sniffer.darVectoricito();
        Packet paquete = vectorPaquetes.get(fila); // saco el valor necesario 
        
        System.out.println("EL PAQUETE SELECCIONADO FUE:"+paquete+"\n-----------------------");
        Vector<String> texto = null;
        
        System.out.print("tipo : ");
        if (paquete instanceof ARPPacket)
        {
            System.out.println(" ARP");
            ARPPacket arp = (ARPPacket) paquete;
           
            System.out.println(arp);
        }
        else if (paquete instanceof ICMPPacket ) 
        {
            System.out.println(" ICMP");
            ICMPPacket icmp = (ICMPPacket) paquete;
            
            System.out.println(icmp);
        }
        else if  (paquete instanceof IPPacket )
        {
            System.out.println(" IP");
            IPPacket ip = (IPPacket) paquete;
            texto.add(""+ip.datalink);
            texto.add(""+ip);
//            System.out.println(ip.datalink);
//            System.out.println(ip);
        }
        
        return texto;
    }
    
    public void llenarComboBoxDispositivos( JComboBox dispositivosCB) {     
        ArrayList<String> dispositivos=sniffer.getNombreDispositivos();
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
