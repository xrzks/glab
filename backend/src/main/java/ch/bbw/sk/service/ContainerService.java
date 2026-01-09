package ch.bbw.sk.service;

import ch.bbw.sk.dto.AddBidRequest;
import ch.bbw.sk.dto.CreateContainerRequest;
import ch.bbw.sk.exception.ContainerNotFoundException;
import ch.bbw.sk.exception.InvalidBidException;
import ch.bbw.sk.exception.ValidationException;
import ch.bbw.sk.model.Bid;
import ch.bbw.sk.model.Container;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class ContainerService {

  private final List<Container> containers = new ArrayList<>();
  private final AtomicLong bidIdCounter = new AtomicLong(1);

  @PostConstruct
  public void initMockData() {
    Container container1 =
        createContainer(
            new CreateContainerRequest(
                "Container 1",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor"
                    + " incididunt ut labore et dolore magna aliqua.",
                "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?w=800",
                Arrays.asList(
                    "https://images.unsplash.com/photo-1523170335258-f5ed11844a49?w=200",
                    "https://images.unsplash.com/photo-1587836374828-4dbafa94cf0e?w=200",
                    "https://images.unsplash.com/photo-1594534475808-b18fc33b045e?w=200")));

    Container container2 =
        createContainer(
            new CreateContainerRequest(
                "Container 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor"
                    + " incididunt ut labore et dolore magna aliqua.",
                "https://images.unsplash.com/photo-1552346154-21d32810aba3?w=800",
                Arrays.asList(
                    "https://images.unsplash.com/photo-1552346154-21d32810aba3?w=200",
                    "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=200")));

    Container container3 =
        createContainer(
            new CreateContainerRequest(
                "Container 3",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor"
                    + " incididunt ut labore et dolore magna aliqua.",
                "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800",
                Arrays.asList(
                    "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=200",
                    "https://images.unsplash.com/photo-1495707902641-75cac588d2e9?w=200",
                    "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=200")));

    Container container4 =
        createContainer(
            new CreateContainerRequest(
                "Container 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor"
                    + " incididunt ut labore et dolore magna aliqua.",
                "https://images.unsplash.com/photo-1612036782180-6f0b6cd846fe?w=800",
                Arrays.asList(
                    "https://images.unsplash.com/photo-1612036782180-6f0b6cd846fe?w=200",
                    "https://images.unsplash.com/photo-1611457194403-d3f156e50cae?w=200")));

    Container container5 =
        createContainer(
            new CreateContainerRequest(
                "Container 5",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor"
                    + " incididunt ut labore et dolore magna aliqua.",
                "https://images.unsplash.com/photo-1603048588665-791ca8aea617?w=800",
                Arrays.asList(
                    "https://images.unsplash.com/photo-1603048588665-791ca8aea617?w=200",
                    "https://images.unsplash.com/photo-1619983081563-430f63602796?w=200",
                    "https://images.unsplash.com/photo-1571330735066-03aaa9429d89?w=200")));

    addBid(container1.getId(), new AddBidRequest("John Doe", 500.0));
    addBid(container1.getId(), new AddBidRequest("Jane Smith", 750.0));
    addBid(container1.getId(), new AddBidRequest("Bob Wilson", 1000.0));

    addBid(container2.getId(), new AddBidRequest("Alice Johnson", 1200.0));
    addBid(container2.getId(), new AddBidRequest("Charlie Brown", 1500.0));

    addBid(container3.getId(), new AddBidRequest("David Lee", 300.0));
    addBid(container3.getId(), new AddBidRequest("Emma Davis", 450.0));
    addBid(container3.getId(), new AddBidRequest("Frank Miller", 600.0));
    addBid(container3.getId(), new AddBidRequest("Grace Kim", 800.0));

    addBid(container4.getId(), new AddBidRequest("Henry Ford", 18000.0));

    addBid(container5.getId(), new AddBidRequest("Ivy Chen", 50.0));
    addBid(container5.getId(), new AddBidRequest("Jack Taylor", 75.0));

    container1.toggleLike();
    container3.toggleLike();
    container5.toggleLike();
  }

  public List<Container> getAllContainers() {
    return new ArrayList<>(containers);
  }

  public Container getContainerById(UUID id) throws ContainerNotFoundException {
    try {
      return containers.stream()
          .filter(c -> c.getId().equals(id))
          .findFirst()
          .orElseThrow(
              () -> new ContainerNotFoundException("Container not found with id: " + id));
    } catch (ContainerNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new ContainerNotFoundException(
          "Error retrieving container with id: " + id, e);
    } finally {
    }
  }

  public Container createContainer(CreateContainerRequest request)
      throws ValidationException {
    try {
      validateCreateContainerRequest(request);

      UUID id = UUID.randomUUID();
      Container container =
          new Container(
              id,
              request.getName(),
              request.getDescription(),
              request.getMainImage(),
              request.getThumbnails());
      containers.add(container);
      return container;
    } catch (ValidationException e) {
      throw e;
    } catch (Exception e) {
      throw new ValidationException("Failed to create container", e);
    }
  }

  private void validateCreateContainerRequest(CreateContainerRequest request)
      throws ValidationException {
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new ValidationException("Container name cannot be empty");
    }
    if (request.getName().length() > 100) {
      throw new ValidationException("Container name cannot exceed 100 characters");
    }
    if (request.getDescription() != null && request.getDescription().length() > 500) {
      throw new ValidationException("Container description cannot exceed 500 characters");
    }
  }

  public Container toggleLike(UUID id) throws ContainerNotFoundException {
    Container container;
    try {
      container = getContainerById(id);
      container.toggleLike();
      return container;
    } catch (ContainerNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new ContainerNotFoundException("Failed to toggle like for container: " + id, e);
    }
  }

  public Container addBid(UUID id, AddBidRequest request)
      throws ContainerNotFoundException, InvalidBidException, ValidationException {
    Container container;
    try {
      validateAddBidRequest(request);

      container = getContainerById(id);

      Long bidId = bidIdCounter.getAndIncrement();
      Bid bid = new Bid(bidId, request.getName(), request.getAmount(), Instant.now());

      container.addBid(bid);

      return container;
    } catch (ContainerNotFoundException e) {
      throw e;
    } catch (InvalidBidException e) {
      throw e;
    } catch (ValidationException e) {
      throw e;
    } catch (Exception e) {
      throw new InvalidBidException("Failed to add bid for container: " + id, e);
    } finally {
    }
  }

  private void validateAddBidRequest(AddBidRequest request)
      throws ValidationException, InvalidBidException {
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new ValidationException("Bidder name cannot be empty");
    }
    if (request.getName().length() > 50) {
      throw new ValidationException("Bidder name cannot exceed 50 characters");
    }
    if (request.getAmount() == null) {
      throw new ValidationException("Bid amount cannot be null");
    }
    if (request.getAmount() <= 0) {
      throw new InvalidBidException("Bid amount must be greater than zero");
    }
    if (request.getAmount() > 1000000) {
      throw new InvalidBidException("Bid amount cannot exceed 1,000,000");
    }
  }
}
