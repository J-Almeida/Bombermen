package pt.up.fe.lpoo.bombermen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

public class Main extends LwjglApplet
{
    private static final long serialVersionUID = 1L;

    public Main()
    {
        super(new Bombermen(), true);
    }
}
