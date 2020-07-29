import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 28;
	
	private Timer timer;
	private int ballDelay = 6;
	
	private int playerX = 500;
	
	private int ballPositionX = 150;
	private int ballPositionY = 250;
	private int ballXdirection = -1;
	private int ballYdirection = -2;
	
	private LevelGenerator level;
	
	public Game() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(ballDelay, this);
		timer.start();
		level = new LevelGenerator(4, 7);
		
	}
	
	public void paint(Graphics g) {
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 690, 590);
		
		//Borders
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Paddle
		g.setColor(Color.WHITE);
		g.fillRect(playerX, 550, 100, 8);
		
		//Ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballPositionX, ballPositionY, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdirection = 0;
			ballYdirection = 0;
			g.setColor(Color.WHITE);
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("You Won!. Your score is: " + score, 190, 300);
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
		}
				
		
		//Create Level
		level.draw((Graphics2D) g);
		
		//Score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		if(ballPositionY > 570) {
			play = false;
			ballXdirection = 0;
			ballYdirection = 0;
			g.setColor(Color.WHITE);
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Game Over. Your score is: " + score, 190, 300);
			
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdirection = -ballYdirection;
			}
			
			A:	for(int i = 0; i < level.level.length; i++) {
					for(int j = 0; j < level.level[0].length; j++) {
						if(level.level[i][j] > 0) {
							int brickX = j * level.brickWidth + 80;
							int brickY = i * level.brickHeight + 50;
							int brickWidth = level.brickWidth;
							int brickHeight = level.brickHeight;
						
							Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
							Rectangle brickRect = rect;
							
							if(ballRect.intersects(brickRect)) {
								level.setBrickValue(0, i, j);
								totalBricks--;
								score += 5;
							
								if(ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width) {
									ballXdirection = -ballXdirection;
								}
								else {
									ballYdirection = -ballYdirection;
								}
							
								break A;
							}
						}
					}
				}
			
			ballPositionX += ballXdirection;
			ballPositionY += ballYdirection;
			if(ballPositionX < 0) {
				ballXdirection = -ballXdirection;
			}
			if(ballPositionY < 0) {
				ballYdirection = -ballYdirection;
			}
			if(ballPositionX > 670) {
				ballXdirection = -ballXdirection;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 0;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPositionX = 150;
				ballPositionY = 250;
				ballXdirection = -1;
				ballYdirection = -2;
				playerX = 510;
				score = 0;
				totalBricks = 28;
				level = new LevelGenerator(4, 7);
				
				repaint();
			}
		}
		
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	

}
