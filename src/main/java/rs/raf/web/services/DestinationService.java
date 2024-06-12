package rs.raf.web.services;

import rs.raf.web.entities.Article;
import rs.raf.web.entities.Destination;
import rs.raf.web.entities.User;
import rs.raf.web.repositories.destionation.DestinationRepository;
import rs.raf.web.requests.DestinationEdit;

import javax.inject.Inject;
import java.util.List;

public class DestinationService {

    @Inject
    private DestinationRepository destinationRepository;

    public List<Destination> allDestinations() {
        return this.destinationRepository.findAll();
    }
    public List<Destination> getPerPage(int page){
        return this.destinationRepository.destinationPerPage(page);
    }

    public void addNew(Destination destination) {
        this.destinationRepository.addDestination(destination);
    }

    public boolean edit(DestinationEdit destinationEdit) {
        return this.destinationRepository.edit(destinationEdit);
    }

    public boolean delete(String destinationName) {
         return this.destinationRepository.delete(destinationName);
    }
}
