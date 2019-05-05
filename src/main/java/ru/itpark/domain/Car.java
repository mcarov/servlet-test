package ru.itpark.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private long id;
    private String model;
    private int enginePower;
    private int year;
    private String color;
    private String description;
    private String imageUrl;

    public boolean hasRequestedValue(String request) {
        return StringUtils.containsIgnoreCase(model, request) ||
                StringUtils.containsIgnoreCase(String.valueOf(enginePower), request) ||
                StringUtils.containsIgnoreCase(String.valueOf(year), request) ||
                StringUtils.containsIgnoreCase(color, request) ||
                StringUtils.containsIgnoreCase(description, request);
    }
}
