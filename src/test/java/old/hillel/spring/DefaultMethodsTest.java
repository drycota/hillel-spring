package hillel.spring;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class DefaultMethodsTest {

    @Test
    public void sortApples() throws Exception {
        final List<Integer> weights = new ArrayList<>(){{
            add(1);
            add(6);
            add(2);
            add(5);
        }};

        // Collections.sort(weights);

        weights.sort(Comparator.naturalOrder());

        System.out.println(weights);
    }


}
