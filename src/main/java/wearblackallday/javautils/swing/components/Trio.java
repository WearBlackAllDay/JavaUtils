package wearblackallday.javautils.swing.components;

import wearblackallday.javautils.swing.SwingUtils;

import javax.swing.*;
import java.awt.GridLayout;

public class Trio extends JComponent {
	public static final Orientation TOP = Orientation.TOP;
	public static final Orientation RIGHT = Orientation.RIGHT;
	public static final Orientation BOTTOM = Orientation.BOTTOM;
	public static final Orientation LEFT = Orientation.LEFT;

	public Trio(Orientation orientation, JComponent single, JComponent pair0, JComponent pair1) {
		switch(orientation) {
			case TOP:
				this.setLayout(new GridLayout(2, 0));
				this.add(single);
				this.add(SwingUtils.addSet(new Box(BoxLayout.X_AXIS), pair0, pair1));
				break;
			case RIGHT:
				this.setLayout(new GridLayout(0, 2));
				this.add(SwingUtils.addSet(new Box(BoxLayout.Y_AXIS), pair0, pair1));
				this.add(single);
				break;
			case BOTTOM:
				this.setLayout(new GridLayout(2, 0));
				this.add(SwingUtils.addSet(new Box(BoxLayout.X_AXIS), pair0, pair1));
				this.add(single);
				break;
			case LEFT:
				this.setLayout(new GridLayout(0, 2));
				this.add(single);
				this.add(SwingUtils.addSet(new Box(BoxLayout.Y_AXIS), pair0, pair1));
				break;
		}
	}

	private enum Orientation {
		TOP, RIGHT, BOTTOM, LEFT
	}
}
