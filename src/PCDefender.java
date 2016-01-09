import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * PC Defender: a game where you try to protect your PC at all costs
 * 
 * Purchase upgrades using white hats to eliminate the malicious virus invasion
 * 
 * @author Dennis Fan and Peter Zhu
 * @version June 10th, 2014
 */

// A Frame for the main program
public class PCDefender extends JFrame
{
	// Main drawing area inside the main frame
	private DrawingPanel drawingArea;

	// Defines configurable variables for PC Defender
	private final static String GAME_NAME = "PC Defender";
	private final int GAME_WIDTH = 1024;
	private final int GAME_HEIGHT = 768;
	// Icon from Aha-Soft on IconFinder.com; used with free commercial license
	private final String FAVICON = "Assets/Favicon.png";
	private int level = 1;
	private double whiteHats = 0;
	private int[] numberOfViruses = { 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50,
			55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 110, 120, 130, 150, 200 };
	private double[] spawnRates = { 0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.6, 0.6,
			0.6, 0.6, 0.6, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.8, 0.8, 0.8, 0.8,
			0.9, 0.9, 0.9, 1 };
	private int virusesSpawned = 0;
	private int virusesOnScreen = 0;
	private int virusIndex = 0;
	private final int MAX_NO_OF_VIRUSES = 200;
	private PowerUp[] powerUps = new PowerUp[MAX_NO_OF_VIRUSES];
	private Virus[] viruses = new Virus[MAX_NO_OF_VIRUSES];
	private int gameStage = 0;
	private final int MAIN_MENU = 0;
	private final int HELP_SCREEN = 1;
	private final int ABOUT_SCREEN = 2;
	private final int LEVEL_SELECT = 3;
	private final int UPGRADE_STORE = 4;
	private final int GAME_PLAY = 5;
	private final int GAME_OVER = 6;
	private final int PAUSED = 7;
	private int powerUpIndex = 0;
	private final int[] SPAWN_INTERVAL = { 0, 500, 500, 500, 500, 500, 450,
			450, 450, 450, 450, 400, 400, 400, 400, 400, 375, 375, 375, 375,
			375, 350, 350, 350, 350, 325 };
	private final int GRAPHIC_UPDATE_INTERVAL = 30;
	// Audio from musicmonks on AudioJungle; regular license obtained
	private AudioClip menuBackground = Applet
			.newAudioClip(getCompleteURL("Audio/Indie.au"));
	// Audio from MikeSea on AudioJungle; regular license obtained
	private AudioClip gameBackground = Applet
			.newAudioClip(getCompleteURL("Audio/Solaris.au"));
	// Audio from dealerdesign on AudioJungle; regular license obtained
	private AudioClip quarantine = Applet
			.newAudioClip(getCompleteURL("Audio/Quarantine.wav"));
	// Audio from wavelia on AudioJungle; regular license Obtained
	private AudioClip scratch = Applet
			.newAudioClip(getCompleteURL("Audio/Scratch.wav"));
	// Image custom made; background from zmkstudio on PhotoDune, regular
	// license obtained
	private final Image MENU_IMAGE = new ImageIcon("Assets/Menu.png")
			.getImage();
	// Image custom made; background from DeathToStock.com, used with free
	// commercial license
	private final Image HELP_SCREEN_IMAGE = new ImageIcon(
			"Assets/Instructions.png").getImage();
	// Image custom made
	private final Image ABOUT_SCREEN_IMAGE = new ImageIcon("Assets/About.png")
			.getImage();
	// Image custom made; background from Victor Hanacek of PicJumbo, used with
	// free commercial license
	private final Image LEVEL_SELECT_IMAGE = new ImageIcon(
			"Assets/Level Selection.png").getImage();
	// Image custom made; background from DeathToStock.com, used with free
	// commercial license
	private final Image UPGRADE_STORE_IMAGE = new ImageIcon(
			"Assets/Upgrade Store.png").getImage();
	// Image custom made
	private final Image HUD_BACKGROUND = new ImageIcon("Assets/HUD.png")
			.getImage();
	// Image from Victor Hanacek on PicJumbo; free commercial license
	private final Image PAUSE_BACKGROUND = new ImageIcon(
			"Level Backgrounds/pauseBackground.jpg").getImage();
	// Image from the open clip art library; public domain
	private final Image GAME_OVER_PIC = new ImageIcon("Assets/Game Over.png")
			.getImage();
	private int highestLevelCleared = 0;
	/**
	 * #1: "Road" by vladmoses on PhotoDune; regular license obtained
	 * 
	 * #2: "Keyboard" by Pressmaster on PhotoDune; regular license obtained
	 * 
	 * #3: "Picture Your Family at the Beach" by butlerm on PhotoDune; regular
	 * license obtained
	 * 
	 * #4: "Hand Pressing Button" byzmkstudio on PhotoDune; regular license
	 * obtained
	 * 
	 * #5: "Computer Service" by sbotas on PhotoDune; regular license obtained
	 * 
	 * #6: "Dark Ominous Clouds of Glory" by Graphic-Studio on PhotoDune;
	 * regular license obtained
	 * 
	 * #7: "Audio Mixing Desk Knobs & Controls" by SamBerson on PhotoDune;
	 * regular license obtained
	 * 
	 * #8-#13, #20-#25: Part of the Hi-Res Photoset by moonloop on
	 * CreativeMarket; SimpleLicense obtained
	 * 
	 * #14-#19: Images from Victor Hanacek on PicJumbo; free commercial license
	 * provided
	 * 
	 */
	private final Image[] GAME_BACKGROUNDS = { null,
			new ImageIcon("Level Backgrounds/1.jpg").getImage(),
			new ImageIcon("Level Backgrounds/2.jpg").getImage(),
			new ImageIcon("Level Backgrounds/3.jpg").getImage(),
			new ImageIcon("Level Backgrounds/4.jpg").getImage(),
			new ImageIcon("Level Backgrounds/5.jpg").getImage(),
			new ImageIcon("Level Backgrounds/6.jpg").getImage(),
			new ImageIcon("Level Backgrounds/7.jpg").getImage(),
			new ImageIcon("Level Backgrounds/8.jpg").getImage(),
			new ImageIcon("Level Backgrounds/9.jpg").getImage(),
			new ImageIcon("Level Backgrounds/10.jpg").getImage(),
			new ImageIcon("Level Backgrounds/11.jpg").getImage(),
			new ImageIcon("Level Backgrounds/12.jpg").getImage(),
			new ImageIcon("Level Backgrounds/13.jpg").getImage(),
			new ImageIcon("Level Backgrounds/14.jpg").getImage(),
			new ImageIcon("Level Backgrounds/15.jpg").getImage(),
			new ImageIcon("Level Backgrounds/16.jpg").getImage(),
			new ImageIcon("Level Backgrounds/17.jpg").getImage(),
			new ImageIcon("Level Backgrounds/18.jpg").getImage(),
			new ImageIcon("Level Backgrounds/19.jpg").getImage(),
			new ImageIcon("Level Backgrounds/20.jpg").getImage(),
			new ImageIcon("Level Backgrounds/21.jpg").getImage(),
			new ImageIcon("Level Backgrounds/22.jpg").getImage(),
			new ImageIcon("Level Backgrounds/23.jpg").getImage(),
			new ImageIcon("Level Backgrounds/24.jpg").getImage(),
			new ImageIcon("Level Backgrounds/25.jpg").getImage() };

