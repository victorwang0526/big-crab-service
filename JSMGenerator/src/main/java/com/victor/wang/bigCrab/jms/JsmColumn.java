package com.victor.wang.bigCrab.jms;

public class JsmColumn
{
	private String tableName;
	private String columnName;
	private String dataType;
	private String comments;

	public JsmColumn(String tableName, String columnName, String dataType, String comments){
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.comments = comments;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}
}
