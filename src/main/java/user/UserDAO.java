package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
		
	//		---------- DB���� ----------  //
	public UserDAO() {
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
	
	
	//     ----------------- �α��� �޼ҵ� -----------------          //
	public int login(String userID,String userPassword) {
		String SQL = "SELECT userPassword FROM user WHERE userID=?";
		try {
			pstmt=conn.prepareStatement(SQL);//DB���� SQL�����غ�
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;
				}else {
					return -1;
				}				
			}else {
				return -2;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return -3;
	}

	
//  ----------------- join(ȸ������) �޼ҵ� -----------------          //
	public int join(String userID,String userPassword,String userName,String userGender,String userEmail) {
		String SQL = "INSERT INTO user VALUES(?,?,?,?,?)";
		try {
			pstmt=conn.prepareStatement(SQL);//DB���� SQL�����غ�
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setString(4, userGender);
			pstmt.setString(5, userEmail);			
			return  pstmt.executeUpdate();//executeUpdate => ����,executeQuery => �˻�			
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
}








