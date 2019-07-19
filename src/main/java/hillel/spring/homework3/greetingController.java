package hillel.spring.homework3;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class greetingController {
    private  final greetingService service;

    @GetMapping("/greeting/en")
    public String greetingEn(){
        return service.greetingEn();
    }

    @GetMapping("/greeting/it")
    public String greetingIt(){
        return service.greetingIt();
    }

    @GetMapping("/greeting/fr")
    public String greetingFr(){
        return service.greetingFr();
    }

    @GetMapping("/greeting/random")
    public String greetingRandom(){
        return service.greetingRandom();
    }
}
