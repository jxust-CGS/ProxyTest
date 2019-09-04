import factory.ProxyFactory;
import maper.StudentMaper;
import maperImpl.StudentMaperImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //test();
        StudentMaper studentMaper = (StudentMaper) new ProxyFactory().createProxyObject(new StudentMaperImpl());
        studentMaper.insert();
        System.out.println();
    }
    public static void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        Statement statement = null;
        connection = DriverManager.getConnection("jdbc:mysql://149.28.89.65:3306/mybites_test","root","MyNewPass4!");
        statement = connection.createStatement();
        statement.execute("insert into student VALUES(3,'mike',9)");
        statement.close();
        connection.close();
    }
}
