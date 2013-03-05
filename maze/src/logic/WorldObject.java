/**
 * 
 */
package logic;

/**
 * The WorldObject Unit.
 */
public abstract class WorldObject
{
	public final WorldObjectType Type;
	
	public WorldObject(WorldObjectType type) {
		Type = type;
		// TODO Auto-generated constructor stub
	}
	
	public boolean IsWall() { return Type == WorldObjectType.Wall; }
	public boolean IsPath() { return Type == WorldObjectType.Path; }
	public boolean IsUnit() { return Type == WorldObjectType.Unit; }
	
	public Wall ToWall() { if (IsWall()) return (Wall)this; else return null;};
	public Path ToPath() { if (IsPath()) return (Path)this; else return null;};
	public Unit ToUnit() { if (IsUnit()) return (Unit)this; else return null;};

}
