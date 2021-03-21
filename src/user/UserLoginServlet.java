package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		UserDAO dao = UserDAO.getInstance();
		System.out.println("Login userID : " + userID);
		System.out.println("Login userPassword : " + userPassword);
		
		
		/* 로그인 정보가 하나라도 안들어 온 경우  */
		if(userID == null || userID.equals("") ||
			userPassword == null || userPassword.equals("")) {
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			resp.sendRedirect("login.jsp");
			return;
		}
		int result = dao.login(userID, userPassword);
		
		if(result == 1) {
			req.getSession().setAttribute("userID", userID);	//로그인 세션 세팅
			req.getSession().setAttribute("messageType", "성공 메세지");
			req.getSession().setAttribute("messageContent", "로그인 완료.");
			resp.sendRedirect("index.jsp");
		} else if(result == 2) {
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "잘못된 비밀번호입니다.");
			resp.sendRedirect("login.jsp");
		} else if(result == 0){
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "가입하지 않은 아이디입니다.");
			resp.sendRedirect("login.jsp");
		} else {
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "로그인 DB 오류 발생");
			resp.sendRedirect("login.jsp");
		}
		
		
	}
	
	
	
}
