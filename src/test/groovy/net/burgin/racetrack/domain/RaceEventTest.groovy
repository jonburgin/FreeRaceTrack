package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification


/**
 * Created by jonburgin on 12/11/15.
 */
class RaceEventTest extends Specification {

    @Shared
    def mapper = new ObjectMapper()
    @Shared
    def raceEvent = new RaceEvent()
    def class1 = "Tiger"
    def class2 = "Web II"
    def expectedJson = "{\"@CLASS\":\"net.burgin.racetrack.domain.RaceEvent\",\"name\":\"Derby 2016\",\"races\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Runoff\",\"name\":\"Grand Championship\",\"races\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race1\",\"competitionClasses\":[\"Web II\",\"Tiger\"]},{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race2\",\"competitionClasses\":[\"Web II\"]}],\"take\":1}],\"competitionClasses\":[\"Web II\",\"Tiger\"],\"racers\":[{\"firstName\":\"Jon\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator\",\"competitionClass\":\"Web II\"}]},{\"firstName\":\"Freedom\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie2\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator2\",\"competitionClass\":\"Web II\"}]}]}"
    def setup(){
        raceEvent.setName("Derby 2016")
        def racer1 = new Racer("Jon", "Burgin")
        racer1.setCars(Arrays.asList(new Car("Herbie", class1), new Car("Dominator", class2)))
        def racer2 = new Racer("Freedom", "Burgin")
        racer2.setCars(Arrays.asList(new Car("Herbie2", class1), new Car("Dominator2", class2)))
        raceEvent.setRacers(Arrays.asList(racer1,racer2))
        def race1 = new Race("Race1", class1, class2)
        def race2 = new Race("Race2", class2)
        Runoff runoff = new Runoff("Grand Championship",1)
        runoff.setRaces(Arrays.asList(race1, race2))
        raceEvent.setRaces(Arrays.asList(runoff))
        raceEvent.setCompetitionClasses(new HashSet(Arrays.asList(class1,class2)))
    }

    def "json output is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(raceEvent)
        //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raceEvent))
        then:
        jsonString.equals(expectedJson)
    }

    def "json input is correct"(){
        when:
        def raceEventRead = mapper.readValue(expectedJson, RaceEvent.class)
        then:
        raceEventRead.equals(raceEvent)
    }
}