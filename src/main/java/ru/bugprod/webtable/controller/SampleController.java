package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.data.Sample;
import ru.bugprod.webtable.repository.SampleRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleRepository repository;

    @ApiOperation(value = "Получение сэмплов всех сэмлов")
    @GetMapping("/get-all-samples")
    public List<Sample> getAllSamples(@RequestHeader String sessionKey) {
        return repository.getAllSamples();
    }

    @ApiOperation(value = "Получение сэмплов по названию таблицы")
    @GetMapping("/get-sample")
    public Sample getSample(@RequestHeader String sessionKey, @RequestParam @ApiParam("Название таблицы") String tableName) {
        return repository.getSample(tableName);
    }

}
