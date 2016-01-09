import java.awt.*;

import javax.swing.*;

public class USB extends Rectangle
{
	// private int xPos;
	// private int yPos;
	public boolean alive = true;
	boolean missile = false;
	// 84 x 128 pixels
	// Image by Deleket on Iconfinder.com; licensed under Creative Commons
	// (Attribution-Noncommercial-Share Alike 3.0 Unported)
	// License available at http://creativecommons.org/licenses/by-nc-sa/3.0/
	public Image USBImage = new ImageIcon("Assets/USB.png").getImage();
	public Laser[] laserList = new Laser[1000];
	private int ammo = 100;
	private int maxAmmo = 100;
	private final int maxUpgradedAmmo = 1000;
	private int newLaserIndex = 0;
	private double maxSystemIntegrity = 100;
	private double systemIntegrity = 100;
	private final double maxUpgradedSystemIntegrity = 2000;
	public int lasersActive = 0;
	private int laserUpgradeLevel = 0;
	private boolean firewall = false;
	private boolean godmode = false;
	private final int USB_WIDTH = 84;
	private final int USB_HEIGHT = 128;
	private final int PLAYER_LASER_WIDTH = 14;
	private final int PLAYER_LASER_HEIGHT = 71;
	private final int[] LASER_UPGRADE_COSTS = { 250, 1000, 4000, 16000 };

	public USB(int startingX, int startingY)

	{
		super(startingX, startingY);
		x = startingX;
		y = startingY;
		// setBounds(startingX, startingY, startingY, startingY);
	}

	public void fillSI()
	{
		systemIntegrity = maxSystemIntegrity;
	}

	public int getNewLaserIndex()
	{
		return newLaserIndex;
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
		newLaserIndex--;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public double virusPenetrated(double systemIntegrityLost)
	{
		if (!godmode)
		{
			if (!firewall)
			{
				if (systemIntegrity > systemIntegrityLost)
					systemIntegrity -= systemIntegrityLost;
				else
				{
					alive = false;
					return 0;
				}
			}
			else
			{
				if (systemIntegrity > systemIntegrityLost / 2)
					systemIntegrity -= systemIntegrityLost / 2;
				else
				{
					alive = false;
					return 0;
				}
			}
		}
		return systemIntegrity;
	}

	public double getSystemIntegrity()
	{
		return systemIntegrity;
	}

	public int getLaserUpgrade()
	{
		return laserUpgradeLevel;
	}

	public int getLaserUpgradeCost()
	{
		return LASER_UPGRADE_COSTS[laserUpgradeLevel];
	}

	public void upgradeLaser()
	{
		laserUpgradeLevel++;
	}

	public int getAmmo()
	{
		return ammo;
	}

	public int getMaxAmmo()
	{
		return maxAmmo;
	}

	public void upgradeAmmo()
	{
		if (maxAmmo < maxUpgradedAmmo)
			maxAmmo += 50;
		ammo = maxAmmo;
	}

	public void upgradeIntegrity()
	{
		if (maxSystemIntegrity < maxUpgradedSystemIntegrity)
			maxSystemIntegrity += 50;
		systemIntegrity = maxSystemIntegrity;
	}

	public void maxUpgrade()
	{
		maxAmmo = maxUpgradedAmmo;
		maxSystemIntegrity = maxUpgradedSystemIntegrity;
		laserUpgradeLevel = 4;
		ammo = maxAmmo;
		systemIntegrity = maxSystemIntegrity;
	}

	public double getMaxIntegrity()
	{
		return maxSystemIntegrity;
	}

	public boolean getFirewall()
	{
		return firewall;
	}

	/**
	 * Adds in a firewall which halves incomming damage to system integrity.
	 */
	public void upgradeFirewall()
	{
		firewall = true;
	}

	public void activateGodMode()
	{
		godmode = true;
	}

	/**
	 * replenishes ammo and S.I.
	 */
	public void replenish()
	{
		ammo = maxAmmo;
		systemIntegrity = maxSystemIntegrity;
	}

	/**
	 * Resets the player for a new game
	 */
	public void reset()
	{
		maxAmmo = 100;
		maxSystemIntegrity = 100;
		ammo = maxAmmo;
		systemIntegrity = maxSystemIntegrity;
		laserUpgradeLevel = 0;
		x = 512;
		y = 600;
		alive = true;
	}

	public void fire()
	{
		if (laserUpgradeLevel == 0)
		{
			laserList[newLaserIndex] = new Laser(x + 35, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			ammo--;
			newLaserIndex++;
			lasersActive++;
		}
		else if (laserUpgradeLevel == 1)
		{
			laserList[newLaserIndex] = new Laser(x + 25, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);

			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 45, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			newLaserIndex++;
			lasersActive++;
			ammo -= 2;
		}
		else if (laserUpgradeLevel == 2)
		{
			laserList[newLaserIndex] = new Laser(x + 15, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);

			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 35, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 55, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			newLaserIndex++;
			lasersActive++;
			ammo -= 3;
		}
		else if (laserUpgradeLevel == 3)
		{
			laserList[newLaserIndex] = new Laser(x + 15, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 3);

			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 15, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);

			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 35, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 3);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 35, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 55, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 3);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 55, y, PLAYER_LASER_WIDTH,
					PLAYER_LASER_HEIGHT, 1);
			newLaserIndex++;
			lasersActive++;
			ammo -= 3;
		}

		else if (laserUpgradeLevel == 4)
		{
			laserList[newLaserIndex] = new Laser(x - 10, y, 32,
					PLAYER_LASER_HEIGHT, 5);

			newLaserIndex++;
			lasersActive++;

			laserList[newLaserIndex] = new Laser(x - 10, y, 32,
					PLAYER_LASER_HEIGHT, 4);

			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 35, y, 32,
					PLAYER_LASER_HEIGHT, 5);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 35, y, 32,
					PLAYER_LASER_HEIGHT, 4);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 80, y, 32,
					PLAYER_LASER_HEIGHT, 5);
			newLaserIndex++;
			lasersActive++;
			laserList[newLaserIndex] = new Laser(x + 80, y, 32,
					PLAYER_LASER_HEIGHT, 4);
			newLaserIndex++;
			lasersActive++;
			ammo -= 3;
		}

	}

	public void healthUp()
	{
		systemIntegrity += 20;
		if (systemIntegrity > maxSystemIntegrity)
			systemIntegrity = maxSystemIntegrity;
	}

	public void ammoUp()
	{

		ammo += 20;
		if (ammo > maxAmmo)
			ammo = maxAmmo;
	}

	public void resetPlayer()
	{
		x = 512;
		y = 600;
	}

	public Rectangle getHitBox()
	{
		return new Rectangle(x, y, USB_WIDTH, USB_HEIGHT);
	}

	/**
	 * 
	 * Gives the y position of the USB to the main program
	 * 
	 * @return
	 */

	// paint component method

}
