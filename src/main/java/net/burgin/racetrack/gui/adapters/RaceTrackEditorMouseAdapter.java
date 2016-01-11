package net.burgin.racetrack.gui.adapters;

import net.burgin.racetrack.detection.HotSpotDetector;
import net.burgin.racetrack.detection.GeometricTrack;
import net.burgin.racetrack.gui.RacetrackWebcamPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jonburgin on 12/2/15.
 */
public class RaceTrackEditorMouseAdapter extends MouseAdapter {

    RacetrackWebcamPanel racetrackWebcamPanel;
    boolean hotSpotPositioning;
    private boolean trackPositioning;
    private boolean trackStretching;

    public RaceTrackEditorMouseAdapter(RacetrackWebcamPanel racetrackWebcamPanel){
        this.racetrackWebcamPanel = racetrackWebcamPanel;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        RacetrackWebcamPanel.MyPainter painter = (RacetrackWebcamPanel.MyPainter) racetrackWebcamPanel.getPainter();
        if(hotSpotPositioning){
            Point hotSpot = racetrackWebcamPanel.getGeometricTrack().getRaceStartHotSpot();
            hotSpot.move(painter.fromDisplayX(mouseEvent.getX()),painter.fromDisplayY(mouseEvent.getY()));
        }
        if(trackPositioning){
            racetrackWebcamPanel.getGeometricTrack()
                    .setFinishLinePosition(new Point(painter.fromDisplayX(mouseEvent.getX()), painter.fromDisplayY(mouseEvent.getY())));
        }
        if(trackStretching){
            racetrackWebcamPanel.getGeometricTrack()
                    .adjustWidth(new Point(painter.fromDisplayX(mouseEvent.getX()), painter.fromDisplayY(mouseEvent.getY())));
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point hotSpot = racetrackWebcamPanel.getGeometricTrack().getRaceStartHotSpot();
        boolean insideHotspot = closeEnough(mouseEvent,hotSpot,20);
        Point trackMovePoint = racetrackWebcamPanel.getGeometricTrack().getMoveHotSpot();
        boolean insideTrackMove = closeEnough(mouseEvent,trackMovePoint,10);
        if(insideHotspot || insideTrackMove){
            setCursorType(Cursor.MOVE_CURSOR);
            return;
        }
        Point trackStretchPoint = racetrackWebcamPanel.getGeometricTrack().getStretchHotSpot();
        boolean insideTrackStretch = closeEnough(mouseEvent,trackStretchPoint, 20);
        if(insideTrackStretch){
            setCursorType(Cursor.E_RESIZE_CURSOR);
            return;
        }
        setCursorType(Cursor.DEFAULT_CURSOR);

    }

    private boolean closeEnough(MouseEvent mouseEvent, Point p, int diameter){
        RacetrackWebcamPanel.MyPainter painter = (RacetrackWebcamPanel.MyPainter) racetrackWebcamPanel.getPainter();
        //todo need to do reverse transposition
        return Math.abs(painter.fromDisplayX(mouseEvent.getX()) - p.x) < diameter && Math.abs(painter.fromDisplayY(mouseEvent.getY()) - p.y)< diameter;
    }

    private void setCursorType(int cursorType){
        if(racetrackWebcamPanel.getCursor().getType() != cursorType) {
            racetrackWebcamPanel.setCursor(new Cursor(cursorType));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point hotSpot = racetrackWebcamPanel.getGeometricTrack().getRaceStartHotSpot();
        if(closeEnough(mouseEvent,hotSpot,20)){
            hotSpotPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getGeometricTrack().getMoveHotSpot(), 10)){
            trackPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getGeometricTrack().getStretchHotSpot(),10)){
            trackStretching = true;
        }
        if(trackPositioning || trackStretching || hotSpotPositioning){
            racetrackWebcamPanel.getHotSpotDetector().setEnabled(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        quitEditing();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        quitEditing();
    }

    protected void quitEditing(){
        hotSpotPositioning = false;
        trackPositioning = false;
        trackStretching = false;
        HotSpotDetector hotSpotDetector = racetrackWebcamPanel.getHotSpotDetector();
        GeometricTrack geometricTrack = racetrackWebcamPanel.getGeometricTrack();
        hotSpotDetector.removeHotSpots();
        geometricTrack.getLanes().values().stream().forEach(point -> hotSpotDetector.addHotSpotPoint(point));
        hotSpotDetector.addHotSpotPoint(geometricTrack.getRaceStartHotSpot());
        hotSpotDetector.setEnabled(true);
    }
}
