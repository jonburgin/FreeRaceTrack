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
    def expectedJson = "{\"@CLASS\":\"net.burgin.racetrack.domain.RaceEvent\",\"name\":\"Derby 2016\",\"raceTypes\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Runoff\",\"name\":\"Grand Championship\",\"raceTypes\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race1\",\"competitionClasses\":[\"Web II\",\"Tiger\"]},{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race2\",\"competitionClasses\":[\"Web II\"]}],\"take\":1}],\"competitionClasses\":[\"Web II\",\"Tiger\"],\"racers\":[{\"firstName\":\"Jon\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator\",\"competitionClass\":\"Web II\"}]},{\"firstName\":\"Freedom\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie2\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator2\",\"competitionClass\":\"Web II\"}]}]}"
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
        runoff.setRaceTypes(Arrays.asList(race1, race2))
        raceEvent.setRaceTypes(Arrays.asList(runoff))
        raceEvent.setCompetitionClasses(new HashSet(Arrays.asList(class1,class2)))
    }

    def "json serilization/deserlization is correct"(){
        when:
        def raceEventRead = mapper.readValue(mapper.writeValueAsString(raceEvent), RaceEvent.class)
        then:
        raceEventRead.equals(raceEvent)
    }

    def "find maxCar returns maximum car id from list"(){
        setup:
        Car car1 = new Car()
        car1.setId(1)
        Car car2 = new Car()
        car2.setId(2)
        Car car3 = new Car()
        car3.setId(3)
        Racer r1 = Mock()
        Racer r2 = Mock()

        r1.getCars() >>> [[car1,car2],[car1],[car3]]
        r2.getCars() >>> [[car3],[car2,car3],[car1,car2]]

        RaceEvent re1 = new RaceEvent()
        re1.setRacers(Arrays.asList(r1, r2))
        expect:
        re1.findMaxCarId() == maxCarIdValue
        where:
        maxCarIdValue << [3, 3, 3]

    }

    def "find car by id returns appropriate car"(){
        setup:
        Car car1 = new Car()
        car1.setId(1)
        Car car2 = new Car()
        car2.setId(2)
        Car car3 = new Car()
        car3.setId(3)
        Racer r1 = Mock()
        Racer r2 = Mock()

        r1.getCars() >>> [[car1,car2],[car1],[car3]]
        r2.getCars() >>> [[car3],[car2,car3],[car1,car2]]

        RaceEvent re = new RaceEvent()
        re.setRacers(Arrays.asList(r1, r2))
        expect:
        re.findCarById(3).get() == car3;
        re.findCarById(3).get() == car3;
        re.findCarById(3).get() == car3;
    }

    def "assign car ids with no assigned ids, assigns starting with 1"(){
        setup:
        Car car1 = new Car()
        Car car2 = new Car()
        Car car3 = new Car()
        Racer r1 = Mock()
        Racer r2 = Mock()
        r1.getCars() >> [car1,car2]
        r2.getCars() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setRacers(Arrays.asList(r1, r2))
        re.assignCarIds()
        expect:
        car1.getId() == 1
        car2.getId() == 2
        car3.getId() == 3
    }
    def "assign car ids with some id, assigns starting with next greater value"(){
        setup:
        Car car1 = new Car()
        Car car2 = new Car()
        Car car3 = new Car()
        car3.setId(3)
        Racer r1 = Mock()
        Racer r2 = Mock()
        r1.getCars() >> [car1,car2]
        r2.getCars() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setRacers(Arrays.asList(r1, r2))
        re.assignCarIds()
        expect:
        car1.getId() == 4
        car2.getId() == 5
        car3.getId() == 3
    }

    def "getCars returns all the cars"(){
        setup:
        Car car1 = new Car()
        Car car2 = new Car()
        Car car3 = new Car()
        Racer r1 = Mock()
        Racer r2 = Mock()
        r1.getCars() >> [car1,car2]
        r2.getCars() >> [car3]
        RaceEvent re = new RaceEvent()
        re.setRacers(Arrays.asList(r1, r2))
        def cars = re.getCars()
        expect:
        cars.size() == 3
        cars.contains(car1)
        cars.contains(car2)
        cars.contains(car3)

    }
}