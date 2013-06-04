package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
    public static void main(String[] args)
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = Constants.WINDOW_TITLE;
        cfg.useGL20 = Constants.USE_GL20;
        cfg.width = Constants.DEFAULT_WIDTH;
        cfg.height = Constants.DEFAULT_HEIGHT;

        new LwjglApplication(new Bombermen2(), cfg);
    }
}
