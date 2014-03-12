/**
 * JFrame Juego Application
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 02/28/14
 * @version 1.3
 */

package parabolico;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Juego extends JFrame implements Runnable, KeyListener,
        MouseListener, MouseMotionListener {

    public Juego() {
        init();
        start();
    }
    
    // Se declaran las variables.
    //Objetos de la clase Animacion para el manejo de la animación
    private Animacion animaBola;
    private Animacion animaCanasta;

    //Variables de control de tiempo de la animación
    private long tiempoActual;
    int posBX, posBY;
    int posCX, posCY;

    private static final long serialVersionUID = 1L;
    private int direccion;  //Variable direccion de canasta
    private int vidas;  //Vidas del juego
    private int cont;   //Contador para gravedad
    private int velXBola;	// Velocidad de bola en X
    private int velYBola;    // Velocidad de bola en Y
    private int numBolas; // Cuenta las bolas que caen
    private final int MIN = -5;    //Minimo al generar un numero al azar.
    private final int MAX = 6;    //Maximo al generar un numero al azar.
    private Image dbImage;	// Imagen a proyectar
    private Image BKG;  //Imagen de fondo
    private Graphics dbg;	// Objeto grafico
    private SoundClip sad;    // Objeto AudioClip
    private SoundClip joy; //Obbjeto AudioClip
    private Bola bola;    // Objeto de la clase bola
    private Canasta canasta;    //Objeto de la clase canasta
    private boolean click;   //Bool que checa si mouse esta sobre bola
    private boolean pausa;	//Bool que checa si el juego esta en pausa o no
    private boolean desaparece; //Bool que checa si desaparece una bola
    private boolean guarda; //Bool para guardar el juego
    private boolean carga;  //Bool para cargar el juego
    private boolean instrucciones;  //Bool para poner/quitar instrucciones
    private boolean sonido; //Bool para activar/quitar sonido

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(800, 700);  //Declara el tamaño del applet
        direccion = 0;  //Direccion Nula
        vidas = 5;  //Numero de vidas
        cont = 0;   //Cont inicia en 0
        desaparece = false; //Bool inicia en falso
        numBolas = 0;  //Contador empieza en 0
        velXBola = 2;   //Velocidad bola en X
        velYBola = 2; //Velocidad bola en Y
        click = false; //Empieza en falso hasta que mouse presiona sobre objeto bola
        pausa = false;	//Empieza en falso hasta que se presiona la letra "P"
        guarda = false; //Empieza en falso hasta que se presiona la letra "G"
        carga = false; //Empieza en falso hasta que se presiona la letra "C"
        instrucciones = false; //Empieza en falso hasta que se presiona la letra "I"
        sonido = true; //Empieza en true hasta que se presiona la letra "S"
        posBX = (getWidth() / 4) - 65;	// posision de bola en X es un cuarto del applet
        posBY = (getHeight() / 2) + 140;    // posicion de bola en Y y es la mitad del applet
        posCX = (getWidth() / 4);   // posision de canasta en X es un cuarto del applet
        posCY = (getHeight() - (getHeight() / 4));    // posicion de canasta en Y es 3/4 del applet
        setBackground(Color.BLUE);
        BKG = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("BKG.png"));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

         //Se cargan los sonidos.
         sad = new SoundClip("sad.wav");
         joy = new SoundClip("joy.wav");

        //Se cargan las imágenes(cuadros) para la animación de bola y canasta
        Image bola1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("bola1.png"));
        Image bola2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("bola2.png"));
        Image bola3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("bola3.png"));
        Image bola4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("bola4.png"));
        Image canasta1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("lugia1.png"));
        Image canasta2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("lugia2.png"));
        Image canasta3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("lugia3.png"));

        //Se crea la animación de bola
        animaBola = new Animacion();
        animaBola.sumaCuadro(bola1, 150);
        animaBola.sumaCuadro(bola2, 150);
        animaBola.sumaCuadro(bola3, 150);
        animaBola.sumaCuadro(bola4, 150);

        //Crea personaje bola
        bola = new Bola(posBX, posBY, animaBola, velXBola, velYBola);
        bola.setPosY(posBY - bola.getAlto());
        bola.setPosX(posBX - bola.getAncho());

        //Se crea la animación de canasta
        animaCanasta = new Animacion();
        animaCanasta.sumaCuadro(canasta1, 200);
        animaCanasta.sumaCuadro(canasta2, 200);
        animaCanasta.sumaCuadro(canasta3, 200);

        //Crea personaje canasta
        canasta = new Canasta(posCX, posCY, animaCanasta);
        canasta.setPosY(posCY - canasta.getAlto());
        canasta.setPosX(posCX - canasta.getAncho());

    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        while (true) {
            //Checa si la variable bool pausa o instrucciones o guarda es diferente a true para detener el juego
            if (!pausa && !instrucciones) {
                actualiza();
                checaColision();
            }
            repaint(); // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos bola y canasta.
     * Actualiza animacion de objetos bola y canasta.
     * Actualiza vidas y bolas caidas.
     *
     */
    public void actualiza() {

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;
        
        //si se presiono la tecla para guardar
        if(guarda) {
            try { //se intenta guardar
                guardar("saveFile.txt"); // se guarda el juego
            } catch (IOException ex) { //si no se pudo se muestra el error
                System.out.println("Error al guardar: " + ex);
            }
            guarda = false;
        }
        
        //si se presiono la tecla para guardar
        if(carga) {
            try { //se intenta cargar
                cargar("saveFile.txt"); // se carga el juego
            } catch (FileNotFoundException ex) {//si no se pudo se muestra el error
                System.out.println("Error al guardar: " + ex);
            }
            carga = false;
        }
        
        //Actualiza la animación de bola al hacer el click, en base al tiempo transcurrido
        if (click) {
            cont++;
            animaBola.actualiza(tiempoTranscurrido);
            bola.setPosX(bola.getPosX() + velXBola);
            bola.setPosY(bola.getPosY() + velYBola);
            velYBola += cont % 2;
        }
        //Actualiza la animación de canasta cuando se mueve, en base al tiempo transcurrido
        if (direccion != 0) {
            animaCanasta.actualiza(tiempoTranscurrido);
        }
        // Actualiza posicion de la canasta por teclado
        if (direccion == 1) {
            canasta.setPosX(canasta.getPosX() + 5);
        } else if (direccion == 2) {
            canasta.setPosX(canasta.getPosX() - 5);
        }
        
        //Checa bolas caidas, si son 3, decrementa vida y aumenta vel en X y Y
        if (numBolas == 3) {
            numBolas = 0;
            vidas--;
            velXBola++;
            velYBola++;
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto bola y canasta con las
     * orillas del <code>Applet</code>.
     */
    public void checaColision() {
        //Colision de la canasta con la mitad del Applet y la Derecha del Applet
        //Colision lado derecho
        if (canasta.getPosX() + canasta.getAncho() > getWidth()) {
            canasta.setPosX(getWidth() - canasta.getAncho());
        }
        //Colision mitad Applet
        if (canasta.getPosX() < getWidth() / 2) {
            canasta.setPosX(getWidth() / 2);
        }

        //Checa colison de la bola con Applet
        //Checa colision de la bola con la parte inferior del applet.
        if (bola.getPosY() + bola.getAlto() > getHeight()) {
            numBolas++;
            if (sonido) {
                sad.stop();  // se detiene el sonido si es que se esta reproduciendo
                sad.play();  // suena al chocar con el piso
            }
            posBX = (getWidth() / 4) - 65;	// posicion de bola en X inicial
            posBY = (getHeight() / 2) + 140;    // posicion de bola en Y inicial
            //Mueve bola a pos inicial en X y Y
            bola.setPosY(posBY - bola.getAlto());
            bola.setPosX(posBX - bola.getAncho());
            click = false;
        }
        //Checa colision de la bola con la parte superior del applet.
        if (bola.getPosY() < 0) {
            bola.setPosY(0 + bola.getAlto());
        }
        //Checa colision de la bola con la parte derecha del applet.
        if (bola.getPosX() > getWidth()) {
            bola.setPosX(getWidth() - bola.getAncho());
        }

        //Colision entre bola y canasta
        if (canasta.intersecta(bola)) {
            desaparece = true;
            if (sonido) {
                joy.stop();    //se detiene el sonido si es que se esta reproduciendo
                joy.play();    //sad al colisionar
            }
            bola.aumentaConteo();   // Aumenta el score
            posBX = (getWidth() / 4) - 65;	// posicion de bola en X inicial
            posBY = (getHeight() / 2) + 140;    // posicion de bola en Y inicial
            //Mueve bola a pos inicial en X y Y
            bola.setPosY(posBY - bola.getAlto());
            bola.setPosX(posBX - bola.getAncho());
            click = false;
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        //Checa si presiona felcha der
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !instrucciones && !guarda && !carga && !pausa) {
            direccion = 1;
        }
        //Checa si presiona flecha izq
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !instrucciones && !guarda && !carga && !pausa) {
            direccion = 2;
        }

        //Checa si presiona tecla P
        if (e.getKeyCode() == KeyEvent.VK_P && !instrucciones && !guarda && !carga) {
            if (pausa) {
                pausa = false;
            } else {
                pausa = true;
            }
        }
        
        //Checa si presiona tecla G
        if (e.getKeyCode() == KeyEvent.VK_G && !instrucciones && !guarda && !carga && !pausa) {
            guarda = true;
        }

        //Checa si presiona tecla C
        if (e.getKeyCode() == KeyEvent.VK_C && !instrucciones && !guarda && !carga && !pausa) {
            carga = true;
        }

        //Checa si presiona tecla I
        if (e.getKeyCode() == KeyEvent.VK_I && !guarda && !carga && !pausa) {
            if (instrucciones) {
                instrucciones = false;
            } else {
                instrucciones = true;
            }
        }

        //Checa si presiona tecla S
        if (e.getKeyCode() == KeyEvent.VK_S && !instrucciones && !guarda && !carga && !pausa) {
            if (sonido) {
                sonido = false;
                joy.stop();
                sad.stop();
            } else {
                sonido = true;
            }
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        direccion = 0;  //Detiene movimiento de bola
    }

    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar el mouse sobre
     * el objeto bola y variable click se vuelve verdadera
     *
     * @param e es el <code>evento</code> que se genera en al presionar el mouse
     * sobre la bola.
     */
    public void mousePressed(MouseEvent e) {
        if (!instrucciones && !pausa) {
            if ((e.getPoint().distance(bola.getPosX(), bola.getPosY()) < bola.getAncho()) && (e.getPoint().distance(bola.getPosX(), bola.getPosY()) >= 0)) {
                click = true; //al presionar mouse sobre bola, variable es verdadera
                //Genera un numero random para velocidades
                Random r = new Random();
                velXBola = r.nextInt(4) + 6;
                velYBola = -r.nextInt(6) - 11;
            }
        }
    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar el boton del
     * mouse y la variable bool se regresa a falsa.
     *
     * @param e es el <code>evento</code> que se genera en al soltar el boton
     * del mouse y click se vuelve falsa.
     */
    public void mouseReleased(MouseEvent e) {
        
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar el boton del
     * mouse.
     *
     * @param e es el <code>evento</code> que se genera en al presionar el boton
     * del mouse.
     */
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al entrar el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al entrar el mouse.
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseExited</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al salir el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al salir el mouse.
     */
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover un objeto con el
     * mouse y se cambia la posicion del objeto respecto al mouse.
     *
     * @param e es el <code>evento</code> que se genera al mover el mouse.
     */
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el mouse.
     *
     * @param e es el <code>evento</code> que se genera en al mover el mouse
     */
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        //Mientras no este pausado el juego pinta
        if (!pausa && !instrucciones) {
            if (canasta != null && bola != null) {
                if (vidas > 0) {
                    //Dibuja imagen de fondo
                    g.drawImage(BKG, 0, 0, this);

                    //Dibuja la imagen en la posicion actualizada de la canasta
                    g.drawImage(canasta.getImagenI(), canasta.getPosX(), canasta.getPosY(), this);

                    //Dibuja la bola
                    g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);

                    if (desaparece) {
                        //Despliega el string sobre la canasta
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Helvetica", Font.BOLD, 14));
                        g.drawString(canasta.getStr1(), (canasta.getPosX() - 8), (canasta.getPosY() - 5));
                    }
                    desaparece = false; //Reinicia variable bool con false

                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Helvetica", Font.BOLD, 18));
                    g.drawString("Score: " + bola.getConteo(), 120, 40); //Despliega el score
                    g.drawString("Vidas: " + vidas, 30, 40); //Despliega vidas
                }
                else {
                    //Despliega "GAME OVER" y los creditos
                    g.setColor(Color.RED);
                    g.setFont(new Font("Helvetica", Font.BOLD, 40));
                    g.drawString("GAME OVER", (getWidth() / 2) - 50, 100); //Despliega "GAME OVER"
                    //Despliega Creditos
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Helvetica", Font.BOLD, 20));
                    g.drawString("Marco Ramírez A01191344", (getWidth() / 2) - 90, (getHeight() / 2) - 30);
                    g.drawString("Alfredo Altamirano A01191157", (getWidth() / 2) - 90, getHeight() / 2);
                }
            } else {
                //Da un mensaje mientras se carga el dibujo 
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        }
        else if (pausa){
            // Despliega "PAUSE"
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.BOLD, 36));
            g.drawString(canasta.getStr2(), (getWidth() / 2) - 80, (getHeight() / 2) - 20);
        }
        else if (instrucciones) {
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            //Despliega las instrucciones
            g.drawString("Instrucciones", (getWidth() / 2) - 80, (getHeight() / 2) - 70);
            g.drawString("-Click sobre la bola para disparar", (getWidth() / 2) - 130, (getHeight() / 2) - 30);
            g.drawString("-Flechas para mover pokemon de lado a lado", (getWidth() / 2) - 130, (getHeight() / 2) -10);
            g.drawString("-Atrapa las pokebolas para sumar puntos", (getWidth() / 2) - 130, (getHeight() / 2) + 10);
            g.drawString("-Cada 3 pokebolas no atrapadas pierdes 1 vida", (getWidth() / 2) - 130, (getHeight() / 2) + 30);
            g.drawString("-Presiona P para detener el juego", (getWidth() / 2) - 130, (getHeight() / 2) + 50);
            g.drawString("-Presiona I para las instrucciones del juego", (getWidth() / 2) - 130, (getHeight() / 2) + 70);
            g.drawString("-Presiona S para activar/quitar los sonidos", (getWidth() / 2) - 130, (getHeight() / 2) + 90);
            g.drawString("-Presiona G para guardar el juego", (getWidth() / 2) - 130, (getHeight() / 2) + 110);
            g.drawString("-Presiona C para cargar un juego", (getWidth() / 2) - 130, (getHeight() / 2) + 130);
        }
    }

    private void guardar(String nombreArchivo) throws IOException {
        File file = new File(nombreArchivo); // crea un objeto archivo a partir del nombre dado
        if(!file.exists()) { //si no existe el archivo, lo crea
            file.createNewFile();
        }
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) { //crea un escritor que se autocierra
            //guarda todas las variables del juego en un archivo de texto
            writer.println(direccion);
            writer.println(vidas);
            writer.println(cont);
            writer.println(velXBola);
            writer.println(velYBola);
            writer.println(numBolas);
            writer.println(click);
            writer.println(pausa);
            writer.println(desaparece);
            writer.println(guarda);
            writer.println(carga);
            writer.println(instrucciones);
            writer.println(sonido);
            
            //se guardan los objetos bola y canasta
            bola.guardar(writer);
            canasta.guardar(writer);
        }
    }

    private void cargar(String nombreArchivo) throws FileNotFoundException {
        File file = new File(nombreArchivo);
        if(!file.exists()) { //si no existe el archivo
            throw new FileNotFoundException(); //tira una excepcion
        }
        
        //si hay sonidos, se detienen
        joy.stop();
        sad.stop();
        try (Scanner scanner = new Scanner(file)) { //crea un lector que se autocierra
            //se cargan todas las variables del juego de un archivo de texto
            direccion = Integer.parseInt(scanner.nextLine());
            vidas = Integer.parseInt(scanner.nextLine());
            cont = Integer.parseInt(scanner.nextLine());
            velXBola = Integer.parseInt(scanner.nextLine());
            velYBola = Integer.parseInt(scanner.nextLine());
            numBolas = Integer.parseInt(scanner.nextLine());
            click = Boolean.parseBoolean(scanner.nextLine());
            pausa = Boolean.parseBoolean(scanner.nextLine());
            desaparece = Boolean.parseBoolean(scanner.nextLine());
            guarda = Boolean.parseBoolean(scanner.nextLine());
            carga = Boolean.parseBoolean(scanner.nextLine());
            instrucciones = Boolean.parseBoolean(scanner.nextLine());
            sonido = Boolean.parseBoolean(scanner.nextLine());
            
            //se cargan los objetos bola y canasta
            bola.cargar(scanner);
            canasta.cargar(scanner);
        }
    }
}
