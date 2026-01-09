package ch.bbw.sk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.bbw.sk.dto.AddBidRequest;
import ch.bbw.sk.dto.CreateContainerRequest;
import ch.bbw.sk.exception.ContainerNotFoundException;
import ch.bbw.sk.exception.InvalidBidException;
import ch.bbw.sk.exception.ValidationException;
import ch.bbw.sk.model.Container;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContainerServiceExceptionHandlingTest {

  private ContainerService containerService;

  @BeforeEach
  public void setUp() {
    containerService = new ContainerService();
  }

  @Test
  public void testGetContainerById_ThrowsContainerNotFoundException() {
    UUID nonExistentId = UUID.randomUUID();

    assertThrows(
        ContainerNotFoundException.class,
        () -> containerService.getContainerById(nonExistentId));
  }

  @Test
  public void testGetContainerById_HasCorrectErrorMessage() {
    UUID nonExistentId = UUID.randomUUID();

    ContainerNotFoundException exception =
        assertThrows(
            ContainerNotFoundException.class,
            () -> containerService.getContainerById(nonExistentId));

    assertTrue(exception.getMessage().contains("Container not found"));
    assertTrue(exception.getMessage().contains(nonExistentId.toString()));
  }

  @Test
  public void testCreateContainer_EmptyName_ThrowsValidationException() {
    CreateContainerRequest request =
        new CreateContainerRequest("", "Description", "image.jpg", null);

    assertThrows(ValidationException.class, () -> containerService.createContainer(request));
  }

  @Test
  public void testCreateContainer_NullName_ThrowsValidationException() {
    CreateContainerRequest request =
        new CreateContainerRequest(null, "Description", "image.jpg", null);

    assertThrows(ValidationException.class, () -> containerService.createContainer(request));
  }

  @Test
  public void testCreateContainer_NameTooLong_ThrowsValidationException() {
    String longName = "a".repeat(101);
    CreateContainerRequest request =
        new CreateContainerRequest(longName, "Description", "image.jpg", null);

    assertThrows(ValidationException.class, () -> containerService.createContainer(request));
  }

  @Test
  public void testCreateContainer_DescriptionTooLong_ThrowsValidationException() {
    String longDescription = "a".repeat(501);
    CreateContainerRequest request =
        new CreateContainerRequest("Name", longDescription, "image.jpg", null);

    assertThrows(ValidationException.class, () -> containerService.createContainer(request));
  }

  @Test
  public void testCreateContainer_ValidData_Succeeds() throws ValidationException {
    CreateContainerRequest request =
        new CreateContainerRequest(
            "Test Container", "Test Description", "image.jpg", null);

    Container result = containerService.createContainer(request);

    assertNotNull(result);
    assertEquals("Test Container", result.getName());
    assertEquals("Test Description", result.getDescription());
  }

  @Test
  public void testAddBid_EmptyName_ThrowsValidationException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("", 100.0);

    assertThrows(
        ValidationException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_NullName_ThrowsValidationException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest(null, 100.0);

    assertThrows(
        ValidationException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_NameTooLong_ThrowsValidationException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    String longName = "a".repeat(51);
    AddBidRequest bidRequest = new AddBidRequest(longName, 100.0);

    assertThrows(
        ValidationException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_NullAmount_ThrowsValidationException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("Bidder", null);

    assertThrows(
        ValidationException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_ZeroAmount_ThrowsInvalidBidException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("Bidder", 0.0);

    assertThrows(
        InvalidBidException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_NegativeAmount_ThrowsInvalidBidException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("Bidder", -100.0);

    assertThrows(
        InvalidBidException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_TooLargeAmount_ThrowsInvalidBidException() {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("Bidder", 1000001.0);

    assertThrows(
        InvalidBidException.class, () -> containerService.addBid(container.getId(), bidRequest));
  }

  @Test
  public void testAddBid_NonExistentContainer_ThrowsContainerNotFoundException() {
    UUID nonExistentId = UUID.randomUUID();
    AddBidRequest bidRequest = new AddBidRequest("Bidder", 100.0);

    assertThrows(
        ContainerNotFoundException.class, () -> containerService.addBid(nonExistentId, bidRequest));
  }

  @Test
  public void testAddBid_ValidData_Succeeds()
      throws ValidationException, InvalidBidException, ContainerNotFoundException {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    AddBidRequest bidRequest = new AddBidRequest("Bidder", 100.0);

    Container result = containerService.addBid(container.getId(), bidRequest);

    assertNotNull(result);
    assertEquals(1, result.getBids().size());
    assertEquals("Bidder", result.getBids().get(0).getName());
    assertEquals(100.0, result.getBids().get(0).getAmount());
  }

  @Test
  public void testToggleLike_NonExistentContainer_ThrowsContainerNotFoundException() {
    UUID nonExistentId = UUID.randomUUID();

    assertThrows(
        ContainerNotFoundException.class, () -> containerService.toggleLike(nonExistentId));
  }

  @Test
  public void testToggleLike_ValidContainer_Succeeds()
      throws ContainerNotFoundException, ValidationException {
    CreateContainerRequest containerRequest =
        new CreateContainerRequest("Container", "Description", "image.jpg", null);
    Container container = containerService.createContainer(containerRequest);

    Container result = containerService.toggleLike(container.getId());

    assertNotNull(result);
    assertTrue(result.isLiked());
  }
}
