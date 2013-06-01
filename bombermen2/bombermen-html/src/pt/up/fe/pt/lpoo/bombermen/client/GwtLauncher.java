package pt.up.fe.pt.lpoo.bombermen.client;

import pt.up.fe.pt.lpoo.bombermen.Bombermen;
import pt.up.fe.pt.lpoo.bombermen.Constants;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication
{
    @Override
    public GwtApplicationConfiguration getConfig()
    {
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        return cfg;
    }

    @Override
    public ApplicationListener getApplicationListener()
    {
        return new Bombermen();
    }
}
