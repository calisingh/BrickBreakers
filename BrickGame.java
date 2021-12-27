import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;

/**
 * Class for the games mechanics and controls
 * The central class for the game
 */

public class BrickGame extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private Random rand = new Random();
	private int ballPosX = rand.nextInt(640) + 40;
	private int ballPosY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private GrayBricks map;
	private RedBricks map2;
	private BlueBricks map3;
	private ArrayList<Bricks> bigMap = new ArrayList<Bricks>();

	/**
	 * 
	 * Constructor method for instance variables
	 * 
	 */

	public BrickGame() {

		map = new GrayBricks(1, 7, 540, 50);
		map2 = new RedBricks(1, 7, 540, 50);
		map3 = new BlueBricks(1, 7, 540, 50);

		bigMap.add(map);
		bigMap.add(map2);
		bigMap.add(map3);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		timer = new Timer(delay, this);
		timer.start();

	}

	/**
	 * 
	 * Graphics for all the aspects of the game
	 * 
	 */

	private Image backgroundImage;

	// Some code to initialize the background image.
	// Here, we use the constructor to load the image. This
	// can vary depending on the use case of the panel.
	/**
	 * Sets the background for the GUI
	 * @param fileName - name of the file to be read
	 * @throws IOException - if there is no file
	 */

	public void setBackground(String fileName) throws IOException {
		backgroundImage = ImageIO.read(new File(fileName));
	}

	/**
	 * Draws the background image
	 * @param g - Graphics object where background is drawn
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}

	/**
	 * Draws the background, ball, platform, starting/winning/losing screens, and bricks
	 * @param x - Graphics object where everything is drawn
	 */
	public void paint(Graphics x) {

		// Backgroud

		x.setColor(Color.GRAY);
		x.fillRect(1, 1, 692, 592);

		// Side walls of the game(3)

		x.setColor(Color.RED);
		x.fillRect(0, 0, 3, 592);
		x.fillRect(0, 0, 692, 3);
		x.fillRect(685, 0, 3, 592);

		// Score Keeper

		x.setColor(Color.BLACK);
		x.setFont(new Font("Comic Sans MC", Font.BOLD, 25));
		x.drawString("Score: " + score, 300, 30);

		// Bricks

		map.draw((Graphics2D) x, 100);
		map2.draw((Graphics2D) x, 50);
		map3.draw((Graphics2D) x, 0);

		// Platform

		x.setColor(Color.PINK);
		x.fillRect(playerX, 550, 100, 8);

		// Ball

		x.setColor(Color.GREEN);
		x.fillOval(ballPosX, ballPosY, 20, 20);

		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			x.setColor(Color.GREEN);
			x.setFont(new Font("serif", Font.BOLD, 30));
			x.drawString("You Won", 260, 300);
			x.setFont(new Font("serif", Font.BOLD, 20));
			x.drawString("Press Space to Restart", 230, 350);
		}

		if (ballPosY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;

			x.setColor(Color.RED);
			x.setFont(new Font("serif", Font.BOLD, 30));
			x.drawString("Game Over Loser", 190, 300);
			x.setFont(new Font("serif", Font.BOLD, 20));
			x.drawString("Press Space to Restart", 230, 350);

		}
		x.dispose();
	}
	
	/**
	 * Adjusts ball direction and window when ball "breaks"(intersects) a brick
	 * Also adjusts score accordingly and starts the game
	 * @param e - ActionEvent object that starts game when space bar is pressed
	 */
	@Override

	public void actionPerformed(ActionEvent e) {

		timer.start();

		if (play) {
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {

				ballYdir = -ballYdir;
				if (ballPosX < playerX || ballPosX >= playerX + 99) {
					ballXdir = -ballXdir;
				}

			}
			A: for (int n = 0; n < bigMap.size(); n++) {
				for (int i = 0; i < bigMap.get(n).map.length; i++) {

					for (int j = 0; j < bigMap.get(n).map[0].length; j++) {

						if (bigMap.get(n).map[i][j] > 0) {

							int brickX = j * bigMap.get(n).brickWidth + 80;
							int brickY = i * bigMap.get(n).brickHeight + 50 + bigMap.get(n).getHeight();
							int brickWidth = bigMap.get(n).brickWidth;
							int brickHeight = bigMap.get(n).brickHeight;

							Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
							Rectangle brickRect = rect;

							if (ballRect.intersects(brickRect)) {

								bigMap.get(n).brickVal(i, j);

								if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {

									ballXdir = -ballXdir;

								} else {

									ballYdir = -ballYdir;
								}
								totalBricks--;

								score = getScore();

								break A;

							}

						}

					}

				}
			}

			ballPosX += ballXdir;

			ballPosY += ballYdir;

			if (ballPosX < 0) {

				ballXdir = -ballXdir;

			}

			if (ballPosY < 0) {

				ballYdir = -ballYdir;

			}

			if (ballPosX > 670) {

				ballXdir = -ballXdir;

			}

		}

		repaint();

	}

	/**
	 * Class for when pressed keys need to be registered as typing
	 * @param e - KeyEvent that indicates when keys are being typed
	 */
	@Override
	
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Class for when a pressed key is released
	 * @param e - KeyEvent that indicates when a key is released
	 */
	@Override

	public void keyReleased(KeyEvent e) {

	}

	/**
	 * Class for when the right, left , or space key is pressed
	 * Platform moves accordingly when a specific key is pressed
	 * @param e - KeyEvent that registers when a key is pressed
	 */
	@Override

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (playerX >= 590) {

				playerX = 590;

			} else {

				moveRight();

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (playerX <= 5) {

				playerX = 5;

			} else {

				moveLeft();

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			if (!play) {

				play = true;
				Random ran = new Random();
				ballPosX = ran.nextInt(580) + 40;

				ballPosY = 350;

				ballXdir = -ran.nextInt(1) - 1;

				ballYdir = -ran.nextInt(1) - 1;

				playerX = 310;

				score = getScore();

				totalBricks = 21;

				map = new GrayBricks(1, 7, 540, 50);
				map2 = new RedBricks(1, 7, 540, 50);
				map3 = new BlueBricks(1, 7, 540, 50);
				
				bigMap.clear();
				bigMap.add(map);
				bigMap.add(map2);
				bigMap.add(map3);
				
				repaint();

			}

		}

	}

	/**
	 * Moves platform to the right
	 */
	public void moveRight() {

		play = true;

		playerX += Math.sqrt(ballXdir * ballXdir + ballYdir * ballYdir) * 10;

	}

	/**
	 * Moves platform to the left
	 */
	public void moveLeft() {

		play = true;

		playerX -= Math.sqrt(ballXdir * ballXdir + ballYdir * ballYdir) * 10;

	}
	
	/**
	 * Returns the score
	 * @return player's score
	 */
	public int getScore(){
		int s = 105;
		for(int i = 0; i < bigMap.size(); i++){
			s += bigMap.get(i).getScore(6);
		}
		return s;
	}
}