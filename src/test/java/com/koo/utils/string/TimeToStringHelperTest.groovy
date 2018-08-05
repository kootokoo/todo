package com.koo.utils.string

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeToStringHelperTest extends Specification {
    def "convert test"(){
        given:
        String strDateTime = "2016-03-04 11:30:40"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(strDateTime, formatter);


        when:
        def result = TimeToStringHelper.convert(localDateTime)

        then:
        result == strDateTime
    }
}
