package ca.utoronto.utm.paint;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PolylineManipulatorStrategy extends ShapeManipulatorStrategy {
    private PolylineCommand polylineCommand;
    private Point curr;
    PolylineManipulatorStrategy(PaintModel model){
        super(model);
        this.polylineCommand = new PolylineCommand();
        this.curr = null;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        // getButton method tells us which mouse button was clicked
        // if its left, get the point and store it
        if (e.getButton() == MouseButton.PRIMARY){
            Point q = new Point((int)e.getX(),(int) e.getY());
            this.polylineCommand.add(q);

        }
        // if its right, the end the line
        else if(e.getButton() == MouseButton.SECONDARY){
            if (polylineCommand != null){
                this.polylineCommand = null;
                this.curr = null;
            }


        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY && this.polylineCommand == null){
            this.polylineCommand = new PolylineCommand();
            this.addCommand(polylineCommand);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e){
        if(this.polylineCommand != null){
            this.curr = new Point((int) e.getX(), (int)e.getY());
        }
    }



}
