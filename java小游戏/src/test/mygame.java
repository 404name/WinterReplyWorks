package test;
//压缩音乐文件
import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class mygame {
	File h = new File("./src/music/moon.wav");
	Music song = new Music(h);
	static int pass = mygame2.pass;
	static int step = 0;
	int BgmOn = 1;
	int line = 5,width = 5,Line =1;
	int[][] next = new int[][]{{0,0},{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};   //中间   上   下  右  左
	JFrame jf = mygame2.jf;
	 JPanel up = new JPanel(),  down  = new JPanel(); //面板
    JButton  jbtArray[]= new JButton[25];  
    JButton nextpass= new JButton(""+pass+"/7  下一关");
    JButton stepButton = new JButton("step:0");
	 JButton reset = new JButton("重置");//
	 JButton bgm = new JButton("bgm:ON");
	 // JLabel jbe = new JLabel("关卡"+pass+"/6    点击重新开始"); //标签
	 Start_click listener = new Start_click();// 实例化监听器
	
	 public mygame() {
		 bgm.addActionListener(listener);
		stepButton.addActionListener(listener);
		reset.addActionListener(listener);
		nextpass.addActionListener(listener);
		for( int i = 0; i < 25; i++) {
			jbtArray[i]= new JButton(new ImageIcon("./src/images/1.png"));
			jbtArray[i].setActionCommand(String.valueOf(i));  //把下标值传给按钮（但必须传字符串所以里面要强制转换）
			//监听鼠标然后如果按下则干什么
			jbtArray[i].addActionListener(listener);		
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
			down.add(stepButton);
			down.add(reset);  //添加按钮
			down.add(nextpass);
			down.add(bgm);
			jf.add(down,"South");  //把下端面板添加到窗口下端
			jf.setTitle("熄灯问题（建议先点中间了解下规则）");
			jf.setSize(500,600);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
			jf.setResizable(false);
			//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class Start_click implements ActionListener {     //监听器
		//@Override
		public void actionPerformed(ActionEvent e) {    //这里e就是读取到的按钮事件
			// TODO Auto-generated method stub
			if(e.getSource() == reset) {
				for(int i = 0; i < 25; i++) {
					jbtArray[i].setIcon(new ImageIcon("./src/images/1.png"));
				}
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
				int res = JOptionPane.showConfirmDialog(null, "确定要跳过嘛", "Bad", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					for(int i = 0; i < 25; i++) {
						jbtArray[i].setIcon(new ImageIcon("./src/images/1.png"));
					}
					step = 0;
					stepButton.setText("Step:"+step+"");
					if(pass < 7)
						pass = pass + 1;
					nextpass.setText(""+pass+"/7  下一关");
					/*down.remove(jbe);
					down.remove(reset);
					JLabel jbe = new JLabel("关卡"+pass+"/6    点击重新开始");
					down.add(jbe);     //把标签添加到下端面板
					down.add(reset);  //添加按钮*/
					if( Line < 5) {
						if(Line == 3)
							Line++;
						Line++;
					}
					else{
						Line = 9;
						nextpass.setText("彩蛋关卡啦，因为我也没找到解法");
						//System.out.println("???");
						int res1 = JOptionPane.showConfirmDialog(null, "恭喜游戏结束啦，是否进入彩蛋关卡", "是进入  否退出游戏", JOptionPane.YES_NO_OPTION);
						if (res1 != JOptionPane.YES_OPTION) {
								System.exit(0);
						}
					}
					//mygame game = new mygame(line - Line - 1);
				}
			}
			else {
				for (int i = 0; i < jbtArray.length; i++) {
					if (e.getSource() ==jbtArray[i]) {
						int num = i,x = num / line,y = num % width;
						for(int l = 0; l <Line; l++) {
							if( x + next[l][0] >= 0 && x + next[l][0] <=4 && y + next[l][1] >= 0 &&  y + next[l][1] <= 4) {
								int imgNum = (x + next[l][0])*line+ y + next[l][1] + 1;
								/*if(jbtArray[imgNum] == "./src/images/1.png")   //判断图片就是先获取图片，然后把图片转换成他的地址sting类型去比较
									jbtArray.setIcon(new ImageIcon("./src/images/"imgNum".gjf"));
								else jbtArray.length;*/
								if( jbtArray[imgNum - 1].getIcon().toString() ==  "./src/images/1.png")   //判断图片就是先获取图片，然后把图片转换成他的地址sting类型去比较
									jbtArray[imgNum - 1].setIcon(new ImageIcon("./src/images/0_"+imgNum+".png"));
								else jbtArray[imgNum - 1].setIcon(new ImageIcon("./src/images/1.png"));
							}
						}
						break;
					}
				}
				step++;
				stepButton.setText("Step:"+step+"");
			}
			//判断游戏通关
			int count = 0;
			for( int j = 0; j < 25; j++) {
				if(jbtArray[j].getIcon().toString() != "./src/images/1.png") 
					++count;
				else {
					count = 0;
					break;
				}
			}
			if(count ==25) {
				/*int res = JOptionPane.showConfirmDialog(null, "你成功了！是否重新开始", "是否重新开始", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					for(int i1 = 0; i1 < line * width;i1++) {
						jbtArray[i1].setIcon(new ImageIcon("./src/images/1.png"));
					}
				}*/
				int res = JOptionPane.showConfirmDialog(null, "恭喜过关了，是否进入下一关", "是否重新开始", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					for(int i = 0; i < 25; i++) {
						jbtArray[i].setIcon(new ImageIcon("./src/images/1.png"));
					}
					step = 0;
					stepButton.setText("Step:"+step+"");
					if(pass < 7)
						pass = pass + 1;
					nextpass.setText(""+pass+"/7  下一关");
					/*down.remove(jbe);
					down.remove(reset);
					JLabel jbe = new JLabel("关卡"+pass+"/6    点击重新开始");
					down.add(jbe);     //把标签添加到下端面板
					down.add(reset);  //添加按钮*/
					if( Line < 5) {
						if(Line == 3)
							Line++;
						Line++;
					}
					else{
						Line = 9;
						//System.out.println("???");
						int res1 = JOptionPane.showConfirmDialog(null, "恭喜过关了，游戏结束啦", "是否退出", JOptionPane.YES_NO_OPTION);
						if (res1 == JOptionPane.YES_OPTION) {
								System.exit(0);
						}
					}
					//mygame game = new mygame(line - Line - 1);
				}
			}
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		mygame2.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//new Song().start(); 
		mygame game = new mygame();
	}
	
}



