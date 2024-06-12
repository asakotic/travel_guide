package rs.raf.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEdit {
    @NotNull(message = "userId is required")
    private int userId;
    @NotNull(message = "status is required")
    @NotEmpty(message = "status is required")
    private String status;
}
