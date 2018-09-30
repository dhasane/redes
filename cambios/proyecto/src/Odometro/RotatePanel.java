package Odometro;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RotatePanel extends JPanel {

    int angulo;

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
        this.angulo = 180 + ang;
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
