package pt.up.fe.lpoo.bombermen.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import pt.up.fe.lpoo.bombermen.BombermenServer;
import pt.up.fe.lpoo.bombermen.Constants;


public class Tests {

    public final float floatDelta = 0.0001f;

    @Test
    public void testMapLoader() { // checks if the players are placed in the correct positions

        BombermenServer testServer;

        try {
            testServer = new BombermenServer(1111, "map0");

            System.out.println("server positions:");
            System.out.println(testServer.getPlayersPositions());

            ArrayList<Vector2> serverPlayersPositions = new ArrayList<Vector2>(testServer.getPlayersPositions());
            ArrayList<Vector2> expectedPlayerPositions = new ArrayList<Vector2>();

            int mapWidth = testServer.getMapWidth();
            int mapHeight = testServer.getMapHeight();

            // one player for each corner of the map
            expectedPlayerPositions.add( new Vector2(1.0f, 1.0f) );
            expectedPlayerPositions.add( new Vector2(mapWidth - 2.0f, 1.0f) );
            expectedPlayerPositions.add( new Vector2(1.0f, mapHeight - 2.0f) );
            expectedPlayerPositions.add( new Vector2(mapWidth - 2.0f, mapHeight - 2.0f) );

            for (int i = 0; i < expectedPlayerPositions.size(); i++)
                expectedPlayerPositions.set(i,
                        new Vector2(
                                expectedPlayerPositions.get(i).x * Constants.CELL_SIZE + 4,
                                expectedPlayerPositions.get(i).y * Constants.CELL_SIZE + 4)
                        );

            boolean allPositionsMatch = true;

            for (int i = 0; i < serverPlayersPositions.size(); i++)
                if ( !expectedPlayerPositions.contains(serverPlayersPositions.get(i)) )
                    allPositionsMatch = false;

            assertTrue(allPositionsMatch);

            testServer.Stop();

        } catch (IOException e) {
            fail("Failed creating test server");
        }

    }

}
