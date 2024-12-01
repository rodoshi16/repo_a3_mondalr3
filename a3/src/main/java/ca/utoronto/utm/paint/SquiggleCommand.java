package ca.utoronto.utm.paint;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class SquiggleCommand extends PaintCommand {
	// ArrayList of points
	private ArrayList<Point> points=new ArrayList<Point>();
	
	public void add(Point p){
		//method to add points and change the set and notify observers
		this.points.add(p); 
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Point> getPoints(){ return this.points; }
	
	
	@Override
	public void execute(GraphicsContext g) {
		// goes through the points and executes them
		ArrayList<Point> points = this.getPoints();
		g.setStroke(this.getColor());
		for(int i=0;i<points.size()-1;i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			g.strokeLine(p1.x, p1.y, p2.x, p2.y);
		}
		
	}

	@Override
	public String getPaintSaveFileString() {
		StringBuilder s = new StringBuilder();
		s.append("Squiggle: ");
		s.append("\tcolor:" + getColor());
		s.append("\tfilled: " + isFill());
		s.append("\tpoints: ");
		s.append("\t\tp1: ");
		s.append("\t\tp2: ");
		s.append("EndSquiggle: ");

		return s.toString();
	}
}
