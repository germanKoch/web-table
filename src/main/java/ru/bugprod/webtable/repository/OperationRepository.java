package ru.bugprod.webtable.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.controller.request.OperationRequest;
import ru.bugprod.webtable.repository.entity.Operation;
import ru.bugprod.webtable.repository.entity.OperationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OperationRepository {

    private final Map<String, List<Operation>> data;
    private final ObjectMapper mapper;

    public OperationRepository(ObjectMapper mapper) {
        this.mapper = mapper;
        this.data = new HashMap<>();
    }

    public void saveOperation(String sessionKey, OperationRequest request, OperationType type) {
        var operMap = mapper.convertValue(request, new TypeReference<HashMap<String, Object>>() {});
        data.computeIfAbsent(sessionKey, key -> new ArrayList<>()).add(new Operation(type, operMap));
    }
}
