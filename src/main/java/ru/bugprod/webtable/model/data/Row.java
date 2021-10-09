package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("Строка")
public class Row {
    @ApiModelProperty("Значения")
    private final List<Value> values;

}
