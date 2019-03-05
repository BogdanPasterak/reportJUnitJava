package reportJUnitJava;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MyLayout implements LayoutManager{
	private static final int TAB = 100;
	private static final int GAP = 5;
    private int minWidth = 0, minHeight = GAP;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		int width, height;
		Dimension d = comp.getPreferredSize();
		
		if (comp instanceof TestPanel) {
			width = GAP * 2 + d.width;
			height = d.height + GAP;
			minWidth = Math.max(minWidth, width);
			width += ((TestPanel)comp).getIndent() * TAB;
			preferredWidth = Math.max(preferredWidth, width);
			minHeight += height;
			if (comp.isVisible())
				preferredHeight += height;
		}
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d = null;

        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();

                if (i > 0) {
                    preferredWidth += d.width/3;
                    preferredHeight += GAP;
                } else {
                    preferredWidth = d.width;
                }
                preferredHeight += d.height;

                minWidth = Math.max(c.getMinimumSize().width, minWidth);
                minHeight = preferredHeight;
            }
        }
    }

	@Override
	public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(preferredWidth, preferredHeight);
        /*
        int nComps = parent.getComponentCount();

        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = preferredWidth
                    + insets.left + insets.right;
        dim.height = preferredHeight
                     + insets.top + insets.bottom;

        sizeUnknown = false;
		*/
        return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		
        Dimension dim = new Dimension(minWidth, minHeight);
        /*
        int nComps = parent.getComponentCount();

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = minWidth
                    + insets.left + insets.right;
        dim.height = minHeight
                     + insets.top + insets.bottom;

        sizeUnknown = false;
		*/
        return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth() - (insets.left + insets.right);
        int nComps = parent.getComponentCount();
        int previousHeight = 0;
        int y = insets.top, xOffset, quotient;

        
        if (sizeUnknown) {
            //setSizes(parent);
        }
        
        xOffset = TAB;
        
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
        return getClass().getName() + "[vgap=" + vgap + str + "]";
    }

}
