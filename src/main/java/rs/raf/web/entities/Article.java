package rs.raf.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int articleId;
    @NotNull(message = "title field is required")
    @NotEmpty(message = "title field is required")
    private String title;
    @NotNull(message = "text field is required")
    @NotEmpty(message = "text field is required")
    private String text;
    @NotNull(message = "createTime field is required")
    private LocalDateTime createTime;
    @NotNull(message = "visitCount field is required")
    @NotEmpty(message = "visitCount field is required")
    private int visitCount = 0;
    @NotNull(message = "author field is required")
    private User author;
    @NotNull(message = "destinationName field is required")
    @NotEmpty(message = "destinationName field is required")
    private String destinationName;
    @NotNull(message = "comments field is required")
    @NotEmpty(message = "comments field is required")
    private List<Comment> comments = new ArrayList<>();
    @NotNull(message = "activities field is required")
    @NotEmpty(message = "activities field is required")
    private List<Activity> activities = new ArrayList<>();


    public Article(int articleId, String title, String text, LocalDateTime createTime,String destinationName, User author) {
        this.articleId = articleId;
        this.title = title;
        this.text = text;
        this.createTime = createTime;
        this.destinationName = destinationName;
        this.author = author;
    }
}
