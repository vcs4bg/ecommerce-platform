package com.tksystem.ecommerceplatform.domain.model.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FILE_MANAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileManageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_manage_seq")
    @SequenceGenerator(name = "file_manage_seq", sequenceName = "tbl_file_manage_file_id_seq", allocationSize = 1)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "file_path", nullable = false, length = 500, unique = true)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_extension", length = 10)
    private String fileExtension;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
