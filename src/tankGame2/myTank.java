package tankGame2;

public class myTank extends Tank {
    // 定义一个Shot对象，表示一个射击线程
    Shot shot = null;
    public myTank(int x, int y) {
        super(x, y);
    }

    // 射击
    public void shotEnemyTank() {
        // 创建shot对象，根据当前myTank的位置和方向
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
            default:
                break;
        }
        // 启动 Shot 线程
        new Thread(shot).start();

    }
}
