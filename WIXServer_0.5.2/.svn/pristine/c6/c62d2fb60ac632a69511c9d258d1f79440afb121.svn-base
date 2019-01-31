package core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.data.Constant;
import core.util.LogUtil;

/**
 * recommend
 * @author shinzato
 */
@WebServlet(name = "recommend", urlPatterns = "/recommend")
public class Recommend extends HttpServlet{

	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;

	// singleton pattern
	private static LogUtil logger;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(Recommend.class);
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();

		String keitaiso_result = request.getParameter("body");
		String outputText = "";

		String[] keywords = keitaiso_result.split("\n");

		Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        PreparedStatement ps = null;

        String url = "jdbc:postgresql://localhost:5432/wix";
        String user = "wixadm";
        String password = "";

        try{
            Class.forName("org.postgresql.Driver");

            //PostgreSQLへ接続
            conn = DriverManager.getConnection(url, user, password);

            //自動コミットOFF
            conn.setAutoCommit(false);
            String sql = "SELECT m.wid, m.name, count(*) from wix_file_meta_info m, wix_file_entry e where m.wid = e.wid AND m.wid != 13 AND (e.keyword like ?";
            int i;
            for (i = 1; i < keywords.length; i++) {
            		sql += " OR e.keyword like ?";
            }
            sql += ") GROUP BY m.wid ORDER BY count DESC";
            ps = conn.prepareStatement(sql);
            int j;
            String key = "";
            for (j = 0; j < keywords.length; j++) {
            		key = keywords[j];
            		ps.setString(j + 1, key);
            }
            Long queryStart = System.currentTimeMillis();
            rset = ps.executeQuery();
            Long queryEnd = System.currentTimeMillis();
//            out.println(ps.toString()+ "<br>");
//            out.println(queryEnd - queryStart + " ms<br>");

            //SELECT結果の受け取り
            while(rset.next()){
                String wid = rset.getString("wid");
                String wixfileName = rset.getString("name");
                String widCount = rset.getString("count");
//                out.println(wid + " " + wixfileName + " -> " + widCount + "個<br>");
                outputText += "<button class=\"wix-attach\" wid=\"" + wid + "\">" + wixfileName + "</button><br>";
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            out.println(e);
        }
        catch (SQLException e){
            e.printStackTrace();
            out.println(e);
        }
        finally {
            try {
                if(rset != null)rset.close();
                if(stmt != null)stmt.close();
                if(conn != null)conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
                out.println(e);
            }
        }

        out.println(outputText);
	}
}
