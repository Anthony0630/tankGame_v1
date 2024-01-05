package tankGame2;

public class Tank {
    private int x;    // 横坐标
    private int y;    // 纵坐标
    boolean isLive = true;    // 坦克是否存活
    // ***************************控制坦克速度***************************
    private int speed = 1;    // 坦克速度
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() {
        return this.speed;
    }
    // ***************************上右下左移动方法***************************
    public void moveUp() {
        y -= speed;
    }
    public void moveRight() {
        x += speed;
    }
    public void moveDown() {
        y += speed;
    }
    public void moveLeft() {
        x -= speed;
    }
    // ***************************获取坦克方向***************************
    public int getDirect() {
        return direct;
    }
    // ***************************设置坦克方向***************************
    public void setDirect(int direct) {
        this.direct = direct;
    }
    // ***************************坦克方向及坐标***************************
    private int direct;   // 坦克方向 0 1 2 3
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
