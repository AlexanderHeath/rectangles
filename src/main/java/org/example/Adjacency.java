package org.example;

public class Adjacency {
    private final AdjacencyType type;

    public Adjacency(AdjacencyType type) {
        this.type = type;
    }

    public AdjacencyType getType() {
        return type;
    }
}
