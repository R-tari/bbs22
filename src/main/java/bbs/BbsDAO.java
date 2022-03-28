package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
		
	//		---------- DB���� ----------  //
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/bbs";//mysql ��ġ
			String dbID = "root";//mysql ID
			String dbPassword = "root";//mysql PASSWORD
			Class.forName("com.mysql.cj.jdbc.Driver");//���� ����̹�
			conn=DriverManager.getConnection(dbURL, dbID, dbPassword);//DB�������� �� ����
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//���� ��¥ ���ϱ�
	public String getDate() {
		String SQL = "SELECT DATE(NOW())";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return "";
	}
	
	//������ �� ������ȣ ���ϱ�
	public int getNext() {
		String SQL = "SELECT bbsID FROM bbs ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;//������ȣ
			}else {
				return 1;//���۹�ȣ
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;//������ȣ
	}
	
	//������ �޼ҵ�
	public int write(String bbsTitle,String userID,String bbsContent) {
		String SQL = "INSERT INTO bbs VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;//�����ͺ��̽� ����
	}
	
	
	//bbs��� �������� �޼ҵ�
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM bbs WHERE bbsID<? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1)*10);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				Bbs bbs =new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
				
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//	페이지 처리 메서드
	public boolean nextPage(int pageNumber)
	{
		String SQL = "SELECT * FROM bbs WHERE bbsID<? AND bbsAvailable = 1;";
		try
		{
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1)*10);
			rs=pstmt.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}








