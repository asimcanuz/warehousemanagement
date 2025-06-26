package org.asodev.monolithic.warehousemanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.asodev.monolithic.warehousemanagement.dto.response.FileResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.model.EntityType;
import org.asodev.monolithic.warehousemanagement.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File Controller", description = "Endpoints for file operations")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<FileResponseDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entityType") EntityType entityType,
            @RequestParam("entityId") Long entityId) {
        return GenericResponse.success(fileService.uploadFile(file, entityType, entityId));
    }

    @GetMapping("/{fileId}")
    @Operation(summary = "Get a file by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        byte[] fileContent = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileContent);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    @Operation(summary = "Get files by entity type and ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<List<FileResponseDTO>> getFilesByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return GenericResponse.success(fileService.getFilesByEntity(entityType, entityId));
    }

    @DeleteMapping("/{fileId}")
    @Operation(summary = "Delete a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<Void> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return GenericResponse.success(null);
    }

}