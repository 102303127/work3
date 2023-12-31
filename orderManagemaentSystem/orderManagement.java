package orderManagemaentSystem;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * 接口实现对方法管理，对商品，订单的增删改。
 * 查询，排序方法属于隐私部分，未通过接口实现
 *
 * @Author 31445
 * @Date 2023/11/23
 */
public interface orderManagement {
    /**
     * 实现增加商品
     *
     * @param sc
     * @throws SQLException
     */
    void addProduct(Scanner sc) throws SQLException;

    /**
     * 删除商品
     *
     * @param sc
     * @throws SQLException
     */
    void deleteProdect(Scanner sc) throws SQLException;

    /**
     * 更新商品
     *
     * @param sc
     * @throws SQLException
     */
    void updateProdect(Scanner sc) throws SQLException;

    /**
     * 查询商品
     *
     * @throws SQLException
     */
    void viewProdect() throws SQLException;
    /**
     * 增加订单
     *
     * @param sc
     * @throws SQLException
     */

    void addOrder(Scanner sc) throws SQLException;

    /**
     * 删除订单
     *
     * @param sc
     * @throws SQLException
     */
    void deleteOrder(Scanner sc) throws SQLException;

    /**
     * 更改订单
     *
     * @param sc
     * @throws SQLException
     */
    void updateOrder(Scanner sc) throws SQLException;

    /**
     * 查询订单
     *
     * @throws SQLException
     */
    void viweOrder() throws SQLException;

    /**
     * 对商品订单进行排序。
     *
      * @param sc
     * @throws SQLException
     */
    void rankOrder( Scanner sc) throws SQLException;
}
