package com.imd.web2.pass.model;

public enum EventType {
    Festival("Festival"),
    Workshop("Workshop"),
    Show("Show"),
    Talk("Talk");

    private final String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
