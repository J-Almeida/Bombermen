package tests;

import java.awt.Point;

import logic.Bomb;

public class TestBomb extends Bomb {

     public TestBomb(int guid, Point position, int playerOwnerId, int radius,int strength, int time)
     {
          super(guid, position, playerOwnerId, radius, strength, time);
     }
}
