package org.asodev.monolithic.warehousemanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class File extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String originalFilename;
    private String contentType;
    private Long size;
    private String filePath;
    private String fileUrl;

    private EntityType entityType; // product, order, category, campaing etc.
    private Long entityId; // related entity ID
}