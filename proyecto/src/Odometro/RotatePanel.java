package Odometro;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RotatePanel extends JPanel {

    long angulo;

    public RotatePanel() {
        this.angulo = 180;
        this.setPreferredSize(new Dimension(100, 100));
    }

    public void paintComponent(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage fondo = cargarImagen("fondo.png");
        g2d.drawImage(fondo, at, null);
        
        at = AffineTransform.getTranslateInstance(20, 20);
        
        BufferedImage img = cargarImagen("circ.png");
        at.scale(0.8, 0.8);
        at.rotate(Math.toRadians(this.angulo), img.getWidth() / 2, img.getHeight() / 2);
        
        g2d.drawImage(img, at, null);

    }

    public void rotar(int ang) {
        
        float val;// = ang ;
        int max = 180000;
        int angmax = 180;
        
        val = 100*ang/max; // porcentaje
        System.out.println("---------------------------------------porc ----"+val);
        val *= 1.8;
        // cual porcentaje?
        System.out.println("-------------------------------------------"+val);
        // total == 180
        this.angulo = 180 + (int)val;
        
        if (this.angulo >= 360){
            this.angulo = 0;
        }
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
