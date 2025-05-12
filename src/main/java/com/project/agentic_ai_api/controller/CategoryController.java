package com.project.agentic_ai_api.controller;

import org.springframework.web.bind.annotation.*;
import com.project.agentic_ai_api.service.CategoryService;
import java.util.Map;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category Management", description = "Optiene CRUD para el manejo de categorias")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(
    summary = "Creación de categoria",
    description = "Se crea categoria en el storage account table"
    )
    @ApiResponse(responseCode = "200", description = "Se crea correctamente la categoria")
     @PostMapping
    public String create(@RequestParam String partitionKey,
                         @RequestParam String rowKey,
                         @RequestBody Map<String, Object> body) {
        categoryService.create(partitionKey, rowKey, body);
        return "Created successfully";
    }

    @Operation(
    summary = "Obtiene de categoria",
    description = "Se obtien categoria en el storage account table por las llaves de particion y fila"
    )
    @ApiResponse(responseCode = "200", description = "Se obtiene correctamente la categoria")
    @GetMapping("/{partitionKey}/{rowKey}")
    public Map<String, Object> read(@PathVariable String partitionKey,
                                    @PathVariable String rowKey) {
        return categoryService.get(partitionKey, rowKey);
    }

    @Operation(
    summary = "Actualización de categoria",
    description = "Se actualiza categoria en el storage account table por las llaves de particion y fila"
    )
    @ApiResponse(responseCode = "200", description = "Se actualiza correctamente la categoria")
    @PutMapping("/{partitionKey}/{rowKey}")
    public String update(@PathVariable String partitionKey,
                         @PathVariable String rowKey,
                         @RequestBody Map<String, Object> body) {
        categoryService.update(partitionKey, rowKey, body);
        return "Updated successfully";
    }

    @Operation(
    summary = "Eliminación de categoria",
    description = "Se elimina categoria en el storage account table por las llaves de particion y fila"
    )
    @ApiResponse(responseCode = "200", description = "Se elimina correctamente la categoria")
    @DeleteMapping("/{partitionKey}/{rowKey}")
    public String delete(@PathVariable String partitionKey,
                         @PathVariable String rowKey) {
        categoryService.delete(partitionKey, rowKey);
        return "Deleted successfully";
    }

    @Operation(
    summary = "Obtener todas las categorias",
    description = "Se obtienen todas las categorias en el storage account table por las llaves de particion y fila"
    )
    @ApiResponse(responseCode = "200", description = "Se obtienen correctamente todas las categorias")
    @GetMapping
    public List<Map<String, Object>> list() {
        return categoryService.listAll();
    }
}