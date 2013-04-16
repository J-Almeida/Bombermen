package ui.gui.graphical.main_menu;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageBackground extends JComponent
{
    private static final long serialVersionUID = 1L;

    private Image _image;

    public ImageBackground(Image image)
    {
        _image = image;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.drawImage(_image, 0, 0, /*MainMenu.WINDOW_WIDTH, MainMenu.WINDOW_HEIGHT,*/ null);
    }
}
