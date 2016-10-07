
import java.util.Arrays;
import java.io.*;

public class Node {
	
	public static int MAXKIDS = 4;
	public static int MAX = 1000;	//Maximum width and height of board
	public static int ASTAR = 1;
	public static int BFS = 2;
	public static int DIJKSTRA = 3;
	
	private double g;			//Cost of getting from root node to this node
	private double h;			//Estimated cost of getting from this node to goal node
	private double f;			//Estimated total cost with this node included in path
	private State state;		//A class describing the state of this node (position and so on)
	private int status;			//Status which is either open or closed (or none of them)
	public Node parent;			//Best parent (previous node)
	//public Node kids;			//Kids (possible next nodes)
	public Node nextInQUEUE;	//Next node to evaluate (either next in OPEN, CLOSED or successor-queue)
	public Node nextSibling;	//Next sibling to evaluate
	public Node finalOPEN;
	public Node finalCLOSED;
	public int kids[];
	
	//Default constructor
	public Node(){
		g=0;
		h=0;
		f=0;
		state = null;
		parent = null;
		kids = new int[MAXKIDS];
		for(int i=0; i<MAXKIDS; i++)
			kids[i] = -1;
		nextInQUEUE = null;
		nextSibling = null;
		status = 0;
	}

	//Pushes newNode to this queue of nodes - SHOULD MAKE MORE EFFICIENT
	public void push(Node newNode){
		Node iterator = this;
		//While iterator is not the last node in the queue, to to next node in queue
		while (iterator.nextInQUEUE != null)
			iterator = iterator.nextInQUEUE;
		//Inserting the new node at the end of the queue
		iterator.nextInQUEUE = newNode;
		//Letting the new node now be the last one in the queue (i.e. pointing at null)
		newNode.nextInQUEUE = null;			
	}
	
	//Same function as push, only using siblings as queue
/*	public void pushToKids(Node newKid){
		Node iterator = kids;
		while (iterator.nextSibling != null){
			iterator = iterator.nextSibling;
			if(iterator == this)
				return;
		}
		iterator.nextSibling = newKid;
		newKid.nextSibling = null;			
	}
	*/
	
	public void pushToKids(Node newKid){
		int i = 0;
		if(newKid == parent)
			return;
		while(i<MAXKIDS && kids[i] == -1){
			if(kids[i] == newKid.getState().getId())
				return;
			i++;
		}
		kids[i-1] = newKid.getState().getId();
	}
	
	//Inserting new node in this queue which is sorted by ascending f-value 
	public void insert(Node newNode, int method){
		if(method == BFS){
			this.push(newNode);
		}else{
			Node iterator = this;
			//If the new node has the lowest f value, place it in the beginning of this queue
			if((method == ASTAR && f > newNode.getF()) || (method == DIJKSTRA && g > newNode.getG()))
				newNode.nextInQUEUE = this;
			//Else if this queue only contain one node, place the new node at the end (second) of this queue 
			else if(nextInQUEUE == null){
				nextInQUEUE = newNode;
				newNode.nextInQUEUE = null;
			}
			//Else go through the queue and checking if the next node in the queue has higher f value than new node
			else{
				double nextf = nextInQUEUE.getF();
				double nextg = nextInQUEUE.getG();
				while(((method == ASTAR && nextf<newNode.getF()) || (method == DIJKSTRA && nextg < newNode.getG())) && iterator.nextInQUEUE != null){
					iterator = iterator.nextInQUEUE;
					if(iterator.nextInQUEUE != null){
						nextf = iterator.nextInQUEUE.getF();
						nextg = iterator.nextInQUEUE.getG();
					}
				}
				Node temp = iterator.nextInQUEUE;
				iterator.nextInQUEUE = newNode;
				newNode.nextInQUEUE = temp;
			}
		}
		
	}
	
