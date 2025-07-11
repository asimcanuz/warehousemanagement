package org.asodev.monolithic.warehousemanagement.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String errorId;
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private Map<String, String> details;
}
