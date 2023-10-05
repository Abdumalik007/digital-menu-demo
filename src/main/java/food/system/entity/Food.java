package food.system.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToOne
    private Image image;
    private String time;
    private String composition;
    @ManyToOne
    private Category category;
    private Integer price;
    private Boolean status;
}
