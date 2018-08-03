package com.koo.todo.application;

import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoNotFoundException;
import com.koo.todo.domain.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	public Page<Todo> getTodoList(Pageable listRequest) {
		return todoRepository.findAll(listRequest);
	}


	@Transactional
	public void add(RequestAddTodo requestAddTodo) {
		Todo newTodo = Todo.builder()
				.description(requestAddTodo.getDescription())
				.build();
		todoRepository.save(newTodo);
	}

	@Transactional
	public void edit(RequestEditTodo requestEditTodo) {
		long todoId = requestEditTodo.getId();
		Todo found = getTodoById(todoId);
		found.updateDescription(requestEditTodo.getDesc());
		todoRepository.save(found);
	}

	@Transactional
	public void makeDone(long todoId){
		Todo found = getTodoById(todoId);
		found.done();
		todoRepository.save(found);
	}

	private Todo getTodoById(long todoId) {
		return todoRepository.findById(todoId)
			.orElseThrow(() -> new TodoNotFoundException("id : " + todoId + "not found"));
	}

}
