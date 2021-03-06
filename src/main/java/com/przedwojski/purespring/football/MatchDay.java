package com.przedwojski.purespring.football;

import java.util.List;

class MatchDay {

    private List<Match> matches;

    public MatchDay() {
//        System.out.println(this);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "MatchDay{" +
            "matches=" + matches +
            '}';
    }
}