	//Printing board and path to output file
	public void print(String outputfile){
		try {
			PrintWriter writer = new PrintWriter(outputfile);

			int board[][] = state.getBoard();
			int pathX[] = new int[MAX]; //x-position of path
			int pathY[] = new int[MAX]; //y-position of path
			int openX[] = new int[MAX];
			int openY[] = new int[MAX];
			int closedX[] = new int[MAX];
			int closedY[] = new int[MAX];
			
			//Getting all opened states
			Node openNode = this.finalOPEN;
			int openIndex = 0;
			while(openNode != null && openNode.nextInQUEUE != null){
				openX[openIndex] = openNode.getState().getX();
				openY[openIndex++] = openNode.getState().getY();
				openNode = openNode.nextInQUEUE;
			}
			
			//Getting all closed states
			Node closedNode = this.finalCLOSED;
			int closedIndex = 0;
			while(closedNode.nextInQUEUE != null){
				closedX[closedIndex] = closedNode.getState().getX();
				closedY[closedIndex++] = closedNode.getState().getY();
				closedNode = closedNode.nextInQUEUE;
			}
 			
			//Getting path
			Node path = this;
			int pathIndex = 0;
			while(path.parent != null){
				pathX[pathIndex] = path.getState().getX();
				pathY[pathIndex++] = path.getState().getY();
				path = path.parent;
			}
			
			//Printing x- and y-coordinates of path, opened nodes and closed nodes
			writer.println(Arrays.toString(Arrays.copyOfRange(pathX, 0, pathIndex)));
			writer.println(Arrays.toString(Arrays.copyOfRange(pathY, 0, pathIndex)));
			writer.println(Arrays.toString(Arrays.copyOfRange(openX, 0, openIndex)));
			writer.println(Arrays.toString(Arrays.copyOfRange(openY, 0, openIndex)));
			writer.println(Arrays.toString(Arrays.copyOfRange(closedX, 0, closedIndex)));
			writer.println(Arrays.toString(Arrays.copyOfRange(closedY, 0, closedIndex)));
			
			//Printing board to file
			for(int row = 0; row<state.getBoardHeight(); row++){
				writer.println(Arrays.toString(Arrays.copyOfRange(board[row], 0, state.getBoardWidth())));
			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
			}
	
	//Calculating cost from moving from parent node, P, to this node
	public int arcCost(Node P){
		return state.getArcCost();
	}
	
	//Calculating estimated cost from this node to final node
	public void calcH(){ 
		h = state.calcH(); 
	}
	
	//Updating the cost from root node to this node, and also updating all costs for all children
	public void updateG(double g, Node hashTable[]){
		this.g = g;
		int i = 0;
		Node kid = new Node();
		while(i<MAXKIDS && kids[i]!=-1){
			kid = hashTable[kids[i++]];
			kid.updateG(g + kid.arcCost(this), hashTable);
			kid.calcH();
			kid.updateF();
		}
		
	}
	
	//Generating all successors of this node
	public Node generateAllSuccessors(){
		//SUCC is the queue with all successors
		Node SUCC = new Node();
		
		//Generating first successor, succ, and placing it in the SUCC queue
		Node succ = SUCC;
		succ.setState(state.generateFirstSuccessor());
		
		//Generating second successor, succ, and placing it in the SUCC queue
		succ.nextInQUEUE = new Node();
		succ.nextInQUEUE.setState(state.generateNextSuccessor(succ.getState()));
		
		//Generating the rest of the successor, succ, and placing it in the SUCC queue
		while(succ.nextInQUEUE.getState() != null)
		{
			succ = succ.nextInQUEUE;
			succ.nextInQUEUE = new Node();
			succ.nextInQUEUE.setState(state.generateNextSuccessor(succ.getState()));
		}
		succ.nextInQUEUE = null;
		
		//Returning the SUCC queue
		return SUCC;
	}
	
	//Updating the f value for this node, s (f(s) = g(s) + h(s))
	public void updateF(){
		this.f = this.g + this.h;
	}
	
	//Setters and getters
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	

}
