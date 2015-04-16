package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.IDAOComputer;
import com.excilys.malbert.persistence.model.Computer;

/**
 * Describes the services
 * 
 * @author excilys
 *
 */
public abstract class ServiceComputer {

    private static IDAOComputer daoComputer = DAOComputer.INSTANCE;

    public static List<Computer> getAllComputers() {
	return daoComputer.getAll();
    }

    public static List<Computer> getSome(int limit, int offset) {
	return daoComputer.getSome(limit, offset);
    }

    public static Computer getComputer(long id) {
	return daoComputer.getComputer(id);
    }

    public static void createComputer(Computer computer) {
	daoComputer.create(computer);
    }

    public static void deleteComputer(Computer computer) {
	daoComputer.delete(computer);
    }

    public static void updateComputer(Computer computer) {
	daoComputer.update(computer);
    }

    public static int getNumberComputer() {
	return daoComputer.getNumberComputer();
    }
}
