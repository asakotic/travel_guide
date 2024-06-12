package rs.raf.web.repositories.destionation;

import rs.raf.web.entities.Destination;
import rs.raf.web.requests.DestinationEdit;

import java.util.List;

public interface DestinationRepository {

    Destination findDestination(String name);
    List<Destination> findAll();
    List<Destination> destinationPerPage(int page);
    boolean edit(DestinationEdit destinationEdit);
    boolean delete(String destinationName);
    void addDestination(Destination destination);
}
