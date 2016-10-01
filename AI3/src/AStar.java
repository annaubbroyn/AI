
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
		Node iterator = QUEUE;
		while (iterator.nextInQUEUE != null)
			iterator = iterator.nextInQUEUE;
		iterator.nextInQUEUE = element;
		element.nextInQUEUE = null;
	}
	
	public static void pushToKids(Node kids, Node newKid)
	{
		Node iterator = kids;
		while (iterator.nextSibling != null)
			iterator = iterator.nextSibling;
		iterator.nextSibling = newKid;
		newKid.nextSibling = null;
	}
	
	public static void insert(Node QUEUE, Node element)
	{
		Node iterator = QUEUE;
		int f = iterator.nextInQUEUE.getF();
		while(f>element.getF() && iterator != null)
		{
			iterator = iterator.nextInQUEUE;
			f = iterator.nextInQUEUE.getF();
		}
		Node temp = iterator.nextInQUEUE;
		iterator.nextInQUEUE = element;
		element.nextInQUEUE = temp;
	}
	
	public static boolean isSolution(Node X)
	{
		//Check if solution
		return false; //Return true if solution
	}
	
	public static Node generateAllSuccessors(Node X)
	{
		//genAllSuccessors
		Node SUCC = new Node();
		return SUCC;
	}
	
	public static void attachAndEval(Node C, Node P)
	{
		C.parent = P;
		C.setG(P.getG() + arcCost(P,C));
		C.calcH();
		C.updateF();
	}
	
	public static int arcCost(Node P, Node C)
	{
		//Calculate cost
		int cost = 0;
		return cost;
	}
	
	public static void propagatePathImprovements(Node P)
	{
		Node C = P.kids;
		while (C != null)
		{
			if((P.getG() + arcCost(P,C)) < C.getG())
			{
				C.parent = P;
				C.setG(P.getG() + arcCost(P,C));
				C.updateF();
				propagatePathImprovements(C);
			}
			C = C.nextSibling;
		}
	}
	
	public static Node AStar_algorithm()
	{
		Node CLOSED = null;
		Node OPEN = null;
		
		Node n0 = new Node();
		n0.setG(0);
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
			
			Node SUCCnode = generateAllSuccessors(X);
			
			while(SUCCnode != null)
			{
				Node next = SUCCnode.nextInQUEUE;
				boolean found_in_OPEN = false;
				boolean found_in_CLOSED = false;
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
				else if((X.getG() + arcCost(X, SUCCnode)) < SUCCnode.getG())
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
		
		Node mySolution = AStar_algorithm();
		if(mySolution == null)
			System.out.println("Could not find solution");
		else
		{
			System.out.println("A solution was found: ");
			mySolution.print();
		}

		
	}
	
}