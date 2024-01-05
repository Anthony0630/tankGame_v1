package tankGame2;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

// 为了监听键盘事件，需要实现 KeyListener
// 为了让 Panel 不停地重绘子弹，需要将 myPanel 实现Runnable 借口，当做线程使用
public class myPanel extends JPanel implements KeyListener, Runnable{
    // 定义 myTank
    tankGame2.myTank myTank = null;
    // 定义敌人坦克，放入 Vector
    static Vector<enemyTank> enemyTanks = new Vector<>();
    // 定义一个Vector，用于存放炸弹
    static Vector<Bomb> bombs = new Vector<>();
    // 当子弹击中坦克时，加入一个 bombs

    // 定义三张爆炸图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    int enemyTankSize = 8;

    Random r = new Random();   // 用来随机敌人坦克的速度
    public myPanel(){
        myTank = new myTank(100, 100);    // 初始化自己的坦克
        myTank.setSpeed(15);

        for (int i = 0; i < enemyTankSize; i++) {
            enemyTank enemytank = new enemyTank(100 * (i + 1), 0);
            enemytank.setDirect(2);   // 设置方向
            new Thread(enemytank).start();// 启动敌人坦克线程
            Shot shot = new Shot(enemytank.getX() + 20, enemytank.getY() + 60, enemytank.getDirect()); // 给该 enemyTank 加入一颗子弹
            enemytank.shots.add(shot);   // / 将 shot 加入 enemyTank 中
            new Thread(shot).start();   // 启动 Shot 线程
            enemytank.setSpeed(r.nextInt(4) + 1);  // 保证速度属于 [1, 5]
            enemyTanks.add(enemytank);
        }
        // 初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("/tankGame2/bomb.jpeg"));
        image2 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("/tankGame2/bomb.jpeg"));
        image3 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("/tankGame2/bomb.jpeg"));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1016, 789); // 画出大背景矩形
        if (myTank != null && myTank.isLive) {
            // 画出自己的坦克
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }

        // 画出myTank的子弹
//        if (myTank.shot != null && myTank.shot.isLive) {
//            g.draw3DRect(myTank.shot.x, myTank.shot.y, 1, 1, false);
//        }

        // 将 myTank 的子弹集合 shots 取出，遍历绘制所有子弹
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {
                myTank.shots.remove(shot);
            }
        }

        // 如果 bombs 中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            // 根据当前bomb 对象的 life 值画出对应的图像
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        // 画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTank enemytank = enemyTanks.get(i);     // 从 Vector 中取出坦克
            if (enemytank.isLive) {    // 只有当敌人坦克存活才画
                drawTank(enemytank.getX(), enemytank.getY(), g, enemytank.getDirect(), 1);
                // 画出 enemytank 所有子弹
                for (int j = 0; j < enemytank.shots.size(); j++) {
                    Shot shot = enemytank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        enemytank.shots.remove(shot);
                    }
                }
            }
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
    // 如果我们的坦克可以发射多个子弹
    // 在判断我方子弹是否击中敌人坦克时，就需要把我方的子弹集合中所有的子弹都取出和敌人的所有坦克进行判断
    public void hitEnemyTank() {
        // 遍历我方子弹
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            // 判断是否击中了敌方坦克
            if (shot != null && shot.isLive) {   // 当我方子弹还存活
                // 遍历敌人所有的坦克
                for (int j = 0; j < enemyTanks.size(); j++) {
                    enemyTank enemytank = enemyTanks.get(j);
                    hitTank(shot, enemytank);
                }
            }
        }
    }

    // 判断敌人坦克是否击中我方坦克
    // public void hitMyTank() {
    //     // 遍历敌人坦克
    //     for (int i = 0; i < enemyTanks.size(); i++) {
    //         enemyTank enemytank = enemyTanks.get(i);
    //         // 遍历我方坦克
    //         for (int j = 0; j < myTank.shots.size(); j++) {
    //             Shot shot = myTank.shots.get(j);
    //             if (myTank.isLive && shot.isLive) {   // 当我方坦克还存活
    //                 hitTank(shot, myTank);
    //             }
    //         }
    //     }
    // }

    public static void hitTank(Shot s, enemyTank enemytank) {     // 判断我方子弹是否击中敌方坦克， run 方法中判断
        // 判断 s 击中坦克
        switch (enemytank.getDirect()) {
            case 0:   // 坦克向上
            case 2:   // 坦克向下
                if (s.x > enemytank.getX()
                        && s.x < enemytank.getX() + 40
                        && s.y > enemytank.getY()
                        && s.y < enemytank.getY() + 60) {
                    s.isLive = false;
                    enemytank.isLive = false;
                    // 当我方子弹击中敌方坦克，就将地方坦克从 Vector 中移除
                    enemyTanks.remove(enemytank);
                    // 创建 Bomb 对象，加入 bombs 集合
                    Bomb bomb = new Bomb(enemytank.getX(), enemytank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:   // 坦克向左
            case 3:   // 坦克向右
                if (s.x > enemytank.getX()
                        && s.x < enemytank.getX() + 60
                        && s.y > enemytank.getY()
                        && s.y < enemytank.getY() + 40) {
                    s.isLive = false;
                    enemytank.isLive = false;
                    // 当我方子弹击中敌方坦克，就将地方坦克从 Vector 中移除
                    enemyTanks.remove(enemytank);
                    // 创建 Bomb 对象，加入 bombs 集合
                    Bomb bomb = new Bomb(enemytank.getX(), enemytank.getY());
                    bombs.add(bomb);
                }
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override  // 监听键盘事件，实现按键按下后，坦克方向改变的功能
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.setDirect(0);
            if (myTank.getY() > 0) {
                myTank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(1);
            if (myTank.getX() + 60 < 1000) {
                myTank.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(2);
            if (myTank.getY() + 60 < 750) {
                myTank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(3);
            if (myTank.getX() > 0) {
                myTank.moveLeft();
            }
        }

        // 如果用户按下的是J，就需要发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            // 判断 myTank 的子弹是否销毁
//            if (myTank.shot == null || !myTank.shot.isLive) {
//                myTank.shotEnemyTank();
//            }
            if (myTank.shots.size() < 5) {
                myTank.shotEnemyTank();
            }
        }
        this.repaint();  // 重绘面板，调用paint方法，画出坦克
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {    // 每隔一段时间，重绘区域，刷新绘图区域，让子弹动起来
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 判断是否击中了敌人坦克
            hitEnemyTank();
            // 判断敌人的坦克是否击中了我方坦克
            // hitMyTank();
            this.repaint();  // 重绘面板
        }
    }
}
