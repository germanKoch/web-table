package ru.bugprod.webtable.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Запрос на добавление поля")
public class AddFieldRequest extends OperationRequest {

    @ApiModelProperty("Тип нового поля")
    private String newFieldType;

    @ApiModelProperty("Название нового поля. " +
            "Если хотим его вложить во что-то, пишем field1.field2. Тогда field1 - будет структура." +
            "Если структура field1 уже есть - новое поле добавится в неё.")
    private String newFieldName;

    @ApiModelProperty("Выражение для рассчёта нового поля")
    private String expression;

}
