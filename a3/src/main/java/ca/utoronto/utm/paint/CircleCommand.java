package ca.utoronto.utm.paint;
import javafx.scene.canvas.GraphicsContext;

public class CircleCommand extends PaintCommand {
	private Point centre;
	private int radius;
	
	public CircleCommand(Point centre, int radius){
		this.centre = centre;
		this.radius = radius;
	}
	public Point getCentre() { return centre; }
	public void setCentre(Point centre) { 
		this.centre = centre; 
		this.setChanged();
		this.notifyObservers();
	}
	public int getRadius() { return radius; }
	public void setRadius(int radius) { 
		this.radius = radius; 
		this.setChanged();
		this.notifyObservers();
	}
	public void execute(GraphicsContext g){
		int x = this.getCentre().x;
		int y = this.getCentre().y;
		int radius = this.getRadius();
		if(this.isFill()){
			g.setFill(this.getColor());
			g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		} else {
			g.setStroke(this.getColor());
			g.strokeOval(x-radius, y-radius, 2*radius, 2*radius);
		}
	}


	@Override
	public String getPaintSaveFileString() {
		StringBuilder s = new StringBuilder();
		s.append("Circle: ");

		// Convert the color to RGB values (using Color's getRed(), getGreen(), getBlue())
		String color = getColor().toString().replace("0x", ""); // Removes "0x"
		String r = String.valueOf(Integer.parseInt(color.substring(0, 2), 16)); // Red
		String g = String.valueOf(Integer.parseInt(color.substring(2, 4), 16)); // Green
		String b = String.valueOf(Integer.parseInt(color.substring(4, 6), 16)); // Blue

		s.append("\tColor: " + r + "," + g + "," + b);
		s.append("\tFilled: " + isFill());
		s.append("\tCenter: " + getCentre());
		s.append("\tRadius: " + getRadius());
		s.append("EndCircle");

		return s.toString();
	}


}
