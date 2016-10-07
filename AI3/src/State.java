
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class State {
	
	public static int PATHFINDER = 1;
	public static int RUSHHOUR = 2;
	
	public PathFinderState PFState;
	public RushHourState RHState;
	public int game;
	private int arcCost;			//The cost at the current position
	private int id;					//The ID of the state. ID = y*boardWidth+x;
	private int numOfStates;		//Total number of possible states = boardWidth*boardHeight
	
	//Initial state read from file
	public void readState(String textfile, String game)
	{
		if(game.equals("pathfinder")){
			this.game = PATHFINDER;
			PFState.readState(textfile);
		}else if(game.equals("rushhour")){
			this.game = RUSHHOUR;
			RHState.readState(textfile);
		}else
			System.out.print("The game is unknown \n");
	}
	
	public void calcId(){
		if(game == PATHFINDER)
			id = PFState.calcId();
		else if(game == RUSHHOUR)
			id = RHState.calcId();
	}
	
	public int getId(){
		return id;
	}
	
	public void calcNumOfStates(){
		numOfStates = boardWidth * boardHeight;
	}
	
	public int getNumOfStates(){
		return numOfStates;
	}
	
	public State generateFirstSuccessor()
	{
		State first = new State();
		if(y>0 && board[y-1][x] != -1){
			first.setX(x);
			first.setY(y-1);
			first.setArcCost(board[y-1][x]);
		}else if((x+1)< boardWidth && board[y][x+1] != -1){
			first.setX(x+1);
			first.setY(y);
			first.setArcCost(board[y][x+1]);
		}else if((y+1) < boardHeight && board[y+1][x] != -1){
			first.setX(x);
			first.setY(y+1);
			first.setArcCost(board[y+1][x]);
		}else if(x>0 && board[y][x-1] != -1){
			first.setX(x-1);
			first.setY(y);
			first.setArcCost(board[y][x-1]);
		}else
			return null;
		first.setBoard(board);
		first.setBoardWidth(boardWidth);
		first.setBoardHeight(boardHeight);
		first.setFinalX(finalX);
		first.setFinalY(finalY);
		first.calcId();
		first.calcNumOfStates();
		return first;
	}
	
	public State generateNextSuccessor(State current){
		if(current == null)
			return null;
		State next = new State();
		next.setBoard(board);
		next.setBoardWidth(boardWidth);
		next.setBoardHeight(boardHeight);
		next.setFinalX(finalX);
		next.setFinalY(finalY);
		next.calcNumOfStates();
		if(current.getX() == x && current.getY() == y-1){
			if((x+1)<boardWidth && board[y][x+1] != -1){
				next.setX(x+1);
				next.setY(y);
				next.setArcCost(board[y][x+1]);
				next.calcId();
				return next;
			}else if((y+1)<boardHeight && board[y+1][x] != -1){
				next.setX(x);
				next.setY(y+1);
				next.setArcCost(board[y+1][x]);
				next.calcId();
				return next;
			}else if(x>0 && board[y][x-1] != -1){
				next.setX(x-1);
				next.setY(y);
				next.setArcCost(board[y][x-1]);
				next.calcId();
				return next;
			}else
				return null;
		}else if(current.getX() == x+1 && current.getY() == y){
			if((y+1)<boardHeight && board[y+1][x] != -1){
				next.setX(x);
				next.setY(y+1);
				next.setArcCost(board[y+1][x]);
				next.calcId();
				return next;
			}else if(x>0 && board[y][x-1] != -1){
				next.setX(x-1);
				next.setY(y);
				next.setArcCost(board[y][x-1]);
				next.calcId();
				return next;
			}else
				return null;
		}else if(current.getX() == x && current.getY() == y+1 && x>0 && board[y][x-1] != -1 ){
			next.setX(x-1);
			next.setY(y);
			next.setArcCost(board[y][x-1]);
			next.calcId();
			return next;
		}
		return null;
	}
	
	public boolean isSolution(){
		if(x == finalX && y == finalY)
			return true;
		return false;
	}
	
	public boolean isEqualTo(State S){
		if(this.x == S.getX() && this.y == S.getY())
			return true;
		return false;
	}
	
	public double calcH(){
		return Math.sqrt((finalX-x)*(finalX-x) + (finalY-y)*(finalY-y));
	}
	
	public int getArcCost(){
		return arcCost;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getFinalX() {
		return finalX;
	}

	public void setFinalX(int finalX) {
		this.finalX = finalX;
	}

	public int getFinalY() {
		return finalY;
	}

	public void setFinalY(int finalY) {
		this.finalY = finalY;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public int getBoardHeight(){
		return boardHeight;
	}
	
	public int getBoardWidth(){
		return boardWidth;
	}
	
	public void setBoardHeight(int boardHeight){
		this.boardHeight = boardHeight;
	}
	
	public void setBoardWidth(int boardWidth){
		this.boardWidth = boardWidth;
	}
	
	public void setArcCost(int arcCost){
		this.arcCost = arcCost;
	}
}
