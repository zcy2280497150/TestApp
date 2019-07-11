package zcy.applibrary.widget;

/**
 * Create 2019/3/8 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class Po implements Runnable{

    private boolean isRemove;

    float x;//x坐标（有效范围0-1,0-1代表在承载视图左到右 ， 用这个值乘以视图宽度就得到X方向坐标 ， 下面同理）
    float y;//Y坐标
    float r;//半径
    float vx;//X方向速度
    float vy;//Y方向速度

    public Po(float x, float y, float r, float vx, float vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * 用来刷新位置
     */
    @Override
    public void run() {
        if ((x < - 0.01f && vx < 0f) || ( x > 1.01f && vx > 0f)) vx = - vx;//超出显示边缘百分之1回弹
        if ((y < - 0.01f && vy < 0f) || ( y > 1.01f && vy > 0f)) vy = - vy;//超出显示边缘百分之1回弹
        x += vx;
        y += vy;
    }

    /**
     * 计算本点和目标点的距离，
     * @param o
     * @return
     */
    public float lenght(Po o){
        float lx = x - o.x;
        float ly = y - o.y;
        return (float) Math.sqrt(lx*lx + ly*ly);
    }

    /**
     * 计算以1位单位，传入坐标值需要换算成（1 ,1）
     * @param x
     * @param y
     * @return
     */
    public float lenght(float x , float y){
        float lx = this.x - x;
        float ly = this.y - y;
        return (float) Math.sqrt(lx*lx + ly*ly);
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

}
