package ch.bbw.sk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;

public class Bid {

  private Long id;

  private String name;

  private Double amount;

  private Instant timestamp;

  @JsonIgnore private Container container;

  public Bid() {}

  public Bid(Long id, String name, Double amount, Instant timestamp) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public Container getContainer() {
    return container;
  }

  public void setContainer(Container container) {
    this.container = container;
  }
}