	/**
	 * Constructs the USB player
	 */
	USB player = new USB(512, 600);

	/**
	 * Constructs a new main frame
	 */
	public PCDefender()
	{
		super(GAME_NAME);
		gameStage = MAIN_MENU;
		setLocation(70, 20);

		// Add in an Icon
		setIconImage(new ImageIcon(FAVICON).getImage());

		// We need to get the contentPane for the Frame
		// And then we can add the drawing area to the contentPane
		Container contentPane = getContentPane();
		drawingArea = new DrawingPanel();
		contentPane.add(drawingArea, BorderLayout.CENTER);
		menuBackground.loop();

	}

	/**
	 * Gets the URL needed for audio files
	 * 
	 * @param fileName the name of the file
	 * @author Mr. Ridout
	 * @return the URL
	 */
	public URL getCompleteURL(String fileName)
	{
		try
		{
			return new URL("file:" + System.getProperty("user.dir") + "/"
					+ fileName);
		}
		catch (MalformedURLException e)
		{
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Adds a given amount to the current amount of white hats
	 * 
	 * @param amountToAdd
	 */
	public void addWhiteHats(double amountToAdd)
	{
		whiteHats += amountToAdd;
	}

	/**
	 * Removes the virus in the given index from the viruses array and shifts
	 * the back of the array up 1 space
	 * 
	 * @param removedVirus the index of the virus to be removed from the array.
	 */
	public void removeVoidViruses(int removedVirus)
	{
		// If the given index is the last element of the array there is no back
		// of array to shift up.
		if (removedVirus == viruses.length - 1)
		{
			viruses[removedVirus] = null;
		}
		else
			for (int movedVirus = removedVirus; movedVirus < viruses.length - 1
					&& viruses[movedVirus] != null; movedVirus++)
			{
				viruses[movedVirus] = viruses[movedVirus + 1];
			}
		// Shifts back the index of the next virus.
		virusIndex--;
	}

	/**
	 * Removes the power up in the given index from the powerUps array and
	 * shifts the back of the array up 1 space.
	 * 
	 * @param removedPowerUp the index of the power up to be removed.
	 */

	public void removeVoidPowerUps(int removedPowerUp)
	{
		if (removedPowerUp == powerUps.length - 1)
		{
			powerUps[removedPowerUp] = null;
		}
		else
			for (int movedPowerUp = removedPowerUp; movedPowerUp < powerUps.length - 1
					&& powerUps[movedPowerUp] != null; movedPowerUp++)
			{
				powerUps[movedPowerUp] = powerUps[movedPowerUp + 1];
			}
		// Shifts back the index of the next power up
		powerUpIndex--;
	}

	/**
	 * Starts a new game
	 */
	public void startGame()
	{
		// Go to the main menu and reset variables
		gameStage = MAIN_MENU;
		highestLevelCleared = 0;
		int noOfViruses = virusIndex;
		for (int currentVirus = 0; currentVirus < noOfViruses; currentVirus++)
		{
			removeVoidViruses(currentVirus);
		}
		int noOfPowerUps = powerUpIndex;
		for (int currentPowerUp = 0; currentPowerUp < noOfPowerUps; currentPowerUp++)
		{
			removeVoidViruses(currentPowerUp);
		}

		virusesSpawned = 0;
		virusIndex = 0;
		virusesOnScreen = 0;
		// Resets all the stats in the player object.
		player.reset();

	}
	// Inner class for the drawing area
	private class DrawingPanel extends JPanel
	{

		// Handle code for the timer
		private Timer spawnTimer;
		private Timer updateTimer;

		/**
		 * Constructs a new DrawingPanel object
		 */
		public DrawingPanel()
		{
			setFont(new Font("Orator Std", Font.PLAIN, 20));
			setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

			// Add mouse listeners to the drawing panel
			this.addMouseListener(new MouseHandler());

			// Add key listeners and setting focus
			this.setFocusable(true);
			this.addKeyListener(new KeyHandler());
			this.requestFocusInWindow();

			// Creates the two timers
			spawnTimer = new Timer(SPAWN_INTERVAL[level],
					new VirusSpawnHandler());
			spawnTimer.start();

			updateTimer = new Timer(GRAPHIC_UPDATE_INTERVAL,
					new updateHandler());
			updateTimer.start();
		}

		/**
		 * Repaint the drawing panel
		 * 
		 * @param g The Graphics context
		 */
		public void paintComponent(Graphics g)
		{

			super.paintComponent(g);

			// Draw the main menu
			if (gameStage == MAIN_MENU)
			{
				g.drawImage(MENU_IMAGE, 0, 0, this);
			}
			// Draw the help screen
			else if (gameStage == HELP_SCREEN)
			{
				g.drawImage(HELP_SCREEN_IMAGE, 0, 0, this);
			}
			// Draw the about screen
			else if (gameStage == ABOUT_SCREEN)
			{
				g.drawImage(ABOUT_SCREEN_IMAGE, 0, 0, this);
			}
			// Draw the level selection screen
			else if (gameStage == LEVEL_SELECT)
			{
				g.drawImage(LEVEL_SELECT_IMAGE, 0, 0, this);
				// Draw OKs for completed levels
				g.setColor(Color.green);
				setFont(new Font("Arial", Font.PLAIN, 24));

				for (int check = 0; check < highestLevelCleared || check == 12; check++)
				{

					g.drawString("OK", 250, 160 + (check) * 53);
				}
				// Points to the highest available level
				if (highestLevelCleared < 12)
					g.drawString("\u2190", 250, 155 + 53 * highestLevelCleared);

				else if (highestLevelCleared < 24)
				{
					for (int check = 0; check < highestLevelCleared - 12; check++)
					{

						g.drawString("OK", 500, 160 + (check) * 53);
					}
					// Points to the highest available level
					g.drawString("\u2190", 500,
							155 + 53 * (highestLevelCleared - 12));
				}
				else if (highestLevelCleared == 24)
				{
					for (int check = 0; check < highestLevelCleared - 12; check++)
					{

						g.drawString("OK", 500, 160 + (check) * 53);
					}
					g.drawString("\u2190", 980, 200);
				}
				else
				{
					for (int check = 0; check < highestLevelCleared - 12; check++)
					{

						g.drawString("OK", 500, 160 + (check) * 53);
					}
					g.drawString("OK", 980, 200);
				}

			}
			// Draws the upgrades shop
			else if (gameStage == UPGRADE_STORE)
			{
				g.drawImage(UPGRADE_STORE_IMAGE, 0, 0, this);
			}

			// Draw the gameplay / pause screen / game over screen
			else if (gameStage == GAME_PLAY || gameStage == PAUSED
					|| gameStage == GAME_OVER)
			{
				// Draw the background
				g.drawImage(GAME_BACKGROUNDS[level], 0, 0, this);
				// Code to draw viruses
				for (int noOfViruses = 0; noOfViruses < virusIndex; noOfViruses++)
				{
					Virus currentVirus = viruses[noOfViruses];
					// Draws the lasers from the current virus
					for (int currentLaserNum = 0; currentLaserNum < currentVirus.laserIndex; currentLaserNum++)
					{
						Laser currentLaser = currentVirus.laserList[currentLaserNum];

						g.drawImage(currentLaser.laserImage, currentLaser.x,
								currentLaser.y, this);
					}

					// Draws the current virus
					g.drawImage(currentVirus.virusImage, currentVirus.x,
							currentVirus.y, this);

				}
				// Draws the power ups.
				for (int noOfPowerUps = 0; noOfPowerUps < powerUpIndex; noOfPowerUps++)
				{
					g.drawImage(powerUps[noOfPowerUps].powerUpImage,
							powerUps[noOfPowerUps].x, powerUps[noOfPowerUps].y,
							this);
				}
				// Draws all the lasers
				for (int noOfLasers = player.getNewLaserIndex() - 1; noOfLasers >= 0; noOfLasers--)
				{
					g.drawImage(player.laserList[noOfLasers].laserImage,
							player.laserList[noOfLasers].x,
							player.laserList[noOfLasers].y, this);
				}

				// Draw the USB
				g.drawImage(player.USBImage, player.x, player.y, this);
				g.setColor(Color.BLACK);

				// Draw the HUD
				g.drawImage(HUD_BACKGROUND, 0, 0, this);
				g.setColor(Color.white);
				g.fillRect(412, 75, 200, 10);
				// Draw the system integrity bar
				if (player.isAlive())
				{
					g.drawString(
							"System Integrity: " + player.getSystemIntegrity()
									+ "/" + player.getMaxIntegrity(), 350, 50);

					g.setColor(Color.red);
					g.fillRect(412, 75, (int) (((double) player
							.getSystemIntegrity() / (double) player
							.getMaxIntegrity()) * 200), 10);
					g.setColor(Color.white);
				}
				setFont(new Font("Orator Std", Font.PLAIN, 20));
				g.drawString(
						"Ammo: " + player.getAmmo() + "/" + player.getMaxAmmo(),
						850, 80);
				g.drawString("White Hats: " + whiteHats, 50, 80);
				g.drawImage(new ImageIcon("Assets/White Hats.png").getImage(),
						50, 90, this);
				g.drawString("Level: " + level, 50, 50);
				g.drawString("Viruses: "
						+ (numberOfViruses[level] - virusesSpawned), 870, 50);
				g.drawString("P - Toggle Pause", 450, 110);
				g.drawString("L - Level Select", 450, 130);

				if (gameStage == GAME_OVER)
					g.drawImage(GAME_OVER_PIC, 0, 0, this);
			}

		} // paint component method
	}

	/**
	 * A class to handle virus and laser spawning
	 * 
	 * @author Dennis Fan and Peter Zhu
	 * 
	 */
	private class VirusSpawnHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (gameStage == GAME_PLAY)
			{
				// Handle virus shooting
				for (int noOfViruses = 0; noOfViruses < virusIndex; noOfViruses++)
				{
					if (Math.random() < viruses[noOfViruses]
							.getLaserProbability())
					{
						viruses[noOfViruses].fire();
					}
				}

				// Spawn viruses
				if (numberOfViruses[level] > virusesSpawned)
				{
					// Spawning a virus
					if (Math.random() <= spawnRates[level])
					{
						if (virusIndex == MAX_NO_OF_VIRUSES - 1
								&& !viruses[0].isAlive())
							virusIndex = 0;
						viruses[virusIndex] = new Virus(level);
						virusesSpawned++;
						virusesOnScreen++;
						virusIndex++;
					}
				}
			}
		}
	}

