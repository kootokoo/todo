package com.koo.link.domain;

import com.koo.todo.domain.Todo;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Link {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Long todoId;

	@Column
	private Long linkedId;

	public Link(Long todoId) {
		this.todoId = todoId;
	}
}
