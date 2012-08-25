package ass1;

import prefuse.data.Graph;
class Tuple{
	int triad;
	double sum;
}
public class BooksAnalysis {
	
	public static Tuple triad(Graph g)
	{
		int n=g.getNodeCount();
		double[] arr=new double[n];
		double sum=0;
		int triad=0;
		
		for(int i=0;i<n;i++){
			int triads=0;
			int neighbours=g.getNode(i).getDegree();
			//System.out.println("n"+neighbours);
			double denominator=(double)(neighbours)*(neighbours-1)/2;
			//System.out.println(denominator);
			for(int j=0;j<n;j++){
				for(int k=0;k<n;k++){
					if(((g.getEdge(g.getNode(i), g.getNode(j))!=null)|(g.getEdge(g.getNode(j), g.getNode(i))!=null))&&((g.getEdge(g.getNode(j), g.getNode(k))!=null)|(g.getEdge(g.getNode(k), g.getNode(j))!=null))&&((g.getEdge(g.getNode(k), g.getNode(i))!=null)|(g.getEdge(g.getNode(i), g.getNode(k))!=null))){
					triad++;
					triads++;
					}
					
				}
			}
			if (denominator>0){
				arr[i]=(double)(triads/2)/denominator;
				//System.out.println(triad/2);
				}
				else {arr[i]=0.0;}
			sum+=arr[i];
		}
		sum=(double)sum/(double)n;
		triad/=6;
		Tuple p=new Tuple();
		p.triad=triad;
		p.sum=sum;
		return p;
		//System.out.println("triad= "+triad);
	}
	
	public static double coefficient(Graph g){
		int n=g.getNodeCount();
		double d=n*(n-1)*(n-2)/6;
		if (d>0)
			return (double)((double)(triad(g).triad))/d;
		else
			return 0;
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
		//int nc2=(n*(n-1))/2;
		double ratio=polariser/e;
		//System.out.println("ratio="+ratio);
		return(ratio);
	}
	

}
