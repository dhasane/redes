package Paquetes;

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

    @Override
    public String toString() {
        return "PaqueteETHERNET{"+ "\n"
               +"MACdestination=" + MACdestination + "\n"
               +"MACsource=" + MACsource + "\n"
               +"type=" + type +"\n" 
               +"arp=" + "Aun no ha sido agregado" +'}';
    }
   
   

    public String getMACdestination() {
        return MACdestination;
    }

    public String getMACsource() {
        return MACsource;
    }

    public PaqueteARP getArp() {
        return arp;
    }

    public PaqueteIP getIp() {
        return ip;
    }
   

    public void setMACdestination(String MACdestination) {
        this.MACdestination = MACdestination;
    }

    public void setMACsource(String MACsource) {
        this.MACsource = MACsource;
    }

    public void setArp(PaqueteARP arp) {
        this.arp = arp;
    }

    public void setIp(PaqueteIP ip) {
        this.ip = ip;
    }
   

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
