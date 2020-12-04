import java.awt.*;
import java.lang.reflect.Field;

import static org.mockito.Mockito.spy;

public class P05_Direction {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Arkanoid arkanoid = spy(Arkanoid.class);
        arkanoid.init();

        // black ball
        assert arkanoid.ball.getColor() == Color.BLACK;

        // delay is changed
        Field delayField = Arkanoid.class.getDeclaredField("DELAY");
        delayField.setAccessible(true);
        Object delay = delayField.get(null);
        if (delay instanceof Integer) {
            int tmp = (int)delay;
            assert tmp > 10;
            assert tmp < 17;
        } else if (delay instanceof Double) {
            double tmp = (double)delay;
            assert tmp > 10;
            assert tmp < 17;
        } else {
            assert false : "static field DELAY type error";
        }
    }
}
