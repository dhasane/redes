package Firewall;

import com.github.ffalcinelli.jdivert.Enums;
import com.github.ffalcinelli.jdivert.Packet;
import com.github.ffalcinelli.jdivert.WinDivert;
import com.github.ffalcinelli.jdivert.exceptions.WinDivertException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    public Conexion() {
        
        String ubi;
        ubi = "filtros.txt"; // ubicacion archivo filtro
        
        String filtro = leerArchivo(ubi); //lectura de filtro lista negra
        
        System.out.println("filtro : "+filtro);
        
        WinDivert w =  new WinDivert(filtro, Enums.Layer.NETWORK_FORWARD, 0, Enums.Flag.DROP);
        
        try {
            w.open(); // canal abierto
            
            while (true) {
                Packet packet = null;
                try {
                    packet = w.recv(); // leer el siguiente paquete
                    
                } catch (WinDivertException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(packet);
                
                try {
                    w.send(packet); // reenviar paquete
                } catch (WinDivertException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (WinDivertException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        w.close();  // parar captura de paquetes
    }

    private String leerArchivo(String texto) {
        String cadena;
        String cadenaCompleta = "";
        FileReader f = null;
        
        try {
            f = new FileReader(texto);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader b;
        b = new BufferedReader(f);
        try {
            while((cadena = b.readLine())!=null){
                
                if (!cadena.isEmpty()) cadena = arreglarStr(cadena);
                cadenaCompleta = cadenaCompleta + cadena +" ";
            }
            b.close();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cadenaCompleta;
    }

    private String arreglarStr(String cad) {
        
        String[] partes = cad.split("[\\s\\n]"); // parte por espacio y endline
        int tam = partes.length;
        
        for (int a = 0 ; a < tam ; a++)
        {
            String tmp = partes[a].toLowerCase().trim();
            
            String Puerto = "port";
            Puerto = "tcp.DstPort ==";
            
            if (tmp.equals("ip")) {
                partes[a] = "ip.SrcAddr ==";
            } else if(tmp.equals("puerto")){
                partes[a] = Puerto;
            }
            else if(tmp.equals("http")){
                partes[a] = Puerto+" 80";
            }
            else if(tmp.equals("https")){
                partes[a] = Puerto+" 443";
            }
            else if(tmp.equals("telnet")){
                partes[a] = Puerto+" 23";
            }
            else if (tmp.equals("port")) {
                partes[a] = Puerto;
            }
        }
        cad = "";
        for (int a = 0 ; a < tam ; a++)
        {
            cad += partes[a];
            
            if(a!=tam-1) cad += " "; 
        }
        return cad;
    }
}
