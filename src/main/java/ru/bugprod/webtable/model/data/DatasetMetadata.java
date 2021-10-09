package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TableMetadata", description = "Метадата датасета")
public class DatasetMetadata implements FieldContainer {
    @ApiModelProperty("Поля таблицы")
    private final List<Field> fields;
    @ApiModelProperty("Тип")
    private final String type;
    @ApiModelProperty("Название")
    private final String name;
    @ApiModelProperty("Нэймспэйс")
    private final String namespace;
    @ApiModelProperty("Название документа")
    private final String doc;
}
