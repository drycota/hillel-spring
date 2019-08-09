package old.hillel.spring.knight;

import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class KnightApp {
    public static void main(String[] args) {
//        val sword = new Sword();
//        val armor = new Armor();
//        val gear = new Gear(sword, armor);
//        val horse = new Horse("Rocenant");
//        val knight = new Knight("Keehot", horse, gear);
//
//        System.out.println(knight);

        val ctx = new AnnotationConfigApplicationContext("hillel.spring.knight");
//        val gear = ctx.getBean(Gear.class);
//        val horse = ctx.getBean(Horse.class);
        val knight = ctx.getBean(Knight.class);

        System.out.println(knight);
    }
}
