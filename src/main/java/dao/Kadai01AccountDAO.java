package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Kadai01Account;
import util.GenerateHashedPw;
import util.GenerateSalt;

public class Kadai01AccountDAO{
	private static Connection getConnection() throws URISyntaxException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
		}
	public static int registerKadai01Account(Kadai01Account account) {
		String sql = "INSERT INTO account_table VALUES(default, ?, ?, ?, ?, ?, ?, ?,current_timestamp)";
		int result=0;
		// ランダムなソルトの取得(今回は32桁で実装)
		String salt = GenerateSalt.getSalt(32);
				
		// 取得したソルトを使って平文PWをハッシュ
		String hashedPw = GenerateHashedPw.getSafetyPassword(account.getPassword(), salt);
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, account.getName());
			pstmt.setInt(2, account.getAge());
			pstmt.setString(3, account.getGender());
			pstmt.setString(4, account.getTell());
			pstmt.setString(5, account.getMail());
			pstmt.setString(6, salt);
			pstmt.setString(7, hashedPw);

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	public static List<Kadai01Account> selectAllAccount() {
		
		// 返却用変数
		List<Kadai01Account> result = new ArrayList<>();

		String sql = "SELECT * FROM account_table";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String gender = rs.getString("gender");
					String tell = rs.getString("tell");
					String mail = rs.getString("mail");
					String salt = rs.getString("salt");
					
					Kadai01Account account = new Kadai01Account(name, age, gender, tell, mail, salt, null,null);
					
					result.add(account);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// Listを返却する。0件の場合は空のListが返却される。
		return result;
	}
	public static Kadai01Account login(String mail, String hashedPw) {
		String sql = "SELECT * FROM account_table WHERE mail = ? AND password = ?";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);
			pstmt.setString(2, hashedPw);

			try (ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String gender = rs.getString("gender");
					String tell = rs.getString("tell");
					String salt = rs.getString("salt");
					String createdAt = rs.getString("created_at");
					
					 return new Kadai01Account( name, age, gender, tell, mail, salt, null,null);
					 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	// メールアドレスを元にソルトを取得
		public static String getSalt(String mail) {
			String sql = "SELECT salt FROM account_table WHERE mail = ?";
			
			try (
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					){
				pstmt.setString(1, mail);

				try (ResultSet rs = pstmt.executeQuery()){
					
					if(rs.next()) {
						String salt = rs.getString("salt");
						return salt;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}
	
}