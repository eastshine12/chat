package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		String userID = req.getParameter("userID");
		String userPassword1 = req.getParameter("userPassword1");
		String userPassword2 = req.getParameter("userPassword2");
		String userName = req.getParameter("userName");
		String userAge = req.getParameter("userAge");
		String userGender = req.getParameter("userGender");
		String userEmail = req.getParameter("userEmail");
		String userProfile = req.getParameter("userProfile");
		UserDAO dao = UserDAO.getInstance();
		//resp.getWriter().write(dao.registerCheck(userID)+"");
		
		System.out.println("userID : " +userID);
		System.out.println("userPassword1 : " +userPassword1);
		System.out.println("userPassword2 : " +userPassword2);
		System.out.println("userName : " +userName);
		System.out.println("userAge : " +userAge);
		System.out.println("userGender : " +userGender);
		System.out.println("userEmail : " +userEmail);
		System.out.println("userProfile : " +userProfile);
		
		
		/* 회원가입 정보가 하나라도 안들어 온 경우 (프로필사진 제외) */
		if(userID == null || userID.equals("") ||
			userPassword1 == null || userPassword1.equals("") ||
			userPassword2 == null || userPassword2.equals("") ||
			userName == null || userName.equals("") ||
			userAge == null || userAge.equals("") ||
			userGender == null || userGender.equals("") ||
			userEmail == null || userEmail.equals("")) {
			
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			resp.sendRedirect("join.jsp");
			return;
		}
		
		/* 값이 다 들어왔지만, 비밀번호 확인이 틀린 경우 */
		if(!userPassword1.equals(userPassword2)) {
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			resp.sendRedirect("join.jsp");
			return;
		}
		
		int result = dao.register(userID, userPassword1, userName, userAge, userGender, userEmail, userProfile);
		
		/* 회원가입 버튼 눌렀을 때 */
		if(result == 1) {
			req.getSession().setAttribute("userID", userID);	//회원가입 완료하면 아이디 세션에 저장
			req.getSession().setAttribute("messageType", "성공 메세지");
			req.getSession().setAttribute("messageContent", "회원가입이 완료 되었습니다.");
			resp.sendRedirect("index.jsp");
		} else {
			req.getSession().setAttribute("messageType", "오류 메세지");
			req.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			resp.sendRedirect("join .jsp");
		}
		
		
	}
	
	

}
