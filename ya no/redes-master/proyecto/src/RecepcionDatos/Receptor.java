package RecepcionDatos;

import Paquetes.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.IPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPv6Option;

public class Receptor implements PacketReceiver {

    JTable tabla;
    int numero;
    long tiempoAnterior;
    Packet paquete;
    PaqueteETHERNET paqueteEthernet;
    
    //Utils utils;

    public Receptor() {
        //utils = new Utils();
        tabla = null;
        paquete = null;
        paqueteEthernet = null;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTiempoAnterior(long tiempoAnterior) {
        this.tiempoAnterior = tiempoAnterior;
    }

    public void setPaqueteEthernet(PaqueteETHERNET paqueteEthernet) {
        this.paqueteEthernet = paqueteEthernet;
    }

    public JTable getTabla() {
        return tabla;
    }

    public int getNumero() {
        return numero;
    }

    public long getTiempoAnterior() {
        return tiempoAnterior;
    }

    public PaqueteETHERNET getPaqueteEthernet() {
        return paqueteEthernet;
    }

    Receptor(JTable tabla) {
        this.tabla = tabla;
    }

    public Packet getPaquete() {
        return paquete;
    }

    public void setPaquete(Packet paquete) {
        this.paquete = paquete;
    }

    public void insertarEnTablaPrimeraSeccion(Packet packet) {
        
        boolean esipv4 = true;
        
        if(packet instanceof IPPacket )
        {
            if(((IPPacket) packet).version != 4)
            {
                esipv4 = false;
            }
          
        }
        else
        {
            esipv4 = false;
        }
        
        if (packet instanceof ICMPPacket || esipv4 ) {

            paquete = packet;
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();
            Vector row = new Vector();
            row.add(numero);
            numero++;
            //-------------------------------------------------------------------------------------------------------------
            row.add((double) Math.round((System.currentTimeMillis() - this.tiempoAnterior) * 1 * 100d) / 100d);
            //-------------------------------------------------------------------------------------------------------------
            byte[] areglo = {paquete.header[26], paquete.header[27], paquete.header[28], paquete.header[29]};
             int[] direccionOrigenBytes = Utils.bytearray2intarray(areglo);
            String direccionOrigenString = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
            row.add(direccionOrigenString);
            //-------------------------------------------------------------------------------------------------------------
            byte[] areglo2 = {paquete.header[30], paquete.header[31], paquete.header[32], paquete.header[33]};
            direccionOrigenBytes = Utils.bytearray2intarray(areglo2);
            String direccionOrigenString2 = "" + direccionOrigenBytes[0] + "." + direccionOrigenBytes[1] + "." + direccionOrigenBytes[2] + "." + direccionOrigenBytes[3];
            row.add(direccionOrigenString2);
            //-------------------------------------------------------------------------------------------------------------
            byte[] arregloProtocolo = {paquete.header[23]};
            int[] protocolo = Utils.bytearray2intarray(arregloProtocolo);
            row.add(Utils.hallarNombreProtocolo((short) protocolo[0]));

            //-------------------------------------------------------------------------------------------------------------
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(paquete.header[17]);
            bb.put(paquete.header[16]);
            short tamanioDecimal = bb.getShort(0);
            row.add(tamanioDecimal);
            if(packet instanceof ICMPPacket){
                row.add("Protocolo ICMP");
            }
            //------------------------------------------------------------------------------------------------------------- 
            model.addRow(row);
        }
    }

    @Override
    public void receivePacket(Packet packet) {

        insertarEnTablaPrimeraSeccion(packet);

    }

}
