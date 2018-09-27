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
   PaqueteARP arp;
   PaqueteIP ip;

    public PaqueteETHERNET() {
       
    }

    public PaqueteARP getArp() {
        return arp;
    }

    public void setArp(PaqueteARP arp) {
        this.arp = arp;
    }

    public void setIp(PaqueteIP ip) {
        this.ip = ip;
    }
   

    public PaqueteIP getIp() {
        return ip;
    }
   
    
}
