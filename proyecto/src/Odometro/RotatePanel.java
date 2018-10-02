package Odometro;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RotatePanel extends JPanel {

    long angulo;
    
    String fondo = "fondoV3.png";
    String aguja = "agujav3.png";
    int max;
    JLabel label;
    JLabel txt;
    
    public RotatePanel() {
        label = new JLabel("prueba");
        txt = new JLabel("actual / maximo");
        
        this.setPreferredSize(new Dimension(100, 100));
        
        int xl = (int)(this.getPreferredSize().width * 0.8);
        int yl =(int) ( this.getPreferredSize().height * 1.5);
        
        label.setBounds(xl, yl, label.getPreferredSize().width*4, label.getPreferredSize().height);
        label.setVisible(true);
        
        txt.setBounds(xl, yl-15, label.getPreferredSize().width*4, label.getPreferredSize().height);
        txt.setVisible(true);
        
        this.add(txt);
        this.add(label);
        max = 5000;
        this.angulo = 180;
    }
    
    
    public void paintComponent(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage fondo = cargarImagen(this.fondo);
        
        g2d.drawImage(fondo, at, null);
        
        at = AffineTransform.getTranslateInstance(20, 20);
        
        BufferedImage img = cargarImagen(this.aguja);
        at.scale(0.8, 0.8);
        at.rotate(Math.toRadians(this.angulo), img.getWidth() / 2, img.getHeight() / 2);
        
        g2d.drawImage(img, at, null);

    }

    public void rotar(int ang) {
        
        float val;
//        int max = 180000;
        int angmax = 180;
        if (ang > max)
        {
            max = ang;
        }
        
        val = 100 * ang/max; // porcentaje
        val *= 1.8;
        this.angulo = 180 + (int)val;
        
        if (this.angulo >= 360){
            this.angulo = 0;
        }
        label.setText(ang+"/"+max);
        System.out.println(ang +"/"+max);
        repaint();
    }

    BufferedImage cargarImagen(String archivo) {
        BufferedImage image = null;
        String path = "/" + archivo;
        try {
            image = ImageIO.read(RotatePanel.class.getResourceAsStream(path));
        } catch (IOException e) {
            System.err.println("nope ");
        }

        return image;
    }

}
