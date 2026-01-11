package ch.bbw.sk.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Container {

  private UUID id;

  private String name;

  private String description;

  private String mainImage;

  private List<String> thumbnails = new ArrayList<>();

  private boolean liked;

  private Instant createdAt;

  private Instant updatedAt;

  private List<Bid> bids = new ArrayList<>();

  public Container() {}

  public Container(
      UUID id, String name, String description, String mainImage, List<String> thumbnails) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.mainImage = mainImage;
    this.thumbnails = thumbnails;
    this.liked = false;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<Bid> getBids() {
    return bids;
  }

  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  public void addBid(Bid bid) {
    bids.add(bid);
    bid.setContainer(this);
    this.updatedAt = Instant.now();
  }

  public void toggleLike() {
    this.liked = !this.liked;
    this.updatedAt = Instant.now();
  }
}
