<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Kadai01Account" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント情報一覧</title>
</head>
<body>
<h1>アカウント一覧</h1>
	<table border="1">
		<tr>
			<th>名前</th>
			<th>年齢</th>
			<th>性別</th>
			<th>メール</th>
			<th>電話番号</th>
		</tr>
	<%
	List<Kadai01Account> list = (ArrayList<Kadai01Account>)request.getAttribute("list");
	for(Kadai01Account s : list) {
	%>
		<tr>
			<td><%=s.getName() %></td>
			<td><%=s.getAge() %></td>
			<td><%=s.getGender() %></td>
			<td><%=s.getMail() %></td>
			<td><%=s.getTell() %></td>
		</tr>
	<%} %>
	</table>
	<a href="./">トップへ	</a>
</body>
</html>