import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Laser extends Rectangle
{
	boolean destroyed = false;
	private int lHeight;
	private int lWidth;
	public Image laserImage;
	private int laserType;

	Laser(int startingXPos, int startingYPos, int width, int height,
			int laserTypeGiven)
	{
		x = startingXPos;
		y = startingYPos;
		lWidth = width;
		lHeight = height;
		laserType = laserTypeGiven;
		if (laserType == 2)
		{
			// Image custom made
			laserImage = new ImageIcon("Assets/Enemy Laser.png").getImage();
		}
		else if (laserType == 1)
		{
			laserImage = new ImageIcon("Assets/Regular Laser.png").getImage();
		}
		else if (laserType == 3)
		{
			laserImage = new ImageIcon("Assets/Upgraded Laser.png").getImage();
		}
		else if (laserType == 4)
			laserImage = new ImageIcon("Assets/Wide Laser.png").getImage();
		else if (laserType == 5)
			laserImage = new ImageIcon("Assets/Wide Laser Piercing.png")
					.getImage();

	}

	// 14 x 71 pixels.

	public void laserMoveUp()
	{

		if (y > 0)
			y -= 20;

		else
			destroyed = true;

	}

	public void laserMoveDown(double laserSpeed)
	{
		if (y < 768)
			y += laserSpeed;
		else
			destroyed = true;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, lWidth, lHeight);
	}
}