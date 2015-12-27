package net.burgin.racetrack.racing

import net.burgin.racetrack.domain.SimpleRace
import net.burgin.racetrack.domain.Vehicle
import net.burgin.racetrack.domain.Competitor
import net.burgin.racetrack.domain.Heat
import net.burgin.racetrack.domain.RaceResult
import net.burgin.racetrack.domain.DefaultSimpleRace
import net.burgin.racetrack.domain.RaceEvent
import net.burgin.racetrack.domain.Race
import net.burgin.racetrack.domain.DefaultRunoffRace
import net.burgin.racetrack.domain.RunoffRace
import net.burgin.racetrack.domain.Track
import spock.lang.Specification

/**
 * Created by jonburgin on 12/11/15.
 */
class DefaultHeatGeneratorTest extends Specification {
    DefaultHeatGenerator heatGenerator = new DefaultHeatGenerator()
    def TIGER_CLASS = "Tiger"
    def WOLF_CLASS = "WOLF"
    def tigerCar1 = new Vehicle("T1", TIGER_CLASS)
    def tigerCar2 = new Vehicle("T2", TIGER_CLASS)
    def tigerCar3 = new Vehicle("T3", TIGER_CLASS)
    def tigerCar4 = new Vehicle("T4", TIGER_CLASS)
    def wolfCar1 = new Vehicle("W1", WOLF_CLASS)
    def wolfCar2 = new Vehicle("W1", WOLF_CLASS)

    def "genearteHeats with #Vehicles < #lanes, doesn't use all lanes"(){
        def race = Mock(Race)
        def cars = [tigerCar1, tigerCar2]
        when:
        List<Heat> heats = heatGenerator.generateHeats(race, cars, 5);
        then:
        race.getName()>>null
        heats.size == 2
        def heat1 = makeHeat(race, tigerCar1, tigerCar2)
        def heat2 = makeHeat(race, tigerCar2, tigerCar1)
        heats.contains(heat1)
        heats.contains(heat2)
    }

    def "genearteHeats with #lanes < #vehicles, permutes cars over all lanes"(){
        def race = Mock(Race)
        def cars = [tigerCar1, tigerCar2, tigerCar3, tigerCar4]
        when:
        List<Heat> heats = heatGenerator.generateHeats(race, cars, 3);
        then:
        race.getName()>>null

        heats.size == 4
        def heat1 = makeHeat(race, tigerCar1, tigerCar2, tigerCar3)
        def heat2 = makeHeat(race, tigerCar2, tigerCar3, tigerCar4)
        def heat3 = makeHeat(race, tigerCar3, tigerCar4, tigerCar1)
        def heat4 = makeHeat(race, tigerCar4, tigerCar1, tigerCar2)
        heats.contains(heat1)
        heats.contains(heat2)
        heats.contains(heat3)
        heats.contains(heat4)
    }

    def makeHeat(Race race,Vehicle...params){
        List<Vehicle> list = new ArrayList<>()
        params.each {c -> list.add(c)}
        return new Heat(race,list);
    }

    def "generateRaceHeats generates heats for all races"(){
        setup:
        def raceEvent = Mock(RaceEvent)
        def race1 = Mock(SimpleRace)
        race1.getCompetitionClasses() >> [TIGER_CLASS]
        def race2 = Mock(SimpleRace)
        race2.getCompetitionClasses() >> [WOLF_CLASS]
        def runoff = Mock(RunoffRace)
        runoff.getRaces() >> [race1, race2]
        def track = Mock(Track)
        track.getLaneCount()>>3
        raceEvent.getTrack()>>track
        raceEvent.getRacesAsList()>>[runoff, race1, race2]
        raceEvent.getVehicles(race1)>>[tigerCar1,tigerCar2,tigerCar3,tigerCar4]
        raceEvent.getVehicles(race2)>>[wolfCar1,wolfCar2]
        runoff.getVehicles()>>[tigerCar1, wolfCar1, tigerCar2, tigerCar3, tigerCar4, wolfCar2]
        heatGenerator.raceEvent =raceEvent
        when:
        heatGenerator.generateAllRaceHeats()
        then:
        1 * race1.setHeats(_)
        4 * race1.getName()
        1 * race2.setHeats(_)
        2 * race2.getName()
        1 * runoff.setHeats(_)
        1 * runoff.hasHeats()
        6 * runoff.getName()

    }
}
