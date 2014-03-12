/*
 * Juego en JFrame de tiro parabólico
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 02/28/14
 * @version 1.3
 */

package parabolico;

import javax.swing.JFrame;

public class Parabolico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Juego app = new Juego();
        app.setVisible(true); 
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}