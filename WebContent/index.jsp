<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<title>JSP Ajax 실시간 회원제 채팅 서비스</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
</head>
<body>

	<%
		/* 로그인 처리 */
		String userID = null;
		if(session.getAttribute("userID") != null) {
			userID = (String)session.getAttribute("userID"); //로그인 된 유저id 가져온다.
		}
	%>	
	
	<!-- 네비게이션 -->
	<nav class="navbar navbar-default">
		<!-- 좌측 상단 -->
 		
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" 
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
			aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">실시간 회원제 채팅 서비스</a>
		</div>
		 
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<!-- 메인 버튼 -->
			<ul class="nav navbar-nav">
				<li class="active"><a href="index.jsp">메인</a>
			</ul>
			
			<!-- 우측 상단 -->
			<%	//비로그인
				if(userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<!-- 드롭다운 메뉴 -->
				<li class="dropdown">	
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			<%	//로그인
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="nav-item">
     				<a class="nav-link disabled" tabindex="-1" aria-disabled="true"><%=userID %>님 반갑습니다</a>
    			</li>
				<li class="dropdown">	
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="logoutAf.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<%		
				} 
			%>
		
		</div> 
	</nav>
	
	
	
		<!-- ---------중복체크 & 회원가입 버튼 alert--------- -->
	<%
		/* UserRegisterServlet.java에서 성공 or 오류 메세지 중 어떤걸 받았는지와 그에대한 정보값을 알려준다. */
		
		String messageContent = null;
		if (session.getAttribute("messageContent") != null) {
			messageContent = (String)session.getAttribute("messageContent");
		}
		
		String messageType = null;
		if (session.getAttribute("messageType") != null) {
			messageType = (String)session.getAttribute("messageType");
		}
			
		if(messageContent != null) { //성공 or 에러 메세지를 출력하는 경우 --> 회원가입 버튼 누를 때 
	 %>
		<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="vertical-alignment-helper">
				<div class="modal-dialog vertical-align-center">
					<div class="modal-content 
					<% if(messageType.equals("오류 메세지")) out.println("panel-warning"); 
						else out.println("panel-success"); %>">
						<div class="modal-header panel-heading">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times</span>
								<span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title">
								<%=messageType %>
							</h4>
						</div>	
						
						<div class="modal-body">
							<%=messageContent %>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
						</div>					
					</div>
				</div>
			</div>
		</div>
		<script>
			$('#messageModal').modal("show");
		</script>
		<%
			session.removeAttribute("messageContent");
			session.removeAttribute("messageType");
		}
		%>




</body>
</html>