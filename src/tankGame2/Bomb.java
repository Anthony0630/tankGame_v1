package tankGame2;

public class Bomb {
    int x, y;   // 炸弹的坐标
    int life = 9;  // 炸弹的生命周期
    boolean isLive = true;  // 炸弹是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 减少生命值
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }  // 如果生命值为0，则炸弹已经死亡，isLive设置为false
    }
}
