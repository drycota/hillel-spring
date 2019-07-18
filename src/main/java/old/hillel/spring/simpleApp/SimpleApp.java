package hillel.spring.simpleApp;

import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SimpleApp {
    public static void main(String[] args) {
//        val repo = new AppleRepository();
//        val appleService = new AppleService(repo);
//        val appleController = new AppleController(appleService);
//        appleController.printHeavyApples();
        val ctx = new AnnotationConfigApplicationContext("hillel.spring.simpleApp");
        val appleController = ctx.getBean(AppleController.class);
        appleController.printHeavyApples();
    }
}
