//because java.util and javax.swing also contain .Timer
//so cannot type java.util.*;
import java.util.Random;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GamePanel extends JPanel implements ActionListener{
	
	//if the value is final it will be all capital
	private static final int SCREEN_WIDTH = 600;	//Screen size
	private static final int SCREEN_HEIGHT = 600;
	private static final int UNIT_SIZE = 25; //object size in the game
	//calculate the screen can fit how many stuff
	private static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
	private static final int DELAY = 75;
	private final int SNAKE_BODY_X[] = new int[GAME_UNITS];	//hold snake body parts
	private final int SNAKE_BODY_Y[] = new int[GAME_UNITS];
	private int bodyparts = 6;	//snake begin length
	private int appleseaten;	//apple has been eaten
	private int appleX,appleY;	//hold apple place
	private char direction = 'R';	//the direction snake head to
	private boolean running = false;	//if the game is running
	private Timer timer;
	private Random random;
	
	//constructor
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_HEIGHT,SCREEN_WIDTH));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());	//use our own class
		startGame();
	}
	
	//private
	private void startGame(){
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	//create a new apple in the frame
	private void newApple(){
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	//draw every object in the frame
	private void draw(Graphics g){
		
		if(running){
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);	//vertical Lines
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);	//horizontal Lines
			}
			g.setColor(Color.red);	//set apple color
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);	//draw a circle to be apple
			
			for(int i=0;i<bodyparts;i++){
				if(i==0){
					g.setColor(Color.green);	//set the color of snake head
					//draw a rectangle to be snake body
					g.fillRect(SNAKE_BODY_X[i],SNAKE_BODY_Y[i],UNIT_SIZE,UNIT_SIZE);
				}
				else{
					g.setColor(new Color(45,180,0));	//set the color of snake body
					g.fillRect(SNAKE_BODY_X[i],SNAKE_BODY_Y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
			gamePoint(g);
		}
		else
			gameOver(g);
	}
	private void move(){
		for(int i=bodyparts;i>0;i--){
			SNAKE_BODY_X[i] = SNAKE_BODY_X[i-1];
			SNAKE_BODY_Y[i] = SNAKE_BODY_Y[i-1];
		}
		
		switch(direction){
			case 'U':
				SNAKE_BODY_Y[0] = SNAKE_BODY_Y[0] - UNIT_SIZE;
				break;
			case 'D':
				SNAKE_BODY_Y[0] = SNAKE_BODY_Y[0] + UNIT_SIZE;
				break;
			case 'L':
				SNAKE_BODY_X[0] = SNAKE_BODY_X[0] - UNIT_SIZE;
				break;
			case 'R':
				SNAKE_BODY_X[0] = SNAKE_BODY_X[0] + UNIT_SIZE;
				break;
		}
	}
	private void checkColloisions(){
		for(int i=bodyparts;i>0;i--){
			if((SNAKE_BODY_X[0] == SNAKE_BODY_X[i]) && (SNAKE_BODY_Y[0] == SNAKE_BODY_Y[i]))
				running = false;
		}
		
		//too many repeat if
		if(SNAKE_BODY_X[0] < 0)
			running = false;
		
		if(SNAKE_BODY_X[0] > SCREEN_WIDTH)
			running = false;
		
		if(SNAKE_BODY_Y[0] < 0)
			running = false;
		
		if(SNAKE_BODY_Y[0] > SCREEN_HEIGHT)
			running = false;
		
		if(!running)
			timer.stop();
	}
	public void checkApple(){
		if((SNAKE_BODY_X[0]==appleX) && (SNAKE_BODY_Y[0] == appleY)){
			bodyparts++;
			appleseaten++;
			newApple();
		}
	}
	private void gameOver(Graphics g){
		gamePoint(g);
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_WIDTH/2);
	}
	private void gamePoint(Graphics g){
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score:" + appleseaten,(SCREEN_WIDTH - metrics.stringWidth("Score:" + appleseaten))/2,(g.getFont().getSize()/2)+10);
	}
	
	//public
	@Override
	public void actionPerformed(ActionEvent e){
		if(running){
			move();
			checkApple();
			checkColloisions();
		}
		repaint();	//reset the paint
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	//inner class
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT:
					if(direction != 'R')
						direction = 'L';
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L')
						direction = 'R';
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D')
						direction = 'U';
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U')
						direction = 'D';
					break;
			}
		}
	}
	
}













