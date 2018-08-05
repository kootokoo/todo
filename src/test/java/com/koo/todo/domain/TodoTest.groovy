package com.koo.todo.domain

import com.koo.link.domain.Link
import spock.lang.Specification

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
}
