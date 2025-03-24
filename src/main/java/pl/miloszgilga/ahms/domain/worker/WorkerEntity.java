package pl.miloszgilga.ahms.domain.worker;

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
@Table(name = "workers")
public class WorkerEntity extends AbstractAuditableEntity implements Serializable {
    private String firstName;

    private String lastName;

    private Integer price;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Override
    public String toString() {
        return "{" +
            "firstName='" + firstName +
            ", lastName='" + lastName +
            ", price=" + price +
            '}';
    }
}
