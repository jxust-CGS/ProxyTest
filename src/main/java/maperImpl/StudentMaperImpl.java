package maperImpl;

import maper.StudentMaper;

import java.sql.SQLException;
import java.sql.Statement;

public class StudentMaperImpl implements StudentMaper {
    private Statement statement;

    public int insert() throws SQLException {
        int result = statement.executeUpdate("insert into student VALUES(5,'mike',9)");
        System.out.println("插入数据成功！");
        return result;
    }
}
