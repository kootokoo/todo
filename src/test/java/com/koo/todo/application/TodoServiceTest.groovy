package com.koo.todo.application

import com.koo.link.application.LinkService
import com.koo.link.domain.Link
import com.koo.todo.application.exception.CannotChangeTobeDoneException
import com.koo.todo.application.exception.CannotMakeLinkBySelf
import com.koo.todo.application.exception.MakeLinkCirculationException
import com.koo.todo.domain.Todo
import com.koo.todo.domain.TodoNotFoundException
import com.koo.todo.domain.TodoRepository
import org.assertj.core.util.Lists
import spock.lang.Specification
import spock.lang.Unroll


class TodoServiceTest extends Specification {
    def todoRepository = Mock(TodoRepository)
    def linkService = Mock(LinkService)
    def todoService = new TodoService(todoRepository: todoRepository, linkService: linkService)


    def "링크 하려는 아이디들이 존재하는지 여부 확인"() {
        given:
        def 링크할Ids = [1L, 2L, 3L]
        def 디비에저장되어있는Ids = [new Todo(id: 1L), new Todo(id: 2L)]
        def 미존재_ids = [3L]
        todoRepository.findAllById(링크할Ids) >> 디비에저장되어있는Ids

        when:
        todoService.checkIsAllExist(링크할Ids)

        then:
        TodoNotFoundException ex = thrown()
        ex.message == "존재하지 않는 링크가 포함되어 있습니다 id : " + 미존재_ids.toString()

    }

    def "todo를 done으로 정상 처리 할 수 있다."() {
        given:
        def targetTodoId = 1L
        def target에링크된참조 = Lists.newArrayList()
        def savedTodo = new Todo(id: targetTodoId)
        todoRepository.findById(targetTodoId) >> Optional.of(savedTodo)
        todoRepository.save(_) >> savedTodo
        linkService.getLinkedIdListByTodoId(targetTodoId) >> target에링크된참조
        todoRepository.findIdsNotDoneYet(target에링크된참조) >> []

        when:
        def result = todoService.changeToDone(targetTodoId)

        then:
        result != null

    }

    def "순환 참조 확인 "() {
        given:
        def sourceTodoId = 1L;
        def updateLinkIds = [1L, 2L, 3L]
        def soureTodoId가_링크로_연결된_Todo_List = [new Link(todoId: 2L), new Link(todoId: 3L), new Link(todoId: 5L)]
        def 순환참조되는_id_list = [2L, 3L]

        linkService.getLinkListByTodoId(sourceTodoId) >> soureTodoId가_링크로_연결된_Todo_List

        when:
        todoService.checkCirculation(sourceTodoId, updateLinkIds)

        then:
        MakeLinkCirculationException ex = thrown()
        ex.message == 순환참조되는_id_list.toString() + "가 순환참조 됩니다."

    }

    def "done이 안된 참조가 있을 경우 done 할 수 없다"() {
        given:
        def targetTodoId = 1L
        def target에링크된Id = [2L, 3L, 4L]
        def done안된todoId = [3L]
        def savedTodo = new Todo(id: targetTodoId)
        todoRepository.findById(targetTodoId) >> Optional.of(savedTodo)
        todoRepository.save(_) >> savedTodo
        linkService.getLinkedIdListByTodoId(targetTodoId) >> target에링크된Id
        todoRepository.findIdsNotDoneYet(target에링크된Id) >> done안된todoId

        when:
        todoService.changeToDone(targetTodoId)

        then:
        CannotChangeTobeDoneException ex = thrown()
        ex.message == done안된todoId.toString() + "가 done 되지 않았습니다"
    }

    @Unroll
    def "스스로 참조를 하는지 확인 #DESC sourceId : #TODO_ID"() {
        given:
        def sourceTodoId = TODO_ID
        def linkList = LINK_LIST

        when:
        todoService.checkContainSelfId(sourceTodoId, linkList)

        then:
        noExceptionThrown()

        where:
        DESC | TODO_ID | LINK_LIST
        "정상" | null    | [1L, 2L, 3L]
        "정상" | 5L      | [13L, 23L, 33L]
    }

    @Unroll
    def "스스로 참조하려 할 때 오류 발생"() {
        given:
        def sourceTodoId = TODO_ID
        def linkList = LINK_LIST

        when:
        todoService.checkContainSelfId(sourceTodoId, linkList)

        then:
        CannotMakeLinkBySelf ex = thrown()
        ex.message == "스스로 참조가 될 수 없습니다"

        where:
        DESC  | TODO_ID | LINK_LIST
        "비정상" | 1L      | [1L, 2L, 3L]
    }


}
