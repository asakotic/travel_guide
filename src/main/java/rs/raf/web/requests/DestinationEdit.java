package rs.raf.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationEdit {
    @NotNull(message = "on is required")
    @NotEmpty(message = "on is required")
    public String oldName;
    @NotNull(message = "nn is required")
    @NotEmpty(message = "nn is required")
    public String newName;
    @NotNull(message = "desc is required")
    @NotEmpty(message = "desc is required")
    public String description;
}
