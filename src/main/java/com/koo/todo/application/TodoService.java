package com.koo.todo.application;

import com.koo.todo.application.vo.TodoAddRequest;
import com.koo.todo.application.vo.TodoEditRequest;
import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoNotFoundException;
import com.koo.todo.domain.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	public Page<Todo> getTodoList(Pageable listRequest) {
		return todoRepository.findAll(listRequest);
	}


	@Transactional
	public void add(TodoAddRequest todoAddRequest) {
		String desc = todoAddRequest.getDesc();
		List<Long> links = todoAddRequest.getLinks();
		Todo newTodo = new Todo(desc,links);
		todoRepository.save(newTodo);
	}

	@Transactional
	public void edit(TodoEditRequest todoEditRequest) {
		long todoId = todoEditRequest.getTodoId();

		Todo found = getTodoById(todoId);
		found.setDescription(todoEditRequest.getDesc());
		found.setLink(todoEditRequest.getLinks());
		todoRepository.save(found);
	}

	@Transactional
	public void makeDone(long todoId){
		Todo found = getTodoById(todoId);
		found.done();
	}

	private Todo getTodoById(long todoId) {
		return todoRepository.findById(todoId)
			.orElseThrow(() -> new TodoNotFoundException("id : " + todoId + "not found"));
	}

}
