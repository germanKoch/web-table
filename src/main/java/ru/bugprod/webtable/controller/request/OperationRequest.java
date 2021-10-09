package ru.bugprod.webtable.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Запрос на операцию над данными")
public class OperationRequest {

    @ApiModelProperty("датасет, над которым операция")
    private String dataset;

}
