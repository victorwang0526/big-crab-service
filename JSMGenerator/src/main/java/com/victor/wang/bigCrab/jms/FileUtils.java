package com.victor.wang.bigCrab.jms;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils
{
	private static FileUtils fileUtils = new FileUtils();

	public final String JAVA_PATH_ROOT = "./BigCrabService/src/main/java/com/victor/wang/bigCrab/";
	public final String MAPPER_PATH_ROOT = "./BigCrabService/src/main/resources/mapper/";

	private FileUtils()
	{
	}

	public static FileUtils getInstance()
	{
		return fileUtils;
	}

	public void writeToJava(String dir, String fileName, String content){
		this.writeToFile(JAVA_PATH_ROOT+dir+"/"+fileName, content);
	}

	public void writeToMapper(String fileName, String content) {
		this.writeToFile(MAPPER_PATH_ROOT+fileName, content);
	}

	private void writeToFile(String fileName, String content)
	{
		FileWriter writer;
		try
		{
			writer = new FileWriter(fileName);
			writer.write(content);
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
