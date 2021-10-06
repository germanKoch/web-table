package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TableColumn", description = "Колонка таблицы")
public class TableColumn {
    @ApiModelProperty("Название колонки")
    private final String key;
    @ApiModelProperty("Значения колонки")
    private final List<?> values;
}
