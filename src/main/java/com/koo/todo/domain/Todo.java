package com.koo.todo.domain;

import com.google.common.collect.Lists;
import com.koo.link.domain.Link;
import com.koo.utils.timelistener.CreatedAndModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
public class Todo extends CreatedAndModifiedEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private String desc;

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="todo_id")
	private List<Link> linkList;
	@Column
	private LocalDateTime doneAt;

	public void markDoneAt() {
		this.doneAt = LocalDateTime.now();
	}

	public boolean isDone(){
		return this.doneAt != null;
	}

	public void updateDescription(String desc) {
		this.desc = desc;
	}

    public void updateLinks(List<Link> linkList) {
        this.linkList = linkList;
    }

}
