package Firewall;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class Conexion implements Runnable 
{
    // recepcion 
    //ReceptorPaquetes rb;
    boolean modoDeCaptura;
    NetworkInterface[] dispositivos;
    
    // para pausa 
    private volatile boolean paused;
    private final Object pauseLock = new Object();
    boolean isRunning;
    
    // hilos
    private Thread hilo;
    private int numHilos;
    private final int cantHilos;
    private Semaphore sem;
    
    public void startTask() {
        
        // se llama a los hilos 
        for(int a = 0 ; a < cantHilos ; a ++) 
        {
            hilo = new Thread(this);
            hilo.start();
        }
    }
    
    public Conexion() {
        isRunning = true;
        this.numHilos = 0;
        paused = false;
        this.modoDeCaptura = true;
        dispositivos = JpcapCaptor.getDeviceList();
        cantHilos = dispositivos.length;
        sem = new Semaphore(cantHilos,true);
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
    
    public void endTask() {
        isRunning = false;
    }
    
    /*
    @Override
    public void run() {
        
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        int pos = numHilos;
        numHilos++;
        sem.release();
    
        try {
            NetworkInterface dispositivo;

            dispositivo = dispositivos[pos];

            JpcapCaptor capturador = JpcapCaptor.openDevice(dispositivo, 65535, modoDeCaptura, 20);

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

                ReceptorPaquetes receptor = new ReceptorPaquetes();
                capturador.processPacket(1, receptor);

            }
            capturador.close();

        } catch (IOException ex) {

        }
        
        
    }
    
    /*/
    
    @Override
    public void run() {
        
        JpcapCaptor captor = null ;
        JpcapSender sender = null;
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        int pos = numHilos;
        numHilos++;
        sem.release();
        NetworkInterface device = dispositivos[pos];
        
        
        try {
            captor = JpcapCaptor.openDevice(device,2000,false,5000);
            sender = captor.getJpcapSenderInstance();
            
            
            agregarFiltro(captor);
            
            
            try {
            NetworkInterface dispositivo;

            dispositivo = dispositivos[pos];

            JpcapCaptor capturador = JpcapCaptor.openDevice(dispositivo, 65535, modoDeCaptura, 20);

                while (isRunning) 
                {

                    synchronized (pauseLock) 
                    {
                        if (!isRunning) 
                        {
                            break;
                        }
                        if (paused) 
                        {
                            try {
                                pauseLock.wait();
                            } 
                            catch (InterruptedException ex) 
                            {
                                break;
                            }
                            if (!isRunning) 
                            {
                                break;
                            }
                        }
                    }

                    Packet pak = captor.getPacket();
                    
                    if(pak!=null)
                    {
                        System.out.println(pak);
                    
                        pak = modificarPaquete(pak);

                        sender.sendPacket(pak);
                    }
                    


                }
                capturador.close();

            } 
            catch (IOException ex) 
            {

            }
            
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Packet modificarPaquete(Packet pak) {
        
        
        
        return pak;
    }

    private void agregarFiltro(JpcapCaptor captor) throws IOException {
        //captor.setFilter("port 80", true);
    }

        
}
