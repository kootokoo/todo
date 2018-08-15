package com.koo.timelistener.string

import com.koo.utils.string.CommaSeparator
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
        "정상 입력 값" | "1"  | [1L]
        "정상 입력 값" | ""  | []
        "정상 입력 값" | "1,"  | [1L]
        "정상 입력 값" | null  | []
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
        "비정상 입력 값" | "null" | NumberFormatException
        "비정상 입력 값" | "q1q2" | NumberFormatException
    }

}
