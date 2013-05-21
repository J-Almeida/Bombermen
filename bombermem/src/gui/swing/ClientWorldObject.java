package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

public interface ClientWorldObject extends IDraw
{
	void Draw(Graphics2D g);
	int GetGuid();
	Point GetPosition();
}
