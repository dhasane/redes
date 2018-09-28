package Paquetes;


import Paquetes.PaqueteIP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author briam
 */
public class PaqueteETHERNET {
   String MACdestination;//DIRECCION MAC DE DESTINO (0-5)
   String MACsource;//DIRECCION MAC DE DESTINO(6-11)
   String type;//PROTOCOLO UTILIZADO A CONTINUACION(12-13)
   PaqueteARP arp=null;
   PaqueteIP ip=null;
   

    public PaqueteETHERNET(String destination, String source, String type) {
        this.MACdestination = destination;
        this.MACsource = source;
        this.type = type;
    }
    
    

   
   
    public PaqueteETHERNET() {
       
    }

    public String getMACDestination() {
        return MACdestination;
    }

    public String getMACSource() {
        return MACsource;
    }

    public String getType() {
        return type;
    }

    public void setMACDestination(String destination) {
        this.MACdestination = destination;
    }

    public void setMACSource(String source) {
        this.MACsource = source;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

    
   
}