	/**
	 * A class to update the game and graphics
	 * 
	 * @author Dennis Fan and Peter Zhu
	 * 
	 */
	private class updateHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (gameStage == GAME_PLAY)
			{

				if (!player.isAlive())
				{
					// Code for ending the game
					scratch.play();
					gameStage = GAME_OVER;
					repaint();
				}
				// Goes through all the viruses and processes their actions.
				for (int noOfViruses = 0; noOfViruses < virusIndex; noOfViruses++)
				{
					Virus currentVirus = viruses[noOfViruses];
					// Removes the current virus if it is off screen.
					if (currentVirus.move() == -1)
					{
						// Makes the player lose system integrity
						player.virusPenetrated(viruses[noOfViruses]
								.getSystemHarm());
						virusesOnScreen--;
						removeVoidViruses(noOfViruses);
						quarantine.play();

					}

					// checks collisions with the USB.
					else if ((viruses[noOfViruses].getBounds())
							.intersects(player.getHitBox()))
					{
						// Finds out how much system integrity is to be lost.
						double systemIntegrityLost = viruses[noOfViruses]
								.getSystemHarm();
						virusesOnScreen--;
						// Remove the collided virus.
						removeVoidViruses(noOfViruses);
						quarantine.play();
						player.virusPenetrated(systemIntegrityLost);
					}
					// Checks if the lasers from the current virus has hit the
					// USB or the end of the screen.
					for (int noOfLasers = 0; noOfLasers < currentVirus.laserIndex; noOfLasers++)
					{
						Laser currentLaser = currentVirus.laserList[noOfLasers];
						// Moves the laser down
						currentLaser
								.laserMoveDown(currentVirus.getLaserSpeed());
						// Checks if the current laser needs to be removed
						if (currentLaser.y > 768
								|| currentLaser.getBounds().intersects(
										player.getHitBox()))
						{
							// Checks if the current laser hits the player.
							if (currentLaser.getBounds().intersects(
									player.getHitBox()))

							{
								// Finds how much System integrity is lost and
								// subtracts that from the current system
								// integrity.
								double systemIntegrityLost = viruses[noOfViruses]
										.getSystemHarm();
								player.virusPenetrated(systemIntegrityLost);
							}
							// Removes the currentLaser
							currentVirus.removeVoidLasers(noOfLasers);

						}

					}
				}
				// Goes through every laser from the laser and processes them.
				for (int noOfLasers = 0; noOfLasers < player.getNewLaserIndex(); noOfLasers++)
				{

					if (!(player.laserList[noOfLasers].destroyed))
					{
						// Moves the current laser up.
						player.laserList[noOfLasers].laserMoveUp();
						// Checks if the laser has hit any of the viruses
						for (int noOfViruses = 0; noOfViruses < virusIndex; noOfViruses++)
						{

							if ((player.laserList[noOfLasers].getBounds())
									.intersects((viruses[noOfViruses])
											.getBounds()))
							{
								player.laserList[noOfLasers].destroyed = true;
								// Adds the appropriate amount to the white hat
								// score
								addWhiteHats(viruses[noOfViruses].getValue());

								quarantine.play();
								// 10% chance of generating bonuses when virus
								// is hit.
								if (Math.random() < 0.1)
								{
									// Picks which bonus to generate
									powerUps[powerUpIndex] = new PowerUp(
											(int) (Math.random() * 3) + 1,
											viruses[noOfViruses].x,
											viruses[noOfViruses].y);
									powerUpIndex++;

								}
								virusesOnScreen--;
								removeVoidViruses(noOfViruses);

							}

						}
					}

					else
					{
						player.removeVoidLasers(noOfLasers);
						noOfLasers--;
						player.lasersActive--;
					}
				}
				// checks if the player has hit a power up
				for (int noOfPowerUps = 0; noOfPowerUps < powerUpIndex; noOfPowerUps++)
				{
					// Checks if the player is on a bonus.
					if (powerUps[noOfPowerUps]
							.checkPickedUp(player.getHitBox()) == 1)
					{
						player.healthUp();

						removeVoidPowerUps(noOfPowerUps);
					}
					// Checks if the player is on an ammo bonus
					else if (powerUps[noOfPowerUps].checkPickedUp(player
							.getHitBox()) == 2)
					{
						player.ammoUp();

						removeVoidPowerUps(noOfPowerUps);
					}
					// Checks if the player is on a white hat bonus
					else if (powerUps[noOfPowerUps].checkPickedUp(player
							.getHitBox()) == 3)
					{
						whiteHats += 20;
						removeVoidPowerUps(noOfPowerUps);
					}

				}

