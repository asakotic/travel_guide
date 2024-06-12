package rs.raf.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    @NotNull(message = "name field is required")
    @NotEmpty(message = "name field is required")
    private String name;
    @NotNull(message = "description field is required")
    @NotEmpty(message = "description field is required")
    private String description;
    @NotNull(message = "articles field is required")
    @NotEmpty(message = "articles field is required")
    private List<Article> articles = new ArrayList<>();

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
