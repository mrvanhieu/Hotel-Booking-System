package com.edu.mum.hbs.restapi.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateWithStringsSerializable 
extends StdSerializer<LocalDate> {

  private static DateTimeFormatter formatter = 
    DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public LocalDateWithStringsSerializable() {
      this(null);
  }

  public LocalDateWithStringsSerializable(Class<LocalDate> t) {
      super(t);
  }
   
  @Override
  public void serialize(
    LocalDate value,
    JsonGenerator gen,
    SerializerProvider arg2)
    throws IOException, JsonProcessingException {

      gen.writeString(formatter.format(value));
  }
}
