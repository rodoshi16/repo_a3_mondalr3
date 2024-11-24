package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PolylineCommand extends PaintCommand{
    //We need to store the set of points where the user clicks to create the line segments//
    private ArrayList<Point> points=new ArrayList<Point>();

    /*This public class adds the points where the user clicks on the canvas to the list of polyline points. It
    * then displays the change in the state and notifies observers of the change.*/
    public void add(Point p){
        this.points.add(p);
        this.setChanged();
        this.notifyObservers();
    }
    /* This public class returns the array of polyline points*/
    public ArrayList<Point> getPoints(){ return this.points; }


    @Override
    public void execute(GraphicsContext g) {
        //sets the color from PaintCommand which is picked randomly
        g.setStroke(this.getColor());
        // iterating through the list of points
        for(int i=0;i<this.points.size()-1;i++){
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i+1);
            // the strokeLine method from the GraphicsContext g class automatically does the calculations for the line segment
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }

    }
}
