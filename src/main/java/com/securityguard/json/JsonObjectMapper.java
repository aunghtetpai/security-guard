package com.securityguard.json;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonObjectMapper {

	private final ObjectMapper mapper = new ObjectMapper();

	public String serialize(Object obj) {
		String str = null;
		try {
			str = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return str;
	}
}
