package pl.miloszgilga.ahms.domain.plane;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.category.CategoryEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planes")
public class PlaneEntity extends AbstractAuditableEntity implements Serializable {
    private String name;

    private Integer price;

    private Integer maxHours;

    private Double baseMultiplier;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Override
    public String toString() {
        return "{" +
            "name=" + name +
            ", price=" + price +
            ", maxHours=" + maxHours +
            ", baseMultiplier=" + baseMultiplier +
            '}';
    }
}
