package ass1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.CSVTableWriter;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.GraphLib;
import prefuse.util.PrefuseLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JForcePanel;
import prefuse.util.ui.JPrefuseApplet;
import prefuse.util.ui.JValueSlider;
import prefuse.util.ui.UILib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

class FinalControlListener1 extends ControlAdapter implements Control{
	
	public void itemClicked(VisualItem item, MouseEvent e){
		if(item instanceof NodeItem)
		{	
			JPopupMenu jpub = new JPopupMenu();
			String name = ((String) item.get("label"));
			int degree = (Integer) item.get("degree");
			//int p=(Integer) vg.getDegree()
			jpub.add(name);
			jpub.add("Degree:"+degree);
			//jpub.add("Column:"+);
			jpub.show(e.getComponent(),(int) item.getX(), (int) item.getY());
		   
		    	
		}
	}
		
}
class NodeRenderer1 extends ShapeRenderer{
	
	@Override
	protected Shape getRawShape(VisualItem item){
		double x = item.getX();
        if ( Double.isNaN(x) || Double.isInfinite(x) )
            x = 0;
        double y = item.getY();
        if ( Double.isNaN(y) || Double.isInfinite(y) )
            y = 0;
        int degree=(int)item.get("degree");
        double width = getBaseSize()+item.getSize()+degree;
        // Center the shape around the specified x and y
        if ( width > 1 ) {
            x = x-width/2;
            y = y-width/2;
        }
        if (!item.canGet("value",String.class))
        	return ellipse(x, y, width, width);
        String v=(String)item.get("value");
        if (v.equals("c"))
        	return star((float)x, (float)y,(float) width);
        else if(v.equals("l"))
        	return triangle_left((float)x, (float)y,(float)width);
        else 
        	return ellipse(x, y, width, width);
        			
	}
}

public class polbooks extends JPrefuseApplet {

	private static final String graph = "graph";
	private static final String nodes = "graph.nodes";
	private static final String edges = "graph.edges";
	
	public void init() {
		UILib.setPlatformLookAndFeel();
		JComponent graphview = demo("/polbooks.xml", "label");
		this.getContentPane().add(graphview);	
		
	}
	
