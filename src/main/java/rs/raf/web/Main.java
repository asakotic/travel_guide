package rs.raf.web;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.web.repositories.article.ArticleRepostitory;
import rs.raf.web.repositories.article.ArticleSqlRepository;
import rs.raf.web.repositories.destionation.DestinationRepository;
import rs.raf.web.repositories.destionation.DestinationSqlRepository;
import rs.raf.web.repositories.user.UserRepository;
import rs.raf.web.repositories.user.UserSqlRepository;
import rs.raf.web.services.ArticleService;
import rs.raf.web.services.DestinationService;
import rs.raf.web.services.UserService;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Main  extends ResourceConfig {

    public Main() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(UserSqlRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(DestinationSqlRepository.class).to(DestinationRepository.class).in(Singleton.class);
                this.bind(ArticleSqlRepository.class).to(ArticleRepostitory.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(DestinationService.class);
                this.bindAsContract(ArticleService.class);
            }
        };
        register(binder);

        packages("rs.raf.web.resources","rs.raf.web.filters","rs.raf.web");
    }

}