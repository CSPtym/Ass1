package ass1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.soap.Node;

import prefuse.data.Graph;
import prefuse.data.tuple.TableNode;

public class Directedtriad {
@SuppressWarnings("unchecked")
static double noftriads=0;
static double nofstriads=0;
static double nof2striads=0;
static double nofdtriads=0;
public static double localcoeff(Graph g)
{
	noftriads=0;
	nofstriads=0;
	nof2striads=0;
	nofdtriads=0;
	int n=(int)g.getNodeCount();
	double [] triads=new double[n];
	double [] localcoefficients=new double[n];
	double [] doublearrow=new double[n];
	double [] neighbours=new double[n];
        g.addColumn("close", HashSet.class, null);
        for (int i = 0; i < g.getNodeCount(); ++i)
            g.getNode(i).set("close", new HashSet<Node>());
        Iterator nodes = g.nodes();
        while (nodes.hasNext()) {
            TableNode s = (TableNode) nodes.next();
            Iterator neighbor = s.neighbors();
            while (neighbor.hasNext()) {
                TableNode t = (TableNode) neighbor.next();
                if ((int)t.get("id") > (int)s.get("id")) {
                    Set<Node> intersection = new HashSet<Node>(
                            (HashSet<Node>) s.get("close"));
                    intersection.retainAll((HashSet<Node>) t.get("close"));
                    int ne = intersection.size();
                    triads[(int) s.get("id")]+=ne;
                    triads[(int) t.get("id")]+=ne;
                    noftriads+=ne;
                    Iterator i = intersection.iterator();
                    while (i.hasNext()) {
                        TableNode temp = (TableNode) i.next();
                        triads[(int) temp.get("id")]+=1;
                        if((s.get("value").equals(t.get("value")))&&(t.get("value").equals(temp.get("value"))))
                        nofstriads++;
                        
                        
                    }
                   // ((HashSet<Node>) t.get("close")).add((Node) s);
                    ((HashSet) t.get("close")).add(s);
                }
            }
        }
        for(int i=0;i<n;i++)
    	{
    		double neighbour=0;
    		Iterator it = g.getNode(i).neighbors();
    		while(it.hasNext())
    		{
    			TableNode ne=(TableNode) it.next();
    			neighbour++;
    		}
    		neighbours[i]=neighbour;
    	}
        for(int i=0;i<n;i++)
    	{
    		double doubleneighbours=0;
    		Iterator it = g.getNode(i).neighbors();
    		while(it.hasNext())
    		{
    			TableNode ne=(TableNode) it.next();
    			if((g.getEdge(i,(int)ne.get("id"))>=0)&&(g.getEdge((int)ne.get("id"),i)>=0))
    			doubleneighbours++;
    		}
    		doublearrow[i]=doubleneighbours;
    	}
        double global=0;
        for(int i=0;i<n;i++)
    	{
    		if((neighbours[i]*(neighbours[i]-1) -2*doublearrow[i])==0)
    			localcoefficients[i]=0;
    		else
    		localcoefficients[i]=triads[i]/(neighbours[i]*(neighbours[i]-1) -2*doublearrow[i]);
    		global+=localcoefficients[i];
    	}
        
        return (global/n);
    }

public static double triad()
{
	return noftriads;
}


public static double striad()
{
	return nofstriads;
}

public static double s2triad()
{
	return nofstriads;
}

public static double dtriad()
{
	return nofdtriads;
}

public static double globalcoeff(Graph g)
{
	//System.out.println("OO teriii");
	int n=g.getNodeCount();
	long ne=n;
	long neighbours=ne*(ne-1)*(ne-2)/6;
	
	System.out.println(neighbours);
	if (neighbours>0)
		return noftriads/neighbours;
	else return 0;
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
