package com.universosystems.authorization.features.apps.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {
    private Integer id;
    private Integer idStatus;
    private String statusName;
    private String code;
    private String name;
    private String description;
    private OffsetDateTime createdAt;
    private Integer createdBy;
    private OffsetDateTime updatedAt;
    private Integer updatedBy;
    private OffsetDateTime deletedAt;
    private Integer deletedBy;
}
