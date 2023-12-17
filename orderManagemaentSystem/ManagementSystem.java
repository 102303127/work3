package orderManagemaentSystem;

import orderManagemaentSystem.jdbcutils;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;
/**
 * 商品订单控制系统
 *
 * @Author 31445
 * @Date 2023/11/23
 */
public class ManagementSystem implements orderManagement {

    @Override
    public void addProduct(Scanner sc) throws SQLException {
        Connection conn=null;
        try {
            conn = jdbcutils.getConnection();
            System.out.println("请输入商品名：");
            String productName=sc.next();
            //检测输入的商品名是否存在
            jdbcutils.productHasExistException(conn,productName);
            System.out.println("输入商品价格：");
            int productPrice=sc.nextInt();

            String sql = "insert into `product` ( `productName`, `productPrice`) values (?, ?)";
            int i = jdbcutils.executeUpdate(conn, sql,  productName, productPrice);
            if (i > 0) {
                System.out.println("商品【"+productName+"】添加成功！");
            } else {
                System.out.println("商品【"+productName+"】添加失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚事务
            jdbcutils.rollTranscation(conn);
        } catch (MyException e) {
            System.out.println("输入错误："+e.getMessage());
        }finally {
            jdbcutils.release(conn, null, null);
        }
    }

    @Override
    public void deleteProdect(Scanner sc) throws SQLException {
        Connection conn=null;
        try {
            conn= jdbcutils.getConnection();
            System.out.println("输入删除商品号：");
            int prodectId=sc.nextInt();
            //检查商品存在
            jdbcutils.productNotFountException(conn,prodectId);

            String sql="delete from `product` where `productId`=?";
            int i = jdbcutils.executeUpdate(conn,sql,prodectId);
            if (i > 0) {
                System.out.println(prodectId+"号商品删除成功！");
            } else {
                System.out.println(prodectId+"号商品删除失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚事务
            jdbcutils.rollTranscation(conn);
        } catch (MyException e) {
            System.out.println("输入异常："+e.getMessage());
        }finally {
            jdbcutils.release(conn,null,null);
        }
    }

    @Override
    public void updateProdect(Scanner sc) throws SQLException {
        Connection conn=null;
        try {
            conn= jdbcutils.getConnection();
            System.out.println("输入商品编号：");
            int productId=sc.nextInt();
            //检查商品是否存在
            jdbcutils.productNotFountException(conn,productId);
            System.out.println("输入新商品名：");
            String productName=sc.next();

            System.out.println("输入新商品价格：");
            double productPrice=sc.nextInt();

            String sql="update `product` set `productName`=? ,`productPrice`=? where `productId`=?";
            int i = 0;
                i = jdbcutils.executeUpdate(conn,sql,productName,productPrice,productId);
            if (i > 0) {
                System.out.println(productId+"号商品更改成功！");
            } else {
                System.out.println(productId+"号商品更改失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚事务
            jdbcutils.rollTranscation(conn);
        } catch (MyException e) {
            System.out.println("输入错误："+e.getMessage());
        } finally {
            jdbcutils.release(conn,null,null);
        }
    }

    @Override
    public void viewProdect() throws SQLException {
        Connection conn=null;
        ResultSet rs = null;
        try {
            conn= jdbcutils.getConnection();
            String sql = "select `productId`, `productName`, `productPrice` from `product` ";
            rs = null;
            rs = jdbcutils.executeQuery(conn, sql);
            System.out.println("商品编号\t商品名\t商品价格");
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                System.out.println(productId + "\t" + productName + "\t" + productPrice);
            }
            jdbcutils.commitTranscation(conn);
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            jdbcutils.rollTranscation(conn);
        } finally {
            jdbcutils.release(conn, null, rs);
        }
    }
    @Override
    public void addOrder(Scanner sc) throws SQLException {
        Connection conn=null;
        ResultSet rs= null;
        try {
            conn= jdbcutils.getConnection();
            System.out.println("输入商品编号：");
            int productId=sc.nextInt();
            System.out.println("输入订单价格：");
            double orderPrice=sc.nextDouble();

            String sql1="select `productPrice` from `product` where `productId`=?";
            rs = jdbcutils.executeQuery(conn,sql1,productId);
            rs.next();
            String p= rs.getString(1);
            double productPrice=Double.parseDouble(p);
            //检查商品存在
            jdbcutils.productNotFountException(conn,productId);
            //检查订单单价是否合理
            jdbcutils.IllegalPriceException(orderPrice,productPrice);

            String sql="insert into `order`(`productId`,`orderPrice`,`productPrice`) values(?,?,?)";
            int i= jdbcutils.executeUpdate(conn,sql,productId,orderPrice,productPrice);

            if (i > 0) {
                System.out.println("订单添加成功！");
            } else {
                System.out.println("订单添加失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            // 回滚事务
            jdbcutils.rollTranscation(conn);
        } catch (MyException e) {
            System.out.println("输入错误："+e.getMessage());
        } finally {
            jdbcutils.release(conn,null,rs);
        }
    }

    public void deleteOrder(Scanner sc) throws SQLException {
        Connection conn=null;
        try {
            conn= jdbcutils.getConnection();
            System.out.println("输入订单编号：");
            int orderId=sc.nextInt();

            //检查订单是否存在
            jdbcutils.orderNotFoundException(conn,orderId);

            String sql1 = "DELETE FROM `order` WHERE `orderId` = ?";
            int rows = jdbcutils.executeUpdate(conn, sql1, orderId);
            if (rows > 0) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MyException e) {
            System.out.println("输入错误："+e.getMessage());
        } finally {
            jdbcutils.release(conn, null,null);
        }
    }

    public void updateOrder(Scanner sc) throws SQLException {
        Connection conn=null;
        ResultSet rs = null;
        try {
            conn= jdbcutils.getConnection();
            System.out.println("输入订单编号：");
            int orderId=sc.nextInt();
            //检查订单是否存在
            jdbcutils.orderNotFoundException(conn,orderId);
            System.out.println("输入新商品编号：");
            int newProductId =sc.nextInt();
            System.out.println("输入新订单价格：");
            double newOrderPrice=sc.nextInt();

            rs = jdbcutils.executeQuery(conn, "select `productPrice` from `product` where `productId`=?", newProductId);
            rs.next();
            String p = rs.getString("productPrice");
            double productPrice = Double.parseDouble(p);
            jdbcutils.IllegalPriceException(newOrderPrice,productPrice);

            String sql = "update `order` set `productId`=?,`orderPrice`=?,`productPrice`=?  where `orderId`=?";
            int i = jdbcutils.executeUpdate(conn, sql, newProductId, newOrderPrice, productPrice, orderId);
            if (i > 0) {
                System.out.println("商品更改成功！");
            }
            else {
                System.out.println("商品更改失败！");
            }
            jdbcutils.commitTranscation(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MyException e) {
            System.out.println("输入错误："+e.getMessage());
        } finally {
            jdbcutils.release(conn, null, rs);
        }
    }
    @Override
    public void viweOrder() throws SQLException {
        Connection conn = jdbcutils.getConnection();
        String sql ="select `orderId`, `productId`, `productPrice`,`orderPrice` ,`time`from `order` ";
        ResultSet rs= null;
        try {
            rs = jdbcutils.executeQuery(conn, sql);

            System.out.println("订单编号"+"\t"+"商品编号"+"\t"+"商品价格"+"\t"+"订单价格"+"\t"+"下单时间");
            while (rs.next()) {
                int orderId =rs.getInt("orderId");
                int productId = rs.getInt("productId");
                double productPrice = rs.getDouble("productPrice");
                double orderPrice =rs.getDouble("orderPrice");
                Date orderDate=rs.getDate("time");
                System.out.println(orderId +" \t    " + productId + "\t     " + productPrice + "\t  " + orderPrice +"\t  "+orderDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            jdbcutils.release(conn,null,rs);
        }
    }

    @Override
    public void rankOrder( Scanner sc) throws SQLException {
        Connection conn= jdbcutils.getConnection();
        String seat2, seat3;
        System.out.println("对商品还是订单排序：");
        System.out.println("1.商品");
        System.out.println("2.订单");
        int choice1 = sc.nextInt();
        System.out.println("请选择排序方式：");
        String sql = "";
        if (choice1 == 1) {
            sql = "SELECT * FROM `product` ORDER BY ";
            seat2 = "productPrice";
            System.out.println("1.按价格升序");
            System.out.println("2.按价格降序");
            int i = sc.nextInt();
            switch (i) {
                case 2:
                    seat3 = "DESC";
                    break;
                case 1:
                default:
                    seat3 = "ASC";
                    break;
            }
            sql += "`" + seat2 + "` " + seat3;
            ResultSet rs = jdbcutils.executeQuery(conn, sql);
            System.out.println("商品编号"+"\t"+"商品名"+"\t"+"商品价格");
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName=rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                System.out.println(productId + "\t     " +productName+"\t     "+ productPrice );
            }
        }
        else {
            sql = "SELECT * FROM `order` ORDER BY ";
            System.out.println("1.按下单时间升序");
            System.out.println("2.按下单时间降序");
            System.out.println("3.按价格升序");
            System.out.println("4.按价格降序");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    seat2 = "time";
                    seat3 = "ASC";
                    break;
                case 2:
                    seat2 = "time";
                    seat3 = "DESC";
                    break;
                case 3:
                    seat2 = "orderPrice";
                    seat3 = "ASC";
                    break;
                case 4:
                    seat2 = "orderPrice";
                    seat3 = "DESC";
                    break;
                default:
                    System.out.println("无效的选择，默认按照下单时间顺序");
                    seat2 = "time";
                    seat3 = "ASC";
                    break;
            }
            sql += "`" + seat2 + "` " + seat3;
            ResultSet rs = jdbcutils.executeQuery(conn, sql);
            System.out.println("订单编号"+"\t"+"商品编号"+"\t"+"商品价格"+"\t"+"订单价格"+"\t"+"下单时间");
            while (rs.next()) {
                int orderId =rs.getInt("orderId");
                int productId = rs.getInt("productId");
                double productPrice = rs.getDouble("productPrice");
                double orderPrice =rs.getDouble("orderPrice");
                Date orderDate=rs.getDate("time");
                System.out.println(orderId +" \t    " + productId + "\t     " + productPrice + "\t  " + orderPrice +"\t  "+orderDate);

            }
            jdbcutils.release(conn, null, rs);
        }
    }
    public void TestManagement() throws SQLException {
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择要执行的选项：");
        System.out.println("1.增加商品");
        System.out.println("2.删除商品");
        System.out.println("3.修改商品");
        System.out.println("4.查看商品");
        System.out.println("5.增加订单");
        System.out.println("6.删除订单");
        System.out.println("7.修改订单");
        System.out.println("8.查看订单");
        System.out.println("9.排序");
        int choice =sc.nextInt();
        switch (choice){
            case 1:
                addProduct(sc);
                break;
            case 2:
                deleteProdect(sc);
                break;
            case 3:
                updateProdect(sc);
                break;
            case 4:
                viewProdect();
                break;
            case 5:
                addOrder(sc);
                break;
            case 6:
                deleteOrder(sc);
                break;
            case 7:
                updateOrder(sc);
                break;
            case 8:
                viweOrder();
                break;
            case 9:
                rankOrder(sc);
                break;
        }
        System.out.println("需要进行下一次操作：");
        System.out.println("1.进行");
        System.out.println("2.退出");
        int choice2=sc.nextInt();
        if(choice2==1){
            TestManagement();
        }
        else System.out.println("感谢使用，Byb!");
    }

}
