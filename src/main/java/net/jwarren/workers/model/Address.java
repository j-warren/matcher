package net.jwarren.workers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.Map;

import static net.jwarren.workers.misc.StringUtil.doubleOrNull;

@Data
public class Address {
    Unit unit;
    Integer maxJobDistance;
    Double longitude;
    Double latitude;

    public Address(Unit unit, Integer maxJobDistance, Double longitude, Double latitude) {
        this.unit = unit;
        this.maxJobDistance = maxJobDistance;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @JsonCreator
    public Address(Map<String, Object> properties) {
        String unitValue = (String) properties.getOrDefault("unit", null);
        this.unit = Unit.toUnitCaseInsensitiveOrNull(unitValue);

        this.maxJobDistance = (Integer) properties.getOrDefault("maxJobDistance", null);
        this.longitude = doubleOrNull((String) properties.getOrDefault("longitude", null));
        this.latitude = doubleOrNull((String) properties.getOrDefault("latitude", null));
    }
}
