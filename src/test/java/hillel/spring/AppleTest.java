package hillel.spring;

import lombok.val;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class AppleTest {

    @Test
    public void lombokWorks() throws Exception {
        val apple = new Apple(100, "Green");

        assertThat(apple.getColor()).isEqualTo("Green");

        assertThat(apple).isEqualTo(new Apple(100, "Green"));
    }

}