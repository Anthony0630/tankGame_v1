package tankGame;

import javax.swing.*;
import java.awt.Graphics;

public class myPanel extends JPanel {
    // 定义 myTank
    myTank myTank = null;
    public myPanel(){
        myTank = new myTank(100, 100);    // 初始化自己的坦克
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); // 画出大背景矩形
//        g.fillRect(0, 0, 1000, 50); // 画出上方大矩形
//        g.fillRect(0, 700, 1000, 50); // 画出下方大矩形
//        g.fillRect(0, 0, 50, 750); // 画出左方大矩形
//        g.fillRect(950, 0, 50, 750); // 画出右方大矩形
//        g.fillRect(0, 0, 1000, 50); // 画出上方大矩形
//        g.fillRect(0, 700, 1000, 50); // 画出下方大矩形
//        g.fillRect(0, 0, 50, 750); // �
    }
}
