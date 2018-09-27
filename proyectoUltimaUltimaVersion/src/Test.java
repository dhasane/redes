
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

   
    
    public void llenarComboBoxDispositivos( JComboBox dispositivosCB) {     
        ArrayList<String> dispositivos=sniffer.getNombreDispositivos();
        for (String string : dispositivos) {
            dispositivosCB.addItem(string);
        }
    }

    void terminarLlenadoDeTabla() {
        sniffer.endTask();
        System.out.println("finalizó");
    }

    void detenerLlenadoDeTabla() {
        sniffer.pause();
        
    }

    void continuarLLenadoDeTabla() {
        sniffer.resume();
    }

    
    
}