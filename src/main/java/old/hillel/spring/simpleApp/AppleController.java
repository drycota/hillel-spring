package old.hillel.spring.simpleApp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class AppleController {
    private final AppleService appleService;

    public void printHeavyApples() {
        System.out.println(appleService.findHeavyApples());
    }
}
