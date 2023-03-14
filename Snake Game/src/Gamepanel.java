import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener{
    static final int Screen_Width= 600;
    static final int Screen_Height= 600;
    static final int Unit_Size= 15;
    static final int Game_Units= (Screen_Height*Screen_Width)/Unit_Size;
    static final int DELAY=75;
    final int x[] = new int[Game_Units];
    final int y[] = new int[Game_Units];
    int bodyparts = 5;
    int applex;
    int appley;
    int Score;
    boolean running= false;
    char Direction='R';
    Timer timer;
    Random random;

    Gamepanel(){
        random=new Random();
        this.setBounds(0, 40, Screen_Width, Screen_Height);
        // this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    public void startgame(){
        newApple();
        running= true;
        timer= new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void newApple(){
        applex=random.nextInt((int)(Screen_Width/Unit_Size))*Unit_Size;
        appley=random.nextInt((int)(Screen_Height/Unit_Size))*Unit_Size;
    }
    public void draw(Graphics g){
        if(running){
            
            // for(int i=0; i<Screen_Height/Unit_Size;i++){
            //     g.drawLine(i*Unit_Size, 0, i*Unit_Size,Screen_Height);
            //     g.drawLine(0, i*Unit_Size, Screen_Width,i*Unit_Size);
            // }
            
            g.setColor(Color.red);
            g.fillOval(applex, appley, Unit_Size, Unit_Size);
            for(int i=0; i<bodyparts;i++){
                if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
                }
                else{
                    g.setColor(new Color(45, 100, 0));
                    g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("Score : "+Score, Screen_Width-metrics.stringWidth("Score : "+Score), g.getFont().getSize());
        }
        else{
            GameOver(g);
        }
    }
    public void move(){
        for(int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(Direction){
            case 'U':
                y[0]-=Unit_Size;
                break;
            case 'D':
                y[0]+=Unit_Size;
                break;
            case 'R':
                x[0]+=Unit_Size;
                break;
            case 'L':
                x[0]-=Unit_Size;
                break;
        }
    }
    public void checkapple(){
        if(x[0]==applex && y[0]==appley){
            bodyparts++;
            Score++;
            newApple();
        }
    }
    public void checkCollisions(){
        // checks if head collides with body
        for(int i=bodyparts; i>0; i--){
            if((x[0]==x[i])&& (y[0]==y[i])){
                running=false;
            }
        }
        //check if head touches left border
        if(x[0]<0){
            running=false;
        }
        //check if head touches right border
        if(x[0]==Screen_Width){
            running=false;
        }
        //check if head touches top border
        if(y[0]<0){
            running=false;
        }
        //check if head touches bottom border
        if(y[0]==Screen_Height){
            running=false;
        }
        
        if(running==false){
            timer.stop();
        }
    }
    public void GameOver(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1= getFontMetrics(g.getFont());
        g.drawString("Score : "+Score, (Screen_Width-metrics1.stringWidth("Score : "+Score))/2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("Game Over", (Screen_Width-metrics2.stringWidth("Game Over"))/2, Screen_Height/2);
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(Direction!='R'){
                        Direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(Direction!='L'){
                        Direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(Direction!='D'){
                        Direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(Direction!='U'){
                        Direction='D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkapple();
            checkCollisions();
        }
        repaint();
    }   
}