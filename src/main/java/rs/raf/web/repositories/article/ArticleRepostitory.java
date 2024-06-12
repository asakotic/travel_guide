package rs.raf.web.repositories.article;

import rs.raf.web.entities.Article;
import rs.raf.web.entities.Comment;

import java.util.List;

public interface ArticleRepostitory {
    List<Article> articlesPerPageOnDest(int page, String destination);
    void addArticle(Article article, String acitivities);
    void editArticle(Article article);
    void deleteArticle(int articleId);

    List<Article> articlesPerPage(int page);

    List<String> getActivities(int articleId);
    List<Comment> getComments(int articleId);

    void addNewComment(Comment comment, int articleId);

    List<Article> newArticles();

    void increment(int articleId);

    List<Article> mostPopular();

    List<Article> allForActivity(int page, String activity);
}
