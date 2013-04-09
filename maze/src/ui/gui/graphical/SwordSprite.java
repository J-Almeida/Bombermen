package ui.gui.graphical;

import java.awt.image.BufferedImage;

import logic.Sword;

public class SwordSprite implements AnimatedSprite {

	public SwordSprite(Sword toSword, TiledImage sprite) {
		_sprite = sprite;
	}

	@Override
	public void Update(int diff) {
	}

	@Override
	public BufferedImage GetCurrentImage() {
		return _sprite.GetBufferedImage();
	}

	private final TiledImage _sprite;

}
