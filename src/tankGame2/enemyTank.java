package tankGame2;

import java.util.Vector;

// 敌人的坦克
public class enemyTank extends Tank implements Runnable{
    // 在敌人坦克类，使用 Vector 存放多个 Shot
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public enemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {
            // 如果 shots.size() == 0，就创建一颗子弹放入 shots 集合并启动线程bing
            if (isLive && shots.size() < 3) {
                Shot s = null;
                // 判断坦克的方向，创建对应的子弹
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                // 启动线程
                new Thread(s).start();
            }
            // 根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0:   // 向上
                    // 让坦克保持一个方向走 30 步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0) {
                            moveUp();
                        } else break;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:   // 向右
                    // 让坦克保持一个方向走 30 步
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        } else break;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:   // 向下
                    // 让坦克保持一个方向走 30 步
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        } else break;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:   // 向左
                    moveLeft();
                    // 让坦克保持一个方向走 30 步
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        } else break;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            // 随机改变坦克的方向  0 - 3
            setDirect((int) (Math.random() * 4));

            if (!isLive) {
                break;
            }
        }
    }
}
