package net.burgin.racetrack.gui;

import net.burgin.racetrack.domain.ImageHolder;
import net.burgin.racetrack.domain.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jonburgin on 1/6/16.
 */
public class RaceTrackImageUtils {
    static String homeDirectory = System.getProperty("user.home") + File.separator
            + "FreeRaceTrack" + File.separator
            + "images" + File.pathSeparator;
    static BufferedImage defaultPersonImage;
    static BufferedImage defaultVehicleImage;

    static{
        try {
            defaultPersonImage = ImageIO.read(ClassLoader.getSystemClassLoader().getSystemResource("images/duke.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            defaultVehicleImage = ImageIO.read(ClassLoader.getSystemClassLoader().getSystemResource("images/pinewood-derby2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getImageIcon(ImageHolder imageHolder) {
        BufferedImage image = imageHolder.getImage();
        if(image != null)
            return new ImageIcon(image);
        image = loadImage(imageHolder);
        if( image == null)
            image = getDefaultImage(imageHolder);
        imageHolder.setImage(image);
        return new ImageIcon(image);
    }

    static private BufferedImage loadImage(ImageHolder imageHolder){
        URL url = determineImageUrl(imageHolder == null?null:imageHolder.getUuid().toString());
        if(url == null)
            return null;
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            return null;
        }
    }

    static private BufferedImage getDefaultImage(ImageHolder imageHolder){
        if(imageHolder instanceof Vehicle)
            return defaultVehicleImage;
        return defaultPersonImage;
    }

    private static File getFileForId(String id){
        String path = homeDirectory;
        path += id == null?"":id + ".png";
        File file = new File(path);
        return file;
    }

    private static URL determineImageUrl(String id){
        File file = getFileForId(id);
        URL url = null;
        try {
            url = file.exists() ? file.toURI().toURL() : null;
        } catch (MalformedURLException e) {
            //intentionally left blank
        }
        return url;
    }

    public static void saveImage(ImageHolder imageHolder) throws IOException {
        ImageIO.write(imageHolder.getImage(), "PNG", getFileForId(imageHolder.getUuid().toString()));
    }
}
