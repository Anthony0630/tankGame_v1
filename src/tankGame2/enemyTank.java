package tankGame2;

import java.util.Vector;

// 敌人的坦克
public class enemyTank extends Tank {
    // 在敌人坦克类，使用 Vector 存放多个 Shot
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public enemyTank(int x, int y) {
        super(x, y);
    }
}
