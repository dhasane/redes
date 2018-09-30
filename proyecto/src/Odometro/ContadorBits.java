package Odometro;

import RecepcionDatos.Receptor;
import RecepcionDatos.Red;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class ContadorBits implements Runnable //extends Thread
{
    int segundos;
    RotatePanel rp;
    ReceptorBits rb;
    int anterior;
    int bitsSeg;
    boolean modoDeCaptura;
    Thread hilo;
    
    JpcapCaptor capturador;
    NetworkInterface[] dispositivos;

    private volatile boolean paused;
    private final Object pauseLock = new Object();
    long ini;
    long end;
    
    public void startTask() {
        hilo = new Thread(this);
        hilo.start();
    }
    
    public ContadorBits(RotatePanel rrp) {
        this.anterior = 0;
        this.segundos = 0 ;
        this.rp = rrp;
        this.rb = new ReceptorBits(this);
        this.bitsSeg = 0;
        paused = false;
        this.modoDeCaptura = true;
        dispositivos = JpcapCaptor.getDeviceList();
        ini = System.nanoTime();
        
        this.startTask();
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

    void contarBitsSegundo(int length) {
        end = System.nanoTime();
        
        long elapsedTime = end - ini;
        
        int act = (int) (elapsedTime / 1000000000);
        
        if (act > anterior) // falta hacer un buen reconocimiento de segundo 
        {
            this.segundos++;
            System.out.println("total : "+ bitsSeg+"     seg : "+segundos);
            
            rp.rotar(bitsSeg);
        
            bitsSeg = 0;
            this.anterior = act;
        }
        bitsSeg += length;
    }

    @Override
    public void run() {
        // le dice a todos los dispositivos que miren por entradas

        boolean isRunning = true;
        try {
            for (NetworkInterface dispositivo : dispositivos) {
                capturador = JpcapCaptor.openDevice(dispositivo, 65535, modoDeCaptura, 20);

            }
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

                ReceptorBits receptor = new ReceptorBits(this);
                capturador.processPacket(1, receptor);

            }
            System.out.println("Ha finalizado el sniffer");
            capturador.close();

        } catch (IOException ex) {

        }
        
        
    }

}
