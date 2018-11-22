/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firewall;

import java.util.List;

/**
 *
 * @author ancam
 */
public class Tabla {
    String direccion;
    List<String> direccionesIP;
    List<String> direccionesMAC;
    int tam;
    
    public Tabla(String d1, List<String> d2, List<String> d3, int capacidad){
        direccion = d1;
        direccionesIP = d2;
        direccionesMAC = d3;
        tam = capacidad;
    }
    
    
}
