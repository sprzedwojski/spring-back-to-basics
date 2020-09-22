package com.przedwojski.purespring;

import java.util.List;

class FootballService {
    private List<FootballClub> clubs;

    public List<FootballClub> getClubs() {
        return clubs;
    }

    public void setClubs(List<FootballClub> clubs) {
        this.clubs = clubs;
    }

    void show() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(this);
        clubs.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "FootballService{" +
            "clubs=" + clubs +
            '}';
    }
}
