

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;


/**
 * 
 * @author Rikard Helgegren
 *
 */
public class Screen extends JPanel{
	private static int size,stBlocks=25;
	private int blockInterval,tick;
	private boolean meAbleToJump,timeIsGoing,meCompressed,meGrowBack;
	private KeyLis listener;
	private Timer timer;
	private Me me;
	private Floor floor;
	private Block[] block = new Block[stBlocks];
	private CrashView crashView;
	private PauseView pauseView;
	private RunView runView;

	//Construktor. Organize layout.
	public Screen(int size){


		this.size=size;
		timer = new Timer(30, new TimeListener());


		//make key listener
		listener = new KeyLis();
		this.setFocusable(true);
		this.addKeyListener(listener);


		// fix the panel
		setLayout(null);
		setPreferredSize(new Dimension((int)(size*1.5),size));
		setBackground(Color.white);

		crashView= new CrashView(size);
		add(crashView);
		crashView.setBounds(450,350,470,100);
		crashView.setVisible(false);


		pauseView= new PauseView();
		add(pauseView);
		pauseView.setBounds(0,0,350,120);
		pauseView.setVisible(false);
		
		runView= new RunView();
		add(runView);
		runView.setBounds(500,0,200,50);
		runView.setVisible(true);


		//start/ choice of start wave
		blockInterval=1;
		letsStart();


	}

	//Sets start values, add figures and some little extra.
	public void letsStart(){
		//Start values
		meCompressed=false;
		meGrowBack=false;
		meAbleToJump=true;
		timeIsGoing=true;
		tick=0;

		// Add figures
		me = new Me(size/2,(int)(size*0.9));
		floor = new Floor(1,(int)(size-size*0.04));
		sendBlockInterval(blockInterval);


		//extra
		crashView.setVisible(false);
		repaint();
		timer.start();

	}

	// Send a bunch off blocks in waves	
	private void sendBlockInterval(int wave){
		// size*0.06 e ett block bode i hojd och i bredd
		// size-size*0.08 is the floor level

		// no 1
		if(wave==1){
			for(int nr=0; nr<12;nr++){
				if(nr%2==0){
					block[nr] = new Block ((int)(size+size/3+(nr*0.06*size)),(int)(size-size*0.1-(nr*0.06*size)));
				}
				else{
					block[nr] = new Block ((int)(size+size/3+(nr*0.06*size)),(int)(size-size*0.1));
				}
			}
			// Too make the rest not be in the way, but they must be initialized.
			for(int nr=12; nr<stBlocks;nr++){
				block[nr] = new Block ((-size),(int)(-size));
			}
		}
		//no 2
		else if(wave==2){
			for(int nr=0; nr<10;nr++){
				block[nr] = new Block ((int)(size+size/2),(int)(size-size*0.08-size*0.06*(nr+1)));
			}
			for(int nr=10, i=0; nr<15;nr++,i++){
				block[nr] = new Block ((int)((size+size*0.7)+size*0.06*(4)),(int)(size-size*0.08-size*0.06*(i)));
			}
			for(int nr=15, i=1; nr<20;nr++,i++){
				block[nr] = new Block ((int)(size+size*0.7+size*0.06*(i+4)),(int)(size-size*0.08-size*0.06*(5)));
			}
			// Too make the rest not be in the way, but they must be initialized.
			for(int nr=20 ,i=6; nr<stBlocks;nr++,i++){
				block[nr] = new Block ((int)(size+size*0.7+size*0.06*(i+4)),(int)(size-size*0.08-size*0.06*(i)));
			}
		}
		// no 3
		else if(wave==3){
			for(int nr=0; nr<12;nr++){
				if(nr%2==0){
					block[nr] = new Block ((int)(size+size/3+(nr*0.06*size)),(int)(size-size*0.1-(nr*0.06*size)));
				}
				else{
					block[nr] = new Block ((int)(size+size/3+(nr*0.06*size)),(int)(size-size*0.1));
				}
			}
			// Too make the rest not be in the way, but they must be initialized.
			for(int nr=12; nr<stBlocks;nr++){
				block[nr] = new Block ((int)(size+size/3+(11*0.06*size)),(int)(size-size*0.1-((nr-1)*0.06*size)));
			}
		}
		
		else{return;}

	}

