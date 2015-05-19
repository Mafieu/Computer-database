package com.excilys.malbert.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Model object to represent the rows of computer's table
 * 
 * @author excilys
 *
 */
@javax.persistence.Entity
@Table(name = "computer")
public class Computer implements Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;
	@Column(name = "name")
	private String name;
	@Column(name = "introduced")
	@Type(type = "com.excilys.malbert.util.CustomLocalDateTimeUserType")
	private LocalDateTime introduced;
	@Column(name = "discontinued")
	@Type(type = "com.excilys.malbert.util.CustomLocalDateTimeUserType")
	private LocalDateTime discontinued;
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "company_id")
	private Company company;

	/**
	 * Default constructor
	 */
	public Computer() {
		this.name = "";
		this.discontinued = null;
		this.introduced = null;
		this.company = null;
	}

	/**
	 * Basic constructor with id
	 * 
	 * @param id
	 * @param name
	 * @param id_company
	 */
	public Computer(long id, String name, Company company) {
		this.id = id;
		this.name = name;
		this.company = company;
		this.introduced = LocalDateTime.now();
	}

	/**
	 * @param id
	 * @param name
	 * @param introduced
	 * @param id_company
	 */
	public Computer(long id, String name, LocalDateTime introduced,
			Company company) {
		this(id, name, company);
		this.introduced = introduced;
	}

	/**
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param id_company
	 */
	public Computer(long id, String name, LocalDateTime introduced,
			LocalDateTime discontinued, Company company) {
		this(id, name, introduced, company);
		this.discontinued = discontinued;
	}

	/**
	 * Basic constructor without id
	 * 
	 * @param name
	 * @param id_company
	 */
	public Computer(String name, Company company) {
		this.name = name;
		this.company = company;
	}

	/**
	 * @param name
	 * @param introduced
	 * @param id_company
	 */
	public Computer(String name, LocalDateTime introduced, Company company) {
		this(name, company);
		this.introduced = introduced;
	}

	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param id_company
	 */
	public Computer(String name, LocalDateTime introduced,
			LocalDateTime discontinued, Company company) {
		this(name, introduced, company);
		this.discontinued = discontinued;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	/**
	 * @return
	 */
	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	/**
	 * @return
	 */
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	/**
	 * @param discontinued
	 */
	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * @return
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Id : ").append(this.id)
				.append("\tName : ").append(this.name)
				.append("\tIntroduced : ").append(this.introduced)
				.append("\tDiscontinued : ").append(this.discontinued)
				.append("\tOwner : ").append(company).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Computer) {
			Computer computer = ((Computer) obj);
			boolean testName, testIntroduced, testDiscontinued, testCompany;
			// We test if both are null or if origin is not null and equals
			// to computer
			testName = (name == null ? computer.name == null : name
					.equals(computer.name));
			testIntroduced = (introduced == null ? computer.introduced == null
					: introduced.equals(computer.introduced));
			testDiscontinued = (discontinued == null ? computer.discontinued == null
					: discontinued.equals(computer.discontinued));
			testCompany = (company == null ? computer.company == null : company
					.equals(computer.company));
			return this.id == computer.id && testName && testIntroduced
					&& testDiscontinued && testCompany;
		} else {
			return false;
		}
	}
}
