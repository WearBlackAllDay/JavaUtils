package wearblackallday.javautils.swing.components;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class GridBagPanel extends JPanel {

	protected final GridBagConstraints gbc;

	public GridBagPanel() {
		this(new GridBagConstraints());
	}

	public GridBagPanel(GridBagConstraints gridBagConstraints) {
		super(new GridBagLayout());
		this.gbc = gridBagConstraints;
	}

	public GridBagPanel(int top, int left, int bottom, int right) {
		this();
		this.setInsets(top, left, bottom, right);
	}

	public void setGrid(int x, int y) {
		this.gbc.gridx = x;
		this.gbc.gridy = y;
	}

	public void setGridLength(int width, int height) {
		this.gbc.gridwidth = width;
		this.gbc.gridheight = height;
	}

	public void setWeight(double x, double y) {
		this.gbc.weightx = x;
		this.gbc.weighty = y;
	}

	public void setAnchor(int anchor) {
		this.gbc.anchor = anchor;
	}

	public void setFill(int fill) {
		this.gbc.fill = fill;
	}

	public void setInsets(int top, int left, int bottom, int right) {
		this.gbc.insets.set(top, left, bottom, right);
	}

	public void setPadding(int x, int y) {
		this.gbc.ipadx = x;
		this.gbc.ipady = y;
	}

	public GridBagConstraints getConstraints() {
		return this.gbc;
	}

	public void setDefaultConstraints() {
		this.setGrid(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE);
		this.setGridLength(1, 1);
		this.setWeight(0, 0);
		this.setAnchor(GridBagConstraints.CENTER);
		this.setFill(GridBagConstraints.NONE);
		this.setInsets(0, 0, 0,0);
		this.setPadding(0, 0);
	}

	public void add(Component comp, int x, int y) {
		this.setGrid(x, y);
		this.add(comp);
	}

	public void add(Component comp, Consumer<GridBagConstraints> constraints) {
		constraints.accept(this.gbc);
		this.add(comp);
	}

	@Override
	public Component add(Component comp) {
		this.add(comp, this.gbc);
		return comp;
	}

	@Override
	public Component add(Component comp, int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Component comp, Object constraints, int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GridBagLayout getLayout() {
		return (GridBagLayout)super.getLayout();
	}

	@Override
	public void setLayout(LayoutManager mgr) {
		if(this.getLayout() == null) super.setLayout(mgr);
		else throw new UnsupportedOperationException();
	}
}
