package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.controller.request.AddFieldRequest;
import ru.bugprod.webtable.controller.request.FilterRequest;
import ru.bugprod.webtable.controller.request.JoinRequest;
import ru.bugprod.webtable.model.data.DatasetMetadata;
import ru.bugprod.webtable.usecase.MetadataUseCase;
import ru.bugprod.webtable.usecase.OperationUseCase;

import java.util.List;

@RequiredArgsConstructor
@RestController("/api")
@Slf4j
public class TableRestController {

    private final MetadataUseCase useCase;
    private final OperationUseCase operationUseCase;

    @ApiOperation(value = "Получение фрагмента датасета")
    @GetMapping("/get-all-metadata")
    public List<DatasetMetadata> getAllMetadata(@RequestHeader String sessionKey) {
        return useCase.getAllMetadata(sessionKey);
    }

    @ApiOperation(value = "Джоиним датасеты")
    @PostMapping("/join")
    public DatasetMetadata joinDatasets(@RequestHeader String sessionKey, @RequestBody JoinRequest request) {
        return operationUseCase.join(sessionKey, request);
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
}
