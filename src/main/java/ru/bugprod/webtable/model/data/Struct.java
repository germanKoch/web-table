package ru.bugprod.webtable.model.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Поле-структура")
public class Struct extends Field implements FieldContainer {
    @ApiModelProperty("Вложенные поля")
    private List<Field> fields;

    public Struct(String name, String type, List<Field> fields) {
        super(name, type);
        this.fields = fields;
    }
}
