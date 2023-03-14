import java.awt.Color;

import javax.swing.JFrame;

public class Gameframe extends JFrame {
    Gameframe(){
        this.add(new Gamepanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocation(0,0);
        this.pack();
        this.setSize(620,640);
        this.setBackground(Color.white);
    }
}
