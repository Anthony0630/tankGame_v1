package tankGame2;

import javax.swing.*;

public class chdTankGame_2 extends JFrame {
    // 定义 MyPanel
    myPanel mp = null;
    public static void main(String[] args) {
        chdTankGame_2 chdtankGame1 = new chdTankGame_2();
    }
    public chdTankGame_2(){
        mp = new myPanel();
        this.add(mp);
        this.setSize(1000, 750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
