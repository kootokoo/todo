package com.koo.todo.application

import com.koo.todo.domain.Todo
import com.koo.todo.domain.TodoNotFoundException
import com.koo.todo.domain.TodoRepository
import spock.lang.Specification


class TodoServiceTest extends Specification {
    def todoRepository = Mock(TodoRepository)
    def service = new TodoService(todoRepository: todoRepository)


    def "링크 하려는 아이디들이 존재하는지 여부 확인"() {
        given:
        def 링크할Ids = [1L, 2L, 3L]
        def 디비에저장되어있는Ids = [new Todo(id:1L), new Todo(id:2L)]
        def 미존재_ids = [3L]
        todoRepository.findAllById(링크할Ids) >> 디비에저장되어있는Ids

        when:
        service.checkIsAllExist(링크할Ids)

        then:
        TodoNotFoundException ex = thrown()
        ex.message == "존재하지 않는 링크가 포함되어 있습니다 id : " + 미존재_ids.toString()

    }


}
