package Model;

import lombok.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "orders", schema = "hotel_storage")
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Order implements BaseEntity<Long>{

    public enum SettleState {
        SETTLED, RESERVED, ARCHIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "settlementdate")
    private LocalDateTime settlementDate;
    @Column(name = "leavedate")
    private LocalDateTime leaveDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "settleState")
    private SettleState settleState;
    @Column(name = "rent")
    private int rent;

}
