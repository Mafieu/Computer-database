package com.excilys.malbert.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model object to represent the rows of company's table
 * 
 * @author excilys
 */
@javax.persistence.Entity
@Table(name = "company")
public class Company implements Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	@Column(name = "name")
	private String name;

	/**
	 * Default constructor
	 */
	public Company() {
		this.name = "";
	}

	/**
	 * Constructor with id
	 * 
	 * @param id
	 * @param name
	 */
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringBuilder("Id : ").append(id).append("\tName : ")
				.append(name).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Company) {
			Company company = ((Company) obj);
			return this.id == company.id
					&& (name == null ? company.name == null : name
							.equals(company.name));
		} else {
			return false;
		}
	}
}
