import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.*;

public class Sniffer implements Runnable{
 
    boolean isRunning;
   
    NetworkInterface [] dispositivos;
    JpcapCaptor capturador;
    public static int web, ftp, otros;
    public Sniffer() {
        isRunning = true;
        dispositivos = JpcapCaptor.getDeviceList();
         
        System.out.println("cantidad de dispositivos : "+dispositivos.length);
        
    }
    public static void main(String[] args) {
        Sniffer s = new Sniffer();
        s.run();
    }
 
    @Override
    public void run() {
        try {
//            Hamilton -> [4]
//            Camilo -> [2]
            capturador = JpcapCaptor.openDevice(dispositivos[4], 65535, true, 20);
            while(isRunning) {
                capturador.processPacket(1, new Receptor());
            }
            capturador.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
}