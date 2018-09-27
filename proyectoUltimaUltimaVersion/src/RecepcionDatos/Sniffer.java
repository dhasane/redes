package RecepcionDatos;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import jpcap.*;
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
    int contador=0;
    Vector<Packet> vectorcito;
    
    public Vector<Packet> darVectoricito()
    {
        return vectorcito;
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
//            Hamilton -> [4]
//            Camilo -> [2] Briam

            capturador = JpcapCaptor.openDevice(dispositivo, 65535, modoDeCaptura, 20);
            long startTime = System.currentTimeMillis();
            while (isRunning) {
                contador++;

                synchronized (pauseLock) {
                    if (!isRunning) { // may have changed while waiting to
                        // synchronize on pauseLock
                        break;
                    }
                    if (paused) {
                        try {
                            pauseLock.wait(); // will cause this Thread to block until 
                            // another thread calls pauseLock.notifyAll()
                            // Note that calling wait() will 
                            // relinquish the synchronized lock that this 
                            // thread holds on pauseLock so another thread
                            // can acquire the lock to call notifyAll()
                            // (link with explanation below this code)
                        } catch (InterruptedException ex) {
                            break;
                        }
                        if (!isRunning) { // running might have changed since we paused
                            break;
                        }
                    }
                }
                Receptor receptor=new Receptor(tabla);
                receptor.numero=contador;
                receptor.tiempoAnterior = startTime;

                capturador.processPacket(1, receptor);
                
                vectorcito.add(receptor.getPaquete());
            
                 
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
