package orderManagemaentSystem;

import orderManagemaentSystem.ManagementSystem;

import java.sql.SQLException;

/**
 * 测试类，订单管理系统实例化，运行
 *
 * @Author 31445
 * @Date 2023/11/23
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        ManagementSystem m=new ManagementSystem();
        m.TestManagement();
    }
}
