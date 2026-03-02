package ca.concordia.model;

public class Arrondissement {
    int size;
    String name;

    public Arrondissement(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
