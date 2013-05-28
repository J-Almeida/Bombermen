package gui.swing;

import java.awt.Graphics2D;

import model.Positionable;

public interface ClientWorldObject extends IDraw, Positionable
{
	void Draw(Graphics2D g);
}
