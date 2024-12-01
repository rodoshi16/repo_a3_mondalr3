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
		s.append("Squiggle\n");

		// Convert the color from hexadecimal (0xRRGGBBAA) to RGB format
		String color = getColor().toString().replace("0x", "");
		String r = String.valueOf(Integer.parseInt(color.substring(0, 2), 16));
		String g = String.valueOf(Integer.parseInt(color.substring(2, 4), 16));
		String b = String.valueOf(Integer.parseInt(color.substring(4, 6), 16));

		s.append("\tcolor: ").append(r).append(",").append(g).append(",").append(b).append("\n");
		s.append("\tfilled: ").append(isFill()).append("\n");
		s.append("\tpoints\n");

		// Iterate through points and format them for output
		for (Point p : getPoints()) {
			s.append("\t\tpoint:(").append(p.x).append(",").append(p.y).append(")\n");
		}

		s.append("\tend points\n");
		s.append("EndSquiggle\n");

		return s.toString();
	}
}

