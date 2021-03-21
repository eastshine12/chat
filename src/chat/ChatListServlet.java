package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;

@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		String fromID = req.getParameter("fromID");
		String toID = req.getParameter("toID");
		String listType = req.getParameter("listType");

		System.out.println("formID : " + fromID);
		System.out.println("toID : " + toID);
		System.out.println("listType : " + listType);
		
		
		if(fromID == null || fromID.equals("") ||
			toID == null || toID.equals("") ||
			listType == null || listType.equals("")) {
			resp.getWriter().write("");
		} else if(listType.equals("ten")) {
			resp.getWriter().write(getTen(URLDecoder.decode(fromID, "utf-8"), URLDecoder.decode(toID, "utf-8"))); 
		} else {
			try {
				resp.getWriter().write(getID(URLDecoder.decode(fromID, "utf-8"), URLDecoder.decode(toID, "utf-8"), listType));
			} catch (IOException e) {
				resp.getWriter().write("");
			}
			
		}
		
	}
	
	
	public String getTen(String fromID, String toID) {
		ChatDAO chatDao = ChatDAO.getInstance();
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\": [");
		ArrayList<ChatDTO> chatList = chatDao.getChatListByRecent(fromID, toID, 10);
		if(chatList.size() == 0) return "";
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\": \"" +chatList.get(chatList.size()-1).getChatID() + "\"}");
		System.out.println(result.toString());
		return result.toString();
				
	}
	
	
	public String getID(String fromID, String toID, String chatID) {
		
		ChatDAO chatDao = ChatDAO.getInstance();
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\": [");
		ArrayList<ChatDTO> chatList = chatDao.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return "";
		for (int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"}, ");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\": \"" +chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString();
				
	}
	

	
}
