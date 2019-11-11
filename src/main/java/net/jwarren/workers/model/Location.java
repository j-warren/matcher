package net.jwarren.workers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static net.jwarren.workers.misc.StringUtil.doubleOrNull;

@Data
public class Location {
    private Double longitude;
    private Double latitude;

    private static final Logger LOGGER = LoggerFactory.getLogger(Location.class);

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location(Map<String, String> properties) {
        this.longitude = doubleOrNull((String) properties.get("longitude"));
        this.latitude = doubleOrNull((String) properties.get("latitude"));
    }
}
