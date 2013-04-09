package ui.gui.graphical.game;

import java.awt.image.BufferedImage;

import logic.Sword;
import model.Position;

public class SwordSprite implements AnimatedSprite {

    public SwordSprite(Sword u, TiledImage sprite) {
        _sprite = sprite;
        _unit = u;
    }

    @Override
    public void Update(int diff) {
    }

    @Override
    public BufferedImage GetCurrentImage() {
        return _sprite.GetBufferedImage();
    }

    private final TiledImage _sprite;
    private final Sword _unit;

	@Override
	public Position GetDeltaPosition(int cell_width, int cell_height) {
		return new Position(0,0);
	}

	@Override
	public Position GetPosition() {
		return _unit.GetPosition();
	}

}
