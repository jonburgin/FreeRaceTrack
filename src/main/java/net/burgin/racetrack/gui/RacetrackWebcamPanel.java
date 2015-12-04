package net.burgin.racetrack.gui;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import lombok.Data;
import net.burgin.racetrack.detection.*;
import net.burgin.racetrack.gui.adapters.RaceTrackEditorMouseAdapter;

import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import static java.awt.RenderingHints.*;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.VALUE_RENDER_SPEED;

/**
 * Created by jonburgin on 11/15/15.
 */
@Data
public class RacetrackWebcamPanel extends WebcamPanel implements HotSpotListener {
    Webcam webcam;
    boolean displayLanes ;
    boolean showHotSpot = true;
    boolean showRaceTime;
    private Track track = new Track();
    private HotSpotDetector oneTimeHotSpotDetector;
    private List<Point> detectedHotSpots = new ArrayList<>();
    public RacetrackWebcamPanel(Webcam webcam) {
        this(webcam, true);
    }

    public RacetrackWebcamPanel(Webcam webcam, boolean start) {
        this(webcam, null, start);
    }

    public RacetrackWebcamPanel(Webcam webcam, Dimension size, boolean start) {
        super(webcam, size, start);
        this.webcam = webcam;
        setPainter(new MyPainter());
        RaceTrackEditorMouseAdapter mouseListener = new RaceTrackEditorMouseAdapter(this);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
    }

    public void setHotSpotDetector(HotSpotDetector hotSpotDetector){
        this.oneTimeHotSpotDetector = hotSpotDetector;
        hotSpotDetector.addHotSpotListener(this);
        webcam.addWebcamListener(hotSpotDetector);
    }

    @Override
    public void hotSpotDetected(HotSpot hotSpot, int count) {
        if(count <=1){
            this.detectedHotSpots.clear();
        }
        detectedHotSpots.add(hotSpot.position);
    }


    /**
     * Default painter used to draw image in panel.
     *
     * @author Bartosz Firyn (SarXos)
     * @author Sylwia Kauczor
     */
    public class MyPainter implements Painter {

        /**
         * Webcam device name.
         */
        private String name = null;

        /**
         * Lat repaint time, uset for debug purpose.
         */
        private long lastRepaintTime = -1;

        /**
         * Buffered image resized to fit into panel drawing area.
         */
        private BufferedImage resizedImage = null;

        @Override
        public void paintPanel(WebcamPanel owner, Graphics2D g2) {

            assert owner != null;
            assert g2 != null;

            Object antialiasing = g2.getRenderingHint(KEY_ANTIALIASING);

            g2.setRenderingHint(KEY_ANTIALIASING, isAntialiasingEnabled() ? VALUE_ANTIALIAS_ON : VALUE_ANTIALIAS_OFF);
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int cx = (getWidth() - 70) / 2;
            int cy = (getHeight() - 40) / 2;

            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(cx, cy, 70, 40, 10, 10);
            g2.setColor(Color.WHITE);
            g2.fillOval(cx + 5, cy + 5, 30, 30);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(cx + 10, cy + 10, 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillOval(cx + 12, cy + 12, 16, 16);
            g2.fillRoundRect(cx + 50, cy + 5, 15, 10, 5, 5);
            g2.fillRect(cx + 63, cy + 25, 7, 2);
            g2.fillRect(cx + 63, cy + 28, 7, 2);
            g2.fillRect(cx + 63, cy + 31, 7, 2);

            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(0, 0, getWidth(), getHeight());
            g2.drawLine(0, getHeight(), getWidth(), 0);

            String str = "";

//            final String strInitDevice = rb.getString("INITIALIZING_DEVICE");
//            final String strNoImage = rb.getString("NO_IMAGE");
//            final String strDeviceError = rb.getString("DEVICE_ERROR");
            if(owner.isStarting()){
                str = "Initializing device";
            }
//            if (errored) {
//                str = starting ? strInitDevice : strNoImage;
//            } else {
//                str = strDeviceError;
//            }

            FontMetrics metrics = g2.getFontMetrics(getFont());
            int w = metrics.stringWidth(str);
            int h = metrics.getHeight();

            int x = (getWidth() - w) / 2;
            int y = cy - h;

            g2.setFont(getFont());
            g2.setColor(Color.WHITE);
            g2.drawString(str, x, y);

            if (name == null) {
                name = webcam.getName();
            }

            str = name;

            w = metrics.stringWidth(str);
            h = metrics.getHeight();

            g2.drawString(str, (getWidth() - w) / 2, cy - 2 * h);
            g2.setRenderingHint(KEY_ANTIALIASING, antialiasing);
        }

        @Override
        public void paintImage(WebcamPanel owner, BufferedImage image, Graphics2D g2) {

            assert owner != null;
            assert image != null;
            assert g2 != null;

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            Object antialiasing = g2.getRenderingHint(KEY_ANTIALIASING);
            Object rendering = g2.getRenderingHint(KEY_RENDERING);

            g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
            g2.setRenderingHint(KEY_RENDERING, VALUE_RENDER_SPEED);
            g2.setBackground(Color.BLACK);
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, panelWidth, panelHeight);

            // resized image position and size
            int resizedX = 0;
            int resizedY = 0;
            int resizedWidth = 0;
            int resizedHeight = 0;

            //this is ridiculous that there is no getDrawMode. Pull request has been submitted.
            DrawMode drawMode = (isFitArea()?DrawMode.FILL:isFillArea()?DrawMode.FILL:DrawMode.NONE);
            switch (drawMode) {
                case NONE:
                    resizedWidth = image.getWidth();
                    resizedHeight = image.getHeight();
                    break;
                case FILL:
                    resizedWidth = panelWidth;
                    resizedHeight = panelHeight;
                    break;
                case FIT:
                    double s = Math.max((double) imageWidth / panelWidth, (double) imageHeight / panelHeight);
                    double niw = imageWidth / s;
                    double nih = imageHeight / s;
                    double dx = (panelWidth - niw) / 2;
                    double dy = (panelHeight - nih) / 2;
                    resizedWidth = (int) niw;
                    resizedHeight = (int) nih;
                    resizedX = (int) dx;
                    resizedY = (int) dy;
                    break;
            }

            if (resizedImage != null) {
                resizedImage.flush();
            }

            if (resizedWidth == image.getWidth() && resizedHeight == image.getHeight() && !isMirrored()) {
                resizedImage = image;
            } else {

                GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsConfiguration gc = genv.getDefaultScreenDevice().getDefaultConfiguration();

                Graphics2D gr = null;
                try {

                    resizedImage = gc.createCompatibleImage(panelWidth, panelHeight);
                    gr = resizedImage.createGraphics();
                    gr.setComposite(AlphaComposite.Src);

                    for (Map.Entry<RenderingHints.Key, Object> hint :WebcamPanel.DEFAULT_IMAGE_RENDERING_HINTS.entrySet()) {
                        gr.setRenderingHint(hint.getKey(), hint.getValue());
                    }

                    gr.setBackground(Color.BLACK);
                    gr.setColor(Color.BLACK);
                    gr.fillRect(0, 0, panelWidth, panelHeight);

                    int sx1, sx2, sy1, sy2; // source rectangle coordinates
                    int dx1, dx2, dy1, dy2; // destination rectangle coordinates

                    dx1 = resizedX;
                    dy1 = resizedY;
                    dx2 = resizedX + resizedWidth;
                    dy2 = resizedY + resizedHeight;

                    sy1 = 0;
                    sy2 = imageHeight;
                    if (isMirrored()) {
                        sx1 = imageWidth;
                        sx2 = 0;
                    } else {
                        sx1 = 0;
                        sx2 = imageWidth;
                    }
                    //this draws the image onto the resized image.
                    gr.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);


                } finally {
                    if (gr != null) {
                        gr.dispose();
                    }
                }
            }
            //this draws the resized image to the 2d graphics
            g2.drawImage(resizedImage, 0, 0, null);

