package rs.raf.web.repositories.article;

import rs.raf.web.entities.*;
import rs.raf.web.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleSqlRepository extends MySqlAbstractRepository implements ArticleRepostitory {
    @Override
    public List<Article> articlesPerPageOnDest(int page, String destination) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from articles as a inner join users as u on a.authorId = u.userId" +
                            " where destinationName = ? order by a.createDate DESC limit 10 offset ?");
            statement.setString(1,destination);
            statement.setInt(2,((page - 1) * 10));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("articleId");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                LocalDateTime ldt = resultSet.getTimestamp("createDate").toLocalDateTime();
                int authorId = resultSet.getInt("authorId");
                String authorName = resultSet.getString("firstName");
                String authorLName = resultSet.getString("lastName");
                String destinationName = resultSet.getString("destinationName");

                articles.add(new Article(id,title,text,ldt,destinationName,new User(authorId,authorName,authorLName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }
    @Override
    public List<Article> allForActivity(int page, String activity) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.prepareStatement(
                    "SELECT * FROM articles AS a " +
                            "INNER JOIN users AS u ON a.authorId = u.userId " +
                            "INNER JOIN article_activity AS au ON a.articleId = au.articleId " +
                            "WHERE au.activity = ? " +
                            "ORDER BY a.createDate DESC " +
                            "LIMIT 10 OFFSET ?");
            statement.setString(1,activity);
            statement.setInt(2,((page - 1) * 10));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("articleId");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                LocalDateTime ldt = resultSet.getTimestamp("createDate").toLocalDateTime();
                int authorId = resultSet.getInt("authorId");
                String authorName = resultSet.getString("firstName");
                String authorLName = resultSet.getString("lastName");
                String destinationName = resultSet.getString("destinationName");

                articles.add(new Article(id,title,text,ldt,destinationName,new User(authorId,authorName,authorLName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }


    @Override
    public void addArticle(Article article, String activities) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> activitiesList = new ArrayList<>();
        try {
            connection = this.newConnection();
            connection.setAutoCommit(false);

            String[] generatedColumns = {"articleId"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO articles (title, text, createDate,visitCount,authorId,destinationName) " +
                            "VALUES(?, ?, ?, 0, ?, ?)", generatedColumns);

            preparedStatement.setString(1,article.getTitle());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(article.getCreateTime()));
            preparedStatement.setInt(4,article.getAuthor().getUserId());
            preparedStatement.setString(5,article.getDestinationName());
            preparedStatement.executeUpdate();

            activitiesList = List.of(activities.split("\n"));

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int auto_id = rs.getInt(1);

            preparedStatement = connection.prepareStatement("INSERT INTO article_activity (articleId,activity) " +
                    "VALUES(?,  ?)");

            for(String activity: activitiesList){
                preparedStatement.setInt(1,auto_id);
                preparedStatement.setString(2, activity);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            this.rollback(connection);
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

    }

    @Override
    public void editArticle(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE articles SET title = ?, text = ?, destinationName = ? WHERE articleId = ?");
            preparedStatement.setString(1,article.getTitle());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setString(3,article.getDestinationName());
            preparedStatement.setInt(4,article.getArticleId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteArticle(int articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "delete from articles WHERE articleId = ?");
            preparedStatement.setInt(1, articleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Article> articlesPerPage(int page) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from articles as a inner join users as u on a.authorId = u.userId" +
                            " order by a.createDate DESC limit 10 offset ?");
            statement.setInt(1,((page - 1) * 10));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("articleId");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                LocalDateTime ldt = resultSet.getTimestamp("createDate").toLocalDateTime();
                int authorId = resultSet.getInt("authorId");
                String authorName = resultSet.getString("firstName");
                String authorLName = resultSet.getString("lastName");
                String destinationName = resultSet.getString("destinationName");

                articles.add(new Article(id,title,text,ldt,destinationName,new User(authorId,authorName,authorLName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<String> getActivities(int articleId) {
        List<String> activities = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from article_activity where articleId = ?");
            statement.setInt(1,articleId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String activity = resultSet.getString("activity");
                activities.add(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return activities;
    }

    @Override
    public List<Comment> getComments(int articleId) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from comments where articleId = ?");
            statement.setInt(1,articleId);
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("commentId");
                String author = resultSet.getString("author");
                String content = resultSet.getString("content");
                LocalDateTime createTime = resultSet.getTimestamp("createDate").toLocalDateTime();
                comments.add(new Comment(id,content,createTime,author));
            }
            System.out.println(comments);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public void addNewComment(Comment comment, int articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"commentId"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO comments (content, createDate, author, articleId) " +
                            "VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(comment.getCreateTime()));
            preparedStatement.setString(3, comment.getAuthor());
            preparedStatement.setInt(4,articleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<Article> newArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from articles as a inner join users as u on a.authorId = u.userId" +
                            " order by a.createDate DESC limit 10");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("articleId");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                LocalDateTime ldt = resultSet.getTimestamp("createDate").toLocalDateTime();
                int authorId = resultSet.getInt("authorId");
                String authorName = resultSet.getString("firstName");
                String authorLName = resultSet.getString("lastName");
                String destinationName = resultSet.getString("destinationName");

                articles.add(new Article(id,title,text,ldt,destinationName,new User(authorId,authorName,authorLName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }
    @Override
    public List<Article> mostPopular() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement(
                    "select * from articles as a inner join users as u on a.authorId = u.userId" +
                            " where a.createDate >= NOW() - INTERVAL 30 DAY order by a.visitCount DESC limit 10");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("articleId");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                LocalDateTime ldt = resultSet.getTimestamp("createDate").toLocalDateTime();
                int authorId = resultSet.getInt("authorId");
                String authorName = resultSet.getString("firstName");
                String authorLName = resultSet.getString("lastName");
                String destinationName = resultSet.getString("destinationName");

                articles.add(new Article(id,title,text,ldt,destinationName,new User(authorId,authorName,authorLName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }



    @Override
    public void increment(int articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE articles SET visitCount = visitCount + 1 WHERE articleId = ?");
            preparedStatement.setInt(1,articleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
    }
}
