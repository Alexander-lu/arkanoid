import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import acm.gui.JStringList;
public class Arkanoid extends GraphicsProgram {
    /* 游戏界面的长和宽 */
    public static final int APPLICATION_WIDTH = 1020;
    public static final int APPLICATION_HEIGHT = 800;
    /* 定义一个randomGenerator随机生成数 */
    RandomGenerator randomGenerator = RandomGenerator.getInstance();
    /* 动画每一帧间隔16ms，即一秒60帧 */
    private static final int DELAY = 16;
    /* 布尔stopGame用于判断是否结束游戏 */
    boolean stopGame = true;
    /* 定义砖块的长宽 */
    public static final int BRICK_WIDTH = 25;
    public static final int BRICK_HEIGHT = 8;
    public static final int BRICK_MARGIN = 1;
    /* 定义挡板的长宽 */
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 5;
    /* 初始水平速度：每一帧水平方向的移动距离 */
    public final int LEVEL = randomGenerator.nextInt(-12, -8);
    /* 初始竖直速度：每一帧竖直方向的移动距离 */
    public final int VERTICAL = randomGenerator.nextInt(-12, -8);
    /* 定义a，用于判断是否死亡3次结束游戏 */
    public int deathTimes = 0;
    public int points =0;
    /* 小球的半径 */
    private static final int BALL_RADIUS = 15;
    /* 小球 挡板 */
    GOval ball;
    GRect paddle;
    /* 小球此刻的水平速度 */
    double vx;
    /* 小球此刻的竖直速度 */
    double vy;
    /* 显示游戏规则 */
    JStringList Rulers = new JStringList();
    String[] strArray={"你的积分是："+Integer.toString(points)+"分","你已经死亡："+Integer.toString(deathTimes)+"次","游戏规则是：","蓝色砖块1分","绿色砖块2分","黄色砖块3分","橙色砖块4分","红色砖块5分","你有3条命","消除所有砖块即获胜"};
    /**
     * 初始化
     */
    @Override
    public void init() {
        this.add(Rulers);
        this.setResizable(false);
        Color[] colarArray = {Color.BLACK,Color.BLACK};
        Rulers.setItems(strArray,colarArray);
        makeBall();// 往屏幕上添加小球
        makeBricks();// 往屏幕上添加砖块
        makePaddle();// 往屏幕上添加挡板
        points = 0;
        addMouseListeners();// 启用鼠标监听器
        vx = LEVEL;// 水平速度
        vy = VERTICAL;// 垂直速度
    }
    @Override
    public void run() {
        // 等待用户点击
        waitForClick();
        while (stopGame) {
            // 检查是否撞墙
            checkCollision();
            // 检查是否撞砖块
            checkBricks();
            Rulers.setItem(0,"你的积分是："+Integer.toString(points)+"分");
            Rulers.setItem(1,"你已经死亡："+Integer.toString(deathTimes)+"次");
            // 移动小球的位置
            ball.move(vx, vy);
            // 延迟
            pause(DELAY);
        }
    }
    /**
     * 检查是否撞砖块
     */
    void checkBricks() {
        GObject objBrick = getCollidingObject();
        if (objBrick != null) {
            if (objBrick == paddle) {
                vy = -vy;
            }
            else {
                if (objBrick.getColor() == Color.CYAN) {
                    remove(objBrick);
                    vy = -vy;
                    points++;
                } else if (objBrick.getColor() == Color.GREEN) {
                    remove(objBrick);
                    vy = -vy;
                    points++;
                    points++;
                } else if (objBrick.getColor() == Color.YELLOW) {
                    remove(objBrick);
                    vy = -vy;
                    points++;
                    points++;
                    points++;
                }  else if (objBrick.getColor() == Color.ORANGE) {
                    remove(objBrick);
                    vy = -vy;
                    points++;
                    points++;
                    points++;
                    points++;
                } else if (objBrick.getColor() == Color.RED) {
                    remove(objBrick);
                    vy = -vy;
                    points++;
                    points++;
                    points++;
                    points++;
                    points++;
                } else if (objBrick.getColor() == Color.BLACK) {
                    vy = -vy;
                }
            }
        }
    }
    /**
     * 检查小球是否和墙相撞，如果相撞，改变小球运动方向
     */
    void checkCollision() {
        // 小球碰到上侧的墙，计算3次生命结束游戏；小球碰到下侧的墙，竖直反弹
        if (hitBottomWall()) {
            if (deathTimes < 2) {
                remove(paddle);
                remove(ball);
                clear();
                JOptionPane.showMessageDialog(null, "你的积分为："+points+"\n"+"点击确定重新开始");
                deathTimes++;
                init();
                run();
                waitForClick();
            }else {
                remove(paddle);
                remove(ball);
                clear();
                JOptionPane.showMessageDialog(null, "Game Over"+"\n"+"点击确定重新开始");
                deathTimes = 0;
                init();
                run();
                waitForClick();
            }
        }
        if (hitTopWall()) {
                vy = VERTICAL;
        }
        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall()) {
                vx = -LEVEL;
        } else if (hitRightWall()) {
                vx = VERTICAL;
            }
        }
    /**
     * 判断小球是否击中了底部边界
     */
    boolean hitBottomWall() {
        return ball.getY() > getHeight() - ball.getHeight();
    }
    /**
     * 判断小球是否击中了顶部边界
     */
    boolean hitTopWall() {
        return ball.getY() <= 0;
    }

    /**
     * 判断小球是否击中了右侧边界
     */
    boolean hitRightWall() {
        return ball.getX() >= getWidth() - ball.getWidth()-509;
    }
    /**
     * 判断小球是否击中了左侧边界
     */
    boolean hitLeftWall() {
        return ball.getX() <= 0;
    }
    /**
     * 画出一个小球来
     */
    public void makeBall() {
        // 设置半径
        double size = BALL_RADIUS * 2;
        ball = new GOval(size, size);
        // 设置小球为实心
        ball.setFilled(true);
        // 填充颜色为随机颜色
        ball.setColor(randomGenerator.nextColor());
        // 添加到画布上(300，500)的位置
        add(ball, 300, 500);
    }
    /**
     * 画出一堆五颜六色的砖块
     */
    public void makeBricks() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 30; j++) {
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);
                if (i < 2) {
                    brick.setColor(Color.RED);
                } else if (i < 4) {
                    brick.setColor(Color.ORANGE);
                } else if (i < 6) {
                    brick.setColor(Color.YELLOW);
                } else if (i < 8) {
                    brick.setColor(Color.GREEN);
                } else {
                    brick.setColor(Color.CYAN);
                }
                int x = j * (BRICK_MARGIN * 2 + BRICK_WIDTH) + BRICK_MARGIN;
                int y = i * (BRICK_MARGIN * 2 + BRICK_HEIGHT) + BRICK_MARGIN;
                add(brick, x, y);
            }
        }
    }
    /**
     * 画出一个挡板
     */
    public void makePaddle(){
        // 设置挡板长宽
        GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        // 设置挡板为实心
        paddle.setFilled(true);
        // 设置挡板颜色
        paddle.setColor(Color.BLACK);
        // 添加挡板到（100，700）
        add(paddle, 100, 700);
        this.paddle=paddle;
    }
    public GObject getCollidingObject() {
        /* getElementAt(x, y)可以获取画布上(x,y)位置的图形 */
        GObject obj = getElementAt(ball.getX(), ball.getY());
        GObject objx1 = getElementAt(ball.getX() + 0.1 * BALL_RADIUS, ball.getY());
        GObject objx2 = getElementAt(ball.getX() + 0.2 * BALL_RADIUS, ball.getY());
        GObject objx3 = getElementAt(ball.getX() + 0.3 * BALL_RADIUS, ball.getY());
        GObject objx4 = getElementAt(ball.getX() + 0.4 * BALL_RADIUS, ball.getY());
        GObject objx5 = getElementAt(ball.getX() + 0.5 * BALL_RADIUS, ball.getY());
        GObject objx6 = getElementAt(ball.getX() + 0.6 * BALL_RADIUS, ball.getY());
        GObject objx7 = getElementAt(ball.getX() + 0.7 * BALL_RADIUS, ball.getY());
        GObject objx8 = getElementAt(ball.getX() + 0.8 * BALL_RADIUS, ball.getY());
        GObject objx9 = getElementAt(ball.getX() + 0.9 * BALL_RADIUS, ball.getY());
        GObject objx10 = getElementAt(ball.getX() + BALL_RADIUS, ball.getY());
        GObject objx11 = getElementAt(ball.getX() + 1.1 * BALL_RADIUS, ball.getY());
        GObject objx12 = getElementAt(ball.getX() + 1.2 * BALL_RADIUS, ball.getY());
        GObject objx13 = getElementAt(ball.getX() + 1.3 * BALL_RADIUS, ball.getY());
        GObject objx14 = getElementAt(ball.getX() + 1.4 * BALL_RADIUS, ball.getY());
        GObject objx15 = getElementAt(ball.getX() + 1.5 * BALL_RADIUS, ball.getY());
        GObject objx16 = getElementAt(ball.getX() + 1.6 * BALL_RADIUS, ball.getY());
        GObject objx17 = getElementAt(ball.getX() + 1.7 * BALL_RADIUS, ball.getY());
        GObject objx18 = getElementAt(ball.getX() + 1.8 * BALL_RADIUS, ball.getY());
        GObject objx19 = getElementAt(ball.getX() + 1.9 * BALL_RADIUS, ball.getY());
        GObject objx20 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
        GObject objy1 = getElementAt(ball.getX(), ball.getY() + 0.1 * BALL_RADIUS);
        GObject objy2 = getElementAt(ball.getX(), ball.getY() + 0.2 * BALL_RADIUS);
        GObject objy3 = getElementAt(ball.getX(), ball.getY() + 0.3 * BALL_RADIUS);
        GObject objy4 = getElementAt(ball.getX(), ball.getY() + 0.4 * BALL_RADIUS);
        GObject objy5 = getElementAt(ball.getX(), ball.getY() + 0.5 * BALL_RADIUS);
        GObject objy6 = getElementAt(ball.getX(), ball.getY() + 0.6 * BALL_RADIUS);
        GObject objy7 = getElementAt(ball.getX(), ball.getY() + 0.7 * BALL_RADIUS);
        GObject objy8 = getElementAt(ball.getX(), ball.getY() + 0.8 * BALL_RADIUS);
        GObject objy9 = getElementAt(ball.getX(), ball.getY() + 0.9 * BALL_RADIUS);
        GObject objy10 = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS);
        GObject objy11 = getElementAt(ball.getX(), ball.getY() + 1.1 * BALL_RADIUS);
        GObject objy12 = getElementAt(ball.getX(), ball.getY() + 1.2 * BALL_RADIUS);
        GObject objy13 = getElementAt(ball.getX(), ball.getY() + 1.3 * BALL_RADIUS);
        GObject objy14 = getElementAt(ball.getX(), ball.getY() + 1.4 * BALL_RADIUS);
        GObject objy15 = getElementAt(ball.getX(), ball.getY() + 1.5 * BALL_RADIUS);
        GObject objy16 = getElementAt(ball.getX(), ball.getY() + 1.6 * BALL_RADIUS);
        GObject objy17 = getElementAt(ball.getX(), ball.getY() + 1.7 * BALL_RADIUS);
        GObject objy18 = getElementAt(ball.getX(), ball.getY() + 1.8 * BALL_RADIUS);
        GObject objy19 = getElementAt(ball.getX(), ball.getY() + 1.9 * BALL_RADIUS);
        GObject objy20 = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
        GObject obju1 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.1 * BALL_RADIUS);
        GObject obju2 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.2 * BALL_RADIUS);
        GObject obju3 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.3 * BALL_RADIUS);
        GObject obju4 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.4 * BALL_RADIUS);
        GObject obju5 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.5 * BALL_RADIUS);
        GObject obju6 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.6 * BALL_RADIUS);
        GObject obju7 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.7 * BALL_RADIUS);
        GObject obju8 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.8 * BALL_RADIUS);
        GObject obju9 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 0.9 * BALL_RADIUS);
        GObject obju10 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + BALL_RADIUS);
        GObject obju11 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.1 * BALL_RADIUS);
        GObject obju12 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.2 * BALL_RADIUS);
        GObject obju13 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.3 * BALL_RADIUS);
        GObject obju14 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.4 * BALL_RADIUS);
        GObject obju15 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.5 * BALL_RADIUS);
        GObject obju16 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.6 * BALL_RADIUS);
        GObject obju17 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.7 * BALL_RADIUS);
        GObject obju18 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.8 * BALL_RADIUS);
        GObject obju19 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 1.9 * BALL_RADIUS);
        GObject obju20 = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji1 = getElementAt(ball.getX() + 0.1 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji2 = getElementAt(ball.getX() + 0.2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji3 = getElementAt(ball.getX() + 0.3 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji4 = getElementAt(ball.getX() + 0.4 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji5 = getElementAt(ball.getX() + 0.5 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji6 = getElementAt(ball.getX() + 0.6 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji7 = getElementAt(ball.getX() + 0.7 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji8 = getElementAt(ball.getX() + 0.8 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji9 = getElementAt(ball.getX() + 0.9 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji11 = getElementAt(ball.getX() + 1.1 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji12 = getElementAt(ball.getX() + 1.2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji13 = getElementAt(ball.getX() + 1.3 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji14 = getElementAt(ball.getX() + 1.4 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji15 = getElementAt(ball.getX() + 1.5 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji16 = getElementAt(ball.getX() + 1.6 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji17 = getElementAt(ball.getX() + 1.7 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji18 = getElementAt(ball.getX() + 1.8 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        GObject obji19 = getElementAt(ball.getX() + 1.9 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
        //循环检测是否撞到小球任意四条边中的一个
        GObject[] list = {obj, objx1, objx2, objx3,objx4,objx5,objx6,objx7,objx8,objx9,objx10,objx11,objx12,objx13,objx14,objx15,objx16,objx17,objx18,objx19,objx20,objy1, objy2, objy3,objy4,objy5,objy6,objy7,objy8,objy9,objy10,objy11,objy12,objy13,objy14,objy15,objy16,objy17,objy18,objy19,objy20,obju1, obju2, obju3,obju4,obju5,obju6,obju7,obju8,obju9,obju10,obju11,obju12,obju13,obju14,obju15,obju16,obju17,obju18,obju19,obju20,obji1, obji2, obji3,obji4,obji5,obji6,obji7,obji8,obji9,obji11,obji12,obji13,obji14,obji15,obji16,obji17,obji18,obji19};
        for (GObject object : list) {
            if (object != null) {
                return object;
            }
        }
        return null;
        //撞到小球任意四条边中的一个回复物体，没撞到回复null
    }
    /**
     * 鼠标监听
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        paddle.setLocation(e.getX() - paddle.getWidth() / 2, 700);
    }
}