import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class BubbleMatrix  extends JFrame{
	private ArrayList<ArrayList<BubbleButton>> matrix;
	private int size;
	private int difficulty;
	
	
	
	public BubbleMatrix(){
		
		difficulty = 5;
		//Create the menu bar.
		JMenuBar menuBar = new JMenuBar();

		//Build the first menu.
		JMenu menu = new JMenu("Game");
		JMenuItem gameMenu = new JMenuItem("New Game",
                KeyEvent.VK_N);
		menu.add(gameMenu);
		menuBar.add(menu);
		
		gameMenu.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				populate(size, difficulty);
				//repaint();
				
			}


			
		});
		
		this.setJMenuBar(menuBar)
;		size = 10;
		matrix = new ArrayList<ArrayList<BubbleButton>>();
		populate(size, difficulty);
		
	
	}
	/**
	 * method used to repaint the matrix
	 */
	private void clear() {
		for(ArrayList<BubbleButton> list : matrix){
			for(BubbleButton button: list){
				this.remove(button);
			}
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * this method fills in the matrix, initializing each bubble and each button
	 */
	public void populate(int size, int difficulty) {
		 boolean top;//topmost bubble
		 boolean leftmost;//...
		 boolean rightmost;//...
		 boolean bottom;
		 
		this.setLayout(new GridLayout(size, size));
		//itterate trhough the row
		for( int i = 0; i< size; i++){
			//determine wether the bubble is at the far side of the matrix
			top = false;
			bottom = false;
			if(i ==0){//top case
				top = true;
			}
			if( i == size -1 ){//bottom case
				bottom = true;
			}
			
			//initialize the array in which the column will be placed
			ArrayList<BubbleButton> array = new ArrayList<BubbleButton>();
			//itterate through the column
			for( int j = 0; j < size; j++){
				//determine top and bottom locations
				leftmost = false;
				rightmost = false;
				if(j==0){
					leftmost = true;
				}
				if( j == size - 1 ){
					rightmost = true;
				}
				
				//create the button, add a listener and add it to the array
				BubbleButton bb = new BubbleButton( top , leftmost , rightmost , bottom , new Point(i,j), difficulty, checkBubbles(j, i,array));
				bb.addActionListener(new BubbleListener());
				this.add(bb);
				array.add(j,bb);
			}
			matrix.add(i,array);
		}
		setTitle("Testing Bubble creation	");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
		repaint();
	}
	
	/**
	 * 
	 * @author Jorge S Figueroa
	 *
	 */
	class BubbleListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Point loc = ((BubbleButton) e.getSource()).getLocation();
			Color color = matrix.get((int)loc.getX()).get((int)loc.getY()).getBubble().getColor();
			markBubbles(loc, color);
			deleteBubbles();
			
			
		}
		
	}
	/**
	 * takes in the location in the array of the bubble, then recursively marks for deletion 
	 * any points that match it's color
	 * @param location
	 */
	public void markBubbles(Point location, Color currentColor){
		int x = (int) location.getX();
		int y = (int) location.getY();
		Bubble thisBubble = matrix.get(x).get(y).getBubble();
		thisBubble.setVisited(true);
		
		
		//now check left
		if(!thisBubble.isLeftmost()){
			if(currentColor == matrix.get(x).get(y -1 ).getBubble().getColor()){
				matrix.get(x).get(y).setBackground(Color.black);
				//matrix.get(x).get(y).getBubble().setColor(Color.black);
				matrix.get(x).get(y).getBubble().setToBeChanged(true);
				
				if(!matrix.get(x).get(y -1 ).getBubble().isVisited()){
					markBubbles(new Point(x ,y - 1 ),currentColor);
				}
			}
		}
		//check the bubble above (if applicable)
		if(!thisBubble.isTop()){
			if(currentColor == matrix.get(x -1).get(y).getBubble().getColor()){
				matrix.get(x).get(y).setBackground(Color.black);
				//matrix.get(x).get(y).getBubble().setColor(Color.black);
				matrix.get(x).get(y).getBubble().setToBeChanged(true);
				
				
				if(!matrix.get(x - 1).get(y).getBubble().isVisited()){
					markBubbles(new Point(x-1,y), currentColor);
				}
			}
		}
		
		//now check below
		if(!thisBubble.isBottom()){
			if(currentColor == matrix.get(x + 1).get(y).getBubble().getColor()){
				matrix.get(x).get(y).setBackground(Color.black);
				//matrix.get(x).get(y).getBubble().setColor(Color.black);
				matrix.get(x).get(y).getBubble().setToBeChanged(true);
				
				if(!matrix.get(x + 1).get(y).getBubble().isVisited()){
					markBubbles(new Point(x + 1,y),currentColor);
				}
			}
		}

		
		//now check right
		if(!thisBubble.isRightmost()){
			if(thisBubble.getColor() == matrix.get(x).get(y + 1).getBubble().getColor()){
				matrix.get(x).get(y).setBackground(Color.black);
				//matrix.get(x).get(y).getBubble().setColor(Color.black);
				matrix.get(x).get(y).getBubble().setToBeChanged(true);
				
				if(!matrix.get(x).get(y +1 ).getBubble().isVisited()){
					markBubbles(new Point(x ,y +  1),currentColor);
				}
			}
		}
		
		//deleteBubble(x,y);
	}
	
	
	/**
	 * changes any bubbles "to be changed"
	 * start from bottom row, and work up
	 */
	public void deleteBubbles() {
		int number = 0;
		//itterate, starting from the bottom
		for(int i = 0 ; i < size; i ++){
			for(int j = 0; j < size; j ++){
				BubbleButton button = matrix.get(i).get(j);
				if(button.getBackground() == Color.black){
					button.getBubble().setColor(Color.black);
					button.setBackground(Color.gray);
					
					
					rowswap(i , j);
				}
			}
		}
	}
	
	/**
	 * helper method used by deleteBubbles to start a chain reaction upwards
	 * @param i
	 * @param j
	 */
	private void rowswap(int i, int j) {
		BubbleButton button = matrix.get(i).get(j);
		int bottom = i;
		//ensure we do not get out of bounds
		if(i > 0 && ! button.getBubble().isTop()){
			BubbleButton above = matrix.get(i - 1).get(j);
		
			//find the first bubble that is not above the top bubble whose background color
			//is not black
			while(above.getBackground() == Color.black && !above.getBubble().isTop()){
				i--;
				above = matrix.get(i - 1).get(j); 
			}
			
			//check that we are not at the top
			if(above.getBackground() != Color.black){
				//above.setBackground(Color.GREEN);
				chainreaction(bottom,i-1, j);
			}
			else if(above.getBubble().isTop()) //above is the top bubble
			{//establish the new top
				//matrix.get(i).get(j).getBubble().setTop(true);
			}
		}
		else//enforce bounds
		{
			matrix.get(i).get(j).getBubble().setTop(true);
		}
		
	}
	
	
	/**
	 * this starts the "gravity" chain reaction, moving bubbles from "up" to "bottom" and
	 * incrementing both, until a bubble is found that .isTop()
	 * @param bottom
	 * @param up
	 * @param column
	 */
	public void chainreaction( int bottom, int up, int column ){
		
		BubbleButton currentBottom = matrix.get(bottom).get(column);
		BubbleButton currentUp = matrix.get(up).get(column);
		
		while(!currentUp.getBubble().isTop()){
			//currentUp.setBackground(Color.green);
			
			//replace the bottom with the top
			currentBottom.setBackground(currentUp.getBackground());
			Bubble tempBubble = new Bubble( currentBottom.getBubble());
			currentBottom.setBubble(new Bubble(currentUp.getBubble()));
			//set any boundaries by getting the logical or of the top bubble and the old bottom bubble
			currentBottom.getBubble().setTop(tempBubble.isTop() ||currentBottom.getBubble().isTop());
			currentBottom.getBubble().setBottom(tempBubble.isBottom() ||currentBottom.getBubble().isBottom());
			currentBottom.getBubble().setLeftmost(tempBubble.isLeftmost() ||currentBottom.getBubble().isLeftmost());
			currentBottom.getBubble().setRightmost(tempBubble.isRightmost() ||currentBottom.getBubble().isRightmost());
			
			
			up--;
			bottom--;
			currentUp = matrix.get(up).get(column);
			currentBottom = matrix.get(bottom).get(column);
		}
		
		//Top Case, similar, but must set the currentUp's background and the bubbleBackgorund to colors not used by the program
		//so they are not accidentaly used by the MarkBubbles method later
		currentBottom.setBackground(currentUp.getBackground());
		Bubble tempBubble = new Bubble( currentBottom.getBubble());
		currentBottom.setBubble(new Bubble(currentUp.getBubble()));
		//set any boundaries by getting the logical or of the top bubble and the old bottom bubble
		currentBottom.getBubble().setTop(tempBubble.isTop() ||currentBottom.getBubble().isTop());
		currentBottom.getBubble().setBottom(tempBubble.isBottom() ||currentBottom.getBubble().isBottom());
		currentBottom.getBubble().setLeftmost(tempBubble.isLeftmost() ||currentBottom.getBubble().isLeftmost());
		currentBottom.getBubble().setRightmost(tempBubble.isRightmost() ||currentBottom.getBubble().isRightmost());
		
		currentUp.setBackground(Color.gray);
		currentUp.getBubble().setColor(Color.black);

		
	}
	
	/**
	* This method goes through and checks to see which bubbles
	* cannot be in a certain location.
	* @param The location in the ArrayList of the place we are checking
	* @return Returns an Color[] depicting what cannot exist there
	* The first element of the array pertains to Leftmost, the second is Topmost
	*/
	public Color[] checkBubbles(int x, int y, ArrayList<BubbleButton> xs) {
		Vector<Color> exclude = new Vector<Color>();
		Bubble temp1;
		Bubble temp2;
	
	
	//Checks to see the constraint to the left
	// If x is < 1 it can't violate any constraint
		if(x > 1) {
			temp1 = xs.get(x-1).getBubble();
			temp2 = xs.get(x-2).getBubble();
			if(temp1.getColor().equals(temp2.getColor())) {
				exclude.add( temp1.getColor());
			}
		}
		
		
		//Checks to see the constraints above
		if(y > 1) {
			temp1 = matrix.get(y-1).get(x).getBubble();
			temp2 = matrix.get(y-2).get(x).getBubble();
			if(temp1.getColor().equals(temp2.getColor())) {
				exclude.add( temp1.getColor());
			}
		}
	
		
		return  exclude.toArray( new Color[exclude.size()]);
	}

	/**
	 * 
	 * @return
	 */
	private int chooseDifficulty(){
		return difficulty;
		
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		JFrame frame = new BubbleMatrix();

	}
}
