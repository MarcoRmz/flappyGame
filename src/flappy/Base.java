/**
 * Clase Base
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 03/12/14
 * @version 1.0
 */

package flappy;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.PrintWriter;
import java.util.Scanner;

public class Base {

    private float posX;    //posicion en x.       
    private float posY;	//posicion en y.
    private float velX; //velocidad en x
    private float velY; //velocidad en y
    private float accelX;   //aceleracion en x
    private float accelY;   //aceleracion en y
    private Animacion animacion;  //animacion del objeto

    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param anima es la <code>animacion</code> del objeto.
     */
    public Base(int posX, int posY, Animacion animacion) {
        this.posX = posX;
        this.posY = posY;
        this.animacion = animacion;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Metodo de acceso que regresa el icono del objeto
     *
     * @return icono es el <code>icono</code> del objeto.
     */
    public ImageIcon getImageIcon() {
        return animacion.getImagen();
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho() {
        return animacion.getImagen().getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto() {
        return animacion.getImagen().getIconHeight();
    }

    /**
     * Metodo de acceso que regresa la imagen del icono
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenI() {
        return animacion.getImagen().getImage();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle((int)getPosX(), (int)getPosY(), (int)getAncho(), (int)getAlto());
    }

    /**
     * Checa si el objeto <code>Animal</code> intersecta a otro
     * <code>Animal</code>
     *
     * @param obj el objeto con el que se checa la interseccion
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersecta(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    public void actualizaPosicion() {
        posX += velX;
        posY += velY;
        velX += accelX;
        velY += accelY;
    }
    
    /**
     * Metodo de acceso que modifica variable de velocidad en x
     *
     * @void
     */
    public void setVelX(float x) {
        velX = x;
    }
    
    /**
     * Metodo de acceso que regresa velocidad en x
     *
     * @return un float
     */
    public float getVelX() {
        return velX;
    }
    
    /**
     * Metodo de acceso que modifica variable de velocidad en y
     *
     * @void
     */
    public void setVelY(float y) {
        velY = y;
    }
    
    /**
     * Metodo de acceso que regresa velocidad en y
     *
     * @return un float
     */
    public float getVelY() {
        return velY;
    }
    
    /**
     * Metodo de acceso que modifica variable de aceleracion en x
     *
     * @void
     */
    public void setAccelX(float x) {
        accelX = x;
    }
    
    /**
     * Metodo de acceso que regresa aceleracion en x
     *
     * @return un float
     */
    public float getAccelX() {
        return accelX;
    }
    
    /**
     * Metodo de acceso que modifica variable de aceleracion en y
     *
     * @void
     */
    public void setAccelY(float y) {
        accelY = y;
    }
    
    /**
     * Metodo de acceso que regresa aceleracion en y
     *
     * @return un float
     */
    public float getAccelY() {
        return accelY;
    }
    
}
