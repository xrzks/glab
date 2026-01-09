package ch.bbw.sk.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CreateContainerRequest {
  @NotBlank(message = "Container name is required")
  @Size(max = 100, message = "Container name cannot exceed 100 characters")
  private String name;

  @Size(max = 500, message = "Container description cannot exceed 500 characters")
  private String description;

  private String mainImage;

  private List<String> thumbnails;

  public CreateContainerRequest() {}

  public CreateContainerRequest(
      String name, String description, String mainImage, List<String> thumbnails) {
    this.name = name;
    this.description = description;
    this.mainImage = mainImage;
    this.thumbnails = thumbnails;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



  public String getMainImage() {
    return mainImage;
  }

  public void setMainImage(String mainImage) {
    this.mainImage = mainImage;
  }

  public List<String> getThumbnails() {
    return thumbnails;
  }

  public void setThumbnails(List<String> thumbnails) {
    this.thumbnails = thumbnails;
  }
}
