package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.core.model.Computer;

public interface IComputerDAO extends ICrudDAO<Computer> {

	public enum Order {
		ASC("asc"), DESC("desc");
		private String name;

		private Order(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public static Order map(String s) {
			if (ASC.name.equalsIgnoreCase(s)) {
				return ASC;
			} else if (DESC.name.equalsIgnoreCase(s)) {
				return DESC;
			}
			return DESC;
		}
	};

	public enum Column {
		ID("computer.id"), NAME("computer.name"), INTRODUCED("introduced"), DISCONTINUED(
				"discontinued"), COMPANY("company.name");
		private String name;

		private Column(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public static Column map(String s) {
			if (ID.name.equalsIgnoreCase(s)) {
				return ID;
			} else if (NAME.name.equalsIgnoreCase(s)) {
				return NAME;
			} else if (INTRODUCED.name.equalsIgnoreCase(s)) {
				return INTRODUCED;
			} else if (DISCONTINUED.name.equalsIgnoreCase(s)) {
				return DISCONTINUED;
			} else if (COMPANY.name.equalsIgnoreCase(s)) {
				return COMPANY;
			}
			return ID;
		}
	}

	public List<Computer> getAll();

	/**
	 * Get the list of computers from offset to offset + limit
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Computer> getSome(Integer limit, Integer offset);

	public int getNumberComputer();

	/**
	 * Get the list of computers from offset to offset + limit ordered by column
	 * in ascending order
	 * 
	 * @param limit
	 * @param offset
	 * @param column
	 * @return
	 */
	public List<Computer> getSomeOrderedByAscending(Integer limit, Integer offset,
			Column column);

	/**
	 * Get the list of computers from offset to offset + limit ordered by column
	 * in descending order
	 * 
	 * @param limit
	 * @param offset
	 * @param column
	 * @return
	 */
	public List<Computer> getSomeOrderedByDescending(Integer limit, Integer offset,
			Column column);

	/**
	 * Get the list of computers from offset to offset + limit with the search
	 * in their name or in their company name ordered by column in order
	 * determined by order
	 * 
	 * @param limit
	 * @param offset
	 * @param column
	 * @param order
	 * @param search
	 * @return
	 */
	public List<Computer> getSomeSearch(Integer limit, Integer offset, Column column,
			Order order, String search);

	/**
	 * Get the number of computers that have search in their name or company
	 * name
	 * 
	 * @param search
	 * @return
	 */
	public int getNumberComputerSearch(String search);

	public void deleteOfCompany(Long id);
}
