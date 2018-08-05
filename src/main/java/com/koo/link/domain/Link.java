package com.koo.link.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {
	@Id
	@GeneratedValue
	private int id;

	@Column(name="todo_id")
	private Long todoId;

	@Column
	private Long linkedId;

	public Link(Long linkedId) {
		this.linkedId = linkedId;
	}

}
