import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

public class Audio
{

	private AudioClip menuBackground;

	// private AudioClip beep;
	// private AudioClip hello;
	// private AudioClip goodbye;
	// private MenuItem playSound;

	public Audio(int sound)
	{
		menuBackground = Applet
				.newAudioClip(getCompleteURL("Audio/Indie Rocker.mp3"));
		// beep = Applet.newAudioClip(getCompleteURL("beep.au"));
		// hello = Applet.newAudioClip(getCompleteURL("hello.wav"));
		if (sound == 1)
			menuBackground.play();
	} // AudioDemo constructor

	// Gets the URL needed for newAudioClip
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

} // AudioApplet class

