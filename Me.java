import java.awt.Color;
import java.awt.Graphics;


public class Me extends Figure{
	private int x,y,moveY;
	private static int hight=(int)(Screen.getViewSize()*0.08), width=(int)(Screen.getViewSize()*0.03);


	public Me(int x, int y){
		super(x,y,hight,width);


	}
}
