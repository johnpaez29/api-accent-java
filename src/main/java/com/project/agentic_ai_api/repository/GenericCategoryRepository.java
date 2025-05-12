package com.project.agentic_ai_api.repository;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.data.tables.models.TableEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GenericCategoryRepository {

    private final TableClient tableClient;

    public GenericCategoryRepository(@Value("${azure.storage.connection-string}") String connectionString,
                              @Value("${azure.table.name}") String tableName) {

        TableServiceClient serviceClient = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();

        serviceClient.createTableIfNotExists(tableName);

        this.tableClient = serviceClient.getTableClient(tableName);
    }

    public void create(String partitionKey, String rowKey, Map<String, Object> data) {
        TableEntity entity = new TableEntity(partitionKey, rowKey);
        entity.getProperties().putAll(data);
        tableClient.createEntity(entity);
    }

    public Map<String, Object> get(String partitionKey, String rowKey) {
        TableEntity entity = tableClient.getEntity(partitionKey, rowKey);
        Map<String, Object> result = new HashMap<>(entity.getProperties());
        result.put("partitionKey", partitionKey);
        result.put("rowKey", rowKey);
        return result;
    }

    public void update(String partitionKey, String rowKey, Map<String, Object> data) {
        TableEntity entity = new TableEntity(partitionKey, rowKey);
        entity.getProperties().putAll(data);
        tableClient.updateEntity(entity);
    }

    public void delete(String partitionKey, String rowKey) {
        tableClient.deleteEntity(partitionKey, rowKey);
    }

    public List<Map<String, Object>> listAll() {
        List<Map<String, Object>> results = new ArrayList<>();
        for (TableEntity entity : tableClient.listEntities()) {
            Map<String, Object> item = new HashMap<>(entity.getProperties());
            item.put("partitionKey", entity.getPartitionKey());
            item.put("rowKey", entity.getRowKey());
            results.add(item);
        }
        return results;
    }
}
