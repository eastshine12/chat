package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		String fromID = req.getParameter("fromID");
		String toID = req.getParameter("toID");
		String chatContent = req.getParameter("chatContent");
		ChatDAO dao = ChatDAO.getInstance();
		System.out.println("formID : " + fromID);
		System.out.println("toID : " + toID);
		System.out.println("chatContent : " + chatContent);
		
		/* 채팅 아이디, 내용이 하나라도 안들어 온 경우 */
		if(fromID == null || fromID.equals("") ||
			toID == null || toID.equals("") ||
			chatContent == null || chatContent.equals("")) {
			resp.getWriter().write("0");
			System.out.println("내용 다 입력 안됐음");
		} else { /* 아닌 경우 */
			fromID = URLDecoder.decode(fromID, "utf-8");
			toID = URLDecoder.decode(toID, "utf-8");
			chatContent = URLDecoder.decode(chatContent, "utf-8");
			resp.getWriter().write(dao.submit(fromID, toID, chatContent) +""); //****문자열로 바꿔줘야 된다는 거 잊지말자!!!
			System.out.println("채팅 정보 DB 저장 완료");
		}
		
	}

	
}
