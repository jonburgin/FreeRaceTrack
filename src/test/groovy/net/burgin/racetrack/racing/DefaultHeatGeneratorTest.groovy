package net.burgin.racetrack.racing

import net.burgin.racetrack.domain.Car
import net.burgin.racetrack.domain.Competitor
import net.burgin.racetrack.domain.Heat
import net.burgin.racetrack.domain.RaceType
import net.burgin.racetrack.domain.Track
import spock.lang.Specification

/**
 * Created by jonburgin on 12/11/15.
 */
class DefaultHeatGeneratorTest extends Specification {
    HeatGenerator heatGenerator = new DefaultHeatGenerator()
    def TIGER_CLASS = "Tiger"
    def WOLF_CLASS = "WOLF"
    def tigerCar1 = new Car("T1", TIGER_CLASS)
    def tigerCar2 = new Car("T2", TIGER_CLASS)
    def tigerCar3 = new Car("T3", TIGER_CLASS)
    def tigerCar4 = new Car("T4", TIGER_CLASS)
    def wolfCar1 = new Car("W1", WOLF_CLASS)

    def "#cars < #lanes, doesn't use all lanes"(){
        def race = Mock(RaceType)
        def cars = Arrays.asList(tigerCar1, wolfCar1, tigerCar2)
        def track = Mock(Track)
        when:
        List<Heat> heats = heatGenerator.generate(race, cars, track);
        then:
        1*track.getLaneCount() >> 5
        1*race.getCompetitionClasses() >> Arrays.asList(TIGER_CLASS)
        heats.size == 2
        def heat1 = makeHeat(tigerCar1, tigerCar2)
        def heat2 = makeHeat(tigerCar2, tigerCar1)
        heats.contains(heat1)
        heats.contains(heat2)
    }

    def "#lanes < #cars, permutes cars over all lanes"(){
        def race = Mock(RaceType)
        def cars = Arrays.asList(tigerCar1, wolfCar1, tigerCar2, tigerCar3, tigerCar4)
        def track = Mock(Track)
        when:
        List<Heat> heats = heatGenerator.generate(race, cars, track);
        then:
        1*track.getLaneCount() >> 3
        1*race.getCompetitionClasses() >> Arrays.asList(TIGER_CLASS)
        heats.size == 4
        def heat1 = makeHeat(tigerCar1, tigerCar2, tigerCar3)
        def heat2 = makeHeat(tigerCar2, tigerCar3, tigerCar4)
        def heat3 = makeHeat(tigerCar3, tigerCar4, tigerCar1)
        def heat4 = makeHeat(tigerCar4, tigerCar1, tigerCar2)
        heats.contains(heat1)
        heats.contains(heat2)
        heats.contains(heat3)
        heats.contains(heat4)
    }

    def makeHeat(Car...params){
        List<Car> list = new ArrayList<>()
        params.each {c -> list.add(new Competitor(c))}
        return new Heat(list);
    }
}
