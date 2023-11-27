package tankGame;

import javax.swing.*;

public class chdTankGame_1 extends JFrame {
    // 定义 MyPanel
    myPanel mp = null;
    public static void main(String[] args) {
        chdTankGame_1 chdtankGame1 = new chdTankGame_1();
    }
    public chdTankGame_1(){
        mp = new myPanel();
        this.add(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
