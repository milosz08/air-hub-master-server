package pl.miloszgilga.ahms.network.account.reqdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateNameReqDto {
    @NotBlank(message = "jpa.validator.firstName.notBlank")
    @Size(min = 2, max = 70, message = "jpa.validator.firstName.length")
    private String firstName;

    @NotBlank(message = "jpa.validator.lastName.notBlank")
    @Size(min = 2, max = 70, message = "jpa.validator.lastName.length")
    private String lastName;

    @Override
    public String toString() {
        return "{" +
            "firstName=" + firstName +
            ", lastName=" + lastName +
            '}';
    }
}
