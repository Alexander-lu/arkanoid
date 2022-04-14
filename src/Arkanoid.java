import acm.graphics.*;
import acm.program.*;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import java.awt.*;

public class Arkanoid extends GraphicsProgram {
    RandomGenerator randomGenerator = RandomGenerator.getInstance();
    /* 动画每一帧间隔10ms*/
    private static final int DELAY = 16;
    boolean c = true;

    /* 初始水平速度：每一帧水平方向的移动距离 */
    private static final double VELOCITY_Y = 5;
    public final int A= randomGenerator.nextInt(1, 10);
    public final int B= randomGenerator.nextInt(1, 10);
    public final int C= randomGenerator.nextInt(1, 10);
    public final int D= randomGenerator.nextInt(1, 10);
    public final int E= randomGenerator.nextInt(1, 10);
    public final int F= randomGenerator.nextInt(1, 10);

    /* 初始竖直速度：每一帧竖直方向的移动距离 */
    private static final double VELOCITY_X = 2;


    /* 小球的半径 */
    private static final int BALL_RADIUS = 15;

    /* 小球的颜色 */
    private static final Color BALL_COLOR1 = Color.BLACK;
    private static final Color BALL_COLOR2 = Color.RED;
    private static final Color BALL_COLOR3 = Color.BLUE;

    /* 小球 */
    GOval ball1;
    GOval ball2;
    GOval ball3;
    /* 小球此刻的水平速度 */
    double vx1;
    double vx2;
    double vx3;
    /* 小球此刻的竖直速度 */
    double vy1;
    double vy2;
    double vy3;

    /**
     * Method: Init
     * -----------------------
     * 初始化
     */
    @Override
    public void init() {
        makeBall1();// 往屏幕上添加小球
        makeBall2();
        makeBall3();
        vx1 = A;        // 水平速度
        vy1 = B;        // 竖直速度
        vx2 = C;        // 水平速度
        vy2 = D;        // 竖直速度
        vx3 = E;        // 水平速度
        vy3 = F;        // 竖直速度
    }
    @Override
    public void run() {
        // 等待用户点击
        waitForClick();

        //noinspection InfiniteLoopStatement
        while (c) {
            // 检查是否撞墙
            checkCollision1();
            checkCollision2();
            checkCollision3();

            // 移动小球的位置
            ball1.move(vx1, vy1);
            ball2.move(vx2, vy2);
            ball3.move(vx3, vy3);


            // 延迟
            pause(DELAY);
        }
    }

    /**
     * Method: Check Collision
     * -----------------------
     * 检查小球是否和墙相撞，如果相撞，改变小球运动方向
     */
    void checkCollision1() {
        // 小球碰到上下两侧的墙，竖直反弹
        if (hitBottomWall1()) {
            c = false;
            GLabel label = new GLabel("Game Over");
            add(label, 100, 100);
        } else if (hitTopWall1()) {
            vy1 = B;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall1()) {
            vx1 = A;

        } else if (hitRightWall1()) {
            vx1 = -A;
        }
    }
    void checkCollision2() {
        // 小球碰到上下两侧的墙，竖直反弹
        if (hitBottomWall2()) {
            c = false;
            GLabel label = new GLabel("Game Over");
            add(label, 100, 100);
        } else if (hitTopWall2()) {
            vy2 = D;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall2()) {
            vx2 = C;

        } else if (hitRightWall2()) {
            vx2 = -C;
        }
    }
    void checkCollision3() {
        // 小球碰到上下两侧的墙，竖直反弹
        if (hitBottomWall3()) {
            c = false;
            GLabel label = new GLabel("Game Over");
            add(label, 100, 100);
        } else if (hitTopWall3()) {
            vy3 = F;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall3()) {
            vx3 = E;

        } else if (hitRightWall3()) {
            vx3 = -E;
        }
    }

    /**
     * Method: Hit Bottom Wall
     * -----------------------
     * 判断小球是否击中了底部边界
     */
    boolean hitBottomWall1() {
        return ball1.getY() >= getHeight() + ball1.getHeight();
    }
    boolean hitBottomWall2() {
        return ball2.getY() >= getHeight() + ball2.getHeight();
    }
    boolean hitBottomWall3() {
        return ball3.getY() >= getHeight() + ball3.getHeight();
    }

    /**
     * Method: Hit Top Wall
     * -----------------------
     * 判断小球是否击中了顶部边界
     */
    boolean hitTopWall1() {
        return ball1.getY() <= 0;
    }
    boolean hitTopWall2() {
        return ball2.getY() <= 0;
    }
    boolean hitTopWall3() {
        return ball3.getY() <= 0;
    }

    /**
     * Method: Hit Right Wall
     * -----------------------
     * 判断小球是否击中了右侧边界
     */
    boolean hitRightWall1() {
        return ball1.getX() >= getWidth() - ball1.getWidth();
    }
    boolean hitRightWall2() {
        return ball2.getX() >= getWidth() - ball2.getWidth();
    }
    boolean hitRightWall3() {
        return ball3.getX() >= getWidth() - ball3.getWidth();
    }

    /**
     * Method: Hit Left Wall
     * -----------------------
     * 判断小球是否击中了左侧边界
     */
    boolean hitLeftWall1() {
        return ball1.getX() <= 0;
    }
    boolean hitLeftWall2() {
        return ball2.getX() <= 0;
    }
    boolean hitLeftWall3() {
        return ball3.getX() <= 0;
    }
    /**
     * Method: Make Ball
     * -----------------------
     * 画出一个小球来
     */
    public void makeBall1() {
        double size = BALL_RADIUS * 2;
        ball1 = new GOval(size, size);

        // 设置小球为实心
        ball1.setFilled(true);

        // 填充颜色是黑色
        ball1.setColor(BALL_COLOR1);

        // 添加到画布上(20,20)的位置
        add(ball1, 20, 20);
    }
    public void makeBall2() {
        double size = BALL_RADIUS * 2;
        ball2 = new GOval(size, size);

        // 设置小球为实心
        ball2.setFilled(true);

        // 填充颜色是黑色
        ball2.setColor(BALL_COLOR2);

        // 添加到画布上(20,20)的位置
        add(ball2, 30, 30);
    }
    public void makeBall3() {
        double size = BALL_RADIUS * 2;
        ball3 = new GOval(size, size);

        // 设置小球为实心
        ball3.setFilled(true);

        // 填充颜色是黑色
        ball3.setColor(BALL_COLOR3);

        // 添加到画布上(20,20)的位置
        add(ball3, 40, 40);
    }
}
