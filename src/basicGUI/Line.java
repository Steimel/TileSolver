package basicGUI;

public class Line {
	private int startX, endX, startY, endY;
	
	public Line(int x1, int y1, int x2, int y2){
		startX = x1;
		startY = y1;
		endX = x2;
		endY = y2;
	}

	public int getStartX() {
		return startX;
	}	

	public int getEndX() {
		return endX;
	}	

	public int getStartY() {
		return startY;
	}	

	public int getEndY() {
		return endY;
	}

	public String toString(){
		return startX + ", " + startY + ", " + endX + ", " + endY;
	}
	
	public boolean equals(Object o){
		if (o instanceof Line){
			Line thisLine = (Line)o;
			return thisLine.getStartX() == startX && thisLine.getStartY() == startY && thisLine.getEndX() == endX && thisLine.getEndY() == endY;
		}
		return false;
			
		
	}
	
	
	
}
