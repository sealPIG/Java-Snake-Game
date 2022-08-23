import java.util.*;
import java.io.*;
import javax.swing.*;

class GameFrame extends JFrame{
	
	//constructor
	GameFrame(){
		super("Snake Game");
		this.add(new GamePanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
