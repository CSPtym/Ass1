package ass1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.soap.Node;

import prefuse.data.Graph;
import prefuse.data.tuple.TableNode;


public class Undirectedlocal {
	static double triad=0;
	static double nofstriads=0;
	static double nof2striads=0;
	static double nofdtriads=0;
	static int typec=0;
	static int typen=0;
	static int typel=0;
	static double polariser=0;
public static double localcoeff(Graph g)
{
	triad=0;
	nofstriads=0;
	nof2striads=0;
	nofdtriads=0;
	
	int n=(int)g.getNodeCount();
	double [] triads=new double[n];
	double [] nc2=new double[n];
	double [] localcoefficients=new double[n];
	//double [] doublearrow=new double[n];
	//double [] neighbours=new double[n];
	
  
      //  g.addColumn("Triangles", int.class, 0);
      //  g.addColumn("localClusteringCoefficient", double.class, 0);
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
                    //c.Triangles += n;
                    triads[(int) s.get("id")]+=ne;
                    triads[(int) t.get("id")]+=ne;
                    triad+=ne;
                    //s.setInt("Triangles", s.getInt("Triangles") + n);
                    //t.setInt("Triangles", t.getInt("Triangles") + n);
                    Iterator i = intersection.iterator();
                    while (i.hasNext()) {
                        TableNode temp = (TableNode) i.next();
                        triads[(int) temp.get("id")]+=1;
                        if((s.get("value").equals(t.get("value")))&&(t.get("value").equals(temp.get("value"))))
                            nofstriads++;
                        if((s.get("value").equals(t.get("value")))&&!(t.get("value").equals(temp.get("value")))|(temp.get("value").equals(t.get("value")))&&!(t.get("value").equals(s.get("value")))|(s.get("value").equals(temp.get("value")))&&!(t.get("value").equals(temp.get("value"))))
                        	nof2striads++;
                        if((!s.get("value").equals(t.get("value")))&&(!t.get("value").equals(temp.get("value")))&&(!temp.get("value").equals(s.get("value"))))
                        	nofdtriads++;
                    }
                    ((HashSet) t.get("close")).add(s);
                }
            }
        }
        
        for(int i=0;i<n;i++)
        {
        	int nc=g.getNode(i).getDegree();
        	nc2[i]=nc*(nc-1)/2;
        }
        double global=0;
        for(int i=0;i<n;i++)
        	{if (nc2[i]>0) localcoefficients[i]=triads[i]/nc2[i];
        	else localcoefficients[i]=0;
        	global+=localcoefficients[i];
        	}
        return (global/n);
    }
	
public static double triad()
{
	return triad;
}
public static double striad()
{
	return nofstriads;
}

public static double s2triad()
{
	return nof2striads;
}
public static double dtriad()
{
	return nofdtriads;
}

public static double globalcoeff(Graph g)
{
	int n=g.getNodeCount();
	double neighbours=n*(n-1)*(n-2)/6;
	if (neighbours>0)
		return triad/neighbours;
	else return 0;
}
public static double ratio(Graph g)
{
	typec=0;
	typen=0;
	typel=0;
	polariser=0.;
	int e=g.getEdgeCount();
	int n=g.getNodeCount();
	for(int i=0;i<e;i++)
	if(g.getNode((int) g.getKey(g.getSourceNode(i))).get("value").equals(g.getNode((int) g.getKey(g.getTargetNode(i))).get("value")) )
		{
		if(g.getNode((int) g.getKey(g.getSourceNode(i))).get("value").equals("c"))
		typec++;
		if(g.getNode((int) g.getKey(g.getSourceNode(i))).get("value").equals("n"))
			typen++;
		else
			typel++;
			polariser++;
		}
	//System.out.println("polariser= "+polariser);
	//int nc2=(n*(n-1))/2;
	double ratio=polariser/e;
	//System.out.println("ratio="+ratio);
	return(ratio);
}



public static double polariser()
{
	return polariser;
}


public static int typec()
{
	return typec;
}

public static int typen()
{
	return typen;
}

public static int typel()
{
	return typel;
}
}
