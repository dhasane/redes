package Paquetes;


import Paquetes.PaqueteICMP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author briam
 */
public class PaqueteIP {
    public int version;//VERSION DEL PROTOCOLO IP
    public int Protocol ;//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE
    public int HeaderLength;//TAMAÑO DE LA TRAMA ETHERNET
    public int Identification;
    public String IdentificationHexa;
    PaqueteICMP icmp;
    
    public PaqueteIP() {
    }
    
    
}
