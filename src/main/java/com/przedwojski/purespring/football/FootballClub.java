package com.przedwojski.purespring.football;

public class FootballClub {

    private String name;
    private int position;
    private String description;

    public FootballClub() {
    }

    public FootballClub(String name, int position, String description) {
        this.name = name;
        this.position = position;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
//        System.out.println("Created: " + name);
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FootballClub{" +
            "name='" + name + '\'' +
            ", position=" + position +
            ", description='" + description + '\'' +
            '}';
    }
}
