package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification


/**
 * Created by jonburgin on 12/11/15.
 */
class DefaultSimpleRaceTest extends Specification {
    def race = new DefaultSimpleRace()
    def mapper = new ObjectMapper()

    def setup(){
        race.name = "Tiger Race"
        race.competitionClasses = Arrays.asList("Tiger", "Wolf", "Cow")
        race.byClassification = true
    }

    def "multi parameter constructor initializes variable correctly" (){
        def race2 = new DefaultSimpleRace("name2", "one", "two")
        expect:
        race2.name.equals("name2")
        race2.competitionClasses.size() == 2
        race2.competitionClasses.contains("one")
        race2.competitionClasses.contains("two")
    }

    def "json serialization is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(race)
        def raceRead = mapper.readValue(jsonString, Race.class)
        then:
        raceRead.equals(race)
    }

    def "race change events are thrown on name change"(){
        def listener = Mock(RaceEventChangeListener)
        race.addRaceEventChangeListener(listener)
        when:
        race.setName("foo")
        then:
        1*listener.raceChanged(race)
    }

    def "toString returns name"(){
        expect:
        race.toString().equals("Tiger Race")
    }
}