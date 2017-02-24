/**
 * 
 * @author R2
 * Planty of figures need this
 *
 */
public class Figure {
	private int dy,dx,x,y,Height,width;
	
	public Figure(int x, int y, int Height, int width,int dx, int dy){
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
		this.Height=Height;
		this.width=width;
	}
	
	
	
	
	public Figure(int x, int y, int Height, int width){
		this.x=x;
		this.y=y;
		this.Height=Height;
		this.width=width;
	}
	public Figure(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	
	
	public int getSpeedY(){
		return dy;
	}
	public int getSpeedX(){
		return dx;
	}
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	public void setY(int value){
		y=value;
	}
	public void setX(int value){
		x=value;
	}
	public int getHeight(){
		return Height;
	}
	public int getWidth(){
		return width;
	}
	public void changeHeight(int change){
		Height=Height+change;
	}
	public void changeWidth(int change){
		width=width+change;
	}
	public void changeSpeedY(int change){
		 dy=dy+change;
	}
	public void changeSpeedX(int change){
		dx=dx+change;
	}
	
	public void move(){
		x=x+dx;
		y=y+dy;
	}
	
	
}
