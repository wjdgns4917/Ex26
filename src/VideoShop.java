

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import  view.VideoView;

public class VideoShop extends JFrame 
{

	VideoView			video;


	public VideoShop(){
		//각각의 화면을 관리하는 클래스 객체 생성
		
	
			video		= new VideoView();


			JTabbedPane  pane = new JTabbedPane();
			pane.addTab("비디오관리", video);
			pane.addTab("대여관리", null);


			pane.setSelectedIndex(0);

			// 화면크기지정
			setTitle("Dvd Manegement");
			ImageIcon icon=new ImageIcon("dvd2.png");
			setIconImage(icon.getImage());
			
			add("Center", pane );
			setSize(800,600);
			setVisible( true );

			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
	}
	public static void main(String[] args) 
	{
			new VideoShop();
	}
}