            if (isFPSDisplayed()) {
                String str = String.format("FPS: %.1f", webcam.getFPS());
                int sx = 5;
                int sy = panelHeight - 5;
                drawString(g2, str, sx, sy);
            }

            if (isImageSizeDisplayed()) {

                String res = String.format("%d\u2A2F%d px", imageWidth, imageHeight);

                FontMetrics metrics = g2.getFontMetrics(getFont());
                int sw = metrics.stringWidth(res);
                int sx = panelWidth - sw - 5;
                int sy = panelHeight - 5;

                drawString(g2, res, sx, sy);
            }

            if (isDisplayDebugInfo()) {

                if (lastRepaintTime < 0) {
                    lastRepaintTime = System.currentTimeMillis();
                } else {

                    long now = System.currentTimeMillis();
                    String res = String.format("DEBUG: repaints per second: %.1f", (double) 1000 / (now - lastRepaintTime));
                    lastRepaintTime = now;
                    g2.setFont(getFont());
                    g2.setColor(Color.BLACK);
                    g2.drawString(res, 6, 16);
                    g2.setColor(Color.WHITE);
                    g2.drawString(res, 5, 15);
                }
            }

            if(displayLanes){
                drawLanes(g2);
            }

            if(showHotSpot){
                g2.setColor(Color.WHITE);
                g2.drawOval(track.getRaceStartHotSpot().x-10, track.getRaceStartHotSpot().y-10, 20,20);
                String s = "Start HotSpot";
                FontMetrics metrics = g2.getFontMetrics(getFont());
                int stringX = track.getRaceStartHotSpot().x - metrics.stringWidth(s)/2;
                int stringY = track.getRaceStartHotSpot().y + metrics.getHeight() + 5;
                drawString(g2, s,stringX, stringY);
            }

            if(showRaceTime){
                //TODO
            }

            g2.setRenderingHint(KEY_ANTIALIASING, antialiasing);
            g2.setRenderingHint(KEY_RENDERING, rendering);
        }

        private void drawLanes(Graphics2D g2) {
            Point finishLinePosition = track.getFinishLinePosition();
            track.getLanes().keySet().stream().forEach(key -> {
                String str = String.format("%d", key + 1);
                FontMetrics metrics = g2.getFontMetrics(getFont());
                Point position = track.getLanes().get(key);
                int stringX = position.x - metrics.stringWidth(str)/2;
                int stringY = position.y - 20;
                drawString(g2, str, stringX, stringY);
                int[] xPoints = {position.x - 10, position.x, position.x + 10};
                int [] yPoints = {position.y, position.y -10, position.y};
                if(detectedHotSpots.contains(position))
                    g2.fillPolygon(xPoints,yPoints,3);
                else
                    g2.drawPolygon(xPoints,yPoints,3);
            });
            g2.drawLine(finishLinePosition.x, finishLinePosition.y, finishLinePosition.x + track.getWidth(), finishLinePosition.y);
            g2.drawOval(finishLinePosition.x - 10, finishLinePosition.y - 5, 10, 10);
            g2.drawOval(finishLinePosition.x + track.getWidth(), finishLinePosition.y - 5, 10, 10);
        }

        private void drawString(Graphics2D g2, String str, int stringX, int stringY) {
            g2.setFont(getFont());
            g2.setColor(Color.BLACK);
            g2.drawString(str, stringX + 1, stringY + 1);
            g2.setColor(Color.WHITE);
            g2.drawString(str, stringX, stringY);
        }
    }
}