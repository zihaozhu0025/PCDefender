import java.awt.*;
import javax.swing.*;

/**
 * A class to handle viruses and other enemies
 * 
 * @author Dennis Fan
 * 
 */
public class Virus extends Rectangle
{

	// boolean missile = false;
	public int laserIndex = 0;
	public Laser[] laserList = new Laser[30];
	public Image virusImage;
	private double[] virusAttributes = new double[2];
	private final int LASER_PROBABILITY = 0;
	private final int WHITE_HAT_VALUE = 1;
	private int level;
	private boolean alive = true;
	private final int WIDTH = 116;
	private final int HEIGHT = 120;
	private int enemyLaserWidth = 14;
	private int enemyLaserHeight = 23;
	private final Image VIRUS_1_IMAGE = new ImageIcon("Assets/Virus 1.png")
			.getImage();
	private final double[] VIRUS_1_ATTRIBUTES = { 0.15, 10, 1, 10, 5 };
	private final Image VIRUS_2_IMAGE = new ImageIcon("Assets/Virus 2.png")
			.getImage();
	private final double[] VIRUS_2_ATTRIBUTES = { 0.2, 20, 2, 15, 6 };
	private final Image VIRUS_3_IMAGE = new ImageIcon("Assets/Virus 3.png")
			.getImage();
	private final double[] VIRUS_3_ATTRIBUTES = { 0.1, 25, 1, 5, 5 };
	private final Image VIRUS_4_IMAGE = new ImageIcon("Assets/Virus 4.png")
			.getImage();
	private final double[] VIRUS_4_ATTRIBUTES = { 0, 30, 3, 20, 10 };
	private final Image VIRUS_5_IMAGE = new ImageIcon("Assets/Virus 5.png")
			.getImage();
	private final double[] VIRUS_5_ATTRIBUTES = { 0.3, 50, 3, 30, 10 };
	private final Image VIRUS_6_IMAGE = new ImageIcon("Assets/Virus 6.png")
			.getImage();
	private final double[] VIRUS_6_ATTRIBUTES = { 0.6, 80, 4, 80, 15 };

	// private Image missileImage = new ImageIcon("").getImage();
	// public laser[] laserList = new laser[100];
	// public int ammo = laserList.length;

	public Virus(int currentLevel)
	{
		super((int) (Math.random() * 800) + 50, 0);
		level = currentLevel;

		if (level < 3)
		{
			// 116 x 120 pixels
			virusImage = VIRUS_1_IMAGE;
			virusAttributes = VIRUS_1_ATTRIBUTES;
		}
		else if (level < 6)
		{
			if (Math.random() < 0.5)
			{
				virusImage = VIRUS_1_IMAGE;
				virusAttributes = VIRUS_1_ATTRIBUTES;
			}
			else
			{
				virusImage = VIRUS_2_IMAGE;
				virusAttributes = VIRUS_2_ATTRIBUTES;
			}
		}
		else if (level <= 10)
		{
			if (Math.random() < 0.2)
			{
				virusImage = VIRUS_3_IMAGE;
				virusAttributes = VIRUS_3_ATTRIBUTES;
			}
			else if (Math.random() < 0.7)
			{
				virusImage = VIRUS_2_IMAGE;
				virusAttributes = VIRUS_2_ATTRIBUTES;
			}
			else
			{
				virusImage = VIRUS_1_IMAGE;
				virusAttributes = VIRUS_1_ATTRIBUTES;
			}
		}
		else if (level <= 15)
		{
			if (Math.random() < 0.3)
			{
				virusImage = VIRUS_3_IMAGE;
				virusAttributes = VIRUS_3_ATTRIBUTES;
			}
			else if (Math.random() < 0.5)
			{
				virusImage = VIRUS_4_IMAGE;
				virusAttributes = VIRUS_4_ATTRIBUTES;
			}
			else if (Math.random() < 0.9)
			{
				virusImage = VIRUS_2_IMAGE;
				virusAttributes = VIRUS_2_ATTRIBUTES;
			}
			else
			{
				virusImage = VIRUS_1_IMAGE;
				virusAttributes = VIRUS_1_ATTRIBUTES;
			}
		}
		else if (level <= 21)
		{
			if (Math.random() < 0.3)
			{
				virusImage = VIRUS_5_IMAGE;
				virusAttributes = VIRUS_5_ATTRIBUTES;
			}
			else if (Math.random() < 0.5)
			{
				virusImage = VIRUS_4_IMAGE;
				virusAttributes = VIRUS_4_ATTRIBUTES;
			}
			else if (Math.random() < 0.8)
			{
				virusImage = VIRUS_3_IMAGE;
				virusAttributes = VIRUS_3_ATTRIBUTES;
			}
			else if (Math.random() < 0.95)
			{
				virusImage = VIRUS_2_IMAGE;
				virusAttributes = VIRUS_2_ATTRIBUTES;
			}
			else
			{
				virusImage = VIRUS_1_IMAGE;
				virusAttributes = VIRUS_1_ATTRIBUTES;
			}
		}
		else if (level < 25)
		{
			if (Math.random() < 0.5)
			{
				virusImage = VIRUS_6_IMAGE;
				virusAttributes = VIRUS_6_ATTRIBUTES;
			}
			else if (Math.random() < 0.75)
			{
				virusImage = VIRUS_5_IMAGE;
				virusAttributes = VIRUS_5_ATTRIBUTES;
			}
			else if (Math.random() < 0.85)
			{
				virusImage = VIRUS_4_IMAGE;
				virusAttributes = VIRUS_4_ATTRIBUTES;
			}
			else if (Math.random() < 0.92)
			{
				virusImage = VIRUS_3_IMAGE;
				virusAttributes = VIRUS_3_ATTRIBUTES;
			}
			else if (Math.random() < 0.97)
			{
				virusImage = VIRUS_2_IMAGE;
				virusAttributes = VIRUS_2_ATTRIBUTES;
			}
			else
			{
				virusImage = VIRUS_1_IMAGE;
				virusAttributes = VIRUS_1_ATTRIBUTES;
			}
		}
		else
		{
			virusImage = VIRUS_6_IMAGE;
			virusAttributes = VIRUS_6_ATTRIBUTES;
		}

		x = (int) (Math.random() * 800) + 50;
		y = 0;
	}

	public double getLaserProbability()
	{
		return virusAttributes[0];
	}

	public double getValue()
	{
		return virusAttributes[1];
	}

	public double getLaserSpeed()
	{
		return virusAttributes[4];
	}

	public int move()
	{
		if (y < 770)
			y += virusAttributes[2];
		else
		{
			alive = false;
			return -1;
		}
		return y;
	}

	public void removeVoidLasers(int removedLaser)
	{
		if (removedLaser == laserList.length - 1)
		{
			laserList[removedLaser] = null;
		}
		else
			for (int movedLaser = removedLaser; movedLaser < laserList.length - 1
					&& laserList[movedLaser] != null; movedLaser++)
			{
				laserList[movedLaser] = laserList[movedLaser + 1];
			}
		laserIndex--;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public int getSystemHarm()
	{
		return (int) virusAttributes[3];
	}

	// public int lasersFired = 0;
	public void fire()
	{
		laserList[laserIndex] = new Laser(x + 50, y + 20, enemyLaserWidth,
				enemyLaserHeight, 2);
		laserIndex++;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, HEIGHT, WIDTH);
	}
	/**
	 * 
	 * Gives the y position of the USB to the main program
	 * 
	 * @return
	 */

	// paint component method

}