import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class panel extends JPanel implements ActionListener
{

    static int width=1200;
    static int height =600;
    static int unit =50;

    // score of game
    int score;

    // x, y coordinated of food
    int fx,fy;

    // initial snake length
    int length=3;

    // initial direction of snake : Right
    char dir='R';

    //initial condition of game :false
    boolean flag=false;


    // rand will make food spawn randomly
    // its class in java (give random values)
    Random random ;

    Timer timer;

    static int delay=160;

    int totunit = width*height/unit;
    // size 12*24 =288 max size of snake
    int xsnake[]=new int[totunit];

    int ysnake[]=new int[totunit];


    panel()
    {
        this.setPreferredSize(new Dimension(width,height));

        this.setBackground(Color.BLACK);

        this.addKeyListener(new MyKey());

        // ? in order to take input from keyboard this focusable should be true
        this.setFocusable(true);

        random = new Random();

        gamestart();

    }

    public void gamestart()
    {

        flag=true;
        //spwan food function
        spawnfood();

        // timer to check on gamestate in each 160 miliseocnd
        timer = new Timer(delay,this);
        timer.start();


    }

    public void spawnfood()
    {
        // we typecast as we do not want fraction
        // nextInt we give range (0 - 24)*50
        fx = random.nextInt((int)width/unit) * unit;

        fy = random.nextInt((int)height/unit) * unit;

    }

    // Graphic is class, which can draw circles and other shapes and text
    // graphics is just name of variable(instance variable)
    public void paintComponent(Graphics graphic)
    {
        // super calling JPanel class method of paintComponent
        super.paintComponent(graphic);
        draw(graphic);
    }

    private void draw(Graphics graphic)
    {
        // if game continue flag=true
        if(flag)
        {
            // to spawn food particle
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);

            // to spawn snake body
            for(int  i = 0 ; i < length ; i++)
            {
                if(i==0)
                {
                    graphic.setColor(Color.red);
                    graphic.fillRect(xsnake[0],ysnake[0] ,unit , unit );
                }
                else
                {
                    graphic.setColor(Color.green);
                    graphic.fillRect(xsnake[i],ysnake[i] ,unit , unit );
                }
            }
        }
        else
        {
            gameover(graphic);
        }

        // For SCORE display
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("Times New Roman" , Font.BOLD,40));
        FontMetrics fme = getFontMetrics(graphic.getFont());

        // here i/p : String , x , y cordinates
        // height i.e. y : graphic.getFont().getSize()
        // x : middle of width : (width-fme.stringWidth("Score :"+score))/2
        graphic.drawString("Score : "+score,(width-fme.stringWidth("Score : "+ score))/2,graphic.getFont().getSize());
    }

    public void gameover(Graphics graphic)
    {
        // the score display
        graphic.setColor(Color.cyan);
        graphic.setFont(new Font("Times New Roman" , Font.BOLD,40));
        FontMetrics fme = getFontMetrics(graphic.getFont());

        // here i/p : String , x , y cordinates
        // height i.e. y : graphic.getFont().getSize()
        // x : middle of width : (width-fme.stringWidth("Score :"+score))/2
        graphic.drawString("Score : "+score,(width-fme.stringWidth("Score : "+ score))/2,graphic.getFont().getSize());

//game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Times New Roman" , Font.BOLD,80));
        FontMetrics fme1 = getFontMetrics(graphic.getFont());

        // here i/p : String , x , y cordinates
        // height i.e. y : graphic.getFont().getSize()
        // x : middle of width : (width-fme.stringWidth("Score :"+score))/2
        graphic.drawString("Game Over",(width-fme1.stringWidth("Game Over : "))/2,height/2);

        //replay promt display
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("Times New Roman" , Font.BOLD,40));
        FontMetrics fme2 = getFontMetrics(graphic.getFont());

        // here i/p : String , x , y cordinates
        // height i.e. y : graphic.getFont().getSize()
        // x : middle of width : (width-fme.stringWidth("Score :"+score))/2
        graphic.drawString("Press R to replay",(width-fme2.stringWidth("Press R to replay"))/2,height/2 - 150);

    }

    public void move(){
        //for all other body parts
        for(int i=length;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }

        //for updating head
        switch(dir){
            case 'U':
             ysnake[0]= ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]= ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]= xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]= xsnake[0]+unit;
                break;

        }
    }

    void check(){
        //checking if head has hit it the body
        for(int i=length;i>0;i--){
            if((xsnake[0]==xsnake[i]) && (ysnake[0]== ysnake[i])){
                flag = false;
            }
        }
        // checking hit with walls
        if(xsnake[0]<0){
            flag = false;
        }
        else if(xsnake[0]>width){
            flag = false;
        }
        else if(ysnake[0]<0){
            flag = false;
        }
        else if(ysnake[0] > height){
            flag = false;
        }
        if(flag==false){
            timer.stop();
        }
    }

    public void foodeaten(){
        if((xsnake[0]==fx)&& (ysnake[0]==fy)){
            length++;
            score++;
            spawnfood();
        }
    }


    public class MyKey extends KeyAdapter
    {
        public void keyPressed(KeyEvent k){
            switch(k.getKeyCode()){
                case VK_UP:
                    if(dir != 'D'){
                        dir = 'U';
                    }
                    break;
                case VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                    }
                    break;
                case VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                    }
                    break;
                case VK_LEFT:
                    if(dir != 'R'){
                        dir = 'L';
                    }
                    break;
                case VK_R:
                    if(!flag){
                        score =0;
                        length=3;
                        dir = 'R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        gamestart();
                    }
                    break;
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(flag){
            move();
            foodeaten();
            check();
        }
        // explicitly calls the paintcomponent function
        repaint();

    }
}
