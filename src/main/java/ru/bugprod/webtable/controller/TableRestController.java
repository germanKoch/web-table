package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
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
    public List<DatasetMetadata> getFragment(@RequestHeader String sessionKey) {
        return useCase.getAllMetadata(sessionKey);
    }

    @ApiOperation(value = "Джоиним датасеты")
    @PostMapping("/join")
    public DatasetMetadata getFragment(@RequestHeader String sessionKey, @RequestBody JoinRequest request) {
        return operationUseCase.join(sessionKey, request);
    }
}
