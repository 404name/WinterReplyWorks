package test;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

//BGM
class Song extends Thread {
	URL url = this.getClass().getResource("/music/ninelie.mid");
	AudioClip audio = Applet.newAudioClip(url);
	@Override
	public void run() {
		audio.play();
		audio.loop();
	}
}
