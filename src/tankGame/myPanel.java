package tankGame;

import javax.swing.*;
import java.awt.*;

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
        drawTank(myTank.getX(), myTank.getY(), g, 0, 0);
    }

    // 编写方法，画出坦克
    // x y 坦克左上角的坐标
    // g 画笔
    // direct 坦克方向(上下左右)
    // type 坦克类型
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 根据坦克类型，设置不同颜色
        switch (type) {
            case 0:  // 玩家的坦克
                g.setColor(Color.red);     // 设置颜色为红色
                break;
            case 1:  // 敌人的坦克
                g.setColor(Color.yellow);   // 设置颜色为黄色
                break;
        }

        // 根据坦克方向，绘制坦克
        switch (direct) {
            case 0: // 向上
                g.fill3DRect(x, y, 10, 60, false);   // 绘制坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  // 绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);   // 绘制坦克中间的盖子
                g.fillOval(x + 10, y + 20, 20, 20);    // 绘制坦克的圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);     // 绘制坦克炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }
}
