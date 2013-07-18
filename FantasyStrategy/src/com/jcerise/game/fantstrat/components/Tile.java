package com.jcerise.game.fantstrat.components;

import com.artemis.Component;

public class Tile extends Component{

    private String type;
    private int numericType;
    private String name;

    public Tile(String type, int numericType, String name) {
        this.type = type;
        this.name = name;
        this.numericType = numericType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int  getNumeric() {
        return numericType;
    }
}

