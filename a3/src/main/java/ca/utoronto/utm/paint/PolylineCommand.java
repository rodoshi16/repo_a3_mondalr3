package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PolylineCommand extends PaintCommand {
    // need to store the point where the user first clicks and then connect them from the last point
    private ArrayList<Point> points=new ArrayList<Point>();

    public ArrayList<Point> getPoints(){
        return points;
    }

    public void add(Point p){
        //method to add points and change the set and notify observers
        this.points.add(p);
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void execute(GraphicsContext g) {
        // giving color to the polyline, gets color from PaintCommand which picks the color randomly
        g.setStroke(this.getColor());
        // to execute PolyLine command, iterate through the list of points
        for (int i = 0; i< this.points.size(); i++){
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i+1);
            //strokeLine method calculates the line segment automatically given the two points
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);

        }


    }
}
