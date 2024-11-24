package ca.utoronto.utm.paint;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PolylineManipulatorStrategy extends ShapeManipulatorStrategy {
    // The job of Manipulator strategy class is to create the commands for polyline and add then to the PaintModel
    private PolylineCommand polylineCommand;
    // We need to track the current point the user is moving the mouse to
    private Point curr;
    PolylineManipulatorStrategy(PaintModel model){
        super(model);
        this.polylineCommand = new PolylineCommand();
        this.curr = null;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        // we need to check if a new polyline is being started
        if (this.polylineCommand == null){
            // polylinecommand is only null when right click occurs
            // then a new polylinrcommand is initialized and added as a command
            this.polylineCommand = new PolylineCommand();
            this.addCommand(this.polylineCommand);
        }
        // the point the user has clicked
        Point q = new Point((int)e.getX(),(int) e.getY());
        // getButton method tells us which mouse button was clicked
        // if its left, we add the point to the command
        if (e.getButton() == MouseButton.PRIMARY){
            this.polylineCommand.add(q);
            System.out.println("left click");

            }
        // if its right, the end the line by setting it to null
        else if(e.getButton() == MouseButton.SECONDARY){
                this.polylineCommand = null;
                //this.curr = null;
            System.out.println("right click");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // if right click has not been pressed yet, we the polylinecommand to the paint model
        if(this.polylineCommand != null){
            this.addCommand(polylineCommand);
        }
    }

    // polyline has no drag feature
    @Override
    public void mouseDragged(MouseEvent e) {

    }


    @Override
    public void mouseMoved(MouseEvent e){
        if(this.polylineCommand != null){
            // as the mouse moves, the curr is updated according to the mouse points
            this.curr = new Point((int) e.getX(), (int)e.getY());
        }
    }



}
