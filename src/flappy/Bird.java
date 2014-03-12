/**
 * Clase Bird
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 03/12/14
 * @version 1.0
 */

package flappy;

import java.io.PrintWriter;
import java.util.Scanner;

public class Bird extends Base {

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto Bola.
     * @param posY es el <code>posiscion en y</code> del objeto Bola.
     * @param anima es la <code>animacion</code> del objeto Bola.
     */
    public Bird(int posX, int posY, Animacion anima) {
        super(posX, posY, anima);
    }
    
    void salta() {
        setVelY(-3);
    }
}
