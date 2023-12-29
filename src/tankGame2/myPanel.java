package tankGame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

// 为了监听键盘事件，需要实现 KeyListener
public class myPanel extends JPanel implements KeyListener {
    // 定义 myTank
    tankGame2.myTank myTank = null;
    // 定义敌人坦克，放入 Vector
    Vector<enemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 5;

    Random r = new Random();   // 用来随机敌人坦克的速度
    public myPanel(){
        myTank = new myTank(100, 100);    // 初始化自己的坦克
        myTank.setSpeed(15);

        for (int i = 0; i < enemyTankSize; i++) {
            enemyTank enemytank = new enemyTank(100 * (i + 1), 0);
            enemytank.setDirect(2);
            enemytank.setSpeed(r.nextInt(9) + 1);  // 保证速度属于 [1, 10]
            enemyTanks.add(enemytank);
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); // 画出大背景矩形
        // 画出自己的坦克
        drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);

        // 画出myTank的子弹
        if (myTank.shot != null && myTank.shot.isLive) {
            g.fill3DRect(myTank.shot.x, myTank.shot.y, 1, 1, false);
        }

        // 画出敌人的坦克
        for (enemyTank enemytank : enemyTanks) {
            drawTank(enemytank.getX(), enemytank.getY(), g, enemytank.getDirect(), 1);
        }
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

        // 根据坦克方向，绘制对应形状的坦克
        // direct 坦克方向（0：上 1：右 2：下 3：左）
        switch (direct) {
            case 0: // 向上
                g.fill3DRect(x, y, 10, 60, false);   // 绘制坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  // 绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);   // 绘制坦克中间的盖子
                g.fillOval(x + 10, y + 20, 20, 20);    // 绘制坦克的圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);     // 绘制坦克炮筒
                break;
            case 1: // 向右
                g.fill3DRect(x, y, 60, 10, false);   // 绘制坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  // 绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);   // 绘制坦克中间的盖子
                g.fillOval(x + 20, y + 10, 20, 20);    // 绘制坦克的圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);     // 绘制坦克炮筒
                break;
            case 2: // 向下
                g.fill3DRect(x, y, 10, 60, false);   // 绘制坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  // 绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);   // 绘制坦克中间的盖子
                g.fillOval(x + 10, y + 20, 20, 20);    // 绘制坦克的圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);     // 绘制坦克炮筒
                break;
            case 3: // 向左
                g.fill3DRect(x, y, 60, 10, false);   // 绘制坦克左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  // 绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);   // 绘制坦克中间的盖子
                g.fillOval(x + 20, y + 10, 20, 20);    // 绘制坦克的圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);     // 绘制坦克炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override  // 监听键盘事件，实现按键按下后，坦克方向改变的功能
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.setDirect(0);
            myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(1);
            myTank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(2);
            myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(3);
            myTank.moveLeft();
        }

        // 如果用户按下的是J，就需要发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            myTank.shotEnemyTank();
        }
        this.repaint();  // 重绘面板，调用paint方法，画出坦克
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
