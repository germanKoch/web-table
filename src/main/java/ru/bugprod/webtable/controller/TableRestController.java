package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.bugprod.webtable.controller.request.AddFieldRequest;
import ru.bugprod.webtable.controller.request.FilterRequest;
import ru.bugprod.webtable.controller.request.JoinRequest;
import ru.bugprod.webtable.model.metadata.DatasetMetadata;
import ru.bugprod.webtable.usecase.DataHubUseCase;
import ru.bugprod.webtable.usecase.MetadataUseCase;
import ru.bugprod.webtable.usecase.OperationUseCase;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TableRestController {

    private final MetadataUseCase useCase;
    private final OperationUseCase operationUseCase;
    private final DataHubUseCase dataHubUseCase;

    @ApiOperation(value = "Получение всех метаданных")
    @GetMapping("/get-all-metadata")
    public List<DatasetMetadata> getAllMetadata(@RequestHeader String sessionKey) {
        return useCase.getAllMetadata(sessionKey);
    }

    @ApiOperation(value = "Джоиним датасеты")
    @PostMapping("/join")
    public ResponseEntity<DatasetMetadata> joinDatasets(@RequestHeader String sessionKey, @RequestBody JoinRequest request) {
        try {
            var metadata = operationUseCase.join(sessionKey, request);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Добавляем новое поле")
    @PostMapping("/compute-field")
    public DatasetMetadata addField(@RequestHeader String sessionKey, @RequestBody AddFieldRequest request) {
        return operationUseCase.addField(sessionKey, request);
    }

    @ApiOperation(value = "Фильтрация датасета")
    @PostMapping("/filter")
    public ResponseEntity<String> addField(@RequestHeader String sessionKey, @RequestBody FilterRequest request) {
        operationUseCase.filter(sessionKey, request);
        return ResponseEntity.ok("filterred request saved");
    }

    @ApiOperation(value = "Получение всех метаданных из datahub")
    @GetMapping("/get-all-metadata-datahub")
    public String getAllMetadataDataHub() {
        return dataHubUseCase.getData();
    }
}
