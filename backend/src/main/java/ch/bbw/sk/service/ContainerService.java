package ch.bbw.sk.service;

import ch.bbw.sk.dto.AddBidRequest;
import ch.bbw.sk.dto.CreateContainerRequest;
import ch.bbw.sk.exception.ContainerNotFoundException;
import ch.bbw.sk.exception.InvalidBidException;
import ch.bbw.sk.model.Bid;
import ch.bbw.sk.model.Container;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
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
    try (InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream("mock-data.json")) {
      ObjectMapper mapper = new ObjectMapper();

      List<MockContainerData> mockData =
          mapper.readValue(inputStream, new TypeReference<List<MockContainerData>>() {});

      for (MockContainerData data : mockData) {
        Container container =
            createContainer(
                new CreateContainerRequest(
                    data.name(), data.description(), data.mainImage(), data.thumbnails()));

        if (data.liked()) {
          container.toggleLike();
        }

        for (MockBidData bidData : data.bids()) {
          addBid(container.getId(), new AddBidRequest(bidData.name(), bidData.amount()));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to load mock data", e);
    } finally {
      System.out.println("Mock data loading completed");
    }
  }

  private record MockContainerData(
      String name,
      String description,
      String mainImage,
      List<String> thumbnails,
      List<MockBidData> bids,
      boolean liked) {}

  private record MockBidData(String name, Double amount) {}

  public List<Container> getAllContainers() {
    return new ArrayList<>(containers);
  }

  public Container getContainerById(UUID id) throws ContainerNotFoundException {
    return containers.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ContainerNotFoundException("Container not found with id: " + id));
  }

  public Container createContainer(CreateContainerRequest request) {
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
  }

  public Container toggleLike(UUID id) throws ContainerNotFoundException {
    Container container = getContainerById(id);
    container.toggleLike();
    return container;
  }

  public Container addBid(UUID id, AddBidRequest request)
      throws ContainerNotFoundException, InvalidBidException {
    Container container = getContainerById(id);

    Long bidId = bidIdCounter.getAndIncrement();
    Bid bid = new Bid(bidId, request.getName(), request.getAmount(), Instant.now());

    container.addBid(bid);

    return container;
  }
}
