import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Bricks {
	public int map[][];
	public int brickWidth;
	public int brickHeight;

	public Bricks(int row, int col, int width, int height) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}

		brickWidth = width / col;
		brickHeight = height / row;
	}

	public abstract void draw(Graphics2D x, int height);

	public void brickVal(int row, int col) {
		map[row][col] = map[row][col] - 1;
	}
	
	public abstract int getHeight();
	
	public abstract int getScore(int a);
}