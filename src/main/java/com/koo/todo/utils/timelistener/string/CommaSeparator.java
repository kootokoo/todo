package com.koo.todo.utils.timelistener.string;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparator {
    private static final String SPLITTER = ",";

    public static List<Long> comma2list(String stringId) {
        return Lists.newArrayList(stringId.trim().split(SPLITTER)).stream().map(Long::valueOf).collect(Collectors.toList());
    }
}
