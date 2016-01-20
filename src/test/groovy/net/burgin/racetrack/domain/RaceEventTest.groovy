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
    def expectedJson = "{\"@CLASS\":\"net.burgin.racetrack.domain.RaceEvent\",\"name\":\"Derby 2016\",\"raceTypes\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Runoff\",\"name\":\"Grand Championship\",\"raceTypes\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race1\",\"competitionClasses\":[\"Web II\",\"Tiger\"]},{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race2\",\"competitionClasses\":[\"Web II\"]}],\"takeNumber\":1}],\"competitionClasses\":[\"Web II\",\"Tiger\"],\"racers\":[{\"firstName\":\"Jon\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator\",\"competitionClass\":\"Web II\"}]},{\"firstName\":\"Freedom\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie2\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator2\",\"competitionClass\":\"Web II\"}]}]}"
    def setup(){
        raceEvent.setName("Derby 2016")
        def racer1 = new Participant("Jon", "Burgin")
        racer1.setVehicles(Arrays.asList(new Vehicle("Herbie", class1), new Vehicle("Dominator", class2)))
        def racer2 = new Participant("Freedom", "Burgin")
        racer2.setVehicles(Arrays.asList(new Vehicle("Herbie2", class1), new Vehicle("Dominator2", class2)))
        raceEvent.setParticipants(Arrays.asList(racer1,racer2))
        def race1 = new DefaultSimpleRace("Race1", class1, class2)
        def race2 = new DefaultSimpleRace("Race2", class2)
        DefaultRunoffRace runoff = new DefaultRunoffRace("Grand Championship",1)
        runoff.setRaces(Arrays.asList(race1, race2))
        raceEvent.setRaces(Arrays.asList(runoff))
        raceEvent.setCompetitionClasses(new HashSet(Arrays.asList(class1,class2)))
    }

    def "json serialization/deserialization is correct"(){
        def string = mapper.writeValueAsString(raceEvent)
        when:
        def raceEventRead = mapper.readValue(string, RaceEvent.class)
        then:
        raceEventRead.equals(raceEvent)
    }

    def "find maxVehicle returns maximum vehicle id from list"(){
        setup:
        Vehicle car1 = new Vehicle()
        car1.setId(1)
        car1.setCompetitionClass("foo")
        Vehicle car2 = new Vehicle()
        car2.setId(2)
        car2.setCompetitionClass("foo")
        Vehicle car3 = new Vehicle()
        car3.setId(3)
        car3.setCompetitionClass("foo")
        Participant r1 = Mock()
        Participant r2 = Mock()

        r1.getVehicles() >>> [[car1, car2], [car1], [car3]]
        r2.getVehicles() >>> [[car3], [car2, car3], [car1, car2]]

        RaceEvent raceEvent = new RaceEvent()
        raceEvent.setCompetitionClasses(new HashSet<>(["foo"]))
        raceEvent.setParticipants([r1, r2])
        expect:
        raceEvent.findMaxVehicleId() == maxCarIdValue
        where:
        maxCarIdValue << [3, 3, 3]

    }

    def "findVehicleById returns appropriate vehicle"(){
        setup:
        Vehicle car1 = new Vehicle()
        car1.setId(1)
        car1.setCompetitionClass("foo")
        Vehicle car2 = new Vehicle()
        car2.setId(2)
        car2.setCompetitionClass("foo")
        Vehicle car3 = new Vehicle()
        car3.setCompetitionClass("foo")
        car3.setId(3)
        Participant r1 = Mock()
        Participant r2 = Mock()

        r1.getVehicles() >>> [[car1, car2], [car1], [car3]]
        r2.getVehicles() >>> [[car3], [car2, car3], [car1, car2]]

        RaceEvent re = new RaceEvent()
        re.setCompetitionClasses(new HashSet<>(["foo"]))
        re.setParticipants([r1, r2])
        expect:
        re.findVehicleById(3).get() == car3;
        re.findVehicleById(3).get() == car3;
        re.findVehicleById(3).get() == car3;
    }

    def "assignVehicleIds with no assigned ids, assigns starting with 1"(){
        setup:
        Vehicle car1 = new Vehicle()
        car1.setCompetitionClass("foo")
        Vehicle car2 = new Vehicle()
        car2.setCompetitionClass("foo")
        Vehicle car3 = new Vehicle()
        car3.setCompetitionClass("foo")
        Participant r1 = Mock()
        Participant r2 = Mock()
        r1.getVehicles() >> [car1, car2]
        r2.getVehicles() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setCompetitionClasses(new HashSet<>(["foo"]))
        re.setParticipants([r1, r2])
        re.assignVehicleIds()
        expect:
        car1.getId() == 1
        car2.getId() == 2
        car3.getId() == 3
    }
    def "assignVehicleIds with some id, assigns starting with next greater value"(){
        setup:
        Vehicle car1 = new Vehicle()
        car1.setCompetitionClass("foo")
        Vehicle car2 = new Vehicle()
        car2.setCompetitionClass("foo")
        Vehicle car3 = new Vehicle()
        car3.setId(3)
        car3.setCompetitionClass("foo")
        Participant r1 = Mock()
        Participant r2 = Mock()
        r1.getVehicles() >> [car1, car2]
        r2.getVehicles() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setParticipants([r1, r2])
        re.setCompetitionClasses(new HashSet<>(["foo"]))
        re.assignVehicleIds()
        expect:
        car1.getId() == 4
        car2.getId() == 5
        car3.getId() == 3
    }

    def "getVehicles returns all the vehicles with the given raceclassification"(){
        setup:
        Vehicle car1 = new Vehicle()
        car1.setCompetitionClass("foo")
        Vehicle car2 = new Vehicle()
        car2.setCompetitionClass("foo")
        Vehicle car3 = new Vehicle()
        Participant r1 = Mock()
        Participant r2 = Mock()
        r1.getVehicles() >> [car1, car2]
        r2.getVehicles() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setParticipants(Arrays.asList(r1, r2))
        re.setCompetitionClasses(new HashSet<>(["foo"]))
        def cars = re.getVehicles()
        expect:
        cars.size() == 2
        cars.contains(car1)
        cars.contains(car2)
    }
}