/**
 * Clase Canasta
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 02/28/14
 * @version 1.3
 */

package parabolico;

import java.io.PrintWriter;
import java.util.Scanner;

public class Canasta extends Base {

    //Constantes strings
    private static final String STR1 = "CAPTURADO";
    private static final String STR2 = "PAUSADO";

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto Bueno.
     * @param posY es el <code>posiscion en y</code> del objeto Bueno.
     * @param anima es la <code>animacion</code> del objeto Bueno.
     */
    public Canasta(int posX, int posY, Animacion anima) {
        super(posX, posY, anima);
    }

    /**
     * Metodo <I>getStr1</I> sobrescrito de la clase <code>Bueno</code>.<P>
     * En este metodo se regresa el valor de STR1.
     *
     * @return  regresa el valor de STR1 de tipo <code>String</code>
     */
    public String getStr1() {
        return STR1;
    }

    //Metodo regresa STR1
    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Bueno</code>.<P>
     * En este metodo se regresa el valor de STR2.
     *
     * @return  regresa el valor de STR2 de tipo <code>String</code>
     */
    public String getStr2() {
        return STR2;
    }

    //hereda javadoc de base
    public void guardar(PrintWriter writer) {
        super.guardar(writer);
    }

    //hereda javadoc de base
    public void cargar(Scanner scanner) {
        super.cargar(scanner);
    }
}
