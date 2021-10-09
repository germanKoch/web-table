package ru.bugprod.webtable.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Запрос на джоин")
public class JoinRequest extends OperationRequest {

    @ApiModelProperty("Поле исходного детасета, по которому джойним. Для вложенных полей путь указываем через точку: field1.field2")
    private String field;

    @ApiModelProperty("Датасет, с которого джойним")
    private String joinDataset;

    @ApiModelProperty("Поле другого датасета, с которого джоиним.  Для вложенных полей путь указываем через точку: field1.field2")
    private String joinField;

}
