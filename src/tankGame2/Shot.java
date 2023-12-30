package tankGame2;

public class Shot implements Runnable{
    int x;    // x 坐标
    int y;    // y 坐标
    int direct;   // 子弹方向
    int speed = 2;  // 子弹速度
    boolean isLive = true;  // 子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {   // 射击行为
        while (true) {
            // sleep 50ms
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 根据方向来改变x,y坐标
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
                default:
                    break;
            }
            System.out.println("子弹 x = " + x + " y = " + y);
            // 碰到边界，就break
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
