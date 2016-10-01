
//import java.util.*;

public class AStar {
	
	public static Node pop(Node QUEUE)
	{
		Node out = QUEUE;
		QUEUE = QUEUE.nextInQUEUE;
		out.nextInQUEUE = null;
		return out;	
	}
	
	public static void push(Node QUEUE, Node element)
	{
		if(QUEUE == null)
			QUEUE = element;
		else{
			Node iterator = QUEUE;
			while (iterator.nextInQUEUE != null)
				iterator = iterator.nextInQUEUE;
			iterator.nextInQUEUE = element;
			element.nextInQUEUE = null;			
		}
	}
	
	public static void pushToKids(Node kids, Node newKid)
	{
		if(kids == null)
			kids = newKid;
		else{
			Node iterator = kids;
			while (iterator.nextSibling != null)
				iterator = iterator.nextSibling;
			iterator.nextSibling = newKid;
			newKid.nextSibling = null;			
		}
	}
	
	public static void insert(Node QUEUE, Node element)
	{
		Node iterator = QUEUE;
		if(QUEUE == null)
			QUEUE = element;
		else if(iterator.getF()>element.getF())
		{
			element.nextInQUEUE = iterator;
			QUEUE = element;
		}
		else if(iterator.nextInQUEUE == null)
			iterator.nextInQUEUE = element;
		else{
			int f = iterator.nextInQUEUE.getF();
			while(f>element.getF() && iterator.nextInQUEUE != null)
			{
				iterator = iterator.nextInQUEUE;
				if(iterator.nextInQUEUE != null)
					f = iterator.nextInQUEUE.getF();
			}
			Node temp = iterator.nextInQUEUE;
			iterator.nextInQUEUE = element;
			element.nextInQUEUE = temp;
		}
	}
	
	public static boolean isSolution(Node X)
	{
		if(X.getState().isSolution())
			return true;
		return false;
	}
	
	public static Node generateAllSuccessors(Node X){
		return X.generateNextSuccessor();
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
		
		Node n0 = new Node();
		n0.setState(initialState);
		n0.updateG(0);
		n0.calcH();
		n0.updateF();
		OPEN = n0;
		
		while(true)
		{
			if(OPEN == null)
				return null;
			Node X = pop(OPEN);
			push(CLOSED, X);
			if(isSolution(X))
				return X;
			
			System.out.print("(x,y): (" + X.getState().getX() + "," + X.getState().getY() + ")\n");
			X.print();
			System.out.print("\n \n");
			
			Node SUCCnode = generateAllSuccessors(X);
			
			while(SUCCnode != null)
			{
				Node next = SUCCnode.nextInQUEUE;
				boolean found_in_OPEN = false;
				boolean found_in_CLOSED = false;
				
				//Should use hash table instead
				Node iterator = OPEN;
				while(iterator != null)
				{
					if(SUCCnode.isEqualTo(iterator))
					{
						SUCCnode = iterator;
						found_in_OPEN = true;
						break;
					}
					iterator = iterator.nextInQUEUE;
				}
				if(!found_in_OPEN)
				{
					iterator = CLOSED;
					while(iterator != null)
					{
						if(SUCCnode.isEqualTo(iterator))
						{
							SUCCnode = iterator;
							found_in_CLOSED = true;
							break;
						}
						iterator = iterator.nextInQUEUE;
					}
				}
				
				pushToKids(X.kids, SUCCnode);
				if(!found_in_OPEN && !found_in_CLOSED)
				{
					attachAndEval(SUCCnode, X);
					insert(OPEN, SUCCnode);
				}
				else if((X.getG() + SUCCnode.arcCost(X)) < SUCCnode.getG())
				{
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
		String textfile = "boards/boards/board-1-1.txt";
		State initialState = new State();
		initialState.readState(textfile);
		Node mySolution = AStar_algorithm(initialState);
		if(mySolution == null)
			System.out.println("Could not find solution");
		else
		{
			System.out.println("A solution was found: \n");
			mySolution.print();
		}

		
	}
	
}
