package io.exiled.salesbook.model;

import lombok.Getter;

@Getter
public class Img {
    private String path;
    private String name;

    public Img(String path, String name) {
        this.path = path;
        this.name = name;
    }
}
