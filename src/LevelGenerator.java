import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class LevelGenerator {
	
	public int level[][];
	public int brickWidth;
	public int brickHeight;
	
	public LevelGenerator(int row, int col) {
		level = new int[row][col];
		for(int i = 0; i < level.length; i++) {
			for(int j = 0; j < level[0].length; j++) {
				level[i][j] = 1;
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < level.length; i++) {
			for(int j = 0; j < level[0].length; j++) {
				if(level[i][j] > 0) {
					g.setColor(Color.RED);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.BLACK);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		level[row][col] = value;
	}
	
	

}
