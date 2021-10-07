package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.controller.requests.ComputeRequest;
import ru.bugprod.webtable.controller.requests.FilterRequest;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.usecase.OperationUseCase;

//TODO: удаление колонки
@RequiredArgsConstructor
@RestController
@Slf4j
public class TableRestController {

    private final OperationUseCase useCase;

    @ApiOperation(value = "Чтоб протестить. Возвращает то, что ему пришлёшь.")
    @GetMapping("/test")
    public String test(@RequestParam String text) {
        return text;
    }

    @ApiOperation(value = "Получение фрагмента датасета")
    @GetMapping("/get-fragment")
    public TableFragment getFragment(@RequestHeader String sessionKey,
                                     @ApiParam("Смещение от начала") @RequestParam int offset,
                                     @ApiParam("Количество элементов") @RequestParam int limit) {
        log.debug("getFragment(sessionKey={}, offset={}, limit={})", sessionKey, offset, limit);
        return useCase.getBetween(sessionKey, offset, limit);
    }


    @ApiOperation(value = "Расчёт новой фичи")
    @PostMapping("/compute-column")
    public TableFragment computeColumn(@RequestHeader String sessionKey,
                                       @ApiParam("Запрос") @RequestBody ComputeRequest request) {
        log.debug("computeColumn(sessionKey={}, request={})", sessionKey, request);
        return useCase.computeAndSave(sessionKey, request.getColumnName(), request.getExpression());
    }

    @ApiOperation(value = "Отфильтровать строки")
    @PostMapping("/filter-rows")
    public TableFragment filterRows(@RequestHeader String sessionKey,
                                    @ApiParam("Выражение фильтрации") @RequestBody FilterRequest request) {
        log.debug("filterRows(sessionKey={}, expression={})", sessionKey, request.getExpression());
        return useCase.filterByCondition(sessionKey, request.getExpression());
    }
}
