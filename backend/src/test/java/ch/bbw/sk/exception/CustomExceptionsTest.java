package ch.bbw.sk.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CustomExceptionsTest {

  @Test
  public void testContainerNotFoundExceptionCreation() {
    String message = "Container not found with id: 123";
    ContainerNotFoundException exception = new ContainerNotFoundException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testContainerNotFoundExceptionWithCause() {
    String message = "Container not found";
    Throwable cause = new RuntimeException("Database error");
    ContainerNotFoundException exception =
        new ContainerNotFoundException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void testInvalidBidExceptionCreation() {
    String message = "Bid amount must be greater than zero";
    InvalidBidException exception = new InvalidBidException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testInvalidBidExceptionWithCause() {
    String message = "Invalid bid";
    Throwable cause = new IllegalArgumentException("Negative amount");
    InvalidBidException exception = new InvalidBidException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void testValidationExceptionCreation() {
    String message = "Name cannot be empty";
    ValidationException exception = new ValidationException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testValidationExceptionWithCause() {
    String message = "Validation failed";
    Throwable cause = new NullPointerException("Field is null");
    ValidationException exception = new ValidationException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  public void testExceptionCanBeThrown() {
    assertThrows(
        ContainerNotFoundException.class,
        () -> {
          throw new ContainerNotFoundException("Not found");
        });

    assertThrows(
        InvalidBidException.class,
        () -> {
          throw new InvalidBidException("Invalid bid");
        });

    assertThrows(
        ValidationException.class,
        () -> {
          throw new ValidationException("Validation error");
        });
  }
}
