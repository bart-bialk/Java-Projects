import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ScoresServlet", value = "/scores-servlet")
public class ScoresServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");

        PrintWriter respWriter = resp.getWriter();
        respWriter.println("<!DOCTYPE html>");
        respWriter.println("<html>");
        respWriter.println("<head>");
        respWriter.println("<link href='servletStyle.css' type='text/css' rel='stylesheet' />");
        respWriter.println("<title>Wyniki Reprezentacji Polski</title>");
        respWriter.println("</head>");
        respWriter.println("<body>");
        respWriter.println("<h1>Mecze Reprezentacji Polski</h1>");
        respWriter.println("<form>");
        respWriter.println("  <label>");
        respWriter.println("    <p class= \"search1\">Przeciwnik:</p>");
        respWriter.println("    <input type=\"text\" name=\"reprezentacja\" />");
        respWriter.println("  </label>");
        respWriter.println("  <label>");
        respWriter.println("    <p class= \"search1\">Rok:</p>");
        respWriter.println("    <input type=\"text\" name=\"rok\" />");
        respWriter.println("  </label>");
        respWriter.println("  <label>");
        respWriter.println("    <p class= \"search1\">Data Od:</p>");
        respWriter.println("    <input type=\"text\" name=\"dataOd\" />");
        respWriter.println("  </label>");
        respWriter.println("  <label>");
        respWriter.println("    <p class= \"search1\">Data Do:</p>");
        respWriter.println("    <input type=\"text\" name=\"dataDo\" />");
        respWriter.println("  </label>");
        respWriter.println("  <input class= \"button\" type=\"submit\" value=\"Szukaj\" />");
        respWriter.println("</form>");
        respWriter.println("<div");

        String teamParameter = req.getParameter("reprezentacja");
        String year = req.getParameter("rok");
        String dateSncParameter = req.getParameter("dataOd");
        String dateToParameter = req.getParameter("dataDo");


        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DB-MSSQL16;databaseName=2019SBD;IntegratedSecurity=true;encrypt=false;";
            connection = DriverManager.getConnection(url);
            String query;

            query = "select r.Nazwa, m.Data, m.BramkiPolska, m.BramkiPrzeciwnik" +
                    " from mecz m join reprezentacja r on m.Reprezentacja = r.IdReprezentacja";

            if (teamParameter != null || year != null || dateSncParameter != null || dateToParameter != null) {
                query += " where";

                if (!teamParameter.equals("")) {
                    query += " r.Nazwa = '" + teamParameter + "'";
                    if (!year.equals("") || !dateSncParameter.equals("") || !dateToParameter.equals("")) {
                        query += " and";
                    }
                }
                if (!year.equals("")) {
                    query += " Year(m.Data) = '" + year + "'";
                    if (!dateSncParameter.equals("") || !dateToParameter.equals("")) {
                        query += " and";
                    }
                }
                if (!dateSncParameter.equals("")) {
                    query += " m.Data > cast( '" + dateSncParameter + "' as datetime)";
                    if (!dateToParameter.equals("")) {
                        query += " and";
                    }
                }
                if (!dateToParameter.equals("")) {
                    query += " m.Data < cast( '" + dateToParameter + "' as datetime)";
                }
            }


            query += " order by m.data";
            System.out.println(query);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            respWriter.println("<ol>");
            while (resultSet.next()) {
                String team = resultSet.getString("Nazwa");
                String date = resultSet.getString("Data");
                int goalsPoland = resultSet.getInt("BramkiPolska");
                int goalsEnemy = resultSet.getInt("BramkiPrzeciwnik");
                String color;
                if (goalsEnemy > goalsPoland) {
                    color = "red";
                } else if (goalsEnemy < goalsPoland) {
                    color = "green";
                } else {
                    color = "yellow";
                }
                respWriter.println("<p class=" + color + ">" + " " + date + " " + " Polska " + goalsPoland
                        + "-" + goalsEnemy + " " + team + " </p>");
            }
            respWriter.println("</ol>");

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
        respWriter.println("</div>");
        respWriter.println("</body>");
        respWriter.println("</html>");
        respWriter.close();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
