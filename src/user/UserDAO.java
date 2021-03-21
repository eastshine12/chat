package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBClose;
import db.DBConnection;

public class UserDAO {

	private static UserDAO dao = new UserDAO();
	
	private UserDAO() {
		DBConnection.initConnection();
	}

	public static UserDAO getInstance() {
		return dao;	//싱글톤 사용
	}
	
	
	/* 로그인 */
	public int login(String userID, String userPassword) {
		
		String sql = " SELECT * FROM USERS "
				+ 	 " WHERE userID = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 login success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userID);
			System.out.println("2/3 login success");
			rs = psmt.executeQuery();
			System.out.println("3/3 login success");
			
			if(rs.next()) {
				if(rs.getString("userPassword").equals(userPassword)) {
					return 1;	//로그인 성공
				}
				return 2; //비밀번호 틀림
			} else {
				return 0;	//해당 ID가 존재하지 않음
			}
			
		}catch (Exception e) {
			System.out.println("3/3 login fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		};
		
		return -1; //데이터베이스 오류
	}
	
	
	/* 회원가입 시 ID 중복 체크 */
	public int registerCheck(String userID) {
		
		String sql = " SELECT * FROM USERS "
				+ 	 " WHERE userID = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 registerCheck success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userID);
			System.out.println("2/3 registerCheck success");
			rs = psmt.executeQuery();
			System.out.println("3/3 registerCheck success");
			
			if(rs.next() || userID.equals("")) {
				return 0; // '이미 존재하는 회원입니다' 출력
			} else {
				return 1;	//'사용 가능한 ID 입니다.' 출력
			}
			
		}catch (Exception e) {
			System.out.println("3/3 registerCheck fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		};
		
		return -1; //데이터베이스 오류
	}	
	
	
	
	/* 회원가입 수행 */
	public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile) {
		
		String sql = " INSERT INTO USERS "
				+ 	 " VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 register success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userID);
			psmt.setString(2, userPassword);
			psmt.setString(3, userName);
			psmt.setInt(4, Integer.parseInt(userAge));	//나이 입력에 문자열이 들어오면 알아서 오류로 뜬다.
			psmt.setString(5, userGender);
			psmt.setString(6, userEmail);
			psmt.setString(7, userProfile);
			System.out.println("2/3 register success");
			int i = psmt.executeUpdate();
			System.out.println("3/3 register success");
			return i;

			
		}catch (Exception e) {
			System.out.println("3/3 register fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		};
		
		return -1; //데이터베이스 오류
	}	
	
	
	
	
	
}
