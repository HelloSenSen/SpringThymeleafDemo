package springdemo.model1.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileFormDTO {
    @Size(min = 2 )
    private String twitterHandle;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    private LocalDate birthDate;
    @NotEmpty
    private List<String> tastes = new ArrayList<>();

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "ProfileFormDTO{" +
                "twitterHandle='" + twitterHandle + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", tastes=" + tastes.toArray() +
                '}';
    }
}
