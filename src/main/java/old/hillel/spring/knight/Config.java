package old.hillel.spring.knight;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public String horseName(){
        return "Rocenant";
    }
    @Bean
    public String knightName(){
        return  "Keehot";
    }
}
