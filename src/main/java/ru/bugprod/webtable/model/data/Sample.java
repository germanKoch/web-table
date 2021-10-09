package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("Сэмпл")
public class Sample {
    @ApiModelProperty("Название таблицы")
    private final String name;
    @ApiModelProperty("Строки")
    private final List<Row> rows;
}
