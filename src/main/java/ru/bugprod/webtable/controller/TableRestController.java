package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
