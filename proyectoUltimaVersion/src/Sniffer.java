
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.*;



    public class Sniffer implements Runnable {

        

        boolean isRunning;
        boolean modoDeCaptura;

        NetworkInterface[] dispositivos;
        JpcapCaptor capturador;
        NetworkInterface dispositivoRealNoFake;

        public boolean isModoDeCapura() {
            return modoDeCaptura;
        }

        public void setModoDeCapura(boolean modoDeCapura) {
            this.modoDeCaptura = modoDeCapura;
        }

        public Sniffer() {
            isRunning = true;
            dispositivos = JpcapCaptor.getDeviceList();

            System.out.println("cantidad de dispositivos : " + dispositivos.length);

        }

        public ArrayList<String> getNombreDispositivos() {
            ArrayList<String> devices = new ArrayList<>();
            for (NetworkInterface n : dispositivos) {
                devices.add(n.name);
                System.out.println(n.name);
            }
            return devices;
        }

        private NetworkInterface buscarDispositivo(String nombre) {
            for (NetworkInterface n : dispositivos) {
                if (n.name.equals(nombre)) {
                    return n;
                }
            }
            return null;
        }

        @Override
        public void run() {
            try {
//            Hamilton -> [4]
//            Camilo -> [2] Briam

                capturador = JpcapCaptor.openDevice(dispositivoRealNoFake, 65535, modoDeCaptura, 20);
                while (isRunning) {
                    capturador.processPacket(1, new Receptor());
                }
                capturador.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void setIsRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        public void setDispositivos(NetworkInterface[] dispositivos) {
            this.dispositivos = dispositivos;
        }

        public void setCapturador(JpcapCaptor capturador) {
            this.capturador = capturador;
        }

        public void setDispositivoRealNoFake(NetworkInterface dispositivoRealNoFake) {
            this.dispositivoRealNoFake = dispositivoRealNoFake;
        }

        public boolean isIsRunning() {
            return isRunning;
        }

        public NetworkInterface[] getDispositivos() {
            return dispositivos;
        }

        public JpcapCaptor getCapturador() {
            return capturador;
        }

        public NetworkInterface getDispositivoRealNoFake() {
            return dispositivoRealNoFake;
        }

        void modificarInterfaceDeRed(String d, boolean modoDeCaptura2) {
            this.setDispositivoRealNoFake(this.buscarDispositivo(d));
            this.setModoDeCapura(modoDeCaptura2);
            System.out.println(modoDeCaptura2);
            System.out.println(dispositivoRealNoFake.name);//PARA VERIFICAR QUE SE MODIFICÓ DE FORMA CORRECTA
        }

    }


