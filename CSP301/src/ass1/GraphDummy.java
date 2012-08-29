package ass1;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
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
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.util.StrokeLib;
import prefuse.util.force.ForceSimulator;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JForcePanel;
import prefuse.util.ui.JValueSlider;
import prefuse.util.ui.UILib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
/**
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
class NodeRenderer2 extends ShapeRenderer{
	
	@Override
	protected Shape getRawShape(VisualItem item){
		double x = item.getX();
        if ( Double.isNaN(x) || Double.isInfinite(x) )
            x = 0;
        double y = item.getY();
        if ( Double.isNaN(y) || Double.isInfinite(y) )
            y = 0;
        int degree=(int)item.get("degree");
        double width = getBaseSize()+item.getSize();
        // Center the shape around the specified x and y
        if ( width > 1 ) {
            x = x-width/2;
            y = y-width/2;
        }
       
        if (!item.canGet("value", String.class))
			return ellipse(x, y, width, width);
		Integer v = Integer.parseInt(item.get("value").toString());
		if (v == 0)
			return star((float) x, (float) y, (float) width);
		else if (v == 1)
			return triangle_left((float) x, (float) y, (float) width);
		else
			return ellipse(x, y, width, width);
        /*if (!item.canGet("value",String.class))
        	return ellipse(x, y, width, width);
        String v=(String)item.get("value");
        if (v.equals("c"))
        	return star((float)x, (float)y,(float) width);
        else if(v.equals("l"))
        	return triangle_left((float)x, (float)y,(float)width);
        else 
        	return ellipse(x, y, width, width);*/
        			
	}
}


public class GraphDummy extends JPanel {

    private static final String graph = "graph";
    private static final String nodes = "graph.nodes";
    private static final String edges = "graph.edges";

