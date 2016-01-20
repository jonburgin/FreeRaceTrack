package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bridj.cpp.com.GUID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends AbstractImageHolder{
    private int id;
    String name = "";
    String competitionClass = "";

    public Vehicle(){}

    public Vehicle(String name, String competitionClass){
        this();
        this.name = name;
        this.competitionClass = competitionClass;
    }

    public String toString(){
        return String.format("%02d:%s (%s)",id,name, competitionClass).toString();
    }

}
