package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserRegisterCheckServlet")
public class UserRegisterCheckServlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("UserRegisterCheckServlet doPost");
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		String userID = req.getParameter("userID");
		UserDAO dao = UserDAO.getInstance();
		String result = dao.registerCheck(userID)+"";
		resp.getWriter().write(result);	//중복아이디 체크 (0 : 중복, 1 : 가능). 문자열로 출력.
		
		
		
		System.out.println("0 : 중복 / 1 : 가능 --> "+result);
	}
	
	

}
