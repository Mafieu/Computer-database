package com.excilys.malbert.client.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.excilys.malbert.binding.CompanyMapper;
import com.excilys.malbert.binding.ComputerMapper;
import com.excilys.malbert.binding.model.CompanyDTO;
import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.Computer;

@Service
public class ClientService implements IClientService {
	private final static String SERVER_ROOT_URI = "http://localhost:8080/web-service";
	private final static String SERVER_ROOT_COMPUTER = SERVER_ROOT_URI
			+ "/computer";
	private final static String SERVER_ROOT_COMPANY = SERVER_ROOT_URI
			+ "/company";

	@Override
	public List<Computer> getAllComputer() {
		// Getting the JSON object in a String object
		Client client = ClientBuilder.newClient();
		ComputerDTO[] response = client.target(SERVER_ROOT_COMPUTER)
				.path("getAll").request(MediaType.APPLICATION_JSON_TYPE)
				.get(ComputerDTO[].class);

		List<Computer> listRet = new ArrayList<Computer>();
		for (ComputerDTO comp : response) {
			listRet.add(ComputerMapper.computerdtoToComputer(comp, Pattern.EN));
		}

		return listRet;
	}

	@Override
	public Computer findComputer(Long id) {
		Client client = ClientBuilder.newClient();
		ComputerDTO response = client.target(SERVER_ROOT_COMPUTER).path("find")
				.queryParam("lang", Pattern.EN.toString()).queryParam("id", id)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get(ComputerDTO.class);

		return ComputerMapper.computerdtoToComputer(response, Pattern.EN);
	}

	@Override
	public void deleteComputer(Long id) {
		Client client = ClientBuilder.newClient();
		client.target(SERVER_ROOT_COMPUTER).path("delete").queryParam("id", id)
				.request().delete();
	}

	@Override
	public void createComputer(Computer computer) {
		Client client = ClientBuilder.newClient();
		ComputerDTO response = client
				.target(SERVER_ROOT_COMPUTER)
				.path("create")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(ComputerMapper.computerToComputerdto(
						computer, Pattern.EN), MediaType.APPLICATION_JSON),
						ComputerDTO.class);

		computer = ComputerMapper.computerdtoToComputer(response, Pattern.EN);
	}

	@Override
	public void updateComputer(Computer computer) {
		Client client = ClientBuilder.newClient();
		client.target(SERVER_ROOT_COMPUTER)
				.path("update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(ComputerMapper.computerToComputerdto(
						computer, Pattern.EN), MediaType.APPLICATION_JSON));
	}

	@Override
	public List<Company> getAllCompany() {
		Client client = ClientBuilder.newClient();
		CompanyDTO[] response = client.target(SERVER_ROOT_COMPANY)
				.path("getAll").request(MediaType.APPLICATION_JSON_TYPE)
				.get(CompanyDTO[].class);

		List<Company> listRet = new ArrayList<Company>();
		for (CompanyDTO comp : response) {
			listRet.add(CompanyMapper.companydtoToCompany(comp));
		}

		return listRet;
	}

	@Override
	public void deleteCompany(Long id) {
		Client client = ClientBuilder.newClient();
		client.target(SERVER_ROOT_COMPANY).path("delete").queryParam("id", id)
				.request().delete();
	}

	@Override
	public Company getCompany(Long id) {
		Client client = ClientBuilder.newClient();
		CompanyDTO response = client.target(SERVER_ROOT_COMPANY).path("find")
				.request(MediaType.APPLICATION_JSON_TYPE).get(CompanyDTO.class);
		return CompanyMapper.companydtoToCompany(response);
	}
}
