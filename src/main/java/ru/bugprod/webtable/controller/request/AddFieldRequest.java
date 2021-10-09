package ru.bugprod.webtable.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Запрос на добавление поля")
public class AddFieldRequest {

    @ApiModelProperty("Название датасета")
    private String dataset;

    @ApiModelProperty("Тип нового поля")
    private String newFieldType;

    @ApiModelProperty("Название нового поля")
    private String newFieldName;

    @ApiModelProperty("Выражение для рассчёта нового поля")
    private String expression;

}
