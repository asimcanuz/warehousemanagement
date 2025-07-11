package org.asodev.monolithic.warehousemanagement.dto.response;

import org.asodev.monolithic.warehousemanagement.model.AddressType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {
  private Long id;
  private AddressType type;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String country;
  private String additionalInfo;
  private Boolean isActive;
}
