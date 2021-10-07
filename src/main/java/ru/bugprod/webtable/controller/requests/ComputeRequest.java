package ru.bugprod.webtable.controller.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Запрос на подсчёт колонок")
public class ComputeRequest {
    private String columnName;
    private String expression;
}
