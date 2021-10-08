package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.data.TableMetadata;
import ru.bugprod.webtable.usecase.MetadataUseCase;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TableRestController {

    private final MetadataUseCase useCase;

    @ApiOperation(value = "Получение фрагмента датасета")
    @GetMapping("/get-all-metadata")
    public List<TableMetadata> getFragment() {
        return useCase.getAllMetadata();
    }
}
