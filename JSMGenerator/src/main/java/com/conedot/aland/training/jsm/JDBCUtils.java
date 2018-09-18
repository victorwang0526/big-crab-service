package com.conedot.aland.training.jsm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils
{
	public static List<JsmColumn> getJsmColumn(String tableName){
		Connection connection= getConnection();
		Statement stm=null;
		ResultSet rs=null;
		List<JsmColumn> jsmColumns = new ArrayList<>();
		try {
			stm= connection.createStatement();
			String sql="select " +
					" table_name AS TABLE_NAME, " +
					" column_name AS COLUMN_NAME, " +
					" data_type AS DATA_TYPE, " +
					" '' AS COMMENTS " +
					" FROM INFORMATION_SCHEMA.COLUMNS " +
					" WHERE TABLE_NAME = '"+tableName.toLowerCase()+"'" +
					" ORDER BY ORDINAL_POSITION ";
			rs=stm.executeQuery(sql);
			while(rs.next()){
				jsmColumns.add(new JsmColumn(rs.getString(1),
						rs.getString(2), rs.getString(3),rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			release(connection, stm, rs);
		}
		return jsmColumns;
	}
	public static Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url="jdbc:sqlserver://118.178.237.18:1433;database=training";
			String username="training";
			String password="training";
			connection= DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void release(Connection con, Statement statement, ResultSet rs){
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs=null;
			}
			if(null!=statement){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					statement=null;
				}
			}
			if(null!=con){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					con=null;
				}
			}
		}
	}
}
