package com.koo.link.domain;

import com.koo.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Link {
	@Id
	@Column(name="seq")
	@GeneratedValue
	private int seq;

	@Column(name="todo_id")
	private Long todoId;

	@Column
	private Long linkedId;

	public Link(Long linkedId) {
		this.linkedId = linkedId;
	}

}
