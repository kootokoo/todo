package com.koo.todo.application.vo

import com.koo.link.domain.Link
import com.koo.todo.domain.Todo
import com.koo.utils.string.TimeToStringHelper
import org.assertj.core.util.Lists
import spock.lang.Specification

import java.time.LocalDateTime

class ResponseTodoTest extends Specification {

    def "응답용 link 변환 테스트"() {
        given:
        def id = 1L
        def desc = "desc"
        def linkList = [new Link(linkedId: 1L), new Link(linkedId: 2L)]
        def todoEntity = new Todo(id: id, desc: desc, linkList: linkList)

        when:
        def result = new ResponseTodo(todoEntity)

        then:
        result.linkList == [1L, 2L]
    }

}
