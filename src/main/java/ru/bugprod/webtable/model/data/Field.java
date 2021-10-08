package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Fields", description = "Поле таблицы")
public class Field {
    @ApiModelProperty("Название поля")
    private String name;
    @ApiModelProperty("Тип поля")
    private String type;
}
