
public class AStar {
	static int open = 1;
	static int closed = 2;
	
	public static boolean isSolution(Node X)
	{
		if(X.getState().isSolution())
			return true;
		return false;
	}
	
	public static void attachAndEval(Node C, Node P)
	{
		C.parent = P;
		C.updateG(P.getG() + C.arcCost(P));
		C.calcH();
		C.updateF();
	}
	
	public static void propagatePathImprovements(Node P)
	{
		Node C = P.kids;
		while (C != null)
		{
			if((P.getG() + C.arcCost(P)) < C.getG())
			{
				C.parent = P;
				C.updateG(P.getG() + C.arcCost(P));
				C.updateF();
				propagatePathImprovements(C);
			}
			C = C.nextSibling;
		}
	}
	
	public static Node AStar_algorithm(State initialState)
	{
		Node CLOSED = null;
		Node OPEN = null;
		Node hashTable[] = new Node[initialState.getBoardWidth()*initialState.getBoardHeight()];
		
		Node X = new Node();
		X.setState(initialState);
		X.updateG(0);
		X.calcH();
		X.updateF();
		OPEN = X;
		X.setStatus(open);
		hashTable[X.getState().getId()] = X;
		
		while(true)
		{
			if(OPEN == null)
				return null;
			
			X = OPEN;
			OPEN = OPEN.nextInQUEUE;
			
			if(CLOSED == null)
				CLOSED = X;
			else
				CLOSED.push(X);
			X.setStatus(closed);
			
			if(isSolution(X))
				return X;
			
			Node SUCCnode = X.generateAllSuccessors();

			while(SUCCnode != null)
			{	
				Node next = SUCCnode.nextInQUEUE;
				SUCCnode.nextInQUEUE = null;
				
				boolean found_in_OPEN = false;
				boolean found_in_CLOSED = false;
				
				if(hashTable[SUCCnode.getState().getId()] != null)
					SUCCnode = hashTable[SUCCnode.getState().getId()];

				if(SUCCnode.getStatus() == open)
					found_in_OPEN = true;
				else if(SUCCnode.getStatus() == closed)
					found_in_CLOSED = true;
				
				if(X.kids == null)
					X.kids = SUCCnode;
				else
					X.pushToKids(SUCCnode);

				if(!found_in_OPEN && !found_in_CLOSED){
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
				else if((X.getG() + SUCCnode.arcCost(X)) < SUCCnode.getG()){
					attachAndEval(SUCCnode, X);
					if(found_in_CLOSED)
						propagatePathImprovements(SUCCnode);
				}

				SUCCnode = next;
			}	
		}
	}
	
	public static void main(String[] args)
	{
		String inputfile = "boards/boards/board-test.txt";
		String outputfile = "solutions/solution-test.txt";
		State initialState = new State();
		initialState.readState(inputfile);
		Node mySolution = AStar_algorithm(initialState);
		if(mySolution == null)
			System.out.println("Could not find a solution");
		else{
			System.out.println("A solution was found");
			mySolution.print(outputfile);
		}
	}
	
}
