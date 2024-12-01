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

    @Override
    public String getPaintSaveFileString() {
        StringBuilder s = new StringBuilder();
        s.append("Polyline\n");

        String c = getColor().toString().replace("0x", "");
        String r = String.valueOf(Integer.parseInt(c.substring(0, 2), 16));
        String g = String.valueOf(Integer.parseInt(c.substring(2, 4), 16));
        String b = String.valueOf(Integer.parseInt(c.substring(4, 6), 16));

        s.append("\tcolor: ").append(r).append(",").append(g).append(",").append(b).append("\n");
        s.append("\tfilled: ").append(isFill()).append("\n");
        s.append("\tpoints\n");
        for (Point p : getPoints()) {
            s.append("\t\tpoint:(").append(p.x).append(",").append(p.y).append(")\n");
        }

        s.append("\tend points\n");
        s.append("End Polyline\n");

        return s.toString();
    }
}


