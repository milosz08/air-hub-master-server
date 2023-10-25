/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.domain.plane;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jmpsl.core.db.AbstractAuditableEntity;
import pl.miloszgilga.domain.category.CategoryEntity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planes")
public class PlaneEntity extends AbstractAuditableEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "max_hours")
    private Integer maxHours;

    @Column(name = "base_multiplier")
    private Double baseMultiplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMaxHours() {
        return maxHours;
    }

    void setMaxHours(Integer maxHours) {
        this.maxHours = maxHours;
    }

    public Double getBaseMultiplier() {
        return baseMultiplier;
    }

    void setBaseMultiplier(Double baseMultiplier) {
        this.baseMultiplier = baseMultiplier;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return "{" +
            "name=" + name +
            ", price=" + price +
            '}';
    }
}
