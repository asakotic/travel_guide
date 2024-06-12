package rs.raf.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.web.entities.enums.Status;
import rs.raf.web.entities.enums.UserType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNew {
    @NotNull(message = "email field is required")
    @NotEmpty(message = "email field is required")
    private String email;
    @NotNull(message = "firstName field is required")
    @NotEmpty(message = "firstName field is required")
    private String firstName;
    @NotNull(message = "lastName field is required")
    @NotEmpty(message = "lastName field is required")
    private String lastName;
    @NotNull(message = "userType field is required")
    @NotEmpty(message = "userType field is required")
    private String userType;
    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String password;
}