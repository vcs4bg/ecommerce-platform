package com.tksystem.ecommerceplatform.domain.service.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.entity.common.FileManageEntity;
import com.tksystem.ecommerceplatform.domain.repository.common.FileManageRepository;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path baseDir;

    private final FileManageRepository repository;

    public FileStorageServiceImpl(
            @Value("${app.file.upload.dir}") Path baseDir,
            FileManageRepository repository) {
        this.baseDir = baseDir;
        this.repository = repository;
    }

    @Override
    public Long saveFile(MultipartFile file, String subDir) {
        Path saveDir = baseDir.resolve(subDir);
        return saveFileInternal(file, saveDir);
    }

    private Long saveFileInternal(MultipartFile file, Path saveDir) {
        // ファイル保存する→ファイル管理テーブルに登録する。
        try {
            // 引数チェック
            if (file.isEmpty()) {
                throw new IllegalArgumentException("ファイルが空です。");
            }

            //ディレクトリ作成
            if (!Files.exists(saveDir)) {
                Files.createDirectories(saveDir);
            }

            // ファイルの保存
            String extention = StringUtils.getFilenameExtension(file.getOriginalFilename());
            Path filePath = saveDir.resolve(UUID.randomUUID().toString())
                    .resolve(extention);
            file.transferTo(filePath);

            // ファイル管理テーブル登録
            FileManageEntity entity = new FileManageEntity(
                    null,
                    file.getOriginalFilename(),
                    filePath.toString(),
                    null,
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null);
            FileManageEntity result = repository.save(entity);

            return result.getFileId();

        } catch (IOException e) {

            throw new RuntimeException("ファイルの保存に失敗しました。", e);

        }
    }

}
