package com.przedwojski.purespring;

class Match {
    private final Ball ball;

    public Match(Ball ball) {
        this.ball = ball;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Match{" +
            "ball=" + ball +
            '}';
    }
}
