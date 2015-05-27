package com.excilys.malbert.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.excilys.malbert.binding.ComputerMapper;
import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Computer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClientService implements IClientService {
	private final static String SERVER_ROOT_URI = "http://localhost:8080/web-service";
	private final static String SERVER_ROOT_COMPUTER = SERVER_ROOT_URI
			+ "/computer";

	@Override
	public List<Computer> getAllComputer(Pattern pattern) {
		// Getting the JSON object in a String object
		Client client = ClientBuilder.newClient();
		String response = client.target(SERVER_ROOT_COMPUTER).path("getAll")
				.queryParam("lang", "fr")
				.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		List<Computer> listRet = new ArrayList<Computer>();
		try {
			// Transforming the String to an array of ComputerDTO
			ComputerDTO[] list = mapper
					.readValue(response, ComputerDTO[].class);
			// Populating the List object
			for (ComputerDTO comp : list) {
				listRet.add(ComputerMapper.computerdtoToComputer(comp, pattern));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listRet;
	}

	@Override
	public Computer findComputer(Long id) {
		return null;
	}

}
