package org.asodev.monolithic.warehousemanagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asodev.monolithic.warehousemanagement.configuration.AwsS3Config;
import org.asodev.monolithic.warehousemanagement.dto.response.FileResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.FileOperationException;
import org.asodev.monolithic.warehousemanagement.model.EntityType;
import org.asodev.monolithic.warehousemanagement.model.File;
import org.asodev.monolithic.warehousemanagement.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final AmazonS3 amazonS3;
    private final AwsS3Config awsS3Config;

    private final ProductService productService;

    public FileResponseDTO uploadFile(MultipartFile file, EntityType entityType, Long entityId) {
        try {
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String filename = UUID.randomUUID() + fileExtension;

            // Create folder structure in S3
            String s3Key = String.format("%s/%s/%s", entityType, entityId, filename);

            // Set up metadata for S3 object
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());


            // Upload to S3
            amazonS3.putObject(
                    awsS3Config.getBucketName(),
                    s3Key,
                    file.getInputStream(),
                    metadata
            );

            // Generate URL for the file
            String fileUrl = amazonS3.getUrl(awsS3Config.getBucketName(), s3Key).toString();

            // Save file metadata to database
            File fileEntity = File.builder()
                    .filename(filename)
                    .originalFilename(originalFilename)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .entityType(entityType)
                    .entityId(entityId)
                    .filePath(s3Key)
                    .fileUrl(fileUrl)
                    .build();

            fileEntity = fileRepository.save(fileEntity);

            return mapToFileResponseDTO(fileEntity);
        } catch (IOException e) {
            log.error("Failed to upload file to S3", e);
            throw new FileOperationException("Failed to upload file: " + e.getMessage());
        }
    }

    public byte[] getFile(Long fileId) {
        File fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileOperationException("File not found with id: " + fileId));

        try {
            S3Object s3Object = amazonS3.getObject(awsS3Config.getBucketName(), fileEntity.getFilePath());
            InputStream inputStream = s3Object.getObjectContent();
            return inputStream.readAllBytes();
        } catch (IOException e) {
            log.error("Failed to read file from S3", e);
            throw new FileOperationException("Failed to read file: " + e.getMessage());
        }
    }

    public List<FileResponseDTO> getFilesByEntity(EntityType entityType, Long entityId) {
        List<File> files = fileRepository.findByEntityTypeAndEntityId(entityType, entityId);
        return files.stream()
                .map(this::mapToFileResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteFile(Long fileId) {
        File fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileOperationException("File not found with id: " + fileId));

        amazonS3.deleteObject(awsS3Config.getBucketName(), fileEntity.getFilePath());
        fileRepository.delete(fileEntity);
    }

    private void validateEntityExists(EntityType entityType, Long entityId) {
        boolean entityExists = switch (entityType) {
            case PRODUCT-> productService.existsById(entityId);
        };

        if (!entityExists) {
            throw new EntityNotFoundException(entityType.getType() + " not found with id: " + entityId);
        }
    }

    private FileResponseDTO mapToFileResponseDTO(File file) {
        return FileResponseDTO.builder()
                .id(file.getId())
                .filename(file.getFilename())
                .originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .entityType(file.getEntityType())
                .entityId(file.getEntityId())
                .fileUrl(file.getFileUrl())
                .build();
    }
}