package com.edu.mum.hbs.restapi.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateWithStringsDeserializer extends StdDeserializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private static DateTimeFormatter formatter = 
			    DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public LocalDateWithStringsDeserializer() {
		this(null);
	}

	public LocalDateWithStringsDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String date = jsonparser.getText();
		return LocalDate.parse(date);
	}

}