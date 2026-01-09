package ch.bbw.sk.controller;

import ch.bbw.sk.dto.AddBidRequest;
import ch.bbw.sk.dto.CreateContainerRequest;
import ch.bbw.sk.exception.ContainerNotFoundException;
import ch.bbw.sk.exception.InvalidBidException;
import ch.bbw.sk.exception.ValidationException;
import ch.bbw.sk.model.Container;
import ch.bbw.sk.service.ContainerService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/containers")
public class ContainerController {

  private final ContainerService containerService;

  public ContainerController(ContainerService containerService) {
    this.containerService = containerService;
  }

  @GetMapping
  public List<Container> getAllContainers() {
    return containerService.getAllContainers();
  }

  @GetMapping("/{id}")
  public Container getContainerById(@PathVariable UUID id) throws ContainerNotFoundException {
    return containerService.getContainerById(id);
  }

  @PostMapping
  public Container createContainer(@RequestBody CreateContainerRequest request)
      throws ValidationException {
    return containerService.createContainer(request);
  }

  @PatchMapping("/{id}/like")
  public Container toggleLike(@PathVariable UUID id) throws ContainerNotFoundException {
    return containerService.toggleLike(id);
  }

  @PostMapping("/{id}/bid")
  public Container addBid(@PathVariable UUID id, @RequestBody AddBidRequest request)
      throws ContainerNotFoundException, InvalidBidException, ValidationException {
    return containerService.addBid(id, request);
  }
}
