package old.hillel.spring;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class AppleTest {

    @Test
    public void lombokWorks() throws Exception {
        Apple apple = new Apple(100, "Green");

        assertThat(apple.getColor()).isEqualTo("Green");

        assertThat(apple).isEqualTo(new Apple(100, "Green"));
    }

}