import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GameFrame extends JFrame{
    GamePanel panel;

    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); //nos sirve para que se adapate al tamaño del GamePanel y así no asignarle al GameFrame un tamaño fijo
        this.setVisible(true);
        this.setLocationRelativeTo(null); //aparece en el centro de la pantalla
    }
}
