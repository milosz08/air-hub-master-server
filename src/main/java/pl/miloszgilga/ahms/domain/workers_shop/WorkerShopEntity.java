package pl.miloszgilga.ahms.domain.workers_shop;

import jakarta.persistence.*;
import lombok.*;
import pl.miloszgilga.ahms.domain.AbstractAuditableEntity;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.worker.WorkerEntity;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workers_shop")
public class WorkerShopEntity extends AbstractAuditableEntity implements Serializable {
    private Integer experience;

    private Integer cooperation;

    private Integer rebelliousness;

    private Integer skills;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkerEntity worker;

    public void setAbilities(int experience, int cooperation, int rebelliousness, int skills) {
        this.experience = experience;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "{" +
            "experience=" + experience +
            ", cooperation=" + cooperation +
            ", rebelliousness=" + rebelliousness +
            ", skills=" + skills +
            '}';
    }
}


