package com.koo.todo.utils.timelistener.string;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparator {
    private static final String SPLITTER = ",";

    public static List<Long> comma2list(String stringId) {
        if(StringUtils.isBlank(stringId)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(stringId.trim().split(SPLITTER)).stream().map(Long::valueOf).collect(Collectors.toList());
    }
}
