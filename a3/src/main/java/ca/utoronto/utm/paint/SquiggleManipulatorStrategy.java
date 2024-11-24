package ca.utoronto.utm.paint;
import javafx.scene.input.MouseEvent;

class SquiggleManipulatorStrategy extends ShapeManipulatorStrategy {
	SquiggleManipulatorStrategy(PaintModel paintModel) {
		super(paintModel);
	}

	// overrides the mouseDragged and mousePressed methods from the squiggle class
	private SquiggleCommand squiggleCommand;
	@Override
	public void mouseDragged(MouseEvent e) {
		this.squiggleCommand.add(new Point((int)e.getX(), (int)e.getY()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
			this.squiggleCommand = new SquiggleCommand();
			this.addCommand(squiggleCommand);
	}
}
