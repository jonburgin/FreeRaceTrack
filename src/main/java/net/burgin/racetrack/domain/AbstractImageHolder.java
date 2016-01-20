package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created by jonburgin on 1/19/16.
 */
@Data
@EqualsAndHashCode(callSuper = false,exclude="image")
public class AbstractImageHolder implements ImageHolder {

    UUID uuid = UUID.randomUUID();
    @JsonIgnore
    BufferedImage image;

}
