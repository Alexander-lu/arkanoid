import acm.graphics.*;
import acm.program.*;

import java.awt.*;

public class Arkanoid extends GraphicsProgram {

    /* 动画每一帧间隔10ms*/
    private static final int DELAY = 10;

    /* 初始水平速度：每一帧水平方向的移动距离 */
    private static final double VELOCITY_Y = 5;

    /* 初始竖直速度：每一帧竖直方向的移动距离 */
    private static final double VELOCITY_X = 2;

    /* 小球的半径 */
    private static final int BALL_RADIUS = 15;

    /* 小球的颜色 */
    private static final Color BALL_COLOR = Color.BLUE;

    /* 小球 */
    GOval ball;
    /* 小球此刻的水平速度 */
    double vx;
    /* 小球此刻的竖直速度 */
    double vy;

    /**
     * Method: Init
     * -----------------------
     * 初始化
     */
    public void init() {
        System.out.println("hello");
        // 设置小球位置和初始速度
        ball = makeBall();

        // 水平速度
        vx = VELOCITY_X;

        // 竖直速度
        vy = VELOCITY_Y;
    }

    public void run() {
        // 等待用户点击
        waitForClick();

        while (true) {
            // 检查是否撞墙
            checkCollision();

            // 移动小球的位置
            ball.move(vx, vy);

            // 延迟
            pause(DELAY);
        }
    }

    /**
     * Method: Check Collision
     * -----------------------
     * 检查小球是否和墙相撞，如果相撞，改变小球运动方向
     */
    void checkCollision() {
        // 小球碰到上下两侧的墙，竖直反弹
        if (hitBottomWall()) {
            vy = -VELOCITY_Y;
        } else if (hitTopWall()) {
            vy = VELOCITY_X;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall()) {
            vx = VELOCITY_X;
        } else if (hitRightWall()) {
            vx = -VELOCITY_X;
        }
    }

    /**
     * Method: Hit Bottom Wall
     * -----------------------
     * 判断小球是否击中了底部边界
     */
    boolean hitBottomWall() {
        return ball.getY() > getHeight() - ball.getHeight();
    }

    /**
     * Method: Hit Top Wall
     * -----------------------
     * 判断小球是否击中了顶部边界
     */
    boolean hitTopWall() {
        return ball.getY() <= 0;
    }

    /**
     * Method: Hit Right Wall
     * -----------------------
     * 判断小球是否击中了右侧边界
     */
    boolean hitRightWall() {
        return ball.getX() >= getWidth() - ball.getWidth();
    }

    /**
     * Method: Hit Left Wall
     * -----------------------
     * 判断小球是否击中了左侧边界
     */
    boolean hitLeftWall() {
        return ball.getX() <= 0;
    }

    /**
     * Method: Make Ball
     * -----------------------
     * 画出一个小球来
     */
    public GOval makeBall() {
        double size = BALL_RADIUS * 2;
        GOval r = new GOval(size, size);
        r.setFilled(true);
        r.setColor(BALL_COLOR);
        add(r, 20, 20);
        return r;
    }

}