	// paints all figures
	public void paintComponent(Graphics g) {
		//System.out.println("me.getX(),me.getY(),me.getWidth(),me.getHeight()"+ me.getX()+" : "+me.getY()+" : "+me.getWidth()+" : "+me.getHeight());
		System.out.println("second");
		super.paintComponent(g);
		//me
		g.setColor(Color.green);
		g.fillRect(me.getX(),me.getY(),me.getWidth(),me.getHeight());

		//floor
		g.setColor(Color.black);
		g.fillRect(floor.getX(),floor.getY(),floor.getWidth(),floor.getHeight());

		//block[nr]1
		g.setColor(Color.black);
		for(int nr=0; nr<stBlocks;nr++){
			g.fillRect(block[nr].getX(),block[nr].getY(),block[nr].getWidth(),block[nr].getHeight());
		}
	}

	// The actionListner witch listens for keys
	class KeyLis implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {

                        System.out.println("third");
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP : 
				if(meAbleToJump){
					me.changeSpeedY(-20);
					meAbleToJump=false;
				}
				break;
			case KeyEvent.VK_DOWN: 
				if(!meCompressed && me.getHeight()>20){
					me.changeHeight(-20);
					meCompressed=true;
				}
				break;
			case KeyEvent.VK_R:
				//System.out.println("Jag kom hit");
				letsStart();
				break;
				
