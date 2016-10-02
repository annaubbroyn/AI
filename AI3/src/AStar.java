
//import java.util.*;

public class AStar {
	
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
		Node CLOSED = new Node();
		Node OPEN = new Node();
		
		Node n0 = new Node();
		n0.setState(initialState);
		n0.updateG(0);
		n0.calcH();
		n0.updateF();
		n0.setIsEmpty(false);
		OPEN = n0;
		
int count = 0;
		
		while(true)
		{
count ++;
			if(OPEN.isEmpty() == true)
				return null;
			Node X = OPEN.pop();
			
			if(CLOSED == null)
				CLOSED = X;
			else
				CLOSED.push(X);
			
			if(isSolution(X))
				return X;
//if(count == 4){
System.out.print("\n(x,y): (" + X.getState().getX() + "," + X.getState().getY() + ")\n");
n0.print();
//}
			
			Node SUCCnode = X.generateAllSuccessors();
			
			while(SUCCnode != null)
			{
//if(count == 4)
System.out.print("SUCC: (" + SUCCnode.getState().getX() +"," + SUCCnode.getState().getY() +")\n");
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
				
				if(X.kids == null)
					X.kids = SUCCnode;
				else
					X.pushToKids(SUCCnode);
				
				if(!found_in_OPEN && !found_in_CLOSED)
				{
					attachAndEval(SUCCnode, X);
					if(OPEN.isEmpty())
						OPEN = SUCCnode;
					else
						OPEN.insert(SUCCnode);
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
