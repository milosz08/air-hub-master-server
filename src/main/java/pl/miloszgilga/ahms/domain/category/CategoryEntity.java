package pl.miloszgilga.ahms.domain.category;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.plane.PlaneEntity;
import pl.miloszgilga.ahms.domain.worker.WorkerEntity;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends AbstractAuditableEntity implements Serializable {
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    private Integer level;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<PlaneEntity> planes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true)
    private Set<WorkerEntity> workers;

    @Override
    public String toString() {
        return "{" +
            "name=" + name +
            ", type=" + type +
            ", level=" + level +
            '}';
    }
}
