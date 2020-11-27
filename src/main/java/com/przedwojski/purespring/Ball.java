package com.przedwojski.purespring;

class Ball {
    private String size;

    public Ball(String size) {
        this.size = size;
//        System.out.println(this);
    }

    String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Ball{" +
            "size='" + size + '\'' +
            '}';
    }
}
