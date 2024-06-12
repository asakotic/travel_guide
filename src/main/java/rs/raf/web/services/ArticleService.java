package rs.raf.web.services;

import rs.raf.web.entities.Article;
import rs.raf.web.entities.Comment;
import rs.raf.web.entities.Destination;
import rs.raf.web.repositories.article.ArticleRepostitory;
import rs.raf.web.repositories.destionation.DestinationRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleService {
    @Inject
    private ArticleRepostitory articleRepostitory;
    public List<Article> getPerPage(int page, String destination) {
        return this.articleRepostitory.articlesPerPageOnDest(page,destination);
    }
    public void addNew(Article article, String activities) {
        this.articleRepostitory.addArticle(article, activities);
    }
    public void edit(Article article){
        this.articleRepostitory.editArticle(article);
    }
    public void delete(int articleId){
        this.articleRepostitory.deleteArticle(articleId);
    }

    public List<Article> allOnPage(int page) {
        return this.articleRepostitory.articlesPerPage(page);
    }

    public List<String> getActivities(int articleId) {
        return this.articleRepostitory.getActivities(articleId);
    }
    public List<Comment> getComments(int articleId) {
        return this.articleRepostitory.getComments(articleId);
    }

    public void addNewComment(Comment comment, int articleId) {
        this.articleRepostitory.addNewComment(comment, articleId);
    }

    public List<Article> allNewArticles() {
        return this.articleRepostitory.newArticles();
    }

    public void increment(int articleId) {
        this.articleRepostitory.increment(articleId);
    }

    public List<Article> mostPopular() {
        return this.articleRepostitory.mostPopular();
    }

    public List<Article> allForActivity(int page, String activity) {
        return this.articleRepostitory.allForActivity(page, activity);
    }
}
