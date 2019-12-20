package factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ProxyFactory implements InvocationHandler {

    private Object object;

    Connection connection;
    Statement statement;

    public Object createProxyObject(Object targetObject) throws NoSuchFieldException, IllegalAccessException, SQLException, ClassNotFoundException {
        this.object = targetObject;
        /**
         * 1、ClassLoader：模板对象的classloader
         * 2、interface：代理模板对象中实现的接口
         * 3、Handler：当代理对象执行接口定义方法时有哪个对象去监听
         */
        Object $proxy =  Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://149.28.89.65:3306/mybites_test","root","MyNewPass4!");
        statement = connection.createStatement();
        Field field = object.getClass().getDeclaredField("statement");
        field.setAccessible(true);
        field.set(object,statement);
        return $proxy;
    }


    /**
     * 当前对象监听到了接口被调用，拦截代码执行
     * @param proxy 代理对象
     * @param method 当前执行的方法
     * @param args 当前方法的入参
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------拦截到方法："+method.getName()+"被执行----------------");
        Object object = null;
        before();
        object = method.invoke(this.object,args);
        after();
        return object;
    }

    private void before() throws SQLException, ClassNotFoundException {
        System.out.println("前置处理操作。。。");
    }

    private void after() throws SQLException {
        statement.close();
        connection.close();
        System.out.println("收尾处理操作。。。");
    }
}
