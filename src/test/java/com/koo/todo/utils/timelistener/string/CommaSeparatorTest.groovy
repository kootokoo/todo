package com.koo.todo.utils.timelistener.string

import spock.lang.Specification
import spock.lang.Unroll

class CommaSeparatorTest extends Specification {

    @Unroll
    def "#DESC 변환 테스트 input : #input"() {

        when:
        def result = CommaSeparator.comma2list(input)

        then:
        result == output

        where:
        DESC      | input   | output
        "정상 입력 값" | "1,2,3" | [1L, 2L, 3L]
        "정상 입력 값" | "1,2,"  | [1L, 2L]
    }

    @Unroll
    def "비정상 입력값 변환시, 에러 발생"() {

        when:
        def result = CommaSeparator.comma2list(input)

        then:
        thrown(expect)

        where:
        DESC       | input  | expect
        "비정상 입력 값" | "1 2 " | NumberFormatException
        "비정상 입력 값" | null   | NullPointerException
    }
}
