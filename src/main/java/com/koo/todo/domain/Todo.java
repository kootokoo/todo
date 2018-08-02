package com.koo.todo.domain;

import com.koo.todo.utils.CreatedAndModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends CreatedAndModifiedEntity {
	@Id
	@GeneratedValue
	@Column(name = "todoId")
	private Long id;

	@Column
	private String description;

	@Column
	private List<Long> link;

	@Column
	private LocalDateTime doneAt;

	public void done() {

	}

	public Todo(String description, List<Long> link) {
		this.description = description;
		this.link = link;
	}
}
