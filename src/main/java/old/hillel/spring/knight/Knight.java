package hillel.spring.knight;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Data
public class Knight {
    private final String name;
    private final Horse horse;
    private final Gear gear;

    public Knight(@Qualifier("knightName") String name, Horse horse, Gear gear) {
        this.name = name;
        this.horse = horse;
        this.gear = gear;
    }
}

//@Component
//@Data
//public class Knight {
//    private final String name;
//    private final Gear gear;
//
//    @Autowired  // ставится над сеттером или полем
//    private Horse horse;  // если надо потом его проинициализировать (но лучше так не делать)
//
//    public Knight(@Qualifier("knightName") String name, Gear gear) {
//        this.name = name;
//        this.gear = gear;
//    }
//}
