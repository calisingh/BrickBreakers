import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GrayBricks extends Bricks{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public int startHeight;

	public GrayBricks(int row, int col, int width, int height) {
		super(row, col, width, height);
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}

		brickWidth = width / col;
		brickHeight = height / row;
	}

	public void draw(Graphics2D x, int height) {
		startHeight = height;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (super.map[i][j] > 0) {
					x.setColor(Color.WHITE);
					x.fillRect(j * brickWidth + 80, i * brickHeight + 50 + height, brickWidth, brickHeight);

					x.setStroke(new BasicStroke(3));
					x.setColor(Color.GRAY);
					x.drawRect(j * brickWidth + 80, i * brickHeight + 50 + height, brickWidth, brickHeight);

				}
			}
		}
	}

	
	
	public int getHeight() {
		return startHeight;
	}
	
	public int getScore(int a){
		if(a > 0){
			return -(super.map[0][a]*3) + getScore(a-1);
		} else {
			return -3*super.map[0][a];
		}
	}
}
