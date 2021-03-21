package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBClose;
import db.DBConnection;

public class ChatDAO {

private static ChatDAO dao = new ChatDAO();
	
	private ChatDAO() {
		DBConnection.initConnection();
	}

	public static ChatDAO getInstance() {
		return dao;	//싱글톤 사용
	}
	
	
	
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
		
		String sql = " SELECT * FROM CHAT "
				+ 	 " WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) "
				+	 " AND chatID > ? "
				+ 	 " ORDER BY chatTime "; 
				
		
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getChatListByID success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, fromID);
			psmt.setString(2, toID);
			psmt.setString(3, toID);
			psmt.setString(4, fromID);
			psmt.setInt(5, Integer.parseInt(chatID));
			System.out.println("2/4 getChatListByID success");
			rs = psmt.executeQuery();
			System.out.println("3/4 getChatListByID success");
			chatList = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
				}
				if(chatTime >= 13) {
					chatTime -=  12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 10) + " " + timeType + " " + chatTime + ":"
						+ rs.getString("chatTime").substring(14, 16) +":" + rs.getString("chatTime").substring(17, 19));	
				
				chatList.add(chat);
			} 
			System.out.println("4/4 getChatListByID success");
			
		}catch (Exception e) {
			System.out.println("getChatListByID fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		};
		
		return chatList; 
		
	}
	
	
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
		
		String sql = " SELECT * FROM CHAT"
				+ 	 " WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) "
				+	 " AND chatID > "
				+		 " (SELECT MAX(chatID) - ? "
				+ "			FROM CHAT) "
				+ 	 " ORDER BY chatTime "; 
				
		
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		

		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getChatListByRecent success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, fromID);
			psmt.setString(2, toID);
			psmt.setString(3, toID);
			psmt.setString(4, fromID);
			psmt.setInt(5, number);
			System.out.println("2/4 getChatListByRecent success");
			rs = psmt.executeQuery();
			System.out.println("3/4 getChatListByRecent success");
			chatList = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
				}
				if(chatTime >= 13) {
					chatTime -=  12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 10) + " " + timeType + " " + chatTime + ":"
						+ rs.getString("chatTime").substring(14, 16) +":" + rs.getString("chatTime").substring(17, 19));	
				
				chatList.add(chat);
			} 
			System.out.println("4/4 getChatListByRecent success");
			
		}catch (Exception e) {
			System.out.println("getChatListByRecent fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		};
		
		return chatList; 
		
	}
	
	
	
	
	public int submit(String fromID, String toID, String chatContent) {
		
		String sql = " INSERT INTO CHAT "
				+	 " VALUES (CHAT_SEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
				
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 submit success");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, fromID);
			psmt.setString(2, toID);
			psmt.setString(3, chatContent);
			System.out.println("2/3 submit success");
			int i = psmt.executeUpdate();
			System.out.println("3/3 submit success / 성공이면  1 : " + i);
			return i;
			
			
		}catch (Exception e) {
			System.out.println("submit fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		};
		
		return -1; // 데이터베이스 오류
	}
	
	
}
