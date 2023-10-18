package food.system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    private String time;
    private String composition;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portion> portions;
    private Boolean status;
}
