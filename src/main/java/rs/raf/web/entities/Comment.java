package rs.raf.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int commentId;
    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;
    @NotNull(message = "CreateTime field is required")
    private LocalDateTime createTime;
    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;


}
