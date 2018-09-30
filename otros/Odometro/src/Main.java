
import java.awt.EventQueue;
import java.util.Scanner;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Main {
//*
    public static void main(String args[]) {
        
        RotatePanel rp = new RotatePanel();
        JFrame F = new JFrame();
        F.add(rp);
        F.setSize(600,600);
        F.setVisible(true);
        F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int num = -1;
        
        while (num != 0)
        {
            //rp  = new RotatePanel();
            num = leer();
            rp.rotar(num);
        }
    }//*/
    
    static public int leer()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("enter an integer");
        return keyboard.nextInt();
    }
}
