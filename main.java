import javax.swing.JFrame;

/**
 * 
 * Driver for the BrickBreaker game
 * creates a new window(JFrame) and a new BrickGame object
 * Sets all the GUI parameters 
 * @param args - driver parameter
 *
 */
public class main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		BrickGame brickGame = new BrickGame();
		obj.setBounds(10, 10, 694, 600);
		obj.setTitle("The Hoonigans");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(brickGame);
		obj.setVisible(true);
	}
}