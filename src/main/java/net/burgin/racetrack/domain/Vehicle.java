package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bridj.cpp.com.GUID;

import java.awt.*;
import java.util.UUID;

/**
 * Created by jonburgin on 12/2/15.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude="image")
public class Vehicle {
    private int id;
    UUID uuid = UUID.randomUUID();
    String name = "";
    String competitionClass = "";
    @JsonIgnore
    Image image;

    public Vehicle(){}

    public Vehicle(String name, String competitionClass){
        this();
        this.name = name;
        this.competitionClass = competitionClass;
    }

    public String toString(){
        return String.format("%s (%s)",name, competitionClass).toString();
    }

}
