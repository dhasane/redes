package Firewall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class Conexion implements Runnable 
{
    String[] nombreTabla;
    String[][] direccionIP = new String [2][20];
    Tabla[] tabla = new Tabla[2];
    // recepcion 
    //ReceptorPaquetes rb;
    boolean modoDeCaptura;
    NetworkInterface[] dispositivos;
    byte[] mac;
    
    // para pausa 
    private volatile boolean paused;
    private final Object pauseLock = new Object();
    boolean isRunning;
    
    // hilos
    private Thread hilo;
    private int numHilos;
    private final int cantHilos;
    private Semaphore sem;
    
    /*
    public void startTask() {
        
        // se llama a los hilos 
        //for(int a = 0 ; a < cantHilos ; a ++) 
        {
            hilo = new Thread(this);
            hilo.start();
        }
        
    }*/
    
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
        
        nombreTabla = new String[cantHilos];
        
        obtenerTablaDirecciones();
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
        mac=device.mac_address;
        
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

                        //sender.sendPacket(pak);
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
    /* LLL----
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
                    
            obtenerTablaDirecciones();
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
    */
    
    void imp(String str)
    {
        System.out.print(str);
    }
    
    private void obtenerTablaDirecciones(){
        int contador=0, con=0, no=0;
        String[] datosDireccion;
        List<String> datosIP = new ArrayList<String>();
        List<String> datosMAC = new ArrayList<String>();
        try
        {
            // Se lanza el ejecutable.
            Process p = Runtime.getRuntime().exec("cmd /C arp -a");
            
            // Se obtiene el stream de salida del programa
            InputStream is = p.getInputStream();
            
            /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
            BufferedReader br = new BufferedReader (new InputStreamReader (is));
            
            // Se lee la primera linea
            String aux = br.readLine();
            
            aux = br.readLine();
           

            while (aux!=null)
            {
                
                if(aux.contains("Interfaz")){
                    no = con;  
                    nombreTabla[contador] = aux;
                    imp("pp ---- "+aux+"\n");
                    aux = br.readLine();
                    contador++;
                    con=0;
                }
                if(aux.contains("Internet")){
                    aux = br.readLine();   
                }
                else{
                   direccionIP[contador-1][con] = aux;
                   con++;
                   
                }
                // y se lee la siguiente.
                aux = br.readLine();
            }
        } 
        catch (Exception e)
        {
            // Excepciones si hay algún problema al arrancar el ejecutable o al leer su salida.*/
            e.printStackTrace();
        } 
        
        
     
       
        for(int i=0; i<nombreTabla.length; i++){
            if(nombreTabla[i] != null)
            {
                imp(nombreTabla[i]+"\n");
                datosDireccion = nombreTabla[i].split(" ");
                for(int j=0; j<direccionIP[i].length; j++){
                    if(direccionIP[i][j]!=null && j!=no-1){
                        StringTokenizer tokens = new StringTokenizer(direccionIP[i][j]);
                        datosIP.add(tokens.nextToken());
                        datosMAC.add(tokens.nextToken());
                    } 
                }
                tabla[i] = new Tabla(datosDireccion[1], datosIP, datosMAC,datosIP.size());
                datosIP = new ArrayList<String>();
                datosMAC = new ArrayList<String>();
            }
            
        }
        
        System.out.println("---------------------------------------------");
        for(int i=0; i<tabla.length; i++){
            if (tabla[i] != null)
            {
                System.out.println("Interfaz "+tabla[i].direccion);
                for(int j=0; j<tabla[i].tam; j++){
                     System.out.println("ip MAC  "+tabla[i].direccionesIP.get(j)+ " |-| "+tabla[i].direccionesMAC.get(j));
                }
            }
            
        }
        System.out.println("---------------------------------------------");
        
    }
    
    private Packet modificarPaquete(Packet pak) {
         
        /*
         IPPacket ip = new IPPacket();
         ip.
         // juejuejue, solo para probar que sucede un reenvio
         InetAddress ia = ip.src_ip;
         ip.src_ip = ip.dst_ip;
         ip.dst_ip = ia;
         
         /*/
        
        
         
         EthernetPacket ether=new EthernetPacket();
         ether.frametype=EthernetPacket.ETHERTYPE_IP;
         ether.src_mac=mac;
         byte[] auxMac=obtenerMacDestino(pak);
         if(auxMac!=null){
            ether.dst_mac=auxMac; 
         }else{
             //aun no se que hacer en este caso
         }
         
         
         
         pak.datalink=ether;
         //*/
        return pak;
    }

    private void agregarFiltro(JpcapCaptor captor) throws IOException {
        //captor.setFilter("port 80", true);
    }

    private byte[] obtenerMacDestino(Packet pak) {
        String macAux=null;
        Boolean encontrado=false;
        //byte[] ipOrg={pak.header[26], pak.header[27], pak.header[28], pak.header[29]};
        String ipDes=String.valueOf(pak.header[30] & 0xff)+"."+String.valueOf(pak.header[31] & 0xff)+"." +String.valueOf( pak.header[32] & 0xff)+"."+ String.valueOf(pak.header[33] & 0xff);
        System.out.println("PARA IP "+ipDes+" SE TIENE MAC ");
        for (Tabla tabla1 : tabla) {
            if (tabla1 != null) {
                //System.out.println("Interfaz "+tabla[i].direccion);
                for (int j = 0; j < tabla1.tam; j++) {
                    if (tabla1.direccionesIP.get(j).equals(ipDes)) {
                        macAux = tabla1.direccionesMAC.get(j);
                        System.out.println(macAux);
                        encontrado=true;
                        break;
                    }
                    //System.out.println("ip MAC  "+tabla[i].direccionesIP.get(j)+ " |-| "+tabla[i].direccionesMAC.get(j));
                }
                if(encontrado){
                    break;
                }
            }
        }
        //System.out.println("\n");
        byte[] arreglo = {pak.header[0], pak.header[1], pak.header[2], pak.header[3], pak.header[4], pak.header[5]};
        if(encontrado){
            byte[] mac=stringMacToByteMac(macAux);
            System.out.println("Byte mac "+Byte.toString(mac[0])+" "+Byte.toString(mac[1])+" "+Byte.toString(mac[2])+" "+Byte.toString(mac[3])+" "+Byte.toString(mac[4])+" "+Byte.toString(mac[5])+
                    " Byte mac(packet) " +Byte.toString(arreglo[0])+" "+Byte.toString(arreglo[1])+" "+Byte.toString(arreglo[2])+" "+Byte.toString(arreglo[3])+" "+Byte.toString(arreglo[4])+" "+Byte.toString(arreglo[5]));
        }
        
        
        
        
        return null;
    }
    public byte[] stringMacToByteMac(String cadena) {

        String[] parts = cadena.split("-");
        
        int[] temp = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            temp[i] = Integer.parseInt(parts[i].trim(), 16);
        }
        byte[] valores={(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0};
        //char[] caracteres = new char[parts.length];
        for (int i = 0; i < parts.length; i++) {
            valores[i] = (byte)temp[i];
        }
        
        return valores;
    }

        
}
