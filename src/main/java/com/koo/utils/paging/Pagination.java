package com.koo.utils.paging;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pagination {
	private Page previous;
	private List<Page> pages = Lists.newArrayList();
	private Page next;

	@Getter
	@ToString
	private static class Page {
		private int number;
		private int visualNumber;
		private boolean active;

		private Page(int number, boolean active) {
			this.number = number;
			this.visualNumber = number + 1;
			this.active = active;
		}
	}

	public static Pagination from(int totalPages, int currentPage) {
		Pagination pagination = new Pagination();
		int lastPage = totalPages - 1;
		int startPage = currentPage / 10 * 10;
		int endPage = Math.min(startPage + 10, lastPage);
		for (int page = startPage; page < endPage + 1; page++) {
			pagination.pages.add(new Page(page, page == currentPage));
		}
		pagination.previous = new Page(startPage - 1, startPage != 0);
		pagination.next = new Page(endPage + 1, endPage < lastPage);
		return pagination;
	}
}
