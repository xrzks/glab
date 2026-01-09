package ch.bbw.sk.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddBidRequest {
  @NotBlank(message = "Bidder name is required")
  @Size(max = 50, message = "Bidder name cannot exceed 50 characters")
  private String name;

  @NotNull(message = "Bid amount is required")
  @DecimalMin(value = "0.01", message = "Bid amount must be greater than zero")
  @DecimalMax(value = "1000000.00", message = "Bid amount cannot exceed 1,000,000")
  private Double amount;

  public AddBidRequest() {}

  public AddBidRequest(String name, Double amount) {
    this.name = name;
    this.amount = amount;
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
}
