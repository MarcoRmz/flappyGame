/**
 * Clase Usb
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 03/12/14
 * @version 1.0
 */

package flappy;

import java.io.PrintWriter;
import java.util.Scanner;

public class Usb extends Base {
    
    private boolean incrementoScore;
    
    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto Bueno.
     * @param posY es el <code>posiscion en y</code> del objeto Bueno.
     * @param anima es la <code>animacion</code> del objeto Bueno.
     */
    public Usb(int posX, int posY, Animacion anima) {
        super(posX, posY, anima);
    }
    
    /**
    * Metodo <I>incrementarScore</I>
    * Se cambia a verdadera la variable incrementoScore.
    *
    * @void
    */
    public void incrementarScore() {
        incrementoScore = true;
    }
    
    /**
    * Metodo <I>haIncrementadoScore</I>
    * Se regresa la variable incrementoScore.
    *
    * @return regresa el valor de la variable de tipo <code>bool</code>
    */
    public boolean haIncrementadoScore() {
        return incrementoScore;
    }
}
