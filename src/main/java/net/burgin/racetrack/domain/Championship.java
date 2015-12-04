package net.burgin.racetrack.domain;

import java.util.List;

/**
 * Created by jonburgin on 12/4/15.
 */
public class Championship extends Race {
    List<Race> children;
    int take;//number of winners to move up from child races.
}
