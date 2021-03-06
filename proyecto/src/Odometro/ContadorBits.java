package Odometro;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class ContadorBits implements Runnable //extends Thread
{
    int segundos;
    RotatePanel rp;
    ReceptorBits rb;
    int anterior;
    int []bitsSeg;
    boolean modoDeCaptura;
    Thread hilo;
    
    NetworkInterface[] dispositivos;

    private volatile boolean paused;
    private final Object pauseLock = new Object();
    long ini;
    long end;
    private int numHilos;
    private final int cantHilos;
    Semaphore sem;
    
    boolean isRunning;
    
    public void startTask() {
        
        // se llaman a los hilos 
        for(int a = 0 ; a < cantHilos ; a ++) 
        {
            bitsSeg[a] = 0;
            hilo = new Thread(this);
            hilo.start();
        }
    }
        
    
    public ContadorBits(RotatePanel rrp) {
        isRunning = true;
        this.numHilos = 0;
        this.anterior = 0;
        this.segundos = 0 ;
        this.rp = rrp;
        paused = false;
        this.modoDeCaptura = true;
        dispositivos = JpcapCaptor.getDeviceList();
        ini = System.nanoTime();
        
        cantHilos = dispositivos.length;
        bitsSeg = new int[cantHilos];
        sem = new Semaphore(cantHilos,true);
        
        
        //this.startTask();
    }

    public void pause() {
        paused = true;
        rp.rotar(0);
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    void contarBitsSegundo(int length,int pos) {
        end = System.nanoTime();
        
        long elapsedTime = end - ini;
        
        int act = (int) (elapsedTime / 1000000000);
        
        try {
            //        desde aqui necesita un semaforo
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(ContadorBits.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (act > anterior) // falta hacer un buen reconocimiento de segundo 
        {
            this.segundos++;
//            System.out.println("total en "+pos+" : "+ bitsSeg[pos]+"     seg : "+segundos);
            int total = 0 ;
            for (int a = 0 ; a < cantHilos ; a ++)
            {
                total += bitsSeg[a];
                bitsSeg[a] = 0;
            }
            //System.out.println("total "+total);
            rp.rotar(total);
            this.anterior = act;
        }
        sem.release();
//        hasta aqui
        
        
        bitsSeg[pos] += length;
    }
    
    public void endTask() {
        isRunning = false;
    }
    @Override
    public void run() {
        
        try {
//        semaforo
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(ContadorBits.class.getName()).log(Level.SEVERE, null, ex);
        }
        int pos = numHilos;
        numHilos++;
        sem.release();
//        fin semaforo

        // le dice a todos los dispositivos que miren por entradas
        
        try {
            NetworkInterface dispositivo;

            dispositivo = dispositivos[pos];

//            System.out.println("-------- disp " + dispositivo.name + "  " + pos);

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

                ReceptorBits receptor = new ReceptorBits(this,pos);
                capturador.processPacket(1, receptor);

            }
            //System.out.println("Ha finalizado el sniffer");
            capturador.close();

        } catch (IOException ex) {

        }
        
        
    }

        
}
