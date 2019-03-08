package reportJUnitJava;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MyLayout implements LayoutManager{
	private static final int TAB = 50;
	private static final int GAP = 5;
    private int minWidth = 0, minHeight = GAP;
    private int preferredWidth = 0, preferredHeight = 0;
    private int counter = 0;

	
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

    private void setSizes(Container parent) {
		int width, height;
        Dimension d = null;
        int nComps = parent.getComponentCount();

        if (nComps != counter) {
	        //Reset preferred/minimum width and height.
        	//System.out.println("Zmiana! " + nComps + " " + counter );
        	counter = nComps;
	        preferredWidth = 0;
	        preferredHeight = GAP;
	        minWidth = 0;
	        minHeight = 0;
	
	        for (int i = 0; i < nComps; i++) {
	            Component c = parent.getComponent(i);
	    		d = c.getPreferredSize();
	    		if (c instanceof TestPanel) {
	    			
	    			width = GAP * 2 + d.width;
	    			height = d.height + GAP;
	    			
	    			minWidth = Math.max(minWidth, width);
	    			width += ((TestPanel)c).getIndent() * TAB;
	    			
	    			preferredWidth = Math.max(preferredWidth, width);
	    			minHeight += height;
	    			if (c.isVisible())
	    				preferredHeight += height;
	    		}
	        }
        }
    }

	@Override
	public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = preferredWidth
                    + insets.left + insets.right;
        dim.height = preferredHeight
                     + insets.top + insets.bottom;

        //System.out.println("Preffered H " + preferredHeight + "  dzieci : " + parent.getComponentCount());
        return dim;
//		setSizes(parent);
//		return new Dimension(minWidth, preferredHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(minWidth, minHeight);

        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = minWidth
                    + insets.left + insets.right;
        dim.height = minHeight
                     + insets.top + insets.bottom;

        return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth() - (insets.left + insets.right);
        int nComps = parent.getComponentCount();
        int previousHeight = 0;
        int y = insets.top, xOffset, quotient;

        //System.out.println(parent.getWidth());
        setSizes(parent);
        
        xOffset = TAB;
        // calculate current TAB
        for (int i = 0 ; i < nComps ; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible() && c instanceof TestPanel) {
                Dimension d = c.getPreferredSize();
            	quotient = ((TestPanel)c).getIndent();
            	if (quotient == 0) {
            		if (maxWidth - d.width < xOffset )
            			xOffset = maxWidth - d.width;
            	} else
            		if ((maxWidth - d.width) / quotient < xOffset)
            			xOffset = (maxWidth - d.width) / quotient;
            }
        }
        

        for (int i = 0 ; i < nComps ; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible() && c instanceof TestPanel) {
                Dimension d = c.getPreferredSize();
            	quotient = ((TestPanel)c).getIndent();

                // increase y, if appropriate
            	y += (i > 0) ? previousHeight + GAP : 0;

                // Set the component's size and position.
                c.setBounds(xOffset * quotient, y, d.width, d.height);

                previousHeight = d.height;
            }
        }
	}
	
	@Override
    public String toString() {
        String str = "";
        return getClass().getName() + "[vgap=" + GAP + str + "]";
    }

}