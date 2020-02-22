package test;
//压缩音乐文件
import javax.swing.*;
import javax.xml.bind.Unmarshaller.Listener;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class mygame3 {
	File h = new File("./src/music/xun.wav");
	static ImageIcon  imgArr[] = new ImageIcon[16];
	Music song = new Music(h);
	 static int pass = mygame4.pass;
	 static int step = 0;
	 int BgmOn = 1;
	int line = 4,width = 4,Line =1;
	int[][] next = new int[][]{{0,0},{1,0},{-1,0},{0,1},{0,-1}};   //中间   上   下  右  左
	 static JFrame jf = mygame4.jf;
	 JPanel up = new JPanel(),  down  = new JPanel(); //面板
	  JButton  jbtArray[]= new JButton[16];  
	  JButton reset = new JButton("重置");
	 JButton nextpass= new JButton(""+pass+"/7  下一关");
	 JButton stepButton = new JButton("step:0");
	 JButton bgm = new JButton("bgm:ON");
	 JButton start = new JButton("开始游戏");
	 //static JLabel jbe = new JLabel("PASS:"+pass+"/6    点击重新开始"); //标签
	 Start_click listener = new Start_click(); // 实例化监听器
	
	public mygame3() {
		start.addActionListener(listener);
		bgm.addActionListener(listener);
		for( int i = 0; i < 16; i++) {
			int j = i+ 1;
			jbtArray[i]= new JButton(new ImageIcon("./src/img3/0_"+j+".png"));
			up.add(jbtArray[i]);
		}
		//给上端面板设置网格布局
			up.setLayout(new GridLayout(line,width));
			//给窗口设置边框布局
			jf.setLayout(new BorderLayout());
			jf.add(up,"Center"); //把上端面板添加到窗口中央
			//给下端面板设置流式布局
			down.setLayout(new FlowLayout());
			down.add(start);
			down.add(bgm);
			//down.add(jbe);     //把标签添加到下端面板
			jf.add(down,"South");  //把下端面板添加到窗口下端
			jf.setTitle("你还能继续嘛（原图在源文件中）");
			jf.setSize(500,600);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
			jf.setResizable(false);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	class Start_click implements ActionListener {     //监听器
		//@Override
		public void actionPerformed(ActionEvent e) {    //这里e就是读取到的按钮事件
			// TODO Auto-generated method stub
			if(e.getSource() == reset) {
				for( int i = 0; i < line * width; i++) {
					int j =( i + 6) % (line * width);
					if( j == 10)
						j = 14;
					else if( j == 14)
						j = 10;
					if( j == 5)
						j = 9;
					else if( j == 9)
						j = 5;
					if(j == 0)
							j = line * width;
					jbtArray[i].setIcon(imgArr[j-1]);
				}
			}
			else if(e.getSource() == start) {
				jf.setTitle("怀旧的16宫格");
				bgm.removeActionListener(listener);
				start.removeActionListener(listener);
				down.remove(start);
				bgm.addActionListener(listener);
				reset.addActionListener(listener);
				nextpass.addActionListener(listener);
				stepButton.addActionListener(listener);
				for( int i = 0; i < 16; i++) {
					int j =( i + 6) % (line * width);
					if(j == 0)
							j = line * width;
					if( j == 10)
						j = 14;
					else if( j == 14)
						j = 10;
					if( j == 5)
						j = 9;
					else if( j == 9)
						j = 5;
					if(j == 13) {
						imgArr[j-1] = new ImageIcon("./src/img/1_2.png");
						jbtArray[i].setIcon((imgArr[j-1]));
					}
					else {
						System.out.println(j);
						imgArr[j-1] = new ImageIcon("./src/img3/0_"+j+".png");
						jbtArray[i].setIcon((imgArr[j-1]));
					}
					jbtArray[i].setActionCommand(String.valueOf(i));  //把下标值传给按钮（但必须传字符串所以里面要强制转换）
					//监听鼠标然后如果按下则干什么
					jbtArray[i].addActionListener(listener);		
				}
				//给上端面板设置网格布局
					down.add(stepButton);
					down.add(reset);  //添加按钮
					down.add(nextpass);
					down.add(bgm);
			}
			else if(e.getSource() == bgm) {
				if(BgmOn == 1) {
					song.aau.stop();
					bgm.setText("bgm:OFF");
					BgmOn = 0;
				}
				else {
					song.aau.loop();
					bgm.setText("bgm:ON");
					BgmOn = 1;
				}
			}
			else if(e.getSource() == nextpass) {
				JOptionPane.showMessageDialog(null, "少开挂喔!","NEXT",JOptionPane.CANCEL_OPTION);
				for(int i = 0; i < line * width; i++) {
					//jbtArray[i].setIcon(new ImageIcon("./src/images/1.png"));
					jbtArray[i].removeActionListener(listener);
				}
				reset.removeActionListener(listener);
				song.aau.stop();
				mygame3.jf.remove(down);
				mygame3.jf.remove(up);
				pass = pass + 1;
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mygame2 game1 = new mygame2();
			}
			else {
				for (int i = 0; i < jbtArray.length; i++) {
					if (e.getSource() ==jbtArray[i]) {
						int num = i,x = num / line,y = num % width;
						for(int l = 1; l <5; l++) {
							if( x + next[l][0] >= 0 && x + next[l][0] <=line - 1 && y + next[l][1] >= 0 &&  y + next[l][1] <= width-1) {
								int imgNum = (x + next[l][0])*line+ y + next[l][1] + 1;
								/*if(jbtArray[imgNum] == "./src/images/1.png")   //判断图片就是先获取图片，然后把图片转换成他的地址sting类型去比较
									jbtArray.setIcon(new ImageIcon("./src/images/"imgNum".gjf"));
								else jbtArray.length;*/
								if( jbtArray[imgNum - 1].getIcon().toString() ==  "./src/img/1_2.png")   //判断图片就是先获取图片，然后把图片转换成他的地址sting类型去比较
									{
											step++;
											stepButton.setText("Step:"+step+"");
											jbtArray[imgNum - 1].setIcon(jbtArray[i].getIcon());
											jbtArray[i].setIcon(imgArr[12]);
											break;
									}
							}
						}
						break;
					}
				}
			}
			//判断游戏通关
			int count = 0;
			for( int j = 0; j <line * width; j++) {
				//System.out.println(jbtArray[j-1].getIcon().toString());
				//System.out.println("./src/img/0_"+j+".png");
				if( jbtArray[j].getIcon() ==imgArr[j]) {
					count = count + 1;
				}
			}
			System.out.println(count);
			if(count ==line * width) {
				int res = JOptionPane.showConfirmDialog(null, "恭喜过关了，是否进入下一关", "是否重新开始", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					for(int i = 0; i < line * width; i++) {
						//jbtArray[i].setIcon(new ImageIcon("./src/images/1.png"));
						jbtArray[i].removeActionListener(listener);
					}
					reset.removeActionListener(listener);
					song.aau.stop();
					mygame3.jf.remove(down);
					mygame3.jf.remove(up);
					pass = pass + 1;
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					new mygame2();
				}
				//JOptionPane.showMessageDialog(null, "You Win!","You Win!",JOptionPane.CANCEL_OPTION);
			}
		}
	  }

		public static void main(String[] args) {
		//new Song().start(); 
			mygame3 game = new mygame3();
		}
}



