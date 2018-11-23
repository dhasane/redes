package Firewall;

import com.github.ffalcinelli.jdivert.Packet;
import com.github.ffalcinelli.jdivert.WinDivert;
import com.github.ffalcinelli.jdivert.exceptions.WinDivertException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    String[] nombreTabla;
    String[][] direccionIP = new String[2][20];
    Tabla[] tabla = new Tabla[2];
    // recepcion 
    boolean modoDeCaptura;

    // para pausa 
    private volatile boolean paused;
    private final Object pauseLock = new Object();
    boolean isRunning;

    public Conexion() {
        isRunning = true;
        paused = false;
        this.modoDeCaptura = true;

        run();
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

    public void run() {
        //String filtr ="";
        /* String filtr= "true";
        WinDivert w = new WinDivert(filtr);
        boolean seguir = true;
        try {
            w.open(); // packets will be captured from now on
            
            imp("--\n");
            while(seguir)
            {
                vPak(w);
            }
        } catch (WinDivertException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        // Capture only TCP packets to port 80, i.e. HTTP requests.
        WinDivert w = new WinDivert("true");
        try {
            w.open(); // packets will be captured from now on
            while (true) {
                Packet packet = null;
                try {
                    packet = w.recv(); // read a single packet
                } catch (WinDivertException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(packet.toString());
                
                try {
                    w.send(packet);  // re-inject the packet into the network stack
                } catch (WinDivertException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (WinDivertException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        w.close();  // stop capturing packets

    }

    public Packet vPak(WinDivert w) {
        Packet packet = null;
        try {

            // Capture only TCP packets to port 80, i.e. HTTP requests.
            //String filtr = " ";
            //filtr= "tcp.DstPort == 80 and tcp.PayloadLength > 0";
            // Capture only TCP packets to port 80, i.e. HTTP requests.
            packet = w.recv(); // read a single packet
            System.out.println(packet.toString());
            w.send(packet);  // re-inject the packet into the network stack

            w.close();  // stop capturing packets
        } catch (WinDivertException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packet;

    }

    /*
    public void run() {
        
        Packet pak = null;
        String filter = "tcp.DstPort == 80";
        //filter = agregarFiltro();
        WinDivert wd = new WinDivert(filter);
        try {
            
            wd.open();
            
            while (isRunning)
            {
                
                synchronized (pauseLock) 
                {
                    if (!isRunning) 
                    {
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

                try {
                    pak = wd.recv();
                } catch (WinDivertException ex) {
                    imp("--- - -- - ");
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(pak);
                if (pak != null) {

                    //pak = modificarPaquete(pak);
                    if (!bloquear(pak)) {
                        //wd.send(pak);
                    }
                }
            }
            wd.close();

        } catch (WinDivertException ex) {
            imp("NO PERRO\n");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
    private boolean bloquear(Packet pak) {
        boolean ret = true;

        return ret;
    }

    // imprime :v
    void imp(String str) {
        System.out.print(str);
    }

    // obtiene la tabla de relaciones de 
    private void obtenerTablaDirecciones() {
        int contador = 0, con = 0, no = 0;
        String[] datosDireccion;
        List<String> datosIP = new ArrayList<String>();
        List<String> datosMAC = new ArrayList<String>();
        try {
            // Se lanza el ejecutable.
            Process p = Runtime.getRuntime().exec("cmd /C arp -a");

            // Se obtiene el stream de salida del programa
            InputStream is = p.getInputStream();

            /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Se lee la primera linea
            String aux = br.readLine();

            aux = br.readLine();

            while (aux != null) {

                if (aux.contains("Interfaz")) {
                    no = con;
                    nombreTabla[contador] = aux;
                    imp("pp ---- " + aux + "\n");
                    aux = br.readLine();
                    contador++;
                    con = 0;
                }
                if (aux.contains("Internet")) {
                    aux = br.readLine();
                } else {
                    direccionIP[contador - 1][con] = aux;
                    con++;

                }
                // y se lee la siguiente.
                aux = br.readLine();
            }
        } catch (Exception e) {
            // Excepciones si hay algún problema al arrancar el ejecutable o al leer su salida.*/
            e.printStackTrace();
        }

        for (int i = 0; i < nombreTabla.length; i++) {
            if (nombreTabla[i] != null) {
                imp(nombreTabla[i] + "\n");
                datosDireccion = nombreTabla[i].split(" ");
                for (int j = 0; j < direccionIP[i].length; j++) {
                    if (direccionIP[i][j] != null && j != no - 1) {
                        StringTokenizer tokens = new StringTokenizer(direccionIP[i][j]);
                        datosIP.add(tokens.nextToken());
                        datosMAC.add(tokens.nextToken());
                    }
                }
                tabla[i] = new Tabla(datosDireccion[1], datosIP, datosMAC, datosIP.size());
                datosIP = new ArrayList<String>();
                datosMAC = new ArrayList<String>();
            }

        }

        System.out.println("---------------------------------------------");
        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i] != null) {
                System.out.println("Interfaz " + tabla[i].direccion);
                for (int j = 0; j < tabla[i].tam; j++) {
                    System.out.println("ip MAC  " + tabla[i].direccionesIP.get(j) + " |-| " + tabla[i].direccionesMAC.get(j));
                }
            }

        }
        System.out.println("---------------------------------------------");

    }

    private String agregarFiltro() {
        String filtr = "";
        //captor.setFilter("port 80", true);
        //filter = "tcp.DstPort == 80 and tcp.PayloadLength > 0";
        return filtr;
    }

}
