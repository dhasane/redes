
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @see https://stackoverflow.com/questions/6333464
 */
public class RotatePanel extends JPanel {

    Graphics gg;
    int angulo;
    int w2;
    int h2;
    //JFrame f;
    
    public RotatePanel() {
        this.angulo = 0;
        //this.f = new JFrame("RotatePanel");
        //this.display();
        this.setPreferredSize(new Dimension(320, 240));
        this.add(new JLabel("------------------", JLabel.CENTER));
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        gg = g;
        Graphics2D g2d = (Graphics2D) g;
        w2 = getWidth() / 2;
        h2 = getHeight() / 2;
        g2d.rotate(Math.toRadians(this.angulo), w2, h2);
        super.paintComponent(g);
    }

    void rotar(int loquesea) {
        
        //this.remove(f);
        //f = new JFrame("RotatePanel");
        this.angulo = loquesea;
        this.update(gg);
        //this.display();
    }

    /*private void display() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }*/
    
    
    
}
