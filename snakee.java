import javax.swing.*;
public class snakee {
    public static void main(String[] args) throws Exception {
       int boardwidth=600;
       int boardheight=boardwidth;
      JFrame frame =new JFrame("snake");
       frame.setVisible(true);
       frame.setSize(boardwidth,boardheight);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       snakegame sn=new snakegame(boardwidth, boardheight);
       frame.add(sn);
       frame.pack();
       sn.requestFocus();
    }
}
