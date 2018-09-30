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
public class PaqueteICMP extends PaqueteIP{
    short TypeOfMessage;//TIPO DEL MENSAJE(34)
    short Code;//NO SE QUE ES(35)
    String CheckSumHexa;//(36-37)
    short IdentifierBE;//IDENTIFICADOR(38-39)
    String IdentifierBEHexa;//------------(38-39)
    short IdentifierLE;//------------(39-38)
    String IdentifierLEHexa;//----------(39-38)
    
    short SequenceNumberBE;//IDENTIFICADOR(40-41)
    String SequenceNumberBEHexa;//------------(40-41)
    short SequenceNumberLE;//------------(41-40)
    String SequenceNumberLEHexa;//----------(41-40)
    
    String Data;//DATOS DEL PAQUETE O TRAMA(42-tamaño del paquete)

    @Override
    public String toString() {
        return "PaqueteICMP{" + "TypeOfMessage=" + TypeOfMessage +"\n"
                +"Code=" + Code+"\n"
                +"CheckSumHexa=" + CheckSumHexa+"\n"
                +"IdentifierBE=" + IdentifierBE+"\n"
                +"IdentifierBEHexa=" + IdentifierBEHexa+"\n"
                +"IdentifierLE=" + IdentifierLE+"\n"
                +"IdentifierLEHexa=" + IdentifierLEHexa+"\n"
                +"SequenceNumberBE=" + SequenceNumberBE+"\n"
                +"SequenceNumberBEHexa=" + SequenceNumberBEHexa+"\n"
                +"SequenceNumberLE=" + SequenceNumberLE+"\n"
                +"SequenceNumberLEHexa=" + SequenceNumberLEHexa+"\n"
                +"Data=" + Data + '}';
    }
    
    

    public PaqueteICMP(short Type, short Code, String CheckSumHexa, short IdentifierBE, String IdentifierBEHexa, short IdentifierLE, String IdentifierLEHexa, short SequenceNumberBE, String SequenceNumberBEHexa, short SequenceNumberLE, String SequenceNumberLEHexa, String Data) {
        this.TypeOfMessage = Type;
        this.Code = Code;
        this.CheckSumHexa = CheckSumHexa;
        this.IdentifierBE = IdentifierBE;
        this.IdentifierBEHexa = IdentifierBEHexa;
        this.IdentifierLE = IdentifierLE;
        this.IdentifierLEHexa = IdentifierLEHexa;
        this.SequenceNumberBE = SequenceNumberBE;
        this.SequenceNumberBEHexa = SequenceNumberBEHexa;
        this.SequenceNumberLE = SequenceNumberLE;
        this.SequenceNumberLEHexa = SequenceNumberLEHexa;
        this.Data = Data;
    }
    
    
    public PaqueteICMP() 
    {
        
    }

    public short getTypeOfMessage() {
        return TypeOfMessage;
    }

    public short getCode() {
        return Code;
    }

    public String getCheckSumHexa() {
        return CheckSumHexa;
    }

    public short getIdentifierBE() {
        return IdentifierBE;
    }

    public String getIdentifierBEHexa() {
        return IdentifierBEHexa;
    }

    public short getIdentifierLE() {
        return IdentifierLE;
    }

    public String getIdentifierLEHexa() {
        return IdentifierLEHexa;
    }

    public short getSequenceNumberBE() {
        return SequenceNumberBE;
    }

    public String getSequenceNumberBEHexa() {
        return SequenceNumberBEHexa;
    }

    public short getSequenceNumberLE() {
        return SequenceNumberLE;
    }

    public String getSequenceNumberLEHexa() {
        return SequenceNumberLEHexa;
    }

    public String getData() {
        return Data;
    }

    public void setTypeOfMessage(short Type) {
        this.TypeOfMessage = Type;
    }

    public void setCode(short Code) {
        this.Code = Code;
    }

    public void setCheckSumHexa(String CheckSumHexa) {
        this.CheckSumHexa = CheckSumHexa;
    }

    public void setIdentifierBE(short IdentifierBE) {
        this.IdentifierBE = IdentifierBE;
    }

    public void setIdentifierBEHexa(String IdentifierBEHexa) {
        this.IdentifierBEHexa = IdentifierBEHexa;
    }

    public void setIdentifierLE(short IdentifierLE) {
        this.IdentifierLE = IdentifierLE;
    }

    public void setIdentifierLEHexa(String IdentifierLEHexa) {
        this.IdentifierLEHexa = IdentifierLEHexa;
    }

    public void setSequenceNumberBE(short SequenceNumberBE) {
        this.SequenceNumberBE = SequenceNumberBE;
    }

    public void setSequenceNumberBEHexa(String SequenceNumberBEHexa) {
        this.SequenceNumberBEHexa = SequenceNumberBEHexa;
    }

    public void setSequenceNumberLE(short SequenceNumberLE) {
        this.SequenceNumberLE = SequenceNumberLE;
    }

    public void setSequenceNumberLEHexa(String SequenceNumberLEHexa) {
        this.SequenceNumberLEHexa = SequenceNumberLEHexa;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
    
    
    
   
    
    
}
