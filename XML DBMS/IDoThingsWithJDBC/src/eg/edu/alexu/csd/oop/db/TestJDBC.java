package eg.edu.alexu.csd.oop.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSet;

public class TestJDBC {
	public static void main(String[] args){
		MyDriver driver = new MyDriver();
		Properties properties= new Properties();
		String path = "menu";
		properties.put("path", path);
		String connectionURL = "jdbc:super//localhost:0000/dessert;user=hungrybunny;password=hamburger";
		String query = "create table menu (item varchar, price int, topping varchar);";
		MyConnection connection = new MyConnection(path);
		MyStatement statement = new MyStatement(connection.path, connection);
		try {
			connection = (MyConnection) driver.connect(connectionURL, properties);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.execute(query));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		query = "create table im_deleting_this (col1 int, col2 varchar);";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.execute(query));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		query = "insert into im_deleting_this (col1, col2) values (100, bye);";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.executeUpdate(query));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query = "delete from im_deleting_this where col1>4;";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.executeUpdate(query));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query = "drop table im_deleting_this;";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.execute(query));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.execute(query));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query = "insert into menu (item, price, topping) values (cupcake, 100, marshmellow);";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.executeUpdate(query));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query = "insert into menu (item, price, topping) values (doughnut, 70, sprinkles);";
		try {
			statement = (MyStatement) connection.createStatement();
			System.out.println(statement.executeUpdate(query));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "select item, price from menu where price>20;";
		try {
			statement = (MyStatement) connection.createStatement();
			MyResultSet set = (MyResultSet) statement.executeQuery(query);
			while (set.next()){
				String itemName = set.getString("item");
				int price = set.getInt("price");
				String topping = set.getString(3);
				System.out.println("\t\titem name:\t" + itemName + "\tprice:\t" + price);
			}
			set.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement.close();
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
