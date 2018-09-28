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
public class PaqueteIP{
    short version;//VERSION DEL PROTOCOLO IP(14-primer cuarteto)
    short HeaderLength;//TAMAÑO DE LA TRAMA ETHERNET(14-segundo cuarteto)
    //DIFFERENTIATED SERVICECS FIELD(15) ---------------------------------------
    short TotalLength;//TAMAÑO TOTAL DEL CAMPO IP(16-17)
    short Identification;//IDENTIFICACION DEL PAQUETE(18-19)
    String IdentificationHexa;//---------------------------------
    String FlagsHexa;//INDICA FRAGMENTACION DEL PAQUETE(20-21)
    String flagCuartetoPrimerByteBinario;//PARA ANALIZAR LAS POSIBLES FRAGMENTACIONES DEL PAQUETE(20-primer cuarteto)
    short TimeToLive;//TIEMPO DE VIDA DEL PAQUETE(22)
    short Protocol ;//CODIGO DEL PROTOCOLO....  MIRAR CODIGOS POR NOMBRE(23)
    String ProtocolName;//NOMBRE DEL PROTOCOLO ANTERIOR
    String HeaderChecksumHexa;//verificacion(24-25)
    String Source;//DIRECCION IP ORIGEN(26-29)
    String Destination;//DIRECCION IP DESTINO(30-33)
    
    PaqueteICMP icmp=null;

    public PaqueteIP(short version, short HeaderLength, short TotalLength, short Identification, String IdentificationHexa, String FlagsHexa, String flagCuartetoPrimerByteBinario, short TimeToLive, short Protocol, String ProtocolName, String HeaderChecksumHexa, String Source, String Destination) {
        this.version = version;
        this.HeaderLength = HeaderLength;
        this.TotalLength = TotalLength;
        this.Identification = Identification;
        this.IdentificationHexa = IdentificationHexa;
        this.FlagsHexa = FlagsHexa;
        this.flagCuartetoPrimerByteBinario = flagCuartetoPrimerByteBinario;
        this.TimeToLive = TimeToLive;
        this.Protocol = Protocol;
        this.ProtocolName = ProtocolName;
        this.HeaderChecksumHexa = HeaderChecksumHexa;
        this.Source = Source;
        this.Destination = Destination;
    }
    
    
    
    
    
    
    public PaqueteIP() {
    }

    public short getVersion() {
        return version;
    }

    public short getHeaderLength() {
        return HeaderLength;
    }

    public short getTotalLength() {
        return TotalLength;
    }

    public short getIdentification() {
        return Identification;
    }

    public String getIdentificationHexa() {
        return IdentificationHexa;
    }

    public String getFlagsHexa() {
        return FlagsHexa;
    }

    public String getFlagCuartetoPrimerByteBinario() {
        return flagCuartetoPrimerByteBinario;
    }

    public short getTimeToLive() {
        return TimeToLive;
    }

    public short getProtocol() {
        return Protocol;
    }

    public String getProtocolName() {
        return ProtocolName;
    }

    public String getHeaderChecksumHexa() {
        return HeaderChecksumHexa;
    }

    public String getSource() {
        return Source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public void setHeaderLength(short HeaderLength) {
        this.HeaderLength = HeaderLength;
    }

    public void setTotalLength(short TotalLength) {
        this.TotalLength = TotalLength;
    }

    public void setIdentification(short Identification) {
        this.Identification = Identification;
    }

    public void setIdentificationHexa(String IdentificationHexa) {
        this.IdentificationHexa = IdentificationHexa;
    }

    public void setFlagsHexa(String FlagsHexa) {
        this.FlagsHexa = FlagsHexa;
    }

    public void setFlagCuartetoPrimerByteBinario(String flagCuartetoPrimerByteBinario) {
        this.flagCuartetoPrimerByteBinario = flagCuartetoPrimerByteBinario;
    }

    public void setTimeToLive(short TimeToLive) {
        this.TimeToLive = TimeToLive;
    }

    public void setProtocol(short Protocol) {
        this.Protocol = Protocol;
    }

    public void setProtocolName(String ProtocolName) {
        this.ProtocolName = ProtocolName;
    }

    public void setHeaderChecksumHexa(String HeaderChecksumHexa) {
        this.HeaderChecksumHexa = HeaderChecksumHexa;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    
    
}
