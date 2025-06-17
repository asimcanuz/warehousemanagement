package org.asodev.monolithic.warehousemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String entityType;
    private Long entityId;
    private String fileUrl;
}