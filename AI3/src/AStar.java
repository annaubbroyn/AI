
public class AStar {	
	
	public static int MAXKIDS = 4;
	
	//Variables defining a node as open or closed 
	public static int ASTAR = 1;
	public static int BFS = 2;
	public static int DIJKSTRA = 3;
	
	static int open = 1;
	static int closed = 2;
	static int METHOD = ASTAR; 
	
	//Checking if the node X is a solution of the problem
	public static boolean isSolution(Node X){
		if(X.getState().isSolution())
			return true;
		return false;
	}
	
	//Giving parent to the node C and calculating the costs
	public static void attachAndEval(Node C, Node P, Node hashTable[]){
		C.parent = P;
		C.updateG(P.getG() + C.arcCost(P), hashTable);
		C.calcH();
		C.updateF();
	}
	
	//Updating best parent and corresponding costs to all of P's children C 
	public static void propagatePathImprovements(Node P, Node hashTable[]){
		Node C = new Node();
		int i = 0;
		while(i<MAXKIDS && P.kids[i]!=-1){
			C = hashTable[P.kids[i++]];
			if((P.getG() + C.arcCost(P)) < C.getG()){
				C.parent = P;
				C.updateG(P.getG() + C.arcCost(P), hashTable);
				C.updateF();
				propagatePathImprovements(C, hashTable);
			}
		}
	}
	
	//The A* algorithm
	public static Node AStar_algorithm(State initialState){
		
		//Queues containing all opened and closed nodes, respectively
		Node OPEN = null;
		Node CLOSED = null;
		
		//Hash table using the ID of a node to find the node
		Node hashTable[] = new Node[initialState.getNumOfStates()];
		
		//Initializing first node,X
		Node X = new Node();
		X.setState(initialState);
		X.updateG(0,hashTable);
		X.calcH();
		X.updateF();
		
		//Pushing X to OPEN and setting status to "open"
		OPEN = X;
		X.setStatus(open);
		hashTable[X.getState().getId()] = X;
		
		Node SUCCnode = new Node();
		Node next = new Node();

		//Running through the queues to find solution
		while(true)
		{
			//If OPEN is empty, no solution was found, return null
			if(OPEN == null)
				return null;
			
			//Popping node from OPEN
			X = OPEN;
			OPEN = OPEN.nextInQUEUE;
			
			//Pushing node to CLOSED and setting status to "closed"
			if(CLOSED == null)
				CLOSED = X;
			else
				CLOSED.push(X);
			X.setStatus(closed);
/*if(count++ == 0){
Node openNode = OPEN;
Node closedNode = CLOSED;
System.out.println("\nX: (" + X.getState().getX() + "," + X.getState().getY() + ")\n");
System.out.print("\nOPEN: \n");
while(openNode != null && openNode.nextInQUEUE != null){
	System.out.print("(" + openNode.getState().getX() + "," + openNode.getState().getY() + ")\n");
	openNode = openNode.nextInQUEUE;
}
System.out.print("\nCLOSED: \n");
while(closedNode != null && closedNode.nextInQUEUE != null){
	System.out.print("(" + closedNode.getState().getX() + "," + closedNode.getState().getY() + ")\n");
	closedNode = closedNode.nextInQUEUE;
}
}*/
			//If X is a solution return the state X
			if(isSolution(X)){
				X.finalOPEN = OPEN;
				X.finalCLOSED = CLOSED;
				return X;
			}
			
System.out.print(X.getState().getId() + ": (" + X.getState().getX() + "," + X.getState().getY() + ")\n");
//System.out.print("numberofkkids: " + X.kids.length + "\n");
			
			//Generating all successors of X and queue them in SUCCnode
			SUCCnode = X.generateAllSuccessors();

			//Run through all of X's successors SUCCnode
			while(SUCCnode != null)
			{	
				//pop SUCCnode out of successor-queue and keep the rest of the queue in next 
				next = SUCCnode.nextInQUEUE;
				SUCCnode.nextInQUEUE = null;
				
				//Using hash table to check if successor is in OPEN or CLOSED
				if(hashTable[SUCCnode.getState().getId()] != null)
					SUCCnode = hashTable[SUCCnode.getState().getId()];
				
				//Pushing successor to X's kids
				X.pushToKids(SUCCnode);

				//If successor is new attach, evaluate and insert in OPEN
				if(SUCCnode.getStatus() != open && SUCCnode.getStatus() != closed){
					attachAndEval(SUCCnode, X, hashTable);
					if(OPEN == null)
						OPEN = SUCCnode;
					else{
						OPEN.insert(SUCCnode, METHOD);
						if(METHOD != BFS && OPEN == SUCCnode.nextInQUEUE) //Means that OPEN has not been updated yet, but should be equal to SUCCnode
							OPEN = SUCCnode;
					}
					hashTable[SUCCnode.getState().getId()]=SUCCnode;
					SUCCnode.setStatus(open);
				}
				//If successor already existed but X is a better parent, update the parent and costs 
				else if((X.getG() + SUCCnode.arcCost(X)) < SUCCnode.getG()){
					attachAndEval(SUCCnode, X, hashTable);
					if(SUCCnode.getStatus() == closed)
						propagatePathImprovements(SUCCnode, hashTable);
				}
				
				//Go to next successor of X
				SUCCnode = next;
			}	
		}
	}
	
	public static void main(String[] args)
	{
		//Getting/setting input/output files
		String inputfile = "boards/boards/board-1-2.txt";
		String outputfile = "solutions/solution-1-2.txt";
		
		//Reading initial state from input file
		State initialState = new State();
		initialState.readState(inputfile);
		
		//Use A* algorithm to find a solution
		Node mySolution = AStar_algorithm(initialState);
		
		//Printing solution if found
		if(mySolution == null)
			System.out.println("Could not find a solution");
		else{
			System.out.println("A solution was found");
			mySolution.print(outputfile);
		}
		
	}
}
