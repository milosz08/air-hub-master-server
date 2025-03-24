package pl.miloszgilga.ahms.network.user_stats.reqdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateLevelReqDto {
    @NotNull(message = "jpa.validator.level.notBlank")
    @Min(value = 1, message = "jpa.validator.level.min")
    @Max(value = 50, message = "jpa.validator.level.max")
    private Integer level;

    @Override
    public String toString() {
        return "{" +
            "level=" + level +
            '}';
    }
}
