package hillel.spring.homework3;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class greetingServiceTest {

    @Test
    public void greetingRandom() {
        greetingService s = new greetingService(new greetingRepository());
        int gr [] = new int[3];
        for (int i = 0; i<100; i++){
            if(s.greetingRandom().equals("Hello"))
                gr[0]++;
            else if(s.greetingRandom().equals("Сiao"))
                gr[1]++;
            else
                gr[2]++;
        }
        for (int g : gr){
            System.out.println(g);
            assertThat(g).isGreaterThan(15);
        }
    }
}