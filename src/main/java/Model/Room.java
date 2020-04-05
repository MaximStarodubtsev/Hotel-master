package Model;

import lombok.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "rooms", schema = "hotel_storage")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Room implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private int number;
    @Enumerated(EnumType.STRING)
    @Column(name = "places")
    private Places places;
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;
    @Column(name = "rent")
    private int rent;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @Override
    public String toString(){
        return id + " " + number + " " + places + " " + level + " " + rent;
    }

    public enum Level {
        BUDGET, MEDIUM, LUX
    }

    public enum Places {
        ONE, TWO, THREE, FOUR
    }
}
