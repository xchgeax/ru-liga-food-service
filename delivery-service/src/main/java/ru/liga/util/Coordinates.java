package ru.liga.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Coordinates {

    private double lat;
    private double lon;

    public Coordinates(String coordinates) {
        String[] coordinate = coordinates.split(",");
        lat = Double.parseDouble(coordinate[0]);
        lon = Double.parseDouble(coordinate[1]);
    }
}
