package com.project.agentic_ai_api.service;

import org.springframework.stereotype.Service;
import com.project.agentic_ai_api.repository.GenericCategoryRepository;
import java.util.Map;
import java.util.List;

@Service
public class CategoryService {

    private GenericCategoryRepository categoryRepository;

    public CategoryService(GenericCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String create(String partitionKey,
                         String rowKey,
                         Map<String, Object> body) {
        categoryRepository.create(partitionKey, rowKey, body);
        return "Created successfully";
    }

    public Map<String, Object> get(String partitionKey,
                                    String rowKey) {
        return categoryRepository.get(partitionKey, rowKey);
    }

    public String update(String partitionKey,
                         String rowKey,
                         Map<String, Object> body) {
        categoryRepository.update(partitionKey, rowKey, body);
        return "Updated successfully";
    }

    public String delete(String partitionKey,
                         String rowKey) {
        categoryRepository.delete(partitionKey, rowKey);
        return "Deleted successfully";
    }

    public List<Map<String, Object>> listAll() {
        return categoryRepository.listAll();
    }
}