package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Значение")
public class Value {
    @ApiModelProperty("Ключ")
    private final String key;
    @ApiModelProperty("Значение")
    private final Object value;

}
