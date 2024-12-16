package ch02sourcecode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreate {

	public static void main(String[] args) {
		Connection connection = null;

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();

			// ...

		} catch(SQLException e)
			{
				System.err.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
		}
	}
}
