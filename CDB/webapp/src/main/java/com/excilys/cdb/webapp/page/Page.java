package com.excilys.cdb.webapp.page;

import org.springframework.stereotype.Component;

import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;

@Component
public class Page {
	
	private int nbByPage;
	private int page;
	private SortingField sortingField = SortingField.ID;
	private SortingOrder sortingOrder = SortingOrder.ASC;
	
	public Page(int nbByPage, int page, SortingField sortingField, SortingOrder sortingOrder) {
		super();
		this.nbByPage = nbByPage;
		this.page = page;
		this.sortingField = sortingField;
		this.sortingOrder = sortingOrder;
	}
	
	public Page() {
	}

	public int getNbByPage() {
		return nbByPage;
	}

	public void setNbByPage(int nbByPage) {
		this.nbByPage = nbByPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public SortingField getSortingField() {
		return sortingField;
	}

	public void setSortingField(SortingField sortingField) {
		this.sortingField = sortingField;
	}

	public SortingOrder getSortingOrder() {
		return sortingOrder;
	}

	public void setSortingOrder(SortingOrder sortingOrder) {
		this.sortingOrder = sortingOrder;
	}
	
	
	

}
