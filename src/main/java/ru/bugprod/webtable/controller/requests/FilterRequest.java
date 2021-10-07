package ru.bugprod.webtable.controller.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Запрос на фильтрацию")
public class FilterRequest {

    @ApiModelProperty("Выражение фильтрации")
    private String expression;

}
