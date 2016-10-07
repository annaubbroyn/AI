
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class State {
	public static int PATHFINDER = 1;
	public static int RUSHHOUR = 2;
	public static int MAX = 1000;	//Maximum width and height of board
	
	private int game;
	private int arcCost;			//The cost at the current position
	private int id;					//The ID of the state. ID = y*boardWidth+x;
	private int numOfStates;		//Total number of possible states = boardWidth*boardHeight
	private int boardWidth;			//Width of board
	private int boardHeight;		//Height of board
	private int finalX;				//x-coordinate of goal position
	private int finalY;				//x-coordinate of goal position
	
	//Members if path finder:
	private int x;					//x-coordinate of current position
	private int y;					//y-coordinate of current position
	private int[][] board;			//Board[row][col] containing the cost at each position
	
	//Members if rush hour:
	private int[][] specification;
	private int numberOfCars;
	
	public void readState(String textfile, String game){
		if(game.equals("pathfinder")){
			this.game = PATHFINDER;
			readPathFinderState(textfile);
		}else if(game.equals("rushhour")){
			this.game = RUSHHOUR;
			readRushHourState(textfile);
		}else
			System.out.print("Unknown game\n");
	}
	
	//Initial path finder state read from file
	public void readPathFinderState(String textfile)
	{
	
		Path boardpath = Paths.get(textfile);
		
		try {
			List<String> lines = Files.readAllLines(boardpath);
			String[] splitted;
			int row = 0;
			board = new int[MAX][MAX];
			arcCost = 0;
			for(String line:lines)
			{
				splitted = line.split("(?!^)");	
				for(int col = 0; col < splitted.length; col++)
				{
					if(splitted[col].equals("."))
						board[row][col] = 1;
					else if(splitted[col].equals("#"))
						board[row][col] = -1;
					else if(splitted[col].equals("A")){
						board[row][col] = -2;
						x = col;
						y = row;
					}else if(splitted[col].equals("B")){
						board[row][col] = -3;
						finalX = col;
						finalY = row;
					}else if(splitted[col].equals("w"))
						board[row][col] = 100;
					else if(splitted[col].equals("m"))
						board[row][col] = 50;
					else if(splitted[col].equals("f"))
						board[row][col] = 10;
					else if(splitted[col].equals("g"))
						board[row][col] = 5;
					else if(splitted[col].equals("r"))
						board[row][col] = 1;
				}
				row++;
			}
			boardHeight = row;
			calcId();
			calcNumOfStates();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Initial rush hour state read from file
	public void readRushHourState(String textfile)
	{
	
		Path boardpath = Paths.get(textfile);
		boardWidth = 6;
		boardHeight = 6;
		
		try {
			
			List<String> lines = Files.readAllLines(boardpath);
			String[] splitted;
			board = new int[MAX][MAX];
			int row = 0;
			arcCost = 0;
			for(String line:lines){
				splitted = line.split("(?!^)");
				for(int col = 0; col < 4; col++)
					specification[row][col] = Integer.parseInt(splitted[col]);
				for(int i = 0; i<specification[row][3]; i++)
					if(specification[row][0] == 0)			
						board[specification[row][2]][specification[row][1] + i] = 1;
					else
						board[specification[row][2] + i][specification[row][1]] = 1;
				row++;
			}
			calcId();
			calcNumOfStates();
			numberOfCars = row;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
	public void calcId(){
		if(game == PATHFINDER)
			id = y*boardWidth + x;
		else if(game == RUSHHOUR){
			id = 0;
			for(int i = 0; i<numberOfCars; i++)
				id += specification[i][2]*6+specification[i][1];
		}
	}
	
	public int getId(){
		return id;
	}
	
	public void calcNumOfStates(){
		if(game == PATHFINDER)
			numOfStates = boardWidth * boardHeight;
		else if(game == RUSHHOUR)
			numOfStates = numberOfCars^6;
	}
	
	public int getNumOfStates(){
		return numOfStates;
	}
	
	public State generateFirstSuccessor(){
		if(game == PATHFINDER)
			return generateFirstSuccessorPathFinder();
		else if(game == RUSHHOUR)
			return generateFirstSuccsessorRushHour();
		else 
			return null;
	}
	
	public State generateFirstSuccessorPathFinder()
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
	
	public State generateFirstSuccsessorRushHour(){
		State first = new State();
		int[][] 
		
		for(int car = 0; car<numberOfCars; car++){
			int vertical = specification[car][0];
			int x = specification[car][1];
			int y = specification[car][2];
			int length = specification[car][3];
			if(vertical == 0 && x > 0 && board[y][x-1] == 0){
				
			}
		}
		
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
