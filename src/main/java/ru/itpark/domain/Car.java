package ru.itpark.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String id;
    private String manufacturer;
    private String model;
    private String enginePower;
    private String year;
    private String color;
    private String image;
}
