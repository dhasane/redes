import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    public RotatePanel(int ang) {
        this.angulo = ang;
        this.setPreferredSize(new Dimension(320, 240));
        this.add(new JLabel("------------------", JLabel.CENTER));
    }

    @Override
    public void paintComponent(Graphics g) {
      	gg = g;
        Graphics2D g2d = (Graphics2D) g;
        w2 = getWidth() / 2;
        h2 = getHeight() / 2;
        g2d.rotate( Math.toRadians( this.angulo ) , w2, h2);

        super.paintComponent(g);
    }
  
    void rotar(int loquesea)
    {
        Graphics2D g2d = (Graphics2D) gg;
        g2d.rotate( Math.toRadians( loquesea ) , w2, h2);
    }
  
    private void display() {
        JFrame f = new JFrame("RotatePanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
/*
    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {

        RotatePanel rp = new RotatePanel(6);
        rp.display();
        
        for (int a = 0 ; a < 500 ; a ++)
        {
            rp.rotar(a);
        }
        /*
            rp.display();
            double dd = 600;
            rp.rotar(dd);
            rp.display();
           /* @Override
            public void run() {
                new RotatePanel().display();
            }
        });
    }*/
}
