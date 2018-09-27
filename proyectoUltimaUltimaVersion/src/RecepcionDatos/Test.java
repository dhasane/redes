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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author briam
 */
public class Test {
    Sniffer sniffer;

    public Test() {
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
        
        Vector<String> texto = null;
        
        if (paquete instanceof ARPPacket)
        {
            // falta sacar datos 
        }
        else if (paquete instanceof ICMPPacket ) 
        {
            
        }
        else if  (paquete instanceof IPPacket )
        {
            
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
