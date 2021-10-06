package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.repository.DataFrameRepository;

@RequiredArgsConstructor
@RestController
public class TableRestController {

    private final DataFrameRepository repo;

    @ApiOperation(value = "Чтоб протестить. Возвращает то, что ему пришлёшь.")
    @GetMapping("/test")
    public String test(@RequestParam String text) {
        return text;
    }

    @ApiOperation(value = "Получение фрагмента датасета")
    @GetMapping("/get-fragment")
    public TableFragment getRows(@RequestHeader String sessionKey,
                                 @ApiParam("Смещение от начала") @RequestParam int offset,
                                 @ApiParam("Количество элементов") @RequestParam int limit) {
        return repo.getBetween(sessionKey, offset, limit);
    }

}
