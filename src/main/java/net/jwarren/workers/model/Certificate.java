package net.jwarren.workers.model;

import lombok.Data;

@Data
public class Certificate {
    private String name;

    public Certificate(String name) {
        this.name = name;
    }
}
