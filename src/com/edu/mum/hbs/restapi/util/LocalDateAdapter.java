package com.edu.mum.hbs.restapi.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    public LocalDate read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String xy = reader.nextString();
      return LocalDate.parse(xy);
    }
    public void write(JsonWriter writer, LocalDate value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      String xy = new SimpleDateFormat("yyyy-MM-dd").format(value);
      writer.value(xy);
    }
  }
