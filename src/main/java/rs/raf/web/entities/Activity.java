package rs.raf.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private int activityId;
    @NotNull(message = "activityName field is required")
    @NotEmpty(message = "activityName field is required")
    private String activityName;
}
