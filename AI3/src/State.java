
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class State {

	private State1 state1;
	private State2 state2;
	
	public void readState(String textfile){
		if(state1 != null)
			state1.readState(textfile);
		else if(state2 != null)
			state2.readState(textfile);
	}
	
	public boolean moreSuccessorsExist(State current){
		if(state1 != null)
			return state1.moreSuccessorsExist(current);
		else if(state2 != null)
			return state2.moreSuccessorsExist(current);
		else
			return false;
	}
	
	public State generateFirstSuccessor()
	{
		if(state1 != null)
			return state1.generateFirstSuccessor();
		else if(state2 != null)
			return state2.generateFirstSuccessor();
		return null;
	}
	
	public State generateNextSuccessor(State current){
		if(state1 != null)
			return state1.generateNextSuccessor(current);
		else if(state2 != null)
			return state2.generateNextSuccessor(current);
		return null;
	}
	
	public boolean isSolution()
	{
		if(state1 != null)
			return state1.isSolution();
		else if(state2 != null)
			return state2.isSolution();
		return false;
	}
	
	public boolean isEqualTo(State S){
		if(state1 != null)
			return state1.isSolution();
		else if(state2 != null)
			return state2.isSolution();
		return false;
	}
	
	public int calcH(){
		if(state1 != null)
			return state1.calcH();
		else if(state2 != null)
			return state2.calcH();
		return -1;
	}
	
	public int getArcCost(){
		if(state1 != null)
			return state1.getArcCost();
		else if(state2 != null)
			return state2.getArcCost();
		return -1;
	}
/*
	public int getX() {
		if(state1 != null)
			return state1.getX();
		else if(state2 != null)
			return state2.getX();
		return -1;
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
*/
	public int[][] getBoard() {
		if(state1 != null)
			return state1.getBoard();
		else if(state2 != null)
			return state2.getBoard();
		return null;
	}
/*
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
	*/
}
