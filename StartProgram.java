import java.util.*;
import javax.swing.*;

import java.awt.*;


public class StartProgram extends JFrame{

	public static void main(String[] args){
		// size should be 800 or things can start to get complicated.
		int size=800;
		
		JFrame frame = new JFrame("How Far Can You Go??");
		Screen screen = new Screen(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(screen);
		frame.pack();
		frame.setVisible(true);
	}
}
