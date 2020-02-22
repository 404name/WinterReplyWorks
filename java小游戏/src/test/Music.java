package test;

import java.applet.AudioClip; 
import java.io.*; 
import java.applet.Applet;
import java.awt.Frame; 
import java.net.MalformedURLException; 
import java.net.URI;
import java.net.URL;
import javax.swing.JFrame;
public class Music extends JFrame{ 
File f;
 URI uri;
    URL url; 
    AudioClip aau; 
// Music(){
//     bgMusic();
//  }
Music(File h){  
  try {      
      f = h; 
      uri = f.toURI();
      url = uri.toURL();  //解析地址
      aau = Applet.newAudioClip(url);
      aau.loop();  //循环播放
  } catch (Exception e) 
  { e.printStackTrace();
  } 
}
 public static void main(String args[]) { 
	 //这里是我自己添加了改动。播放方法就是Music(文件)；
	 //然后可以进行auu.stop
	 //song.aau.stop();*/
 }
}