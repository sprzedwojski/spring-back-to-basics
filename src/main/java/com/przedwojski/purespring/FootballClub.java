package com.przedwojski.purespring;

public class FootballClub {

    private String name;
    private int position;

    public FootballClub() {
        System.out.println("\n\n>>>>>>CREATED!!!\n\n");
    }

    public FootballClub(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
