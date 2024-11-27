package ca.utoronto.utm.paint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel; 
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");

	private Pattern pCircleColor= Pattern.compile("");
	private Pattern pCircleFilled = Pattern.compile("");
	private Pattern pCircleCenter= Pattern.compile("");
	private Pattern pCircleRadius = Pattern.compile("");

	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");

	private Pattern pRectangleColor=Pattern.compile("");
	private Pattern pRectangleFilled=Pattern.compile("");
	private Pattern pRectanglePoint1=Pattern.compile("");
	private Pattern pRectanglePoint2=Pattern.compile("");

	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");

	private Pattern pSquiggleColor=Pattern.compile("");
	private Pattern pSquiggleFilled=Pattern.compile("");
	private Pattern pSquigglePoint=Pattern.compile("");
	private Pattern pSquigglePoint1=Pattern.compile("");
	private Pattern pSquigglePoint2=Pattern.compile("");


	private Pattern pPolylineStart=Pattern.compile("^Polyline$");
	private Pattern pPolylineColor=Pattern.compile("");
	private Pattern pPolylineFilled=Pattern.compile("");
	private Pattern pPolylinePoint=Pattern.compile("");
	private Pattern pPolylinePoint1=Pattern.compile("");
	private Pattern pPolylinePoint2=Pattern.compile("");
	private Pattern pPolylineEnd=Pattern.compile("^EndPolyline$");

	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}

	/**
	 * Parse the specified file
	 * @param fileName
	 * @return
	 */
	public boolean parse(String fileName){
		boolean retVal = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			PaintModel pm = new PaintModel();
			retVal = this.parse(br, pm);
		} catch (FileNotFoundException e) {
			error("File Not Found: "+fileName);
		} finally {
			try { br.close(); } catch (Exception e){};
		}
		return retVal;
	}

	/**
	 * Parse the specified inputStream as a Paint Save File Format file.
	 * @param inputStream
	 * @return
	 */
	public boolean parse(BufferedReader inputStream){
		PaintModel pm = new PaintModel();
		return this.parse(inputStream, pm);
	}

	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following commands. As we parse the file, we modify 
		// the appropriate command.
		
		CircleCommand circleCommand = null; 
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
	
		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				this.lineNumber++;
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
					case 0:
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Expected Start of Paint Save File");
						return false;
					case 1: // Looking for the start of a new object or end of the save file
						m=pCircleStart.matcher(l);
						if(m.matches()){
							state=2; 
							break;
						}
						error("Expected Start of Shape or End Paint Save File");
				
					case 2: {
						m= pCircleColor.matcher(l);
						if(m.matches()){
							state=3;
						}
						error("Expected Expected Circle color.");
						break;
					}
					case 3:{
						m= pCircleFilled.matcher(l);
						if(m.matches()){
							state= 4;
						}
						error("Expected Circle Filled.");
						break;
					}
					case 4:{
						m= pCircleCenter.matcher(l);
						if(m.matches()){
							state= 5;
						}
						error("Expected Circle Center.");
						break;

					}
					case 5:{
						m= pCircleRadius.matcher(l);
						if(m.matches()){
							state= 6;
						}
						error("Expected Circle Radius.");
					}
					case 6:{
						m= pCircleEnd.matcher(l);
						if(m.matches()){
							state= 7;
						}
						error("Expected End Circle.");

					}
					case 7: // Looking for the start of a new object or end of the save file
						m=pRectangleStart.matcher(l);
						if(m.matches()){
							state=8;
							break;
						}
						error("Expected Start of Shape or End Paint Save File");

					case 8: {
						m= pRectangleColor.matcher(l);
						if(m.matches()){
							state=9;
						}
						error("Expected Rectangle color.");
						break;
					}
					case 9:{
						m= pRectangleFilled.matcher(l);
						if(m.matches()){
							state= 10;
						}
						error("Expected Rectangle Filled.");
						break;
					}
					case 10:{
						m= pRectanglePoint1.matcher(l);
						if(m.matches()){
							state= 11;
						}
						error("Expected Rectangle p1.");
						break;

					}
					case 11:{
						m= pRectanglePoint2.matcher(l);
						if(m.matches()){
							state= 12;
						}
						error("Expected Rectangle p2.");
					}
					case 12:{
						m= pRectangleEnd.matcher(l);
						if(m.matches()){
							state= 13;
						}
						error("Expected End Rectangle.");

					}
					case 13: // Looking for the start of a new object or end of the save file
						m=pSquiggleStart.matcher(l);
						if(m.matches()){
							state=14;
							break;
						}
						error("Expected Start of Shape or End Paint Save File");

					case 14: {
						m= pSquiggleColor.matcher(l);
						if(m.matches()){
							state=15;
						}
						error("Expected Squiggle color.");
						break;
					}
					case 15:{
						m= pSquiggleFilled.matcher(l);
						if(m.matches()){
							state= 16;
						}
						error("Expected Squiggle Filled.");
						break;
					}
					case 16:{
						m= pSquigglePoint.matcher(l);
						if(m.matches()){
							state= 17;
						}
						error("Expected Squiggle points.");
						break;

					}
					case 17:{
						m= pSquigglePoint1.matcher(l);
						if(m.matches()){
							state= 18;
						}
						error("Expected Squiggle p1.");
					}
					case 18:{
						m= pSquigglePoint2.matcher(l);
						if(m.matches()){
							state= 19;
						}
						error("Expected Squiggle p2.");

					}
					case 19:{
						m= pSquigglePoint2.matcher(l);
						if(m.matches()){
							state= 20;
						}
						error("Expected Squiggle endpoints.");

					}
					case 20: // Looking for the start of a new object or end of the save file
						m=pPolylineStart.matcher(l);
						if(m.matches()){
							state=21;
							break;
						}
						error("Expected Start of Shape or End Paint Save File");

					case 21: {
						m= pPolylineColor.matcher(l);
						if(m.matches()){
							state=22;
						}
						error("Expected Polyline color.");
						break;
					}
					case 22:{
						m= pPolylineFilled.matcher(l);
						if(m.matches()){
							state= 23;
						}
						error("Expected Polyline Filled.");
						break;
					}
					case 23:{
						m= pPolylinePoint.matcher(l);
						if(m.matches()){
							state= 24;
						}
						error("Expected Polyline points.");
						break;

					}
					case 24:{
						m= pPolylinePoint1.matcher(l);
						if(m.matches()){
							state= 26;
						}
						error("Expected Polyline p1.");
					}
					case 25:{
						m= pPolylinePoint2.matcher(l);
						if(m.matches()){
							state= 27;
						}
						error("Expected Polyline p2.");

					}
					case 26:{
						m= pPolylineEnd.matcher(l);
						if(m.matches()){
							state= 28;
						}
						error("Expected Polyline endpoints.");

					}


					// ...
					/**
					 * I have around 20+/-5 cases in my FSM. If you have too many
					 * more or less, you are doing something wrong. Too few, and I bet I can find
					 * a bad file that you will say is good. Too many and you are not capturing the right concepts.
					 *
					 * Here are the errors I catch. All of these should be in your code.
					 *
					 	error("Expected Start of Paint Save File");
						error("Expected Start of Shape or End Paint Save File");
						error("Expected Circle color");
						error("Expected Circle filled");
						error("Expected Circle center");
						error("Expected Circle Radius");
						error("Expected End Circle");
						error("Expected Rectangle color");
						error("Expected Rectangle filled");
						error("Expected Rectangle p1");
						error("Expected Rectangle p2");
						error("Expected End Rectangle");
						error("Expected Squiggle color");
						error("Expected Squiggle filled");
						error("Expected Squiggle points");
						error("Expected Squiggle point or end points");
						error("Expected End Squiggle");
						error("Expected Polyline color");
						error("Expected Polyline filled");
						error("Expected Polyline points");
						error("Expected Polyline point or end points");
						error("Expected End Polyline");
						error("Extra content after End of File");
						error("Unexpected end of file");
					 */
				}
			}
		}  catch (Exception e){
			
		}
		return true;
	}
}
