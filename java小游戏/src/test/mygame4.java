//待修复         无
/*------------------------------
 * 开工日期2020.2.19   刚刚入门java 模仿了一遍熄灯问题。然后自己写了其他内容。
 * 修复日志
 *  添加音乐关闭开关。。
 * 更改标题名字
 * 已修复  重置恢复原状 2020.2.21 22.46
 ------------------------------*/

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

public class mygame4 {
	File h = new File("./src/music/Daisy.wav");
	static ImageIcon  imgArr[] = new ImageIcon[16];
	Music song = new Music(h);
	 static int pass = 1;
	 static int step = 0;
	 int BgmOn = 1;
	int line = 3,width = 3,Line =1;
	int[][] next = new int[][]{{0,0},{1,0},{-1,0},{0,1},{0,-1}};   //中间   上   下  右  左
	 static JFrame jf = new JFrame();
	 JPanel up = new JPanel(),  down  = new JPanel(); //面板
	  JButton  jbtArray[]= new JButton[16];  
	  JButton reset = new JButton("重置");
	 JButton nextpass= new JButton(""+pass+"/7  下一关");
	 JButton stepButton = new JButton("step:0");
	 JButton bgm = new JButton("bgm:ON");
	 JButton startButton = new JButton("开始游戏");
	 Start_click listener = new Start_click(); // 实例化监听器
	 
	public mygame4() {
		bgm.addActionListener(listener);
		startButton.addActionListener(listener);
		for( int i = 0; i < line * width; i++) {
				int j = i + 1;
				jbtArray[i]= new JButton(new ImageIcon("./src/img4/0_0"+j+".png"));
				up.add(jbtArray[i]);
		}
		//给上端面板设置网格布局
			up.setLayout(new GridLayout(line,width));
			//给窗口设置边框布局
			jf.setLayout(new BorderLayout());
			jf.add(up,"Center"); //把上端面板添加到窗口中央
			//给下端面板设置流式布局
			down.setLayout(new FlowLayout());
			//down.add(jbe);     //把标签添加到下端面板
			down.add(startButton);
			down.add(bgm);
			jf.add(down,"South");  //把下端面板添加到窗口下端
			jf.setTitle("快记住原图喔（源文件中也有）");
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
				System.out.println("heihei");
				for( int i = 0; i < line * width; i++) {
					int j =( i + 4) % (line * width);
					if( j == 4)
						j = 8;
					else if( j == 8)
						j = 4;
					if(j == 0)
							j = line * width;
					jbtArray[i].setIcon(imgArr[j-1]);
				}
			}
			else if(e.getSource() == startButton) {
				jf.setTitle("怀旧的九宫格拼图");
				bgm.removeActionListener(listener);
				startButton.removeActionListener(listener);
				down.remove(startButton);
				bgm.addActionListener(listener);
				reset.addActionListener(listener);
				nextpass.addActionListener(listener);
				stepButton.addActionListener(listener);
				for( int i = 0; i < line * width; i++) {
					int j =( i + 4) % (line * width);
					if(j == 0)
							j = line * width;
					if( j == 4)
							j = 8;
					else if( j == 8)
							j = 4;
					if(j == 7) {
						imgArr[j-1] = new ImageIcon("./src/img/1_2.png");
						jbtArray[i].setIcon(imgArr[j-1]);
					}
					else {
						System.out.println(j);
						imgArr[j-1] = new ImageIcon("./src/img4/0_0"+j+".png");
						jbtArray[i].setIcon(imgArr[j-1]);
					}
					jbtArray[i].setActionCommand(String.valueOf(i));  //把下标值传给按钮（但必须传字符串所以里面要强制转换）
					//监听鼠标然后如果按下则干什么
					jbtArray[i].addActionListener(listener);		
					//up.add(jbtArray[i]);
				}
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
				mygame4.jf.remove(down);
				mygame4.jf.remove(up);
				pass = pass + 1;
				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				new mygame3();
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
											jbtArray[i].setIcon(imgArr[6]);
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
					mygame4.jf.remove(down);
					mygame4.jf.remove(up);
					pass = pass + 1;
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					new mygame3();
				}
				//JOptionPane.showMessageDialog(null, "You Win!","You Win!",JOptionPane.CANCEL_OPTION);
			}
		}
	  }

		public static void main(String[] args) {
		//new Song().start(); 
		new mygame4();
		}
}



