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
public class PaqueteICMP {
    int version;//VERSION DEL PROTOCOLO IP
    int Protocol ;//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE
    int HeaderLength;//TAMAÑO DE LA TRAMA ETHERNET
    int Identification;
    String IdentificationHexa;
    
    public PaqueteICMP() 
    {
        
    }
    
    public int getversion() {
        return version;
    }

    public int getHeaderLength() {
        return HeaderLength;
    }

    public int getIdentification() {
        return Identification;
    }

    public int getProtocol() {
        return Protocol;
    }

    public String getIdentificationHexa() {
        return IdentificationHexa;
    }

    public void setHeaderLength(int HeaderLength) {
       this.HeaderLength = HeaderLength;
    }

    public void setIdentification(int Identification) {
        this.Identification = Identification;
    }

    public void setProtocol(int Protocol) {
        this.Protocol = Protocol;
    }

    public void setversion(int version) {
        this.version = version;
    }

    public void setIdentificationHexa(String IdentificationHexa) {
        this.IdentificationHexa = IdentificationHexa;
    }
    
    
}
