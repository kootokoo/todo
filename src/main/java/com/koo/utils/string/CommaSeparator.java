package com.koo.utils.string;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparator {
    private static final String SPLITTER = ",";

    public static List<Long> comma2list(String linkListStr) {
        if(StringUtils.isBlank(linkListStr)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(linkListStr.trim().split(SPLITTER)).stream().map(Long::valueOf).collect(Collectors.toList());
    }

}
