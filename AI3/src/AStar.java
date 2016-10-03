
public class AStar {
	
	//Variables defining a node as open or closed 
	static int open = 1;
	static int closed = 2;
	
	//Checking if the node X is a solution of the problem
	public static boolean isSolution(Node X){
		if(X.getState().isSolution())
			return true;
		return false;
	}
	
	//Giving parent to the node C and calculating the costs
	public static void attachAndEval(Node C, Node P){
		C.parent = P;
		C.updateG(P.getG() + C.arcCost(P));
		C.calcH();
		C.updateF();
	}
	
	//Updating best parent and corresponding costs to all of P's children C 
	public static void propagatePathImprovements(Node P){
		Node C = P.kids;
		while (C != null){
			if((P.getG() + C.arcCost(P)) < C.getG()){
				C.parent = P;
				C.updateG(P.getG() + C.arcCost(P));
				C.updateF();
				propagatePathImprovements(C);
			}
			C = C.nextSibling;
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
		X.updateG(0);
		X.calcH();
		X.updateF();
		
		//Pushing X to OPEN and setting status to "open"
		OPEN = X;
		X.setStatus(open);
		hashTable[X.getState().getId()] = X;
		
		//Running through the queues to find solution
		while(true)
		{
			//If OPEN is empty, no solution was found, return null
			if(OPEN == null)
				return null;
			
			//Poping node from OPEN
			X = OPEN;
			OPEN = OPEN.nextInQUEUE;
			
			//If X is a solution return the state X
			if(isSolution(X))
				return X;
			
			//Pushing node to CLOSED and setting status to "closed"
			if(CLOSED == null)
				CLOSED = X;
			else
				CLOSED.push(X);
			X.setStatus(closed);
			
			//Generating all successors of X and queue them in SUCCnode
			Node SUCCnode = X.generateAllSuccessors();

			//Run through all of X's successors SUCCnode
			while(SUCCnode != null)
			{	
				//pop SUCCnode out of successor-queue and keep the rest of the queue in next 
				Node next = SUCCnode.nextInQUEUE;
				SUCCnode.nextInQUEUE = null;
				
				//Using hash table to check if successor is in OPEN or CLOSED
				if(hashTable[SUCCnode.getState().getId()] != null)
					SUCCnode = hashTable[SUCCnode.getState().getId()];
				
				//Pushing successor to X's kids
				if(X.kids == null)
					X.kids = SUCCnode;
				else
					X.pushToKids(SUCCnode);

				//If successor is new attach, evaluate and insert in OPEN
				if(SUCCnode.getStatus() != open && SUCCnode.getStatus() != closed){
					attachAndEval(SUCCnode, X);
					if(OPEN == null)
						OPEN = SUCCnode;
					else{
						OPEN.insert(SUCCnode);
						if(OPEN == SUCCnode.nextInQUEUE)
							OPEN = SUCCnode;
					}
					hashTable[SUCCnode.getState().getId()]=SUCCnode;
					SUCCnode.setStatus(open);
				}
				//If successor already existed but X is a better parent, update the parent and costs 
				else if((X.getG() + SUCCnode.arcCost(X)) < SUCCnode.getG()){
					attachAndEval(SUCCnode, X);
					if(SUCCnode.getStatus() == closed)
						propagatePathImprovements(SUCCnode);
				}
				
				//Go to next successor of X
				SUCCnode = next;
			}	
		}
	}
	
	public static void main(String[] args)
	{
		//Getting/setting input/output files
		String inputfile = "boards/boards/board-test.txt";
		String outputfile = "solutions/solution-test.txt";
		
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
