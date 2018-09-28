package RecepcionDatos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import jpcap.*;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class Sniffer implements Runnable {

    boolean isRunning;
    boolean modoDeCaptura;
    NetworkInterface[] dispositivos;
    JpcapCaptor capturador;
    NetworkInterface dispositivo;
    Thread hiloParaLlenarTabla;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    JTable tabla;
    int contador = 1;
    Vector<Packet> vectorcito;

    public Vector<Packet> darVectoricito() {
        return vectorcito;
    }

    public void agregarPaquete(Packet paquete) {
        vectorcito.add(paquete);
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public void pause() {
        // you may want to throw an IllegalStateException if !running
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }

    public void startTask() {
        hiloParaLlenarTabla = new Thread(this);
        hiloParaLlenarTabla.start();
    }

    public void endTask() {
        // Interrupt the thread so it unblocks any blocking call
        //hiloParaLlenarTabla.interrupt();

        // Change the states of variable
        isRunning = false;
        contador=1;
        //hiloParaLlenarTabla=null;

    }

    public void setModoDeCaptura(boolean modoDeCaptura) {
        this.modoDeCaptura = modoDeCaptura;
    }

    public boolean isModoDeCaptura() {
        return modoDeCaptura;
    }

    public void setModoDeCapura(boolean modoDeCapura) {
        this.modoDeCaptura = modoDeCapura;
    }

    public Sniffer() {

        dispositivos = JpcapCaptor.getDeviceList();
        vectorcito=new Vector<>();

        System.out.println("cantidad de dispositivos : " + dispositivos.length);

    }

    public ArrayList<String> getNombreDispositivos() {
        ArrayList<String> devices = new ArrayList<>();
        for (NetworkInterface n : dispositivos) {
            devices.add(n.name);
            System.out.println(n.name);
        }
        return devices;
    }

    private NetworkInterface buscarDispositivo(String nombre) {
        for (NetworkInterface n : dispositivos) {
            if (n.name.equals(nombre)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public void run() {

        isRunning = true;
        try {
         
            capturador = JpcapCaptor.openDevice(dispositivo, 65535, modoDeCaptura, 20);
            long startTime = System.currentTimeMillis();
            while (isRunning) {
                

                synchronized (pauseLock) {
                    if (!isRunning) { 
                        break;
                    }
                    if (paused) {
                        try {
                            pauseLock.wait(); 
                        } catch (InterruptedException ex) {
                            break;
                        }
                        if (!isRunning) { 
                            break;
                        }
                    }
                }
                Receptor receptor = new Receptor(tabla);
                receptor.numero = contador;
                receptor.tiempoAnterior = startTime;
                
                capturador.processPacket(1, receptor);
                contador=receptor.numero;
                Packet packet;
                packet = receptor.getPaquete();
                
                if (packet != null) {
                    //System.out.println(" el paquete : " + receptor.getPaquete() + " \n");
                    vectorcito.add(receptor.getPaquete());
                }
               

            }
            System.out.println("se ha detenido el sniffer");
            capturador.close();

        } catch (IOException ex) {
            
            ex.printStackTrace();
        }

    }

    public void setDispositivos(NetworkInterface[] dispositivos) {
        this.dispositivos = dispositivos;
    }

    public void setCapturador(JpcapCaptor capturador) {
        this.capturador = capturador;
    }

    public void setDispositivoRealNoFake(NetworkInterface dispositivo) {
        this.dispositivo = dispositivo;
    }

    public NetworkInterface[] getDispositivos() {
        return dispositivos;
    }

    public JpcapCaptor getCapturador() {
        return capturador;
    }

    public NetworkInterface getDispositivo() {
        return dispositivo;
    }

    void modificarInterfaceDeRed(String d, boolean modoDeCaptura2) {
        this.setDispositivoRealNoFake(this.buscarDispositivo(d));
        this.setModoDeCapura(modoDeCaptura2);
        System.out.println(modoDeCaptura2);
        System.out.println(dispositivo.name);//PARA VERIFICAR QUE SE MODIFICÓ DE FORMA CORRECTA
    }

}
