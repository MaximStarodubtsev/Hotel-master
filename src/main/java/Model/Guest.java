package Model;

import lombok.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "guests", schema = "hotel_storage")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Guest implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "phonenumber", unique = true)
    private String phoneNumber;
    @Column(name = "birthdate")
    private LocalDateTime birthDate;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "guest")
    private List<Order> orders;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString(){
        return id + " " + name + " " + surname + " " + patronymic + " " + birthDate;
    }

}
