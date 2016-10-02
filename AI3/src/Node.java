
import java.util.Arrays;
import java.io.*;

public class Node {
	
	private double g;
	private double h;
	private double f;
	private State state;
	public Node parent;
	public Node kids;
	public Node nextInQUEUE;
	public Node nextSibling;
	
	public Node(){
		g=0;
		h=0;
		f=0;
		state = null;
		parent = null;
		kids = null;
		nextInQUEUE = null;
		nextSibling = null;
	}

	public void push(Node element)
	{
		Node iterator = this;
		while (iterator.nextInQUEUE != null)
			iterator = iterator.nextInQUEUE;
		iterator.nextInQUEUE = element;
		element.nextInQUEUE = null;			
	}
	public void pushToKids(Node newKid)
	{
		Node iterator = kids;
		while (iterator.nextSibling != null)
			iterator = iterator.nextSibling;
		iterator.nextSibling = newKid;
		newKid.nextSibling = null;			
	}
	public void insert(Node element)
	{
		Node iterator = this;
		if(f>element.getF())
			element.nextInQUEUE = this;
		else if(nextInQUEUE == null)
		{
			nextInQUEUE = element;
			element.nextInQUEUE = null;
		}
		else{
			double nextf = nextInQUEUE.getF();
			while(nextf<element.getF() && iterator.nextInQUEUE != null)
			{
				iterator = iterator.nextInQUEUE;
				if(iterator.nextInQUEUE != null)
					nextf = iterator.nextInQUEUE.getF();
			}
			Node temp = iterator.nextInQUEUE;
			iterator.nextInQUEUE = element;
			element.nextInQUEUE = temp;
		}
	}
	public void print(String outputfile){
		try {
			PrintWriter writer = new PrintWriter(outputfile);

			int board[][] = state.getBoard();
			int traceX[] = new int[1000];
			int traceY[] = new int[1000];

			int tracePos = 0;
			int startMarker = -2;
			int finalMarker = -3;
			Node trace = this;
			while(trace.parent != null)
			{
				traceX[tracePos] = trace.getState().getX();
				traceY[tracePos++] = trace.getState().getY();
				trace = trace.parent;
			}
			board[trace.getState().getY()][trace.getState().getX()] = startMarker;
			board[state.getFinalY()][state.getFinalX()] = finalMarker;
			writer.println(Arrays.toString(Arrays.copyOfRange(traceX, 0, tracePos)));
			writer.println(Arrays.toString(Arrays.copyOfRange(traceY, 0, tracePos)));
			for(int row = 0; row<state.getBoardHeight(); row++){
				writer.println(Arrays.toString(Arrays.copyOfRange(board[row], 0, state.getBoardWidth())));
			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
			}
	
	public int arcCost(Node P){
		return state.getArcCost();
	}
	public void calcH(){ 
		h = state.calcH(); 
	}
	public void updateG(double g)
	{
		this.g = g;
		Node kid = kids;
		while(kid != null){
			kid.updateG(g + kid.arcCost(this));
			kid.calcH();
			kid.updateF();
			kid = kid.nextSibling;
		}
		
	}
	public Node generateAllSuccessors(){
		Node SUCC = new Node();
		Node succ = SUCC;
		succ.setState(state.generateFirstSuccessor());
		succ.nextInQUEUE = new Node();
		succ.nextInQUEUE.setState(state.generateNextSuccessor(succ.getState()));
		while(succ.nextInQUEUE.getState() != null)
		{
			succ = succ.nextInQUEUE;
			succ.nextInQUEUE = new Node();
			succ.nextInQUEUE.setState(state.generateNextSuccessor(succ.getState()));
		}
		succ.nextInQUEUE = null;
		return SUCC;
	}
	public void updateF(){
		this.f = this.g + this.h;
	}
	public boolean isEqualTo(Node X){
		if(X.state == null)
			return false;
		return this.state.isEqualTo(X.state);
	}
	public double getG() {
		return g;
	}
	public double getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public double getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}	

}
