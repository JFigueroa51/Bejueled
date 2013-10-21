import java.awt.Color;


public class Bubble {
	private Color color;//color of the bubble
	private boolean visited;//to be used for recursion
	private boolean toBeChanged;//marked for deletion
	private boolean top;//topmost bubble
	private boolean leftmost;//...
	private boolean rightmost;//...
	private boolean bottom;
	
	
	/**
	 * @param color
	 * @param top
	 * @param leftmost
	 * @param rightmost
	 * @param notThese 
	 */
	public Bubble(Color color, boolean top, boolean leftmost, boolean rightmost, boolean bottom, int difficulty) {
		this.color =  color;
		this.top = top;
		this.leftmost = leftmost;
		this.rightmost = rightmost;
		this.bottom = bottom;
	}
	/**\
	 * lazy constructor (no color specified)
	 * @param  
	 * @param top
	 * @param leftmost
	 * @param rightmost
	 * @param notThese 
	 */
	public Bubble( boolean top, boolean leftmost, boolean rightmost, boolean bottom, int difficulty, Color[] notThese){
		this(generateRandomColor(difficulty, notThese),top,leftmost,rightmost,bottom, difficulty );
		
	}
	/**
	 * copy constructor
	 * @param bubble
	 */
	public Bubble(Bubble bubble) {
		this.color = bubble.color;
		this.top = bubble.top;
		this.leftmost = bubble.leftmost;
		this.rightmost = bubble.rightmost;
		this.bottom = bubble.bottom;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * @param visited the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return the toBeChanged
	 */
	public boolean isToBeChanged() {
		return toBeChanged;
	}

	/**
	 * @param toBeChanged the toBeChanged to set
	 */
	public void setToBeChanged(boolean toBeChanged) {
		this.toBeChanged = toBeChanged;
	}

	/**
	 * @return the top
	 */
	public boolean isTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(boolean top) {
		this.top = top;
	}

	/**
	 * @return the leftmost
	 */
	public boolean isLeftmost() {
		return leftmost;
	}

	/**
	 * @param leftmost the leftmost to set
	 */
	public void setLeftmost(boolean leftmost) {
		this.leftmost = leftmost;
	}

	/**
	 * @return the rightmost
	 */
	public boolean isRightmost() {
		return rightmost;
	}

	/**
	 * @param rightmost the rightmost to set
	 */
	public void setRightmost(boolean rightmost) {
		this.rightmost = rightmost;
	}


	/**
	 * used when no color is assigned
	 * @param notThese 
	 * @return
	 */
	private static Color generateRandomColor(int difficulty, Color[] notThese){
		int colint = 0;
		int length = notThese.length;
		//if there are numbers to exclude, exclude them;
		if( length > 0 && length < 3){
			
			//find the one or two numbers to be excluded 
			//NOTE
			//this method only works for arrays of length 1 or 2!!!
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for(int i = 0; i < notThese.length; i++){
				int temp = colorToInt(notThese[i]);
				if(temp < min) min = temp;
				if(temp > max) max = temp;
			}
			int newLength = difficulty;
			if( min != max) newLength += -1;
			int[] partitions = new int [newLength];
			for(int i  = 0, j = 0; i <= difficulty ; i ++){
				if(j == newLength){
					System.out.println("oops!");
				}
				if((i != min) && (i != max)){
					partitions[j] = i;
					j++;
				}
			}
			colint = partitions[ (int) (Math.random()*newLength)];
		//otherwise, all numbers are valid
		}else if(length > 2){
			System.out.println("SOMETHING WENT WRONG!!!! Excluding more than two colors! how could this happen???");
		}
		else{
			colint = (int) (Math.random() * difficulty);
		}

		
		return intToColor(colint);
	}
	/**
	 * @return the bottom
	 */
	public boolean isBottom() {
		return bottom;
	}
	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
	
	/**
	 * used by generate random color to go back to color once an integer has been chosen
	 * @param colint
	 * @return
	 */
	public static Color intToColor (int colint){
		
		Color color;
		
		switch (colint){
		case 0:
			color = Color.BLUE;
			break;
		case 1:
			color = Color.RED;
			break;
		case 2:
			color = Color.YELLOW;
			break;
		case 3:
			color = Color.GREEN;
			break;
		case 4:
			color = Color.magenta;
			break;
			
		case 5:
			color = Color.CYAN;
			break;
		default:
			color = Color.GRAY;
		
		}
		
		return color;
		
	}
	
	/**
	 * used by generate random color to exclude certain colors
	 * @param color
	 * @return
	 */
	public static int colorToInt (Color color){
		int colint = 0;
		
		if(color.equals(Color.BLUE)){
			colint = 0;
		}else if(color.equals(Color.RED)){
			colint = 1;
		}else if (color.equals(Color.YELLOW)){
			colint =2;
		}else if (color.equals(Color.GREEN)){
			colint = 3;
		}else if (color.equals(Color.magenta)){
			colint = 4;
		}else if (color.equals(Color.GRAY)){
			colint = 5;
		}
		
		return colint;
	}
}
