package com.example.trainticketbooking;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static String[] splitDateTime(String dateTime) {
        String[] dateTimeArray = new String[2];
        try {
            // Định dạng ban đầu của chuỗi
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, inputFormatter);

            // Tách chuỗi thành định dạng "dd-MM-yyyy" cho ngày và "hh:mm a" cho giờ
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            // Gán giá trị cho mảng chuỗi
            dateTimeArray[0] = parsedDateTime.toLocalDate().format(dateFormatter); // Date
            dateTimeArray[1] = parsedDateTime.toLocalTime().format(timeFormatter); // Time

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return dateTimeArray;
    }
}
