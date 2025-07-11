package org.asodev.monolithic.warehousemanagement.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {
  private Long id;
  private String name;
  private String email;
  private CustomerType type;
  private String phone;
  private String contactPerson;
  private Boolean isActive;
  private List<AddressResponseDTO> addresses;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
