/**
 * Clase Animacion
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 02/28/14
 * @version 1.3
 */

package parabolico;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;

/**
 * La clase Animacion maneja una serie de imágenes (cuadros) y la cantidad de
 * tiempo que se muestra cada cuadro.
 */
public class Animacion {

    private ArrayList<cuadroDeAnimacion> cuadros;
    private int indiceCuadroActual;
    private long tiempoDeAnimacion;
    private long duracionTotal;

    /**
     * Metodo <I>constructor</I> sobrescrito de la clase
     * <code>Animacion</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public Animacion() {
        cuadros = new ArrayList();
        duracionTotal = 0;
        iniciar();
    }

    /**
     * Añade una cuadro a la animación con la duración indicada (tiempo que se
     * muestra la imagen).
     */
    /**
     * Metodo <I>sumaCuadro</I> sobrescrito de la clase
     * <code>Animacion</code>.<P>
     * Añade una cuadro a la animación con la duración indicada (tiempo que se
     * muestra la imagen).
     *
     * @param imagen es la imagen de tipo <code>Image</code>
     * @param duracion es el tiempo de la animacion de tipo <code>long</code>
     * @void
     */
    public synchronized void sumaCuadro(Image imagen, long duracion) {
        duracionTotal += duracion;
        cuadros.add(new cuadroDeAnimacion(imagen, duracionTotal));
    }

    /**
     * Metodo <I>iniciar</I> sobrescrito de la clase <code>Animacion</code>.<P>
     * Inicializa la animación desde el principio a usarse en el
     * <code>Applet</code>.
     */
    public synchronized void iniciar() {
        tiempoDeAnimacion = 0;
        indiceCuadroActual = 0;
    }

    /**
     * Metodo <I>actualiza</I> sobrescrito de la clase
     * <code>Animacion</code>.<P>
     * Actualiza la imagen (cuadro) actual de la animación, si es necesario.
     *
     * @param tiempoTranscurrido es para saber el tiempo que lleva la animacion
     * es de tipo <code>long</code>
     * @void
     */
    public synchronized void actualiza(long tiempoTranscurrido) {
        if (cuadros.size() > 1) {
            tiempoDeAnimacion += tiempoTranscurrido;

            if (tiempoDeAnimacion >= duracionTotal) {
                tiempoDeAnimacion = tiempoDeAnimacion % duracionTotal;
                indiceCuadroActual = 0;
            }

            while (tiempoDeAnimacion > getCuadro(indiceCuadroActual).tiempoFinal) {
                indiceCuadroActual++;
            }
        }
    }

    /**
     * Metodo <I>getImagen</I> sobrescrito de la clase
     * <code>Animacion</code>.<P>
     * Captura la imagen actual de la animación. Regeresa null si la animación
     * no tiene imágenes.
     */
    public synchronized ImageIcon getImagen() {
        if (cuadros.size() == 0) {
            return null;
        } else {
            return getCuadro(indiceCuadroActual).imagen;
        }
    }

    /**
     * Metodo <I>getCuadro</I> sobrescrito de la clase
     * <code>Animacion</code>.<P>
     * En este metodo se obtiene el cuadro deseado por el parametro.
     *
     * @param i es para indicar el cuadro, es de tipo <code>int</code>
     * @return regresa el cuadro deseado, de tipo <code>cuadroDeAnimacion</code>
     */
    private cuadroDeAnimacion getCuadro(int i) {
        return cuadros.get(i);
    }

    //Clase cuadroDeAnimacion
    public class cuadroDeAnimacion {

        ImageIcon imagen;
        long tiempoFinal;

        /**
         * Metodo <I>constructor</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * En este metodo se inizializan las variables o se crean los objetos a
         * usarse en el <code>Applet</code> y se definen funcionalidades.
         */
        public cuadroDeAnimacion() {
            this.imagen = null;
            this.tiempoFinal = 0;
        }

        /**
         * Metodo <I>constructor</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * En este metodo se inizializan las variables o se crean los objetos a
         * usarse en el <code>Applet</code> y se definen funcionalidades.
         *
         * @param imagen es la imagen del cuadro de tipo <code>Image</code>
         * @param tiempoFinal es para el tiempo del cuadro <code>long</code>
         */
        public cuadroDeAnimacion(Image imagen, long tiempoFinal) {
            this.imagen = new ImageIcon(imagen);
            this.tiempoFinal = tiempoFinal;
        }

        /**
         * Metodo <I>getImagen</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * En este metodo se regresa la imagen del cuadro.
         *
         * @return regresa la imagen de tipo <code>Image</code>
         */
        public ImageIcon getImagen() {
            return imagen;
        }

        /**
         * Metodo <I>setImagen</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * En este metodo se pone la imagen que se recibe de parametro.
         *
         * @param imagen es la imagen del cuadro de tipo <code>Image</code>
         * @void
         */
        public void setImagen(Image imagen) {
            this.imagen = new ImageIcon(imagen);
        }

        /**
         * Metodo <I>getTiempoFinal</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * Se regresa el tiempo final del cuadro de animacion.
         *
         * @return regresa el tiempo de tipo <code>long</code>
         */
        public long getTiempoFinal() {
            return tiempoFinal;
        }

        /**
         * Metodo <I>setTiempoFinal</I> sobrescrito de la clase
         * <code>cuadroDeAnimacion</code>.<P>
         * En este metodo se modifica el tiempo de la animacion con el parametro
         * recibido.
         *
         * @param tiempoFinal es el tiempo del cuadro de animacion de tipo
         * <code>long</code>
         * @void
         */
        public void setTiempoFinal(long tiempoFinal) {
            this.tiempoFinal = tiempoFinal;
        }
    }
}
