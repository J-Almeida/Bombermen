package pt.up.fe.lpoo.bombermen.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import pt.up.fe.lpoo.bombermen.EntityBuilder;
import pt.up.fe.lpoo.bombermen.entities.Bomb;
import pt.up.fe.lpoo.bombermen.entities.Explosion;
import pt.up.fe.lpoo.bombermen.entities.Player;
import pt.up.fe.lpoo.bombermen.entities.PowerUp;
import pt.up.fe.lpoo.bombermen.entities.Wall;
import pt.up.fe.lpoo.utils.Direction;
import pt.up.fe.lpoo.utils.Ref;

public class Tests {

    public final float floatDelta = 0.0001f;

    @Test
    public void testApplyDirectionX() {
        float positionX = 2.0f;
        final float testIncrement = 1.0f;
        assertEquals(positionX, Direction.ApplyMovementX(positionX, Direction.NONE, testIncrement), floatDelta);
        assertEquals(positionX, Direction.ApplyMovementX(positionX, Direction.NORTH, testIncrement), floatDelta);
        assertEquals(positionX + testIncrement, Direction.ApplyMovementX(positionX, Direction.EAST, testIncrement), floatDelta);
        assertEquals(positionX, Direction.ApplyMovementX(positionX, Direction.SOUTH, testIncrement), floatDelta);
        assertEquals(positionX - testIncrement, Direction.ApplyMovementX(positionX, Direction.WEST, testIncrement), floatDelta);
    }

    @Test
    public void testApplyDirectionY() {
        float positionY = 3.0f;
        final float testIncrement = 1.0f;
        assertEquals(positionY, Direction.ApplyMovementY(positionY, Direction.NONE, testIncrement), floatDelta);
        assertEquals(positionY + testIncrement, Direction.ApplyMovementY(positionY, Direction.NORTH, testIncrement), floatDelta);
        assertEquals(positionY, Direction.ApplyMovementY(positionY, Direction.EAST, testIncrement), floatDelta);
        assertEquals(positionY - testIncrement, Direction.ApplyMovementY(positionY, Direction.SOUTH, testIncrement), floatDelta);
        assertEquals(positionY, Direction.ApplyMovementY(positionY, Direction.WEST, testIncrement), floatDelta);
    }

    @Test
    public void testRef() {
        Ref<Integer> testRef = new Ref<Integer>(5);
        assertEquals( (Integer) 5, testRef.Get());
        testRef.Set(8);
        assertEquals( (Integer) 8, testRef.Get());
        assertEquals( "8", testRef.toString());

        Ref<Integer> testRef2 = new Ref<Integer> (10);
        assertNotSame(testRef.hashCode(), testRef2.hashCode());
        testRef.Set(10);
        assertEquals(testRef.hashCode(), testRef2.hashCode());
    }

    @Test
    public void testEntityBuilder_Bomb() {
        final int bombGuid = 10, bombCreatorGuid = 11;
        final float bombX = 12.0f, bombY = 13.0f;

        EntityBuilder.LoadTextures = false;
        Bomb testBomb = EntityBuilder.CreateBomb(bombGuid, bombCreatorGuid, bombX, bombY);

        assertEquals(bombGuid, testBomb.GetGuid());
        assertEquals(bombCreatorGuid, testBomb.GetCreatorGuid());
        assertEquals(bombX, testBomb.getX(), floatDelta);
        assertEquals(bombY, testBomb.getY(), floatDelta);
    }

    @Test
    public void testEntityBuilder_Player() {
        final int playerGuid = 20, playerScore = 21;
        final float playerX = 22.0f, playerY = 24.0f;
        final String playerName = new String("testPlayerName");

        EntityBuilder.LoadTextures = false;
        Player testPlayer = EntityBuilder.CreatePlayer(playerGuid, playerName, playerScore, playerX, playerY);

        assertEquals(playerGuid, testPlayer.GetGuid());
        assertEquals(playerScore, testPlayer.GetScore());
        assertEquals(playerName, testPlayer.getName());
        assertEquals(playerX, testPlayer.getX(), floatDelta);
        assertEquals(playerY, testPlayer.getY(), floatDelta);
    }

    @Test
    public void testEntityBuilder_Wall() {
        final int wallGuid = 30, wallHitPoints = 31;
        final float wallX = 32.0f, wallY = 33.0f;

        EntityBuilder.LoadTextures = false;
        Wall testWall = EntityBuilder.CreateWall(wallGuid, wallHitPoints, wallX, wallY);

        assertEquals(wallGuid, testWall.GetGuid());
        assertEquals(wallHitPoints, testWall.GetHitPoints());
        assertEquals(wallX, testWall.getX(), floatDelta);
        assertEquals(wallY, testWall.getY(), floatDelta);
    }

    @Test
    public void testEntityBuilder_PowerUp() {
        final int powerUpGuid = 40, powerUpType = 4;
        final float powerUpX = 42.0f, powerUpY = 43.0f;

        EntityBuilder.LoadTextures = false;
        PowerUp testPowerUp = EntityBuilder.CreatePowerUp(powerUpGuid, powerUpX, powerUpY, powerUpType);

        assertEquals(powerUpGuid, testPowerUp.GetGuid());
        assertEquals(powerUpX, testPowerUp.getX(), floatDelta);
        assertEquals(powerUpY, testPowerUp.getY(), floatDelta);
    }

    @Test
    public void testEntityBuilder_Explosion() {
        final int explosionGuid = 50, explosionDir = Direction.NONE;
        final float explosionX = 51.0f, explosionY = 52.0f;
        final boolean explosionEnd = false;

        EntityBuilder.LoadTextures = false;
        Explosion testExplosion = EntityBuilder.CreateExplosion(explosionGuid, explosionX, explosionY, explosionDir, explosionEnd);

        assertEquals(explosionGuid, testExplosion.GetGuid());
        assertEquals(explosionX, testExplosion.getX(), floatDelta);
        assertEquals(explosionY, testExplosion.getY(), floatDelta);
    }
}
