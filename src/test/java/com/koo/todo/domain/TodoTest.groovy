package com.koo.todo.domain

import com.koo.link.domain.Link
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class TodoTest extends Specification {

    def "link list cvs 형식으로 반환 테스트"() {
        given:

        def LinkList = [new Link(linkedId: 1L), new Link(linkedId: 2L), new Link(linkedId: 3L)]
        def todo = new Todo(linkList: LinkList)

        when:
        def result = todo.getCommaLinks();

        then:
        result == "1,2,3"

    }

    @Unroll
    def "is done 여부 파악 테스트. condition : #DESC"(){
        given:
        def todo = new Todo(doneAt: DONE_AT)

        when:
        def result = todo.isDone()

        then:
        result == EXPECT

        where:
        DESC | DONE_AT |EXPECT
        "done 시간이 존재 " | LocalDateTime.now() | true
        "done 시간이 미존재 " | null | false


    }
}
