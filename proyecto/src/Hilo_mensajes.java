
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jpcap.packet.ARPPacket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danih
 */
public class Hilo_mensajes extends Thread{
    
    
    JTable TablaSniffer;
    
    
    public Hilo_mensajes(String nombre,JTable TS)
    {
        super(nombre);
        
        TablaSniffer = TS;
    }
    
    public void run()
    {
        System.out.println(this.getName());
        
        DefaultTableModel model = new DefaultTableModel();
        this.TablaSniffer.setModel(model);
        TablaSniffer.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Número", "Tiempo", "Fuente", "Destino", "Protocolo","Tamaño","Información"
                }
        ));
        d=dispositivosCB.getSelectedItem().toString();//PARA BUSCAR EL DISPOSITIVO SELECIONADO

        if(promiscuo.isSelected()){
            modoDeCaptura=true;
        }else{
            modoDeCaptura=false;
        }
        
        //test.llenarTabla(TablaSniffer, d, modoDeCaptura);
        //*/
    }
    
}
