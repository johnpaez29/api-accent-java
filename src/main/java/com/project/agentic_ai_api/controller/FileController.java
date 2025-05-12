package com.project.agentic_ai_api.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.agentic_ai_api.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File Management", description = "Optiene operaciones para el manejo de archivos")
public class FileController {
    
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Actualización inserción archivo", description = "Inserta o actualiza archivo en el blob storage")
    @ApiResponse(responseCode = "200", description = "Archivo actualizado correctamente")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @Parameter(description = "File to upload", required = true)
        @RequestParam("file") MultipartFile file) {
        try {
            String url = fileService.upload(file);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload error: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Descarga archivo por el nombre",
        description = "Retorna archivo del blob storage"
    )
    @ApiResponse(responseCode = "200", description = "Descarga exitosa de archivo")
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        try {
            byte[] data = fileService.download(filename);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(data);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
    summary = "Elimina archivo por el nombre",
    description = "Elimina archivo del blob storage"
    )
    @ApiResponse(responseCode = "200", description = "Se borra correctamente el archivo")
    @DeleteMapping("/{filename}")
    public ResponseEntity<String> delete(@PathVariable String filename) {
        try {
            fileService.delete(filename);
            return ResponseEntity.ok("Archivo eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}