import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakegame extends JPanel implements ActionListener,KeyListener{
    public class tile{
        int x;
        int y;
        tile(int x,int y)
        {
          this.x=x;
          this.y=y;
        }
    }
    int boardwidth;
    int boardheight;
    int tilesize=25;
     tile food;
    tile snakehead;
    ArrayList<tile> snakeBODY;
    Random random;
    Timer gameloop;
    int velocityx;
    int velocityy;
boolean gameover=false;
    snakegame(int boardwidth,int boardheight)
    {
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        snakehead=new tile(5 ,5);
        snakeBODY=new ArrayList<tile>();
        food =new tile(10,10);
        random=new Random();
        placefood();
        velocityx=0;
        velocityy=0;
        gameloop=new Timer(100,this);
        gameloop.start();
    }
    public void paintComponent(Graphics g)
    {
    super.paintComponent(g);  
      draw(g);
    }
    public void draw(Graphics g){
        for(int i=0;i<boardwidth/tilesize;i++){
            g.drawLine(i*tilesize, 0, i*tilesize, boardwidth);
            g.drawLine(0,i*tilesize,boardheight,i*tilesize);
        }
        g.setColor(Color.red);
        g.fillRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize);
    
        g.setColor(Color.green);
        g.fillRect(snakehead.x*tilesize,snakehead.y*tilesize,tilesize,tilesize);

        for(int i=0;i<snakeBODY.size();i++)
        {
          tile snakePart=snakeBODY.get(i);
          g.fillRect(snakePart.x*tilesize,snakePart.y*tilesize,tilesize,tilesize);
        }
      g.setFont(new Font("Arial",Font.PLAIN,16));
      if(gameover)
        {
          g.setColor(Color.RED);
          g.drawString("game over:"+String.valueOf(snakeBODY.size()),tilesize-16,tilesize);
        } 
        else{
          g.drawString("score:"+String.valueOf(snakeBODY.size()),tilesize-16,tilesize);
        } 
    }
    public void placefood(){
        food.x=random.nextInt(boardwidth/tilesize);
        food.y=random.nextInt(boardheight/tilesize);
    }
    public boolean collision(tile tile1,tile tile2){
      return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void move()
    {
      if(collision(snakehead,food)){
        snakeBODY.add(new tile(food.x, food.y));
        placefood();
      }
      for(int i=snakeBODY.size()-1;i>=0;i--)
      {
        tile snakePart=snakeBODY.get(i);
        if(i==0)
        {
          snakePart.x=snakehead.x;
          snakePart.y=snakehead.y;
        }
        else{
          tile prevsnakepart=snakeBODY.get(i-1);
          snakePart.x=prevsnakepart.x;
          snakePart.y= prevsnakepart.y;
        }
      }
      snakehead.x+=velocityx;
      snakehead.y+=velocityy;
      for(int i=0;i<snakeBODY.size();i++)
      {
        tile snakePart=snakeBODY.get(i);
        if(collision(snakehead,snakePart)){
          gameover=true;
        }
      }
      if(snakehead.x*tilesize<0||snakehead.x*tilesize>boardwidth||snakehead.y*tilesize< 0||snakehead.y*tilesize>boardheight){
        gameover=true;
      }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      move();
      repaint();
      if(gameover){
        gameloop.stop();
      }
    }
    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_UP && velocityy!=1){
        velocityx=0;
        velocityy=-1;
      }
      else if(e.getKeyCode()==KeyEvent.VK_DOWN &&  velocityy!=-1){
        velocityx=0;
        velocityy=1;
      }
      else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityx!=1){
        velocityx=-1;
        velocityy=0;
      }
      else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityx!=-1){
        velocityx=1;
        velocityy=0;
      }
    }
    @Override
    public void keyReleased(KeyEvent e) {
      
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
}