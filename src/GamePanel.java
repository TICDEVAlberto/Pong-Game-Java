import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); //medidas de una tabla de ping pong
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameTread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameTread = new Thread(this);
        gameTread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        /** Dibuja todos los elementos, mediante la llamda de los métodos de cada elemento*/
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        /** Permite que todos los elementos empiecen a moverse invocando sus métodos*/
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        /** Calcula todas las colisiones además de la que nos indica si se debe marcar o no un pt*/
        //detectan la colisión entre la bola y un paddle
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;

            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;

            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }


        //colisión de la bola con los bordes y
        if(ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER)) {
            ball.setYDirection(-ball.yVelocity);
        }


        //colisión de los paddles con los bordes y
        if(paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle1.y = (GAME_HEIGHT - PADDLE_HEIGHT);
        }

        if(paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle2.y = (GAME_HEIGHT - PADDLE_HEIGHT);
        }

        //colisiones que calculan a que jugador se le da un pt
        if (ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
        }

        if (ball.x >= (GAME_WIDTH - BALL_DIAMETER)) {
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    public void run() {
        /** Se encarga de crear un efecto de movimiento de los elementos*/
        //game loop - intenta hacerlo mejor
        long lastTime = System.nanoTime();
        double amountOgTicks = 60.0;
        double ns = 1000000000 / amountOgTicks;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    //
    public class AL extends KeyAdapter {
        //clase nos permite el uso de manejo de teclas
        public void keyPressed(KeyEvent e) {
            /** Invoca los métodos del paddle que se encargan de realizar una acción cuando la tecla es presionada*/
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            /** Invoca los métodos del paddle que se encargan de parar la acción de la tecla cuando deja de ser presionada*/
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
