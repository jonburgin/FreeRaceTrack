/**
 * Created by jonburgin on 12/11/15.
 */
package net.burgin.racetrack

import com.fasterxml.jackson.databind.ObjectMapper
import net.burgin.racetrack.domain.Car
import spock.lang.*

class CarTest extends Specification{
    def car = new Car();

    @Shared
    def mapper = new ObjectMapper();

    def setup(){
        car.setName("Herbie")
        car.setCompetitionClass("Tiger")
    }

    def "Json output is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(car)
        then:
        jsonString.equals("{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"}")
    }

    def "Json input is correct"(){
        when:
        def carRead = mapper.readValue("{\"name\":\"TestName\",\"competitionClass\":\"ClassName\"}",Car.class)
        then:
        carRead.name == "TestName"
        carRead.competitionClass == "ClassName"
    }

    def "toString use name and class formatted correctly"(){
        expect:
        car.toString().equals("Herbie (Tiger)")
    }
}
