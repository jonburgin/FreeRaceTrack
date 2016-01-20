package net.burgin.racetrack.domain;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created by jonburgin on 1/19/16.
 */
public interface ImageHolder {
    BufferedImage getImage();
    UUID getUuid();
    void setImage(BufferedImage image);
}
