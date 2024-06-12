package rs.raf.web.resources;

import org.modelmapper.ModelMapper;
import rs.raf.web.entities.Article;
import rs.raf.web.entities.Comment;
import rs.raf.web.entities.Destination;
import rs.raf.web.entities.User;
import rs.raf.web.repositories.user.UserRepository;
import rs.raf.web.requests.ArticleEdit;
import rs.raf.web.requests.ArticleNew;
import rs.raf.web.services.ArticleService;
import rs.raf.web.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/articles")
public class ArticleResource {
    @Inject
    private ArticleService articleService;
    @Inject
    private UserRepository userRepository;
    @GET
    @Path("/articlesPP/{page}/{destination}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPerPage(@PathParam("page") int page, @PathParam("destination") String destination){

        List<Article> articles = this.articleService.getPerPage(page,destination);
        return Response
                .ok(articles)
                .build();
    }
    @GET
    @Path("/articlesUser/{page}/{activity}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPerPageForActivity(@PathParam("page") int page, @PathParam("activity") String activity){

        List<Article> articles = this.articleService.allForActivity(page,activity);
        return Response
                .ok(articles)
                .build();
    }
    @GET
    @Path("/activities/{articleId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getActivities(@PathParam("articleId") int articleId){

        List<String> activities = this.articleService.getActivities(articleId);
        return Response
                .ok(activities)
                .build();
    }
    @GET
    @Path("/comments/{articleId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComments(@PathParam("articleId") int articleId){

        List<Comment> comments = this.articleService.getComments(articleId);
        return Response
                .ok(comments)
                .build();
    }
    @GET
    @Path("/articlesPP/{page}/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPerPage(@PathParam("page") int page){

        List<Article> articles = this.articleService.allOnPage(page);
        return Response
                .ok(articles)
                .build();
    }
    @GET
    @Path("/newArticles")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllNewArticles(){

        List<Article> articles = this.articleService.allNewArticles();
        return Response
                .ok(articles)
                .build();
    }
    @GET
    @Path("/mostPopular")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMostPopular(){

        List<Article> articles = this.articleService.mostPopular();
        return Response
                .ok(articles)
                .build();
    }
    @GET
    @Path("/increment/{articleId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response incrementCount(@PathParam("articleId") int articleId){

        this.articleService.increment(articleId);
        return Response
                .ok()
                .build();
    }
    @POST
    @Path("/addNew")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNew(@Valid ArticleNew articleNew){

        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(articleNew, Article.class);
        User user = new User();
        int id = userRepository.getMeUser(articleNew.getEmail());
        user.setUserId(id);
        article.setAuthor(user);
        String activities = articleNew.getActivities();

        this.articleService.addNew(article, activities);
        return Response
                .ok()
                .build();
    }
    @POST
    @Path("/newComment/{articleId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNewComment(@Valid Comment comment, @PathParam("articleId") int articleId ){


        this.articleService.addNewComment(comment,articleId);
        return Response
                .ok()
                .build();
    }
    @PUT
    @Path("/edit")
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@Valid ArticleEdit articleEdit){

        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(articleEdit, Article.class);

        this.articleService.edit(article);
        return Response
                .ok()
                .build();
    }
    @DELETE
    @Path("/delete/{articleId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("articleId") int articleId){

        this.articleService.delete(articleId);
        return Response
                .ok()
                .build();
    }
}
