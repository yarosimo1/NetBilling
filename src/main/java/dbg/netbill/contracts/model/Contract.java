package dbg.netbill.contracts.model;

import dbg.netbill.users.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 10, unique = true)
    private Long number;

    @Column(nullable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
    private LocalDateTime closeAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;
}
