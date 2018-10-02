/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquetes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jpcap.packet.Packet;

/**
 *
 * @author briam
 */
public final class Utils {

    public static String hallarNombreProtocolo(short num) {
        String nombre;

        switch (num) {
            case 1:
                nombre = "ICMP";
                break;
            case 4:
                nombre = "IPv4";
                break;
            case 6:
                nombre = "TCP";
                break;
            case 17:
                nombre = "UDP";
                break;
            case 41:
                nombre = "IPv6";
                break;
            default:
                nombre = ""+ num;
//                System.out.println("paquete raro :"+nombre);
                nombre = "No reconocido";
                break;
        }
        return nombre;
    }

    public static PaqueteETHERNET captureETHERNETFields(Packet packet) {
        PaqueteETHERNET ethernet = new PaqueteETHERNET();
        ethernet.setType("IPv4");
        byte[] arreglo = {packet.header[0], packet.header[1], packet.header[2], packet.header[3], packet.header[4], packet.header[5]};
        ethernet.setMACDestination(obtenerMac(arreglo));
        byte[] arreglo2 = {packet.header[6], packet.header[7], packet.header[8], packet.header[9], packet.header[10], packet.header[11]};
        ethernet.setMACSource(obtenerMac(arreglo2));

        return ethernet;
    }

    public static String obtenerData(byte[] data) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {

            sb.append(String.format("%02X ", data[i]));
        }
        return "0x" + sb.toString();

    }

    public static int[] bytearray2intarray(byte[] barray) {
        int[] iarray = new int[barray.length];
        int i = 0;
        for (byte b : barray) {
            iarray[i++] = b & 0xff;
        }
        return iarray;
    }

    public static String byteToBinaryString(byte b) {

        String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        System.out.println(s1);
        return s1;
    }

    public static String transformadorAHexa(byte[] arreglo) {

        StringBuilder sb = new StringBuilder();
        for (byte b : arreglo) {
            sb.append(String.format("%02X", b));
        }
        return "0x" + sb.toString();
    }

    public static String obtenerMac(byte[] arreglo) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arreglo.length - 1; i++) {

            sb.append(String.format("%02X:", arreglo[i]));
        }
        sb.append(String.format("%02X", arreglo[arreglo.length - 1]));
        return sb.toString();
    }

    public static short byteToShort(byte[] arreglo) {
        ByteBuffer bb = ByteBuffer.allocate(arreglo.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = arreglo.length - 1; i >= 0; i--) {
            bb.put(arreglo[i]);
        }
        return bb.getShort(0);
    }

    public static int AbyteToAInt(byte b) {
        int iarray ;
        iarray= b & 0xff; 
        return iarray;
    }

    public static String hexaStringToASCII(String cadena) {

        String[] parts = cadena.split("x");
        String part1 = parts[1]; // 123 
        parts = part1.split(" ");
        int[] temp = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            temp[i] = Integer.parseInt(parts[i].trim(), 16);
        }
        char[] caracteres = new char[parts.length];
        for (int i = 0; i < parts.length; i++) {
            caracteres[i] = (char) temp[i];
        }
        String valores = new String(caracteres);
        return valores;
    }

}
