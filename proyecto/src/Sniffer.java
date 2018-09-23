import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.*;

//Mio es [2]
/**
 *
 * @author Orlando
 */
public class Sniffer implements Runnable{
 
    /**
     * @param args the command line arguments
     */
    boolean isRunning;
   
    NetworkInterface [] dispositivos;
    JpcapCaptor capturador;
    public static int web, ftp, otros;
    public Sniffer() {
        isRunning = true;
        dispositivos = JpcapCaptor.getDeviceList();
        
        
        
        
    }
    public static void main(String[] args) {
        Sniffer s = new Sniffer();
        s.run();
    }
 
    @Override
    public void run() {
        try {
            capturador = JpcapCaptor.openDevice(dispositivos[2], 65535, true, 20);
            while(isRunning) {
                capturador.processPacket(1, new Receptor());
            }
            capturador.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
}