/**
 * Clase Juego
 *
 * @author Marco Ramírez A01191344
 * @author Alfredo Altamirano A01191157
 * @date 03/12/14
 * @version 1.0
 */

package flappy;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Juego extends JFrame implements Runnable, KeyListener,
        MouseListener, MouseMotionListener {

    public Juego() {
        init();
        start();
    }

    // Se declaran las variables.
    //Objetos de la clase Animacion para el manejo de la animación
    private Animacion animaBird;
    private Animacion animaUsb, animaUsb2;

    //constantes de dificultad por nivel
    private static final float velSalto = 7.5f; //velocidad en y al saltar
    private static final float gravedad = .55f; //cambio de velocidad en y por frame
    private static final float velUsb = 4.5f; //velocidad de los tubos en x
    private static final int separacion = 220; //separacion entre el tubo de arriba y abajo

    //Variables de control de tiempo de la animación
    private long tiempoActual;

    private static final long serialVersionUID = 1L;
    private static final int VIVO = 0, MUERTO = 1, NO_EMPEZADO = 2, PAUSA = 4; //posibles estados del juego

    private Image dbImage;	// Imagen a proyectar
    private Image BKG;  //Imagen de fondo
    private Graphics dbg;	// Objeto grafico

    private Bird bird;    // Objeto de la clase bola
    private LinkedList<Usb> arriba, abajo; //los tubos de arriba y abajo

    private boolean salta, restart; //indica si salta el pajaro y si se restartea el juego
    private int score; //score del jugador
    private int state; //estado del juego
    private int nivel; //nivel de dificultad
    private String nombre; //nombre del jugador

    //regresa la separacion de las columnas en funcion del nivel de dificultad
    private int getSeparacionColumnas(int nivel) {
        if(nivel >= 4) //despues de nivel 4 ya no se juntan mas porque no se puede
            return 120;
        return 280 - nivel*40;
    }

    //regresa la minima posicion en y del tubo en funcion del nivel de dificultad
    private int getMin(int nivel) {
        if (nivel > 20) {
            return 0;
        }
        return 200 - nivel * 10;
    }

    //regresa la maxima posicion en y del tubo en funcion del nivel de dificultad
    private int getMax(int nivel) {
        if (nivel > 20) {
            return 800;
        }
        return 600 + nivel * 10;
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(480, 800);  //Declara el tamaño del applet
        
        //carga el background
        BKG = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("background.png"));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        //Se cargan las imágenes(cuadros) para la animación de bola y canasta
        Image bird1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("bird1.png"));
        Image usb1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("usb.png"));
        Image usb2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("usb2.png"));

        //Se crea la animación del parajaro y del usb
        animaBird = new Animacion();
        animaBird.sumaCuadro(bird1, 150);
        animaUsb = new Animacion();
        animaUsb.sumaCuadro(usb1, 150);
        animaUsb2 = new Animacion();
        animaUsb2.sumaCuadro(usb2, 150);

        startGame(); //inicializa el juego
    }

    private void startGame() {

        //Crea personaje bird y lo pone en el centro
        bird = new Bird(getWidth() / 2, getHeight() / 2, animaBird);
        bird.setPosX(bird.getPosX() - bird.getAncho() / 2);
        bird.setPosY(bird.getPosY() - bird.getAlto() / 2);
        bird.setAccelY(gravedad);

        //crea las listas de tubos
        arriba = new LinkedList<>();
        abajo = new LinkedList<>();

        //inicializa las variables del juego
        nivel = 0;
        score = 0;
        state = NO_EMPEZADO; //pone el estado actual a no empezado

        //inserta 3 tubos
        insertaUsbs(getSeparacionColumnas(nivel) * 3);
        insertaUsbs(getSeparacionColumnas(nivel) * 4);
        insertaUsbs(getSeparacionColumnas(nivel) * 5);

    }

    //inserta un tubo en la lista de tubos dada la posicion en x
    private void insertaUsbs(int x) {
        Random r = new Random();
        //se selecciona al azar el punto medio entre los tubos en base al minimo y al maximo
        int puntoMedio = r.nextInt(getMax(nivel) - separacion - getMin(nivel)) + getMin(nivel) + separacion / 2;
        
        //agrega tubos a la lista de tubos
        arriba.addLast(new Usb(x, puntoMedio - separacion / 2 - 800, animaUsb2));
        abajo.addLast(new Usb(x, puntoMedio + separacion / 2, animaUsb));
        //les pone velocidad en x a los tubos
        arriba.peekLast().setVelX(-velUsb);
        abajo.peekLast().setVelX(-velUsb);
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
        //marda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        while (true) {
            //Checa si la variable bool pausa para que no se actualize el juego
            if (state < 3) {
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
     * Actualiza animacion de objetos bola y canasta. Actualiza vidas y bolas
     * caidas.
     *
     */
    public void actualiza() {

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        //si el pajaro esta vivo y jugando
        if (state == VIVO) {
            bird.actualizaPosicion(); //se actualiza su posicion en base a su velocidad
            boolean cambioNivel = false; //indica si se incremento la dificultad
            for (Usb usb : arriba) { //para cada usb, se actualiza su posicion y se incrementa el score si pasa al pajaro
                usb.actualizaPosicion();
                if (!usb.haIncrementadoScore() && usb.getPosX() + usb.getAncho() / 2 <= bird.getPosX() + bird.getAncho() / 2) {
                    usb.incrementarScore();
                    score++;
                    if (score % 10 == 0) { //si en nivel es un multiplo de 10 se incrementa la dificultad
                        nivel++;
                        //se actualizan las velocidades de los tubos
                        for (Usb usb2 : arriba) {
                            usb2.setVelX(-velUsb);
                        }
                        for (Usb usb2 : abajo) {
                            usb2.setVelX(-velUsb);
                        }
                    }
                }
            }

            for (Usb usb : abajo) {
                usb.actualizaPosicion(); //se actualizan las posiciones de los tubos en base a su velocidad
            }
            //si debe saltar el pajaro, se incrementa su velocidad en y
            if (salta) {
                salta = false;
                bird.salta();
                bird.setVelY(-velSalto);
            }
        } else if (state == MUERTO) {
            bird.actualizaPosicion(); //si esta muerto el pajaro y se presiono space o click, se restartea el juego
            if (restart) {
                restart = false;
                startGame();
            }
        }

    }

    //indica que se hace cuando muere el pajaro
    private void morir() {
        state = MUERTO; //se cambia el estado del juego a muerto
        bird.setVelY(0); // se detiene el pajaro del salto
        nombre = JOptionPane.showInputDialog(this, "Ingresa tu nombre:", ""); //se muestra un mensaje para escribir el nombre
        
        //se cargan los scores anteriores, se agrega el nuevo y se guarda todo
        try {
            List<String> scores = cargar("src/flappy/saveFile.txt");
            scores.add(nombre + " - " + score);
            guardar("src/flappy/saveFile.txt", scores); // se guarda el juego
        } catch (IOException ex) { //si no se pudo se muestra el error
            System.out.println("Error al guardar: " + ex);
        }
    }
    
    /**
     * Metodo usado para checar las colisiones del objeto bola y canasta con las
     * orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (state == VIVO) {
            //colision del pajaron con la ventana
            if (bird.getPosY() + bird.getImageIcon().getIconHeight() >= getHeight() || bird.getPosY() <= 0) {
                morir();
            }
            
            //si se sale el tubo de la ventana, se quita de la lista y se agrega otro al final
            if (arriba.peekFirst().getPosX() + arriba.peekFirst().getAncho() <= 0) {
                insertaUsbs(getWidth() + getSeparacionColumnas(nivel));
                arriba.pollFirst();
                abajo.pollFirst();
            }
            
            //interseccion de los tubos de arriba con el pajaro
            for (Usb usb : arriba) {
                if (bird.intersecta(usb)) {
                    morir();
                }
            }
            //interseccion de los tubos de abajo con el pajaro
            for (Usb usb : abajo) {
                if (bird.intersecta(usb)) {
                    morir();
                }
            }
        } else if (state == MUERTO) { //si el pajaro esta muerto
            //colision del pajaro con el piso, para que se detenga el pajaro
            if (bird.getPosY() + bird.getImageIcon().getIconHeight() >= getHeight()) {
                bird.setAccelY(0);
                bird.setPosY(getHeight() - bird.getImageIcon().getIconHeight());
            }
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
        if (state == MUERTO) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                restart = true;
            }
        } else if (state == VIVO && e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (bird.getPosY() > 0) {
                salta = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_P) { //se pausa el juego
            if (state < 3) {
                state += PAUSA;
            } else if ((state & 4) != 0) {
                state -= PAUSA;
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
        if (state == NO_EMPEZADO) { //empieza a jugar
            state = VIVO;
            salta = true;
        } else if (state == VIVO) {
            if (bird.getPosY() > 0) { //salta
                salta = true;
            }
        } else if (state == MUERTO) { //se restartea el juego
            restart = true;
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
        g.clearRect(0, 0, getWidth(), getHeight());
        //dibuja el background
        g.drawImage(BKG, 0, 0, null);
        //dibuja al pajaro
        g.drawImage(bird.getImagenI(), (int) bird.getPosX(), (int) bird.getPosY(), null);
        //dibuja los tubos de arriba
        for (Usb usb : arriba) {
            g.drawImage(usb.getImagenI(), (int) usb.getPosX(), (int) usb.getPosY(), null);
        }

        //dibuja los tubos de abajo
        for (Usb usb : abajo) {
            g.drawImage(usb.getImagenI(), (int) usb.getPosX(), (int) usb.getPosY(), null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        //escribe el score
        g.drawString(String.valueOf(score), getWidth() / 2 - 20, getHeight() / 3);

        if (state == MUERTO) { //si esta muerto, se muestra un mensaje para volver a jugar
            g.drawString("Volver a jugar.", getWidth() / 2 - 120, getHeight() / 2);
        }
    }

    //guarda los scores en el archivo
    private void guardar(String nombreArchivo, List<String> scores) throws IOException {
        File file = new File(nombreArchivo); // crea un objeto archivo a partir del nombre dado
        if (!file.exists()) { //si no existe el archivo, lo crea
            file.createNewFile();
        }
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) { //crea un escritor que se autocierra
            for(String score : scores) { //guarda cada score de la lista
                writer.println(score);
            }
        }
    }

    //carga los scores de la lista
    private List<String> cargar(String nombreArchivo) throws FileNotFoundException {
        File file = new File(nombreArchivo);
        if (!file.exists()) { //si no existe el archivo
            throw new FileNotFoundException(); //tira una excepcion
        }
        
        List<String> scores = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) { //crea un lector que se autocierra
            while(scanner.hasNextLine()) {
                scores.add(scanner.nextLine()); //pone el score en la lista
            }
        }
        
        return scores;
    }
}