			case KeyEvent.VK_SPACE:
				//System.out.println("Jag kom hit");
				letsStart();
				break;
			case KeyEvent.VK_ENTER:
				//System.out.println("Jag kom hit");
				letsStart();
				break;
			case KeyEvent.VK_P:
				if(timeIsGoing){
					timer.stop();
					runView.setVisible(false);
					pauseView.setVisible(true);
					timeIsGoing=false;
					//System.out.println("PAUS!!");
				}
				else if(!timeIsGoing){
					timer.start();
					timeIsGoing=true;
					runView.setVisible(true);
					pauseView.setVisible(false);
					//System.out.println("START!!");
				}
				break;
			default ://System.out.println("Hi")
			    break;
			}

		}
		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN: 
				if(meCompressed){
					if( meAbleToJump){
						me.changeSpeedY(-30);
						meAbleToJump=false;
					}
					meGrowBack=true;
					//me.changeHeight(20);
					meCompressed=false;

				}
				break;
			}


		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			//switch (e.getKeyCode()){
                           
			//}
		}

	}

	// Controlling if "me" is standing on something.
	private boolean OnSomething(){
		if((me.getY()+me.getHeight())>=(int)floor.getY() && me.getSpeedY()>0){
			me.setY(floor.getY()-me.getHeight());
			return true;
		}
		// Checking if "me" is straight above. 
		for(int nr=0; nr<stBlocks;nr++){
			// Checking if ontop and not to low down
			if((me.getY()+me.getHeight())>=(block[nr].getY()-me.getSpeedY()) &&
					me.getY()+me.getHeight()<block[nr].getY()+2 &&//+2 for safty margin
					// Checking if object is right on x axis
					(me.getX()+me.getWidth())>block[nr].getX() &&
					me.getX()<(block[nr].getX()+block[nr].getWidth()) && me.getSpeedY()>0){

				// place me on top of block[nr] instead of in it.
				me.setY(block[nr].getY()-me.getHeight());

				if(meGrowBack){
					me.setY(block[nr].getY()-me.getHeight()-3);
				}
				//System.out.println("im here!!");
				return true;
			}
		}
		//System.out.println("false");
		return false;

	}


	public class PauseView extends JPanel {

		public PauseView(){

			setBackground(Color.yellow);
			//a labels
			
			JLabel text = new JLabel("Down arrow: reduce air resistance, makes you shorter,");
			add(text, BorderLayout.NORTH);
			JLabel text4 = new JLabel(" hold down and releas causing jump");
			add(text4, BorderLayout.NORTH);
			JLabel text1 = new JLabel("  Up arrow: causing jump  ");
			add(text1, BorderLayout.NORTH);
			JLabel text2 = new JLabel("       R or Enetr or space: to restart      ");
			add(text2, BorderLayout.CENTER);
			JLabel text3 = new JLabel("    P: to go again     ");
			add(text3, BorderLayout.SOUTH);
			

		}
	}

	public class RunView extends JPanel{
		
		public RunView(){
			
			setBackground(Color.yellow);
			JLabel nr1 = new JLabel("P: to Pause + Controls");
			add(nr1, BorderLayout.NORTH);
		}
		
	}
	
	
	//A JPanel witch shows when you crash =P
	public class CrashView extends JPanel {

		public CrashView(int size){
			//setMaximumSize(new Dimension(size/4,size/8));
			//System.out.println(size);
			setBackground(Color.RED);

			setLayout(new BorderLayout());
			// a button
			JButton goButt = new JButton("Go Again");
			add(goButt, BorderLayout.CENTER);
			goButt.addActionListener(new ButtListener());
			//goButt.setPreferredSize(new Dimension(size/2,size/8));
			//a label
			JLabel text = new JLabel("YOU CRASHED MY FRIEND. \nhitt \"Go Again\" to go again from the beginning");
			add(text, BorderLayout.NORTH);

		}
		private class ButtListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				blockInterval=1;
				letsStart();

			}

		}
	}

	// Controlling if "me" is crashing into something.
	private boolean Crash(){
		try{
			for(int nr=0; nr<stBlocks;nr++){
				// if my lower part is beneath the block[nr]s upper part 
				if((me.getY()+me.getHeight())>block[nr].getY() &&
						//if my upper part is above the block[nr]s lower part 
						me.getY()<block[nr].getY()+block[nr].getHeight() &&
						//if my right side is touching or is inside the other block[nr]
						(me.getX()+me.getWidth())>=block[nr].getX() &&
						//if my left side is touching or inside the block[nr]
						me.getX()<=(block[nr].getX()+block[nr].getWidth())){
					return true;
				}


			}
		}

		catch(NullPointerException error){
			System.out.println("det blir fel i crash metoden");
		}

		return false;
	}

	// Controls a bunch of things that is related to time as: ordering movement, asks a bunch of methods if they have anything valuable to say  for example: if "me" has crashed.
	private class TimeListener implements ActionListener{

		public void actionPerformed(ActionEvent actionEvent) {
			
			if(me.getSpeedY()<size/54 && me.getY()<(int)(size-size*0.04)){
				if(me.getSpeedY()<15 && !meCompressed){
					me.changeSpeedY(1);
				}
				if(me.getSpeedY()<size/40 && meCompressed){
					me.changeSpeedY(2);
				}
			}
			if((me.getY()+me.getHeight())>(int)(size-size*0.04) && me.getSpeedY()>0){

			}
			// is me standing on something
			if(OnSomething()){
				me.changeSpeedY(-me.getSpeedY());
				meAbleToJump=true;

			}
			else{
				meAbleToJump=false;
			}
			// have me crashed or am i alive
			if(Crash()){
				timer.stop();
				repaint();
				crashView.setVisible(true);
				return;
			}
			// time to move everything
			System.out.println("First");
			for(int nr=0; nr<stBlocks;nr++){
				block[nr].move();
			}
			me.move();
			if(meGrowBack){
				me.changeHeight(2);
				if(me.getHeight()>=(int)(size*0.08)){
					meGrowBack=false;
				}
			}

			//Counts and changes wave off blocks
			tick++;
			if(tick>460){
				tick=0;
				blockInterval++;
				sendBlockInterval(blockInterval);
			}
			repaint();
		}
	}

	//returns the size of the view.
	public static int getViewSize(){
		return size;
	}

}


