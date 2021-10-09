package ru.bugprod.webtable.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Запрос на фильтрацию датасета")
public class FilterRequest extends OperationRequest {

    @ApiModelProperty("Выражение фильтрации")
    private String expression;
}