	public static JComponent demo(String datafile, String label) {
		Graph g = null;
		if (datafile == null) {
			g = GraphLib.getGrid(15, 15);
		} else {
			try {
				g = new GraphMLReader().readGraph(datafile);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return demo(g, label);
	}
	public static void caller(VisualGraph vg,Graph g,String label){
		int[] A=new int[30];
		double[] B=new double[30];
		double[] C=new double[30];
		double[] D=new double[30];
		 BooksAnalysis book=new  BooksAnalysis();
		 System.out.println(book.triad(g).sum);
		 	Table table=new Table();

			table.addColumn("Graphs",String.class);
			table.addColumn("Ratios", Double.class);
			table.addColumn("Triads",Integer.class);
			table.addColumn("Global Coefficients",Double.class);
			table.addColumn("Local Coefficients",Double.class);
		/*	for(int i=0;i<30;i++)
		{
			Graph h=randomgenerator(g,label);
			A[i]=book.triad(h).triad;
			B[i]=book.ratio(h);
			C[i]=book.coefficient(h);
			//System.out.println(C[i]);
			D[i]=book.triad(h).sum;
			System.out.println(D[i]);
		}
		*/
		
		/*for (int k=0;k<A.length;k++){
			int row=table.addRow();
			table.set(row,"Graphs","Graph"+k);
			table.set(row,"Ratios",B[k]);
			table.set(row,"Triads",A[k]);
			table.set(row,"Global Coefficients",C[k]);
			table.set(row,"Local Coefficients", D[k]);
		}*/
		
		try{
		//	FileWriter fstream = new FileWriter("out.txt");
			//BufferedWriter out = new BufferedWriter(fstream);
			
			//FileOutputStream f = new FileOutputStream(new File("out1.txt"));
			//OutputStream output = new FileOutputStream("out1.txt");
			//CSVTableWriter writer=new CSVTableWriter();
			//writer.writeTable(table,"out1.txt");
			/*String[] arr={"\"Graphs\""+",","\"Ratio\""+",","\"Triads\""};
			for (int i=0;i<arr.length;i++){
			out.write(arr[i]);
			}
			out.close();*/
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		
	}
	
	}	
	
	
	public static Graph randomgenerator(Graph g, String label)
	{
		int e=g.getEdgeCount();
		int n=g.getNodeCount();
		Graph h=new Graph();
		
		for(int i=0;i<n;i++)
		{
			h.addNode();
		}
		h.addColumn("label", String.class);
		h.addColumn("value", String.class);
		for(int i=0;i<n;i++)
		{
			h.getNode(i).set(0, g.getNode(i).get(0));
			h.getNode(i).set(1, g.getNode(i).get(1));
		}
		//System.out.println((n*(n-1)*(n-2))/6);
	//	for(int i=0;i<e;i++)
		//	g.removeEdge(i);
		Random rn=new Random();
		for(int i=0;i<e;i++)
		{
			int a=rn.nextInt(n);
			int b=rn.nextInt(n);
			while(b==a)
			b=rn.nextInt(n);
			h.addEdge(a,b);
		}
		
		//helper(h,label);
		return h;
		
		
		
		//System.out.println(g.getEdge(g.getNode(1), g.getNode(0)));
		
	}
	
	
	
	
	public static JComponent helper(Graph g, String label) {
		
	
	
	final Visualization vis = new Visualization();
	VisualGraph vg = vis.addGraph(graph, g);
	caller(vg,g,label);
	g.addColumn("degree",Integer.class);
	for (int i=0;i<g.getNodeCount();i++){
		g.getNode(i).set("degree",g.getNode(i).getDegree());
	}
	
	vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
	TupleSet focusGroup = vis.getGroup(Visualization.FOCUS_ITEMS);
	focusGroup.addTupleSetListener(new TupleSetListener() {
		public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem) {
			for (int i = 0; i < rem.length; ++i)
				((VisualItem) rem[i]).setFixed(false);
			for (int i = 0; i < add.length; ++i) {
				((VisualItem) add[i]).setFixed(false);
				((VisualItem) add[i]).setFixed(true);
			}
			vis.run("draw");
		}
	});
	
	NodeRenderer1 rn=new NodeRenderer1();
	vis.setRendererFactory(new DefaultRendererFactory(rn));

	int maxhops = 5, hops = 5;
	final GraphDistanceFilter filter = new GraphDistanceFilter(graph, hops);

	int[] palette = new int[] { ColorLib.rgb(145, 211, 167),
			ColorLib.rgb(190, 190, 255), ColorLib.rgb(225, 195, 225) };
	// map nominal data values to colors using our provided palette
	DataColorAction filler = new DataColorAction(nodes, "value",
			Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
	filler.add("_fixed", ColorLib.rgb(255, 100, 100));// Giving red color to
														// focussed node
	filler.add("_highlight", ColorLib.rgb(255, 255, 153));// Giving yellow
															// colors to
															// it's
															// neighbours
	
	ActionList draw = new ActionList();
	draw.add(filter);
	draw.add(filler);
	draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,
			0, 0)));
	draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib
			.gray(200)));
	draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.rgb(
			232, 204, 204)));
	ForceDirectedLayout fdl = new ForceDirectedLayout(graph);
	
	
	
	ForceSimulator fsim = fdl.getForceSimulator();
	fsim.getForces()[0].setParameter(0, -8.2f);
	fsim.getForces()[0].setParameter(1, -8.2f);
	ActionList animate = new ActionList(Activity.INFINITY);
	animate.add(fdl);
	animate.add(filler);
	animate.add(new RepaintAction());

	// finally, we register our ActionList with the Visualization.
	// we can later execute our Actions by invoking a method on our
	// Visualization, using the name we've chosen below.
	vis.putAction("draw", draw);
	vis.putAction("layout", animate);
	vis.runAfter("draw", "layout");


	Display display = new Display(vis);
	display.setSize(500,500);
	display.setBackground(Color.WHITE);
	display.addControlListener(new FocusControl(1));
	display.addControlListener(new DragControl());
	display.addControlListener(new PanControl());
	display.addControlListener(new FinalControlListener1());
	display.addControlListener(new ZoomControl());
	display.addControlListener(new WheelZoomControl());
	display.addControlListener(new ZoomToFitControl());
	display.addControlListener(new NeighborHighlightControl());
	
	final JFastLabel name1=new JFastLabel();
	name1.setPreferredSize(new Dimension(390, 30));
	name1.setMaximumSize(new Dimension(390,30));
    name1.setVerticalAlignment(SwingConstants.TOP);
    name1.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
    name1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 12));
    final String info1="Hover on any node to get it's info";
    name1.setText(info1);
    
	final JFastLabel aff1=new JFastLabel();
	aff1.setPreferredSize(new Dimension(390, 30));
	aff1.setMaximumSize(new Dimension(390,30));
    aff1.setVerticalAlignment(SwingConstants.TOP);
    aff1.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
    aff1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
	display.addControlListener(new ControlAdapter(){
		public void itemEntered(VisualItem item,MouseEvent e){
			if (item instanceof NodeItem){
			String name=(String)item.get("label");
			name1.setText(name);
			String aff=(String)item.get("value");
			if (aff.equals("c")){
				aff1.setText("Conservative");
			}
			else if (aff.equals("l")){
				aff1.setText("Liberal");
			}
			else aff1.setText("Neutral");
			}
			
		}
		public void itemExited(VisualItem item,MouseEvent e){
			aff1.setText(null);
			name1.setText(info1);
		}
	});
	
	
	
	
	
	
	
	Box info = UILib.getBox(new Component[]{name1,aff1},false,0,2, 0);
	info.setBorder(BorderFactory.createTitledBorder("Node Info"));
	info.setAlignmentX((float)(0.4));
	info.setAlignmentY((float)10.0);
	info.setMaximumSize(new Dimension(420,60));
	
	
	
	
	JTextField star=new JTextField("Star -- Conservative");
	JTextField triangle =new JTextField("Triangle -- Liberal");
	JTextField Circle=new JTextField("Circle -- Neutral");
	Box pf=new Box(BoxLayout.Y_AXIS);
	pf.add(star);
	pf.add(triangle);
	pf.add(Circle);
	
	pf.setBorder(BorderFactory.createTitledBorder("Values"));
	pf.setMaximumSize(new Dimension(300, 30));
	final JForcePanel fpanel = new JForcePanel(fsim);
	final JValueSlider slider = new JValueSlider("Distance", 0, maxhops,
			hops);
	slider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			filter.setDistance(slider.getValue().intValue());
			vis.run("draw");
		}
	});
	slider.setBackground(Color.WHITE);
	slider.setPreferredSize(new Dimension(300, 30));
	slider.setMaximumSize(new Dimension(300, 30));

	Box cf = new Box(BoxLayout.Y_AXIS);
	cf.add(slider);
	cf.setBorder(BorderFactory.createTitledBorder("Connectivity Filter"));

	fpanel.add(cf);
	
	fpanel.add(pf);
	fpanel.add(info);
	
	fpanel.add(Box.createVerticalGlue());
	// create a new JSplitPane to present the interface
	JSplitPane split = new JSplitPane();
	split.setLeftComponent(display);
	split.setRightComponent(fpanel);
	
	split.setOneTouchExpandable(true);
	split.setContinuousLayout(false);
	split.setDividerLocation(530);
	split.setDividerLocation(800);
	split.setResizeWeight(0.8);
	
	// position and fix the default focus node
	NodeItem focus = (NodeItem) vg.getNode(0);
	PrefuseLib.setX(focus, null, 800);
	PrefuseLib.setY(focus, null, 400);
	focusGroup.setTuple(focus);
	
	// now we run our action list and return
	return split;
}
	
	public static JComponent demo(Graph g, String label) {
		// create a new, empty visualization for our data
		

		return (helper(g, label));
	}
 

} // end of class GraphView
