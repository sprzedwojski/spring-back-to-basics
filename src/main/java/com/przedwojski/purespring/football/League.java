package com.przedwojski.purespring.football;

import java.util.Arrays;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;

public class League {

    private final FootballClub footballClub;
    private FootballClub footballClub2;

    // You can also instruct Spring to provide all beans of a particular type from the ApplicationContext by adding the
    // @Autowired annotation to a field or method that expects an array of that type.
    // The same applies for typed collections.
//    @Inject
    @Resource
    private FootballClub[] allClubs;

    // Even typed Map instances can be autowired as long as the expected key type is String.
    // he map values contain all beans of the expected type, and the keys contain the corresponding bean names.
    @Inject
    private Map<String, FootballClub> clubsMap;

//    @Inject // This or @Autowired no longer necessary as of Spring 4.3 if there's only one constructor
    public League(FootballClub manchesterUnited) {
        this.footballClub = manchesterUnited;
    }

    @Inject
    public League(FootballClub manchesterUnited, FootballClub leicester) {
        this.footballClub = manchesterUnited;
        System.out.println(leicester);
    }

    @Autowired // You can also apply the annotation to methods with arbitrary names and multiple arguments
    public void anyNameIWant(FootballClub leicester) {
        this.footballClub2 = leicester;
    }

    @Override
    public String toString() {
        return "League{" +
            "footballClub=" + footballClub +
            ", footballClub2=" + footballClub2 +
            ", allClubs=" + Arrays.toString(allClubs) +
            ", clubsMap=" + clubsMap +
            '}';
    }
}
