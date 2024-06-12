package rs.raf.web.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleNew {
    @NotNull(message = "title is required")
    @NotEmpty(message = "title is required")
    String title;
    @NotNull(message = "text is required")
    @NotEmpty(message = "text is required")
    String text;
    @NotNull(message = "destination is required")
    @NotEmpty(message = "destination is required")
    String destinationName;
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    String email;
    @NotNull(message = "date is required")
    LocalDateTime createTime;
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    String activities;
}
