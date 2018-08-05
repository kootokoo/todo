package com.koo.todo.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TodoRepositoryTest extends Specification {
    @Autowired
    TodoRepository todoRepository

    def cleanup(){
        todoRepository.deleteAll()
    }

    def "entity save test"() {
        given:
        def description = "description"
        def todo = todoRepository.save(new Todo(description: description))

        when:
        def result = todoRepository.findById(todo.getId())

        then:
        result.get().desc == description

    }


}
