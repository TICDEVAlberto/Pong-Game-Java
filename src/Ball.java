import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 3;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();
        //establecemos un rango que solo pueda ser 1 o 0 y si es 0 se moverá a la izquierda pero si no, seguirá siendo a la derecha
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0) {
            randomXDirection--;
        }
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0) {
            randomYDirection--;
        }
        setXDirection(randomYDirection * initialSpeed);
    }

    public void setYDirection(int ramdomYDirection) {
        yVelocity = ramdomYDirection;
    }

    public void setXDirection(int ramdomXDirection) {
        xVelocity = ramdomXDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }
}
