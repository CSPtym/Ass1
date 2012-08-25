package ass1;

import prefuse.data.Graph;

public class BooksAnalysis {
	public static int triad(Graph g)
	{
		int n=g.getNodeCount();

		int triad=0;
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				for(int k=0;k<n;k++)
					if(((g.getEdge(g.getNode(i), g.getNode(j))!=null)|(g.getEdge(g.getNode(j), g.getNode(i))!=null))&&((g.getEdge(g.getNode(j), g.getNode(k))!=null)|(g.getEdge(g.getNode(k), g.getNode(j))!=null))&&((g.getEdge(g.getNode(k), g.getNode(i))!=null)|(g.getEdge(g.getNode(i), g.getNode(k))!=null)))
					triad++;
		
		triad/=6;
		
		return(triad);
		//System.out.println("triad= "+triad);
	}
	public static double ratio(Graph g)
	{

		double polariser=0.;
		int e=g.getEdgeCount();
		int n=g.getNodeCount();
		for(int i=0;i<e;i++)
		if(g.getNode((int) g.getKey(g.getSourceNode(i))).get(1).equals(g.getNode((int) g.getKey(g.getTargetNode(i))).get(1)) )
			polariser++;
		
		//System.out.println("polariser= "+polariser);
		int nc2=(n*(n-1))/2;
		double ratio=polariser/nc2;
		//System.out.println("ratio="+ratio);
		return(ratio);
	}
	

}
