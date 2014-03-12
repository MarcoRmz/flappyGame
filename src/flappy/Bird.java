/**
 * Clase Bola
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 02/28/14
 * @version 1.3
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

    //hereda javadoc de base
    public void guardar(PrintWriter writer) {
        super.guardar(writer);
        /*writer.println(CONT);
        writer.println(velocidadX);
        writer.println(velocidadY);*/
    }

    //hereda javadoc de base
    public void cargar(Scanner scanner) {
        super.cargar(scanner);
        /*CONT = Integer.parseInt(scanner.nextLine());
        velocidadX = Integer.parseInt(scanner.nextLine());
        velocidadY = Integer.parseInt(scanner.nextLine());*/
    }

    void salta() {
        setVelY(-3);
    }
}
