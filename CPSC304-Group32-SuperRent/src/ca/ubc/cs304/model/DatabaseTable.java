package ca.ubc.cs304.model;

import java.util.List;

public class DatabaseTable {
    private String name;
    private List<String> columns;

    public String getName() {
        return name;
    }

    public List<String> getColumns() {
        return columns;
    }
}
