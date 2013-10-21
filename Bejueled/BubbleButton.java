import java.awt.Color;
import java.awt.Point;

import javax.swing.JButton;


public class BubbleButton extends JButton{
	private Bubble bubble;
	/**
	 * @param bubble the bubble to set
	 */
	public void setBubble(Bubble bubble) {
		this.bubble = bubble;
	}

	private Point location;
	private static int counter = 0;
	
	/**
	 * "special use" constructor, in case I am making a pattern or something later... this
	 * constructor takes a color, instead of creating one randomly
	 * @param color
	 * @param top
	 * @param leftmost
	 * @param rightmost
	 * @param bottom
	 */
	public BubbleButton(Color color, boolean top, boolean leftmost, boolean rightmost, boolean bottom, Point l, int difficulty) {
		super(Integer.toString(counter));
		//counter ++;
		bubble = new Bubble(color,top,leftmost,rightmost, bottom, difficulty);
		location = l;
		setBackground(color);
	}
	
	/**
	 * general use constructor, lets the bubble choose its own color
	 * @param top
	 * @param leftmost
	 * @param rightmost
	 * @param bottom
	 */
	public BubbleButton(boolean top, boolean leftmost, boolean rightmost,boolean bottom, Point l, int difficulty, Color [] notThese){
		//super(Integer.toString(counter));
		//counter ++;
		location = l;
		bubble = new Bubble(top,leftmost,rightmost, bottom, difficulty,notThese);
		setBackground(bubble.getColor());
		
	}
	
	public Bubble getBubble(){
		return bubble;
	}
	
	public Point getLocation(){
		return location;
	}
	
}
