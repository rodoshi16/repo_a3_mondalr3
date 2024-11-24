package ca.utoronto.utm.paint;

import javafx.beans.Observable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.PrintStream;

public class PolylineManipulatorStrategy extends ShapeManipulatorStrategy {
    private PolylineCommand polylineCommand;
    PolylineManipulatorStrategy(PaintModel model){
        super(model);
        this.polylineCommand = new PolylineCommand();
    }


    @Override
    //invoked when the mouse button has been clicked
    public void mouseClicked(MouseEvent e) {
        // getButton will tell us which mouse button (left or right) was clicked
        // if its left, we need to add the point to our list
        if (e.getButton() == MouseButton.PRIMARY){
            Point q = new Point((int)e.getX(), (int)e.getY());
            this.polylineCommand.add(q);
            System.out.println("mouse clicked at" + q);
        }
        // if its right, then we need to stop and not extend the point
        else if(e.getButton() == MouseButton.SECONDARY){
            addCommand(polylineCommand);
            this.polylineCommand = new PolylineCommand();
            // observable object has been modified
            //this.setChanged();
            // inform all observers that the object has been changed
            //this.notifyObservers();


        }

    }
//    public void mouseMoved(MouseEvent e) { }
//    public void mousePressed(MouseEvent e) { }
//    public void mouseReleased(MouseEvent e) { }
//    public void mouseEntered(MouseEvent e) { }
//    public void mouseExited(MouseEvent e) { }

}