				// Check if a level is completed
				if (numberOfViruses[level] == virusesSpawned
						&& virusesOnScreen == 0)
				{
					// Checks if the level just completed was the highest level
					// completed
					if (level > highestLevelCleared)
					{
						highestLevelCleared = level;
					}

					// Resets all the stats and goes on back to the level
					// selection screen.
					player.resetPlayer();
					player.replenish();
					virusesSpawned = 0;
					virusIndex = 0;
					virusesOnScreen = 0;
					int noOfLasers = player.getNewLaserIndex();
					for (int currentLaser = 0; currentLaser < noOfLasers; currentLaser++)
					{
						player.removeVoidLasers(currentLaser);
					}

					int noOfPowerUps = powerUpIndex;
					for (int currentPowerUp = 0; currentPowerUp < noOfPowerUps; currentPowerUp++)
					{
						removeVoidPowerUps(currentPowerUp);
					}
					gameStage = LEVEL_SELECT;
					repaint();
				}

				repaint();
			}
		}
	}

	/**
	 * Checks if laser upgrades can be purchased and purchases them if they can.
	 * 
	 * @param cost The cost of the upgrade to be purchased.
	 * @param name The name of the upgrade (To overload the other methods)
	 * @param laserLevel
	 */
	public void purchaseUpgrade(int cost, String name, int laserLevel)
	{
		// Checks if maximum laser upgrade has been purchased
		if (player.getLaserUpgrade() == 4)
		{
			JOptionPane
					.showMessageDialog(
							drawingArea,
							"This field is maxed out and cannot be upgraded any further.",
							"Warning!", JOptionPane.ERROR_MESSAGE);
		}
		// Checks if player has insufficient white hats
		else if (whiteHats < cost)
		{
			JOptionPane.showMessageDialog(drawingArea, "This upgrade costs "
					+ cost + ".0 white hats. You currently only have "
					+ whiteHats + ".", "Warning!", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Confirmation of purchase
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(drawingArea,
					"You're about to purchase the " + name + " upgrade for "
							+ cost + " White Hats. You currently have "
							+ whiteHats + ". Are you sure?", "Confirm",
					dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				// Deduct the cost of white hats and upgrade the laser
				whiteHats -= cost;
				player.upgradeLaser();
				JOptionPane.showMessageDialog(drawingArea,
						"You have purchased the " + name
								+ " upgrade. You currently have " + whiteHats
								+ " White Hats.", "Complete!",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(drawingArea,
						"You have declined this purchase. You currently have "
								+ whiteHats + " White Hats.",
						"Purchase Declined!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Checks if ammo or system integrity upgrades can be purchased and
	 * purchases them if they can.
	 * 
	 * @param cost How much the current upgrade costs
	 * @param type tells the method what is being purchased
	 */
	public void purchaseUpgrade(int cost, String type)
	{
		// Check if the maximum upgrade has been purchased
		if (player.getMaxAmmo() >= 1000 && type == "ammo")
		{
			JOptionPane.showMessageDialog(drawingArea,
					"You already have the maximum starting ammo!", "Warning!",
					JOptionPane.ERROR_MESSAGE);
		}
		// Check if the maximum upgrade has been purchased
		else if (player.getMaxIntegrity() >= 2000 && type == "integrity")
		{
			JOptionPane.showMessageDialog(drawingArea,
					"You already have the maximum starting system integrity!",
					"Warning!", JOptionPane.ERROR_MESSAGE);
		}
		// Check if the player has sufficient funds
		else if (whiteHats < cost)
		{
			JOptionPane.showMessageDialog(drawingArea, "This upgrade costs "
					+ cost + ".0 white hats. You currently only have "
					+ whiteHats + ".", "Warning!", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Confirmation
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(drawingArea,
					"You're about to purchase 50 more starting " + type
							+ " for " + cost
							+ " White Hats. You currently have " + whiteHats
							+ ". Are you sure?", "Confirm", dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				// Deduct the cost and perform the upgrade
				whiteHats -= cost;
				if (type == "ammo")
				{
					player.upgradeAmmo();
					JOptionPane.showMessageDialog(drawingArea,
							"You have purchased 50 more starting ammo. Your starting ammo is now "
									+ player.getMaxAmmo()
									+ ". You currently have " + whiteHats
									+ " White Hats.", "Complete!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					player.upgradeIntegrity();
					JOptionPane
							.showMessageDialog(
									drawingArea,
									"You have purchased 50 more starting integrity. Your starting integrity is now "
											+ player.getMaxIntegrity()
											+ ". You currently have "
											+ whiteHats + " White Hats.",
									"Complete!",
									JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(drawingArea,
						"You have declined this purchase. You currently have "
								+ whiteHats + " White Hats.",
						"Purchase Declined!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Checks if a firewall can be purchased and purchases it if it can.
	 */
	public void purchaseUpgrade()
	{
		// Check if the user already has the upgrade
		if (player.getFirewall())
		{
			JOptionPane.showMessageDialog(drawingArea,
					"You already have the firewall upgrade!", "Warning!",
					JOptionPane.ERROR_MESSAGE);
		}
		// Checks if the user has sufficient white hats
		else if (whiteHats < 5000)
		{
			JOptionPane.showMessageDialog(drawingArea,
					"This upgrade costs 5000.0 white hats. You currently only have "
							+ whiteHats + ".", "Warning!",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Confirmation of purchase
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane
					.showConfirmDialog(
							drawingArea,
							"You're about to purchase the firewall upgrade for 5000 White Hats. You currently have "
									+ whiteHats + ". Are you sure?", "Confirm",
							dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				// Deducts the cost and performs the upgrade
				whiteHats -= 5000;
				player.upgradeFirewall();
				JOptionPane.showMessageDialog(drawingArea,
						"You have purchased the firewall upgrade. You currently have "
								+ whiteHats + " White Hats.", "Complete!",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(drawingArea,
						"You have declined this purchase. You currently have "
								+ whiteHats + " White Hats.",
						"Purchase Declined!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	// Inner class to handle mouse events
	private class MouseHandler extends MouseAdapter
	{
		public void mouseReleased(MouseEvent event)
		{
			// Checks if the buttons in the main menu have been clicked
			if (gameStage == MAIN_MENU)
			{
				Rectangle startButton = new Rectangle(560, 52, 876, 273);
				Rectangle helpButton = new Rectangle(250, 180, 230, 120);
				Rectangle aboutButton = new Rectangle(386, 374, 556, 519);

				// Goes to the level selection screen when play is clicked.
				if (startButton.contains(event.getPoint()))
				{
					gameStage = LEVEL_SELECT;
					menuBackground.stop();
					gameBackground.loop();
				}
				// Goes to the help screen when instructions is clicked.
				else if (helpButton.contains(event.getPoint()))
					gameStage = HELP_SCREEN;

				// Goes to the about screen when about is clicked.
				else if (aboutButton.contains(event.getPoint()))
					gameStage = ABOUT_SCREEN;
			}
			// Checks if the buttons in the help screen have been clicked
			else if (gameStage == HELP_SCREEN)
			{
				Rectangle instructionsBackButton = new Rectangle(11, 687, 149,
						761);
				// Goes back to the main menu when back is clicked.
				if (instructionsBackButton.contains(event.getPoint()))
					gameStage = MAIN_MENU;
			}
			// Checks if the back button in the about screen is pressed.
			else if (gameStage == ABOUT_SCREEN)
			{
				Rectangle aboutBackButton = new Rectangle(10, 7, 156, 77);
				if (aboutBackButton.contains(event.getPoint()))
					gameStage = MAIN_MENU;
			}
			// Checks if a button is pressed in the level select screen.
			else if (gameStage == LEVEL_SELECT)
			{
				Rectangle[] firstLevelsColumn = new Rectangle[12];
				// Checks if the player chose a level to play in the first
				// column
				for (int levelBox = 0; levelBox < firstLevelsColumn.length; levelBox++)
				{
					firstLevelsColumn[levelBox] = new Rectangle(50,
							130 + 53 * levelBox, 100, 40);
					if (firstLevelsColumn[levelBox].contains(event.getPoint()))
					{
						// Checks if the player has unlocked the chosen level.
						if (levelBox <= highestLevelCleared)
						{
							level = levelBox + 1;
							gameStage = GAME_PLAY;

						}
						else
						{
							JOptionPane.showMessageDialog(drawingArea,
									"You have not unlocked this level yet!",
									"Warning!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				Rectangle[] secondLevelsColumn = new Rectangle[12];
				// Checks if the player chose a level to play in the first
				// column.
				for (int levelBox = 0; levelBox < secondLevelsColumn.length; levelBox++)
				{
					secondLevelsColumn[levelBox] = new Rectangle(375,
							130 + 53 * levelBox, 100, 40);
					if (secondLevelsColumn[levelBox].contains(event.getPoint()))
					{
						// Checks if the player has unlocked the selected level.
						if (levelBox + 12 <= highestLevelCleared)
						{
							level = levelBox + 13;
							gameStage = GAME_PLAY;

						}
						else
						{
							JOptionPane.showMessageDialog(drawingArea,
									"You have not unlocked this level yet!",
									"Warning!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				Rectangle finalButton = new Rectangle(725, 130, 240, 90);
				if (finalButton.contains(event.getPoint()))
				{
					if (highestLevelCleared >= 24)
					{
						level = 25;
						gameStage = GAME_PLAY;
					}
					else
					{
						JOptionPane.showMessageDialog(drawingArea,
								"You have not unlocked this level yet!",
								"Warning!", JOptionPane.ERROR_MESSAGE);
					}
				}
				Rectangle levelSelectMenuButton = new Rectangle(720, 550, 250,
						95);
				// Checks if the player has chosen to go to the main menu.
				if (levelSelectMenuButton.contains(event.getPoint()))
				{
					gameBackground.stop();
					gameStage = MAIN_MENU;
					menuBackground.loop();
				}
				Rectangle levelSelectStoreButton = new Rectangle(720, 665, 250,
						85);
				// Checks if the player has chosen to go to the upgrade store
				if (levelSelectStoreButton.contains(event.getPoint()))
				{
					gameStage = UPGRADE_STORE;
				}
			}

			// Checks if the player has clicked a button in the upgrade store.
			else if (gameStage == UPGRADE_STORE)
			{
				Rectangle upgradeShopBackButton = new Rectangle(877, 696, 136,
						47);
				Rectangle moreScans = new Rectangle(49, 505, 217, 101);
				Rectangle moreIntegrity = new Rectangle(601, 486, 217, 34);
				Rectangle moreAmmo = new Rectangle(601, 537, 217, 34);
				Rectangle firewall = new Rectangle(601, 588, 214, 34);
				Rectangle enterCode = new Rectangle(601, 643, 214, 34);

				if (upgradeShopBackButton.contains(event.getPoint()))
					gameStage = LEVEL_SELECT;

				else if (moreScans.contains(event.getPoint()))
				{
					String nextProcessor = "Pentium ";
					if (player.getLaserUpgrade() >= 4)
					{
						JOptionPane.showMessageDialog(drawingArea,
								"You already have all the upgrades!",
								"Warning!", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						for (int upgradeNumber = 1; upgradeNumber <= player
								.getLaserUpgrade() + 1; upgradeNumber++)
						{
							if (upgradeNumber != 4)
								nextProcessor += "I";
							else
								nextProcessor = " Pentium IV";
						}
						purchaseUpgrade(player.getLaserUpgradeCost(),
								nextProcessor, player.getLaserUpgrade());
					}
				}
				else if (moreIntegrity.contains(event.getPoint()))
				{
					purchaseUpgrade(1000, "integrity");
				}
				else if (moreAmmo.contains(event.getPoint()))
				{
					purchaseUpgrade(2000, "ammo");
				}
				else if (firewall.contains(event.getPoint()))
				{
					purchaseUpgrade();
				}
				else if (enterCode.contains(event.getPoint()))
				{
					String cheatCode = JOptionPane
							.showInputDialog("Please enter your code here.");
					if (cheatCode.equals("trap"))
					{
						gameBackground.stop();
						// Audio
						// "Bro Safari - The Drop"
						gameBackground = Applet
								.newAudioClip(getCompleteURL("Audio/Trap.au"));
						gameBackground.loop();
					}
					else if (cheatCode.equals("2spooky4me"))
					{
						gameBackground.stop();
						// Audio
						// "Andrew Gold - Spooky Scary Skeletons"
						gameBackground = Applet
								.newAudioClip(getCompleteURL("Audio/Spooky.au"));
						gameBackground.loop();
					}
					else if (cheatCode.equals("how2play"))
						highestLevelCleared = 25;
					else if (cheatCode.equals("how2upgrade"))
					{

						player.upgradeFirewall();
						player.maxUpgrade();

					}
					else if (cheatCode.equals("ridout"))
					{
						player.activateGodMode();
					}
				}

			}
			repaint();

		}
	}

	// Inner class to handle key events
	private class KeyHandler extends KeyAdapter
	{
		public void keyPressed(KeyEvent event)
		{
			if (gameStage == GAME_PLAY)
			{
				// Moves player in the direction of the arrow key pressed if the
				// player is within the frame.
				if (event.getKeyCode() == KeyEvent.VK_DOWN && player.y < 628)
					player.setLocation(player.x, player.y + 40);

				if (event.getKeyCode() == KeyEvent.VK_LEFT && player.x > 0)
					player.setLocation(player.x - 40, player.y);

				if (event.getKeyCode() == KeyEvent.VK_UP && player.y > 0)
					player.setLocation(player.x, player.y - 40);
				if (event.getKeyCode() == KeyEvent.VK_RIGHT && player.x < 920)
					player.setLocation(player.x + 40, player.y);

				// Fires a laser when the space key is pressed.
				if (event.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if (player.getAmmo() > 0)
					{
						player.fire();
					}
				}

				// Return to the level select screen
				if (event.getKeyCode() == KeyEvent.VK_L)
				{
					player.resetPlayer();
					player.replenish();
					virusesSpawned = 0;
					virusIndex = 0;
					virusesOnScreen = 0;
					int numLasers = player.getNewLaserIndex();
					for (int currentLaser = 0; currentLaser < numLasers; currentLaser++)
					{
						player.removeVoidLasers(currentLaser);
					}
					// System.out.println(player.getNewLaserIndex());
					int numPowerUps = powerUpIndex;
					for (int powerUpNum = 0; powerUpNum < numPowerUps; powerUpNum++)
					{
						removeVoidPowerUps(powerUpNum);
					}
					gameStage = LEVEL_SELECT;
					repaint();

				}
			}
			// Starting a new game/continuing a game once the game ends
			else if (gameStage == GAME_OVER)
			{
				// Return to the level select screen
				if (event.getKeyCode() == KeyEvent.VK_L)
				{
					gameStage = LEVEL_SELECT;
					player.replenish();
				}
				// Start a new game
				else if (event.getKeyCode() == KeyEvent.VK_N)
					startGame();
			}
			// Pauses the game during game play when p is pressed and resumes it
			// when p is pressed again.
			if (event.getKeyCode() == KeyEvent.VK_P)
			{
				if (gameStage == PAUSED)
					gameStage = GAME_PLAY;
				else if (gameStage == GAME_PLAY)
					gameStage = PAUSED;
			}

		}
	}

	public static void main(String[] args)
	{
		// Set up the main frame
		PCDefender mainFrame = new PCDefender();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

}