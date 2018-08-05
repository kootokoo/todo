package com.koo.todo.application

import com.koo.link.application.LinkService
import com.koo.link.domain.Link
import com.koo.todo.application.exception.CannotChangeTobeDoneException
import com.koo.todo.application.exception.CannotMakeLinkBySelf
import com.koo.todo.domain.Todo
import com.koo.todo.domain.TodoNotFoundException
import com.koo.todo.domain.TodoRepository
import org.assertj.core.util.Lists
import spock.lang.Specification
import spock.lang.Unroll


class TodoServiceTest extends Specification {
    def todoRepository = Mock(TodoRepository)
    def linkService = Mock(LinkService)
    def todoService = new TodoService(todoRepository: todoRepository,linkService:linkService)


    def "링크 하려는 아이디들이 존재하는지 여부 확인"() {
        given:
        def 링크할Ids = [1L, 2L, 3L]
        def 디비에저장되어있는Ids = [new Todo(id:1L), new Todo(id:2L)]
        def 미존재_ids = [3L]
        todoRepository.findAllById(링크할Ids) >> 디비에저장되어있는Ids

        when:
        todoService.checkIsAllExist(링크할Ids)

        then:
        TodoNotFoundException ex = thrown()
        ex.message == "존재하지 않는 링크가 포함되어 있습니다 id : " + 미존재_ids.toString()

    }

    def "todo를 done으로 정상 처리 할 수 있다."(){
        given:
        def targetTodoId = 1L
        def target에링크된참조 = Lists.newArrayList()
        def savedTodo = new Todo(id:targetTodoId)
        todoRepository.findById(targetTodoId) >> Optional.of(savedTodo)
        todoRepository.save(_) >> savedTodo
        linkService.getLinkedIdListByTodoId(targetTodoId) >> target에링크된참조

        when:
        def result = todoService.changeToDone(targetTodoId)

        then:
        result.doneAt != null

    }

    def "todo를 done 할 때, 해당 id를 가지는 링크가 있을 경우 done 실패"(){
        given:
        def targetTodoId = 1L
        def 링크Id = 2L
        def target에링크된참조 = [new Link(id: 링크Id)]
        def savedTodo = new Todo(id:targetTodoId)
        todoRepository.findById(targetTodoId) >> Optional.of(savedTodo)
        todoRepository.save(_) >> savedTodo
        linkService.getLinkedIdListByTodoId(targetTodoId) >> target에링크된참조

        when:
        def result = todoService.changeToDone(targetTodoId)

        then:
        CannotChangeTobeDoneException ex = thrown()
        ex.message == target에링크된참조.toString() +"가 존재하여 done 처리 할 수 없습니다."

    }


    @Unroll
    def "스스로 참조를 하는지 확인 #DESC sourceId : #TODO_ID"(){
        given:
        def sourceTodoId = TODO_ID
        def linkList = LINK_LIST

        when:
        todoService.checkContainSelfId(sourceTodoId, linkList)

        then:
        noExceptionThrown()

        where:
        DESC | TODO_ID | LINK_LIST
        "정상" | null | [1L,2L,3L]
        "정상" | 5L | [13L,23L,33L]
    }

    @Unroll
    def "스스로 참조하려 할 때 오류 발생"(){
        given:
        def sourceTodoId = TODO_ID
        def linkList = LINK_LIST

        when:
        todoService.checkContainSelfId(sourceTodoId, linkList)

        then:
        CannotMakeLinkBySelf ex = thrown()
        ex.message == "스스로 참조가 될 수 없습니다"

        where:
        DESC | TODO_ID | LINK_LIST
        "비정상" | 1L | [1L,2L,3L]
    }


}
