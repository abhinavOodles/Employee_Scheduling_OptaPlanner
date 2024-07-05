package com.example.Employee_Scheduling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String dateTime = null;
            try {
                dateTime = jsonParser.getText();
            } catch (IOException e) {
                return null;
            }
            //convert String to LocalDate
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            return localDateTime;
        } catch (DateTimeParseException ex) {
            return LocalDateTime.of(LocalDate.of(1111,11,011), LocalTime.of(0,0));
        } catch (Exception e){
            return null;
        }
    }
}
