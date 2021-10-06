package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TableFragment", description = "Фрагмент датасета")
public class TableFragment {
    @ApiModelProperty("Название датасета")
    private final String name;
    @ApiModelProperty("Колонки датасета")
    private final List<TableColumn> columns;
}
