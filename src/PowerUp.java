import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PowerUp extends Rectangle

{
	Image powerUpImage;
	private final int HEALTH_TYPE = 1;
	private final int AMMO_TYPE = 2;
	private final int WHITE_HATS_TYPE = 3;
	private final int HEIGHT = 53;
	private final int WIDTH = 56;
	private int type;

	PowerUp(int typeGiven, int xPos, int yPos)
	{
		type = typeGiven;
		if (type == 1)

			powerUpImage = new ImageIcon("Assets/HP PowerUp.png").getImage();

		else if (type == 3)
			powerUpImage = new ImageIcon("Assets/white hat.png").getImage();
		else if (type == 2)
			powerUpImage = new ImageIcon("Assets/ammoUp.png").getImage();
		x=xPos;
		y=yPos;
	}

	public Rectangle getHitBox()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public int checkPickedUp(Rectangle playerHitBox)
	{
		if (playerHitBox.intersects(getHitBox()))
		{
			return type;
		}
		return 0;
	}
}
