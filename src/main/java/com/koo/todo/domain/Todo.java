package com.koo.todo.domain;

import com.koo.todo.utils.timelistener.CreatedAndModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
public class Todo extends CreatedAndModifiedEntity {
	@Id
	@GeneratedValue
	@Column(name = "todoId")
	private Long id;

	@Column
	private String description;

//	@Column
//	private List<Long> link;

	@Column
	private LocalDateTime doneAt;

	public void done() {

	}

	public void updateDescription(String desc) {
		this.description = desc;
	}

	public boolean isDone() {
		return false;
	}
}
