package ru.bugprod.webtable.repository.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import tech.tablesaw.api.Table;

import java.util.Set;

public class VarNameFormer {

    public static String getVarName(Table dataFrame, Set<String> usedKeys) {
        var varName = RandomStringUtils.randomAlphabetic(1);
        while (usedKeys.contains(varName) || dataFrame.columnNames().contains(varName)) {
            varName = RandomStringUtils.randomAlphabetic(1);
        }
        return varName;
    }

}
