package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PolylineCommand extends PaintCommand{
    // need to store the points where the user clicks to extend a line segment
    private ArrayList<Point> points=new ArrayList<Point>();

    public void add(Point p){
        this.points.add(p);
        this.setChanged();
        this.notifyObservers();
    }
    public ArrayList<Point> getPoints(){ return this.points; }


    @Override
    public void execute(GraphicsContext g) {
        //sets the color from PaintCommand which is picked randomly
        g.setStroke(this.getColor());
        // iterating through the list of points
        for(int i=0;i<this.points.size()-1;i++){
            Point p1 = this.points.get(i);
            Point p2 = this.points.get(i+1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }

    }
}
