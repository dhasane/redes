
import java.awt.EventQueue;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Main {
/*
    public static void main(String args[]) {
        
        new prueba().setVisible(true);
        /*RotatePanel rp = new RotatePanel();
        
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
