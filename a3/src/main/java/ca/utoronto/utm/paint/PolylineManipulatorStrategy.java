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
        if (this.polylineCommand == null){
            this.polylineCommand = new PolylineCommand();
            this.addCommand(this.polylineCommand);
        }
        Point q = new Point((int)e.getX(),(int) e.getY());
        if (e.getButton() == MouseButton.PRIMARY){
            this.polylineCommand.add(q);
            System.out.println("left click");

            }
        // if its right, the end the line
        else if(e.getButton() == MouseButton.SECONDARY){
                this.polylineCommand = null;
                //this.curr = null;
            System.out.println("right click");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.polylineCommand != null){
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
