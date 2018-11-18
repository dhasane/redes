package Firewall;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
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
        hilo = new Thread(this);
            hilo.start();
        // se llama a los hilos 
        /*for(int a = 0 ; a < cantHilos ; a ++) 
        {
            
        }
        */
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
        for(int i=0;i<dispositivos.length;i++){
            System.out.println(dispositivos[i].description);
        }
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        
        int pos = 1;
        numHilos++;
        sem.release();
        NetworkInterface device = dispositivos[pos];
        NetworkInterface device2 = dispositivos[0];
        
        try {
            captor = JpcapCaptor.openDevice(device,2000,false,5000);
            //sender = captor.getJpcapSenderInstance();
            sender=JpcapSender.openDevice(device2);
                    
            
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
        
         
        
         EthernetPacket ether=new EthernetPacket();
         ether.frametype=EthernetPacket.ETHERTYPE_IP;
         ether.src_mac=new byte[]{(byte)108,(byte)59,(byte)229,(byte)140,(byte)105,(byte)74};
         ether.dst_mac=new byte[]{(byte)112,(byte)28,(byte)231,(byte)235,(byte)32,(byte)79};
         pak.datalink=ether;
        
        return pak;
    }

    private void agregarFiltro(JpcapCaptor captor) throws IOException {
        //captor.setFilter("port 80", true);
    }

        
}