    private Visualization vis;
    
    
    public GraphDummy(Graph g, String label,int id) {
    	super(new BorderLayout());
    	
        // create a new, empty visualization for our data
        vis = new Visualization();
        
        // --------------------------------------------------------------------
        // set up the renderers
        VisualGraph vg = vis.addGraph(graph, g);
    	//caller(vg,g);
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

    	// set up the renderers

    	//ShapeRenderer shape1=new ShapeRenderer(20);
    	if(id==257)
    	{
    	
    		NodeRenderer2 rn2=new NodeRenderer2();
    		vis.setRendererFactory(new DefaultRendererFactory(rn2));
    	}
    	
    	else
    	{
    		
    		LabelRenderer rn=new LabelRenderer(label);
        	rn.setRoundedCorner(8, 8);
        	rn.setMaxTextWidth(5000);
       
        
        	vis.setRendererFactory(new DefaultRendererFactory(rn));
    	}
    	// -- set up the actions ----------------------------------------------

    	int maxhops = 5, hops = 5;
    	final GraphDistanceFilter filter = new GraphDistanceFilter(graph, hops);

    	int[] palette = new int[] { ColorLib.rgb(255,0,102),
				ColorLib.rgb(153,255,51)};
		// map nominal data values to colors using our provided palette
		DataColorAction filler = new DataColorAction(nodes, "value",
				Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
		int[] shapes=new int[]{Constants.SHAPE_STAR,Constants.SHAPE_TRIANGLE_LEFT};
		DataShapeAction shape=new DataShapeAction(nodes,"value",shapes);
		int[] color = new int[] { ColorLib.rgb(41,0,102),
				ColorLib.rgb(26, 255, 0)};
		DataColorAction strokecolor = new DataColorAction(nodes, "value",
				Constants.NOMINAL, VisualItem.STROKECOLOR, color);
		int[] color1 = new int[] {ColorLib.rgb(204,204,208)};
		DataColorAction edgecolor = new DataColorAction(edges, "value",
				Constants.NOMINAL, VisualItem.STROKECOLOR, color1);
		
		
			
			
		
		filler.add("_fixed", ColorLib.rgba(200,0,0,0));// Giving red
																// color to
																// focussed node
		filler.add("_highlight", ColorLib.rgb(255,255,204));// Giving yellow
																// colors to
																// it's
																// neighbours
		
		strokecolor.add("_highlight", ColorLib.rgba(0, 0, 0,100));
		
		edgecolor.add("_highlight", ColorLib.rgb(0,0,0));

		ActionList draw = new ActionList();
		draw.add(filter);
		draw.add(filler);
		draw.add(shape);
		draw.add(strokecolor);
		draw.add(edgecolor);
		draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0,
				0, 0)));
		// draw.add(new ColorAction(nodes, VisualItem.STROKECOLOR,
		// ColorLib.rgba(131,170,241,150)));
		draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib
				.rgb(90,90,90)));
		draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.rgb(
				232, 204, 204)));
		ForceDirectedLayout fdl = new ForceDirectedLayout(graph);
    	
    	
    	ForceSimulator fsim = fdl.getForceSimulator();
    	fsim.getForces()[0].setParameter(0, -8.2f);
    	fsim.getForces()[0].setParameter(1, -8.2f);
    	
		// fsim.getForces()[1].setParameter(0, -8.2f);
		ActionList animate = new ActionList(30000);
		animate.add(fdl);
		animate.add(filler);
		animate.add(shape);
		animate.add(strokecolor);
		animate.add(edgecolor);
		animate.add(new RepaintAction());
    	// finally, we register our ActionList with the Visualization.
    	// we can later execute our Actions by invoking a method on our
    	// Visualization, using the name we've chosen below.
    	vis.putAction("draw", draw);
    	vis.putAction("layout", animate);
    	vis.runAfter("draw", "layout");

    	// --------------------------------------------------------------------
    	// STEP 4: set up a display to show the visualization

    	Display display = new Display(vis);
    	display.setSize(500,500);
    	//display.setForeground(Color.GRAY);
    	display.setBackground(Color.WHITE);

    	// main display controls
    	display.addControlListener(new FocusControl(1));
    	display.addControlListener(new DragControl());
    	display.addControlListener(new PanControl());
    	//display.addControlListener(new FinalControlListener());
    	display.addControlListener(new ZoomControl());
    	display.addControlListener(new WheelZoomControl());
    	display.addControlListener(new ZoomToFitControl());
    	display.addControlListener(new NeighborHighlightControl());

    	
    	// --------------------------------------------------------------------
    	// STEP 5: launching the visualization

    	// create a panel for editing force values
    	
    	
    	final JFastLabel name1 = new JFastLabel();
		name1.setPreferredSize(new Dimension(390, 30));
		name1.setMaximumSize(new Dimension(390, 30));
		name1.setVerticalAlignment(SwingConstants.TOP);

		name1.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		name1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 12));
		final JFastLabel aff1 = new JFastLabel();
		aff1.setPreferredSize(new Dimension(390, 30));
		aff1.setMaximumSize(new Dimension(390, 30));
		aff1.setVerticalAlignment(SwingConstants.TOP);
		aff1.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		aff1.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
		final StrokeLib stroke = new StrokeLib();
		display.addControlListener(new ControlAdapter() {
			public void itemEntered(VisualItem item, MouseEvent e) {
				if (item instanceof NodeItem) {
					String label = (String) item.get("label");
					name1.setText(label);
					BasicStroke p = stroke.getStroke(2);
					item.setStroke(p);
					int aff = Integer.parseInt((String) item.get("value"));
					if (aff == 0) {
						aff1.setText("Value:0");
						item.setStrokeColor(ColorLib.rgba(131, 170, 241, 250));
						item.setStroke(p);
					} else if (aff == 1) {
						aff1.setText("Value:1");
						item.setStrokeColor(ColorLib.rgba(140, 255, 25, 200));
						item.setStroke(p);
					} else {
						aff1.setText("Value:2");
						item.setStrokeColor(ColorLib.rgb(225, 195, 225));
						item.setStroke(p);
					}
				}
			}

			public void itemExited(VisualItem item, MouseEvent e) {

				BasicStroke p = stroke.getStroke(0);
				item.setStroke(p);
				item.setStrokeColor(ColorLib.rgba(131, 170, 241, 150));
				aff1.setText(null);
				name1.setText(null);
			}
		});
	
    	
		Box info = UILib
				.getBox(new Component[] { name1, aff1 }, false, 0, 2, 0);
		info.setBorder(BorderFactory.createTitledBorder("Node Info"));
		info.setAlignmentX((float) (0.4));
		info.setAlignmentY((float) 10.0);
		info.setMaximumSize(new Dimension(420, 60));

		JTextField star = new JTextField("Star --0");
		JTextField triangle = new JTextField("Triangle -- 1");
		//JTextField Circle = new JTextField("Circle -- 2");
		Box pf = new Box(BoxLayout.Y_AXIS);
		pf.add(star);
		pf.add(triangle);
    	
    	pf.setBorder(BorderFactory.createTitledBorder("Values"));
    	pf.setMaximumSize(new Dimension(250, 15));
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
    	
    	display.setAlignmentX(CENTER_ALIGNMENT);
    	// create a new JSplitPane to present the interface
    	JSplitPane split = new JSplitPane();
    	split.setLeftComponent(display);
    	split.setRightComponent(fpanel);
    	
    	//split.setTopComponent(opanel);
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
    	add(split);


    }
    
    /*public void setGraph(Graph g, String label) {
        // update labeling
        DefaultRendererFactory drf = (DefaultRendererFactory)
                                                m_vis.getRendererFactory();
        ((LabelRenderer)drf.getDefaultRenderer()).setTextField(label);
        
        // update graph
        m_vis.removeGroup(graph);
        VisualGraph vg = m_vis.addGraph(graph, g);
        m_vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
        VisualItem f = (VisualItem)vg.getNode(0);
        m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
        f.setFixed(false);
    }*/
    
    // ------------------------------------------------------------------------
    // Main and demo methods
    
  
    }
    
 
     // end of class GraphView
