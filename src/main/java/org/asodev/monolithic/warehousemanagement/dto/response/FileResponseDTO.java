package org.asodev.monolithic.warehousemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asodev.monolithic.warehousemanagement.model.EntityType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {
    private Long id;
    private String filename;
    private String originalFilename;
    private String contentType;
    private Long size;
    private EntityType entityType;
    private Long entityId;
    private String fileUrl;
}