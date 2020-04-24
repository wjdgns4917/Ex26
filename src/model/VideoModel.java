package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import model.vo.Video;

public class VideoModel {

	Connection con;

	public VideoModel() throws Exception {

		con = DBcon.getConnection();
	}

	public void insertVideo(Video dao, int count) throws Exception {
		con.setAutoCommit(false);
		for(int i=0; i<count;i++){
		// 3. sql 문장 만들기
		String sql1 = "INSERT INTO vinfo(VICODE, title, genre, DIRECTOR, ACTOR, DETAIL,imgfname) "
				+ "VALUES (seq_vi_code.nextval,?,?,?,?,?,?)";
//		String sql2 = "INSERT INTO video(VCODE, VICODE) VALUES (seq_v_code.nextval, seq_vi_code.currval)";
		
		// 4. sql 전송객체 (PreparedStatement)
		PreparedStatement ps1 = con.prepareStatement(sql1);		
		ps1.setString(1, dao.getVideoName());
		ps1.setString(2, dao.getGenre());
		ps1.setString(3, dao.getDirector());
		ps1.setString(4, dao.getActor());
		ps1.setString(5, dao.getExp());
		ps1.setString(6, dao.getImgfname());
		

//		PreparedStatement ps2 = con.prepareStatement(sql2);
		
		// 5. sql 전송
		int r1 = ps1.executeUpdate();
//		int r2 = ps2.executeUpdate();
		
//		if(r1 != 1 || r2!= 1){
//			con.rollback();	
//		}
		if(r1 != 1 ){
			con.rollback();	
		}
		con.commit();
		
		// 6. 닫기 (PreparedStatement 만 닫기)
		ps1.close();
//		ps2.close();
		}
		con.setAutoCommit(true);
	}

	public ArrayList searchVideo(int idx, String str) throws Exception {
		String[] key = {"TITLE","DIRECTOR"};
		String sql = "SELECT vicode, title, genre, director, actor FROM vinfo "
					+ "	WHERE  "+ key[idx] +" LIKE '%" + str + "%'" ;
		// WHERE 'title' LIKE '%ㅎㅎ%'; // ?쓰고 setString 하면 컬럼명에 ''이 들어가서 안됨 
		System.out.println(sql);
	
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList data = new ArrayList();
		while(rs.next()){
			ArrayList temp = new ArrayList();
			temp.add(rs.getString("VICODE"));
			temp.add(rs.getString("TITLE"));
			temp.add(rs.getString("GENRE"));
			temp.add(rs.getString("DIRECTOR"));
			temp.add(rs.getString("ACTOR"));
			data.add(temp);
		}
		
		rs.close();
		ps.close();
		return data;
	}

	public Video selectbyPk(int no) throws Exception{
		Video vo = new Video();
		String sql = "SELECT * FROM vinfo WHERE vicode=" + no;
		//각각의 컬럼들을 Video에 저장하고 리턴
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			vo.setVideoNo(Integer.parseInt(rs.getString("VICODE")));
			vo.setActor(rs.getString("ACTOR"));
			vo.setDirector(rs.getString("DIRECTOR"));
			vo.setGenre(rs.getString("GENRE"));
			vo.setVideoName(rs.getString("TITLE"));
			vo.setExp(rs.getString("DETAIL"));
			//이미지로 추가
			vo.setImgfname(rs.getString("imgfname"));
//			alter table vinfo add( imgfname varchar2(50));
			
		}
		rs.close();
		ps.close();
		return vo;
	}

	public void modifyVideo(Video vo) throws Exception{
		
		String sql = "UPDATE vinfo SET title =?, genre=?, director=?, actor=?, detail=? WHERE vicode=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vo.getVideoName());
		ps.setString(2, vo.getGenre());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getExp());
		ps.setInt(6, vo.getVideoNo());
		
		ps.executeUpdate();
		System.out.println(vo.getExp());
		ps.close();
	}

	public void delete(Video vo) throws Exception{
		con.setAutoCommit(false);
		
		String sql1 = "DELETE FROM vinfo WHERE vicode=?";
		String sql2 = "DELETE FROM video WHERE vicode=?";
		
		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps1.setInt(1, vo.getVideoNo());
		PreparedStatement ps2 = con.prepareStatement(sql2);
		ps2.setInt(1, vo.getVideoNo());
		

		// 5. sql 전송
				int r2 = ps2.executeUpdate();
				int r1 = ps1.executeUpdate();
				
				if(r1 != 1 || r2!= 1){
					con.rollback();	
				}
				con.commit();
				
				// 6. 닫기 (PreparedStatement 만 닫기)
				ps1.close();
				ps2.close();
				
				con.setAutoCommit(true);
	}

}
