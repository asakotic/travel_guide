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
public class ArticleEdit {
    @NotNull(message = "title is required")
    @NotEmpty(message = "title is required")
    private String title;
    @NotNull(message = "text is required")
    @NotEmpty(message = "text is required")
    private String text;
    @NotNull(message = "destination is required")
    @NotEmpty(message = "destination is required")
    private String destinationName;
    @NotNull(message = "articleId is required")
    private int articleId;
}
