package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Kadai01Account;

/**
 * Servlet implementation class Kadai01RegisterConfirmServlet
 */
@WebServlet("/Kadai01RegisterConfirmServlet")
public class Kadai01RegisterConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kadai01RegisterConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String age1 = request.getParameter("age");
		String gender = request.getParameter("gender");
		String tell = request.getParameter("tell");
		String mail = request.getParameter("mail");
		String pw = request.getParameter("pw");
		
		int age = Integer.parseInt(age1);
		
		Kadai01Account account = new Kadai01Account( name, age, gender, tell,mail,null, pw,null);
		// セッションスコープのインスタンス取得
		HttpSession session = request.getSession();
				
		// セッションスコープに値の保存
		// 第1引数：キー
		// 第2引数：保存する値
		session.setAttribute("input_data", account);
				
		
		String view = "WEB-INF/view/kadai01confirm.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
