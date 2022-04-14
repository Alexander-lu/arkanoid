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
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;
    public static final Color BRICK_COLOR = Color.BLACK;
    public static final int BRICK_WIDTH = 18;
    public static final int BRICK_HEIGHT = 8;
    public static final int BRICK_MARGIN = 1;
    /* 初始水平速度：每一帧水平方向的移动距离 */
    private static final double VELOCITY_Y = 5;
    public final int A = randomGenerator.nextInt(1, 10);

    /* 初始竖直速度：每一帧竖直方向的移动距离 */
    private static final double VELOCITY_X = 2;
    public final int B = randomGenerator.nextInt(1, 10);

    /* 小球的半径 */
    private static final int BALL_RADIUS = 15;

    /* 小球的颜色 */
    private static final Color BALL_COLOR = Color.BLACK;

    /* 小球 */
    GOval ball;
    GRect paddle;

    /* 小球此刻的水平速度 */
    double vx;
    /* 小球此刻的竖直速度 */
    double vy;

    //        这里调用了函数getCollidingObject()
    public GObject getCollidingObject() {
        double x = ball.getX();
        double y = ball.getY();

        double x1 = ball.getX();
        double y1 = ball.getBottomY();

        double x2 = ball.getRightX();
        double y2 = ball.getY();

        double x3 = ball.getRightX();
        double y3 = ball.getBottomY();

        if (getElementAt(x, y) != null) {
            return getElementAt(x, y);
        } else if (getElementAt(x1, y1) != null) {
            return getElementAt(x1, y1);
        } else if (getElementAt(x2, y2) != null) {
            return getElementAt(x2, y2);
        } else if (getElementAt(x3, y3) != null) {
            return getElementAt(x3, y3);
        } else {
            return null;
        }
    }

    /**
     * Method: Init
     * -----------------------
     * 初始化
     */
    @Override
    public void init() {
        makeBall();             // 往屏幕上添加小球
        makeBricks();
        vx = A;        // 水平速度
        vy = B;        // 竖直速度
    }

    @Override
    public void run() {

        // 等待用户点击
        waitForClick();

        //noinspection InfiniteLoopStatement
        while (c) {
            // 检查是否撞墙
            checkCollision();

            // 移动小球的位置
            ball.move(vx, vy);
            we();

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
            vy = -B;
//            c = false;
//            GLabel label = new GLabel("Game Over");
//            add(label, 100, 100);
        } else if (hitTopWall()) {
            vy = B;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall()) {
            vx = A;
            GLabel label = new GLabel("Game Over");
        } else if (hitRightWall()) {
            vx = -A;
        }
    }

    /**
     * Method: Hit Bottom Wall
     * -----------------------
     * 判断小球是否击中了底部边界
     */
    boolean hitBottomWall() {
        return ball.getY() >= getHeight() - ball.getHeight();
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
    public void makeBall() {
        double size = BALL_RADIUS * 2;
        ball = new GOval(size, size);

        // 设置小球为实心
        ball.setFilled(true);

        // 填充颜色是黑色
        ball.setColor(BALL_COLOR);

        // 添加到画布上(20,20)的位置
        add(ball, 300, 500);
    }

    public void makeBricks() {
        // 一堆黑色的砖块
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 20; j++) {
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);
                brick.setColor(BRICK_COLOR);
                int x = j * (BRICK_MARGIN * 2 + BRICK_WIDTH) + BRICK_MARGIN;
                int y = 0 + i * (BRICK_MARGIN * 2 + BRICK_HEIGHT) + BRICK_MARGIN;
                add(brick, x, y);
            }
        }
        GRect brick1 = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
        brick1.setFilled(true);
        brick1.setColor(Color.black);
        add(brick1, 100, 100);
        paddle=brick1;
    }

    public void we() {
        if (getCollidingObject() != null) {
            vx = -vx;
            vy = -vy;
            remove(getCollidingObject());
        }
    }
}
