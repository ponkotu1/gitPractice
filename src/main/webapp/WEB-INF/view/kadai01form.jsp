<%@page import="dto.Kadai01Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録画面</title>
</head>
<body>
<%
		request.setCharacterEncoding("UTF-8");
		String errorCode = request.getParameter("error");
		if(errorCode != null && errorCode.equals("1")){
			Kadai01Account ac = (Kadai01Account)session.getAttribute("input_data");
	%>
		<p style="color:red">登録に失敗しました。</p>
		<h3>新規会員登録</h3>
		<form action="Kadai01RegisterConfirmServlet" method="post">
			名前：<input type="text" name="name" value="<%=ac.getName()%>"><br>
			年齢：<input type="text" name="age"><br>
			性別：<input type="radio" name = "gender" value="男">男:<br>
			<input type="radio" name = "gender" value="女">女:<br>
			電話番号：<input type="text" name="tell"><br>
			メール：<input type="email" name="mail" value="<%=ac.getMail()%>"><br>
			パスワード：<input type="password" name="pw"><br>
			<input type="submit" value="登録">
		</form>
	<%
		} else {
	%>
	<h3>新規会員登録</h3>
	<form action="Kadai01RegisterConfirmServlet" method="post">
		名前：<input type="text" name="name"><br>
		年齢：<input type="text" name="age"><br>
		性別：<input type="radio" name = "gender" value="男">男:
		<input type="radio" name = "gender" value="女">女:<br>
		電話番号：<input type="text" name="tell"><br>
		メール：<input type="email" name="mail"><br>
		パスワード：<input type="password" name="pw"><br>
		<input type="submit" value="登録">
	</form>
	<%
		}
	%>
</body>
</html>