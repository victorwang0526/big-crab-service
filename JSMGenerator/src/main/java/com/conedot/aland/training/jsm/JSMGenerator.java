package com.conedot.aland.training.jsm;

import com.sun.javafx.binding.StringFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * jersey(resource) + spring(manager) + mybatis(dao/model/xml) generator
 */

public class JSMGenerator
{

	public final static String ARTIFACTS = "aland.training";
	public final static String GROUPS = "com.conedot";

	public final static String DAO_PACKAGE = StringFormatter.format("%s.%s.dao", GROUPS, ARTIFACTS).getValue();
	public final static String MODEL_PACKAGE = StringFormatter.format("%s.%s.model", GROUPS, ARTIFACTS).getValue();
	public final static String MANAGER_PACKAGE = StringFormatter.format("%s.%s.manager", GROUPS, ARTIFACTS).getValue();
	public final static String RESOURCE_PACKAGE = StringFormatter.format("%s.%s.resource", GROUPS, ARTIFACTS).getValue();
	public final static String EXCEPTION_PACKAGE = StringFormatter.format("%s.%s.exception", GROUPS, ARTIFACTS).getValue();
	public final static String SHARED_OBJECT_PACKAGE = StringFormatter.format("%s.%s.sharedObject", GROUPS, ARTIFACTS).getValue();

	public final static List<String> EXCLUDE_FIELD = Arrays.asList("id", "rvn", "lastModifiedAt", "createdAt");

	public static void main(String[] args)
	{
		new JSMGenerator().generateAll("");
	}

	/**
	 * table name should be spilt by '-'
	 *
	 * @param tableName table name should be like this: "jsm_column"
	 */
	public void generateAll(String tableName)
	{
		if(tableName == null || tableName.equals("")){
			return;
		}
		List<JsmColumn> columns = JDBCUtils.getJsmColumn(tableName);

		//name: when table name is "table_name", then the model name is "TableName"
		String modelName = getHump(tableName.substring(2), false);

		generateModel(modelName, columns);
		generateDao(modelName, columns);
		generateManager(modelName, columns);
		generateMapper(tableName, modelName, columns);
		generateResource(modelName, columns);
		generateSharedObject(modelName, columns);
		generateException(modelName);

	}

	public void generateModel(String modelName, List<JsmColumn> columns)
	{
		String content = "";

		//package & import
		content += "package " + MODEL_PACKAGE + ";\n"
				+ "import " + GROUPS + "." + ARTIFACTS + ".model.base.AuditedEntity;\n"
				+ "import java.util.Date;\n"
				+ "\n"
				+ "public class " + modelName + "\n" +
				"\textends AuditedEntity\n" +
				"{\n\n";


		//add column
		for (JsmColumn column : columns)
		{
			if(column.getColumnName().toLowerCase().equals("rvn")
					|| column.getColumnName().toLowerCase().equals("created_at")
					|| column.getColumnName().toLowerCase().equals("last_modified_at")){
				continue;
			}
			content += StringFormatter.format(
					"\t/**\n" +
							"\t * %s\n" +
							"\t */" +
							"\n\tprivate %s %s;\n\n",
					column.getComments(),
					getPropertyType(column.getDataType(), true),
					getHump(column.getColumnName(), true)).getValue();
		}

		for (JsmColumn column : columns)
		{
			if(column.getColumnName().toLowerCase().equals("rvn")
					|| column.getColumnName().toLowerCase().equals("created_at")
					|| column.getColumnName().toLowerCase().equals("last_modified_at")){
				continue;
			}
			String columnNameU = getHump(column.getColumnName(), false);
			String columnNameL = getHump(column.getColumnName(), true);
			String propertyType = getPropertyType(column.getDataType(), true);
			content += StringFormatter.format(
					"\tpublic %s get%s()\n" +
							"\t{\n" +
							"\t\treturn %s;\n" +
							"\t}\n" +
							"\n" +
							"\tpublic void set%s(%s %s)\n" +
							"\t{\n" +
							"\t\tthis.%s = %s;\n" +
							"\t}\n\n", propertyType, columnNameU, columnNameL, columnNameU,
					propertyType,
					columnNameL, columnNameL, columnNameL).getValue();
		}

		//class end
		content += "}";


		String fileName = StringFormatter.format("%s.java", modelName).get();

		FileUtils.getInstance().writeToJava("model", fileName, content);
	}

	public void generateDao(String modelName, List<JsmColumn> columns)
	{
		String content = "package " + DAO_PACKAGE + ";\n" +
				"\n" +
				"import " + MODEL_PACKAGE + "." + modelName + ";\n" +
				"import org.springframework.stereotype.Repository;\n" +
				"\n" +
				"@Repository\n" +
				"public interface " + modelName + "Dao\n" +
				"\t\textends GenericDao<String, " + modelName + ">\n" +
				"{\n" +
				"\n" +
				"\tclass " + modelName + "QueryBuild\n" +
				"\t\t\textends QueryBuilder\n" +
				"\t{\n" +
				"\n" +
				"\t\tpublic static " + modelName + "QueryBuild build()\n" +
				"\t\t{\n" +
				"\t\t\treturn new " + modelName + "QueryBuild();\n" +
				"\t\t}\n" ;
		for(JsmColumn c: columns){
			String columnName = c.getColumnName().toLowerCase();
			switch (columnName) {
				case "id":
				case "rvn":
				case "last_modified_at":
				case "created_at":
				case "del_flg":
					continue;
				default:
					if ("nvarchar".equals(c.getDataType())) {
						String humpMethod = "filterBy" + getHump(columnName, false);
						String humpVar = getHump(columnName, true);
						content +=
								"\t\tpublic " + modelName + "QueryBuild " + humpMethod + "(String " + humpVar+ ")\n" +
								"\t\t{\n" +
								"\t\t\treturn add(\"" + humpVar + "\", " + humpVar + ");\n" +
								"\t\t}\n" ;
					}
			}
		}
		content +=
				"\t}\n" +
				"\n" +
			    "}\n";

		String fileName = StringFormatter.format("%sDao.java", modelName).get();

		FileUtils.getInstance().writeToJava("dao", fileName, content);
	}

	public void generateManager(String modelName, List<JsmColumn> columns)
	{
		String lowModelName = getLowCaseFirst(modelName);
		String content = "package " + MANAGER_PACKAGE + ";\n" +
				"\n" +
				"import " + DAO_PACKAGE + "." + modelName + "Dao;\n" +
				"import " + EXCEPTION_PACKAGE + "." + modelName + "NotFoundException;\n" +
				"import " + MODEL_PACKAGE + "." + modelName + ";\n" +
				"import " + SHARED_OBJECT_PACKAGE + "." + modelName + "Create;\n" +
				"import " + SHARED_OBJECT_PACKAGE + "." + modelName + "Update;\n" +
				"import " + GROUPS + "." + ARTIFACTS + ".util.UniqueString;\n" +
				"import " + GROUPS + "." + ARTIFACTS + ".util.dao.DaoHelper;\n" +
				"import ma.glasnost.orika.MapperFacade;\n" +
				"import net.sf.oval.constraint.AssertValid;\n" +
				"import net.sf.oval.guard.Guarded;\n" +
				"import org.slf4j.Logger;\n" +
				"import org.slf4j.LoggerFactory;\n" +
				"import org.springframework.beans.factory.annotation.Autowired;\n" +
				"import org.springframework.stereotype.Service;\n" +
				"\n" +
				"import java.util.List;\n" +
				"\n" +
				"@Service\n" +
				"@Guarded\n" +
				"public class " + modelName + "Manager\n" +
				"{\n" +
				"\tprivate static final Logger LOGGER = LoggerFactory.getLogger(" + modelName + "Manager.class);\n" +
				"\n" +
				"\t@Autowired\n" +
				"\t" + modelName + "Dao "+lowModelName+"Dao;\n" +
				"\n" +
				"\t@Autowired\n" +
				"\tprivate MapperFacade mapper;\n" +
				"\n" +
				"\tpublic "+modelName+" get"+modelName+"(String id)\n" +
				"\t{\n" +
				"\t\tLOGGER.debug(\""+modelName+"Manager, get"+modelName+"; id: {}\", id);\n" +
				"\n" +
				"\t\t"+modelName+" "+lowModelName+" = "+lowModelName+"Dao.get(id);\n" +
				"\t\tif ("+lowModelName+" == null)\n" +
				"\t\t{\n" +
				"\t\t\tthrow new "+modelName+"NotFoundException(id);\n" +
				"\t\t}\n" +
				"\t\treturn "+lowModelName+";\n" +
				"\t}\n" +
				"\n" +
				"\tpublic "+modelName+" create"+modelName+"(@AssertValid "+modelName+"Create "+lowModelName+"Create)\n" +
				"\t{\n" +
				"\t\tLOGGER.info(\""+modelName+"Manager, create"+modelName+"; "+lowModelName+"Create: {}\", "+lowModelName+"Create);\n" +
				"\t\t"+modelName+" "+lowModelName+" = mapper.map("+lowModelName+"Create, "+modelName+".class);\n" +
				"\t\t"+lowModelName+".setId(UniqueString.uuidUniqueString());\n" +
				"\t\tDaoHelper.insert("+lowModelName+"Dao, "+lowModelName+");\n" +
				"\t\treturn "+lowModelName+";\n" +
				"\t}\n" +
				"\n" +
				"\tpublic "+modelName+" update"+modelName+"(String id, @AssertValid "+modelName+"Update "+lowModelName+"Update)\n" +
				"\t{\n" +
				"\t\tLOGGER.info(\""+modelName+"Manager, update"+modelName+"; id: {}, "+lowModelName+"Update: {}\", id, "+lowModelName+"Update);\n" +
				"\t\t"+modelName+" "+lowModelName+" = get"+modelName+"(id);\n" +
				"\t\tDaoHelper.updateFromSource("+lowModelName+"Dao, "+lowModelName+"Update, "+lowModelName+");\n" +
				"\t\treturn "+lowModelName+";\n" +
				"\t}\n" +
				"\n" +
				"\tpublic List<"+modelName+"> find"+modelName+"s(\n";
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += "\t\t\tString " +  getHump(columnName, true) + ",\n";
                    }
            }
        }
        content +=
                "\t\t\tint page,\n" +
                "\t\t\tint size)\n" +
				"\t{\n" +
				"\t\tLOGGER.debug(\""+modelName+"Manager, find"+modelName+"; ";
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += getHump(columnName, true) + ": {}, ";
                    }
            }
        }
        content +=
                "page: {}, size: {}\", ";
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += getHump(columnName, true) + ", ";
                    }
            }
        }
        content +=" page, size);\n" +
				"\t\t"+modelName+"Dao."+modelName+"QueryBuild queryBuild = "+modelName+"Dao."+modelName+"QueryBuild.build()\n" ;
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += "\t\t\t\t.filterBy" + getHump(columnName, false) + "(" +  getHump(columnName, true) + ")\n";
                    }
            }
        }
        content +=
                "\t\t\t\t.pagination(page, size);\n" +
				"\t\treturn "+lowModelName+"Dao.getList(queryBuild.toParameter());\n" +
				"\t}\n" +
				"\n" +
                "\tpublic int count"+modelName+"s(";
        boolean isFirst = true;
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += (isFirst ? "": ",") + "\n\t\t\tString " +  getHump(columnName, true);
                        isFirst = false;
                    }
            }
        }
        content +=
                ")\n" +
                "\t{\n" +
                "\t\tLOGGER.debug(\""+modelName+"Manager, count"+modelName+"; ";
        isFirst = true;
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += (isFirst ? "": ", ") + getHump(columnName, true) + ": {}";
                        isFirst = false;
                    }
            }
        }
        content +=
                "\"";
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += ", " + getHump(columnName, true);
                    }
            }
        }
        content +=");\n" +
                "\t\t"+modelName+"Dao."+modelName+"QueryBuild queryBuild = "+modelName+"Dao."+modelName+"QueryBuild.build()" ;
        for(JsmColumn c: columns){
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        content += "\n\t\t\t\t.filterBy" + getHump(columnName, false) + "(" +  getHump(columnName, true) + ")";
                    }
            }
        }
        content +=
                ";\n" +
                "\t\treturn "+lowModelName+"Dao.getCount(queryBuild.toParameter());\n" +
                "\t}\n" +
                "\n" +
				"\tpublic void delete"+modelName+"(String id)\n" +
				"\t{\n" +
				"\t\tLOGGER.info(\""+modelName+"Manager, delete"+modelName+"; id: {}\", id);\n" +
				"\t\tget"+modelName+"(id);\n" +
				"\t\t"+lowModelName+"Dao.delete(id);\n" +
				"\t}\n" +
				"}\n";


		String fileName = StringFormatter.format("%sManager.java", modelName).get();

		FileUtils.getInstance().writeToJava("manager", fileName, content);
	}

	public void generateResource(String modelName, List<JsmColumn> columns)
	{
		String lowModelName = getLowCaseFirst(modelName);
		String content = "package "+RESOURCE_PACKAGE+";\n" +
				"\n" +
				"import "+MANAGER_PACKAGE+"."+modelName+"Manager;\n" +
				"import "+SHARED_OBJECT_PACKAGE+"."+modelName+"Create;\n" +
				"import "+SHARED_OBJECT_PACKAGE+"."+modelName+"Info;\n" +
				"import "+SHARED_OBJECT_PACKAGE+"."+modelName+"Update;\n" +
				"import "+SHARED_OBJECT_PACKAGE+".PaginatedAPIResult;\n" +
				"import ma.glasnost.orika.MapperFacade;\n" +
				"import org.springframework.beans.factory.annotation.Autowired;\n" +
				"import org.springframework.stereotype.Component;\n" +
				"\n" +
				"import javax.ws.rs.Consumes;\n" +
				"import javax.ws.rs.DELETE;\n" +
				"import javax.ws.rs.DefaultValue;\n" +
				"import javax.ws.rs.GET;\n" +
				"import javax.ws.rs.POST;\n" +
				"import javax.ws.rs.PUT;\n" +
				"import javax.ws.rs.Path;\n" +
				"import javax.ws.rs.PathParam;\n" +
				"import javax.ws.rs.Produces;\n" +
				"import javax.ws.rs.QueryParam;\n" +
				"import javax.ws.rs.core.MediaType;\n" +
				"\n" +
				"/**\n" +
				" * The "+lowModelName+" resource\n" +
				" *\n" +
				" * @author victor.wang\n" +
				" * @version $Id$\n" +
				" */\n" +
				"@Component\n" +
				"@Path(\"/api/"+lowModelName+"s\")\n" +
				"public class "+modelName+"Resource\n" +
				"{\n" +
				"\n" +
				"\t@Autowired\n" +
				"\tprivate "+modelName+"Manager "+lowModelName+"Manager;\n" +
				"\n" +
				"\t@Autowired\n" +
				"\tprivate MapperFacade mapper;\n" +
				"\n" +
				"\t/**\n" +
				"\t * <h3>Description</h3>.\n" +
				"\t * <p>Get a "+lowModelName+"</p>\n" +
				"\t *\n" +
				"\t * @param id The "+lowModelName+" id\n" +
				"\t */\n" +
				"\t@GET\n" +
				"\t@Path(\"{id}\")\n" +
				"\t@Produces(MediaType.APPLICATION_JSON)\n" +
				"\tpublic "+modelName+"Info get"+modelName+"(@PathParam(\"id\") String id)\n" +
				"\t{\n" +
				"\t\treturn mapper.map("+lowModelName+"Manager.get"+modelName+"(id), "+modelName+"Info.class);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * <h3>Description</h3>.\n" +
				"\t * <p>Create a "+lowModelName+"</p>\n" +
				"\t *\n" +
				"\t * @param "+lowModelName+"Create the create object\n" +
				"\t */\n" +
				"\t@POST\n" +
				"\t@Consumes(MediaType.APPLICATION_JSON)\n" +
				"\t@Produces(MediaType.APPLICATION_JSON)\n" +
				"\tpublic "+modelName+"Info create"+modelName+"("+modelName+"Create "+lowModelName+"Create)\n" +
				"\t{\n" +
				"\t\treturn mapper.map("+lowModelName+"Manager.create"+modelName+"("+lowModelName+"Create), "+modelName+"Info.class);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * <h3>Description</h3>.\n" +
				"\t * <p>Update a "+lowModelName+"</p>\n" +
				"\t *\n" +
				"\t * @param id           the "+lowModelName+" id\n" +
				"\t * @param "+lowModelName+"Update the update object\n" +
				"\t */\n" +
				"\t@PUT\n" +
				"\t@Path(\"{id}\")\n" +
				"\t@Consumes(MediaType.APPLICATION_JSON)\n" +
				"\t@Produces(MediaType.APPLICATION_JSON)\n" +
				"\tpublic "+modelName+"Info update"+modelName+"(@PathParam(\"id\") String id, "+modelName+"Update "+lowModelName+"Update)\n" +
				"\t{\n" +
				"\t\treturn mapper.map("+lowModelName+"Manager.update"+modelName+"(id, "+lowModelName+"Update), "+modelName+"Info.class);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * <h3>Description</h3>.\n" +
				"\t * <p>Delete a "+lowModelName+"</p>\n" +
				"\t *\n" +
				"\t * @param id the "+lowModelName+" id\n" +
				"\t */\n" +
				"\t@DELETE\n" +
				"\t@Path(\"{id}\")\n" +
				"\tpublic void delete"+modelName+"(@PathParam(\"id\") String id)\n" +
				"\t{\n" +
				"\t\t"+lowModelName+"Manager.delete"+modelName+"(id);\n" +
				"\t}\n" +
				"\n" +
				"\t/**\n" +
				"\t * <h3>Description</h3>.\n" +
				"\t * <p>Search "+lowModelName+"s, with paginated results.</p>\n" +
				"\t *\n";
		for(JsmColumn c: columns){
			String columnName = c.getColumnName().toLowerCase();
			switch (columnName) {
				case "id":
				case "rvn":
				case "last_modified_at":
				case "created_at":
				case "del_flg":
					continue;
				default:
					if ("nvarchar".equals(c.getDataType())) {
						String hump = getHump(columnName, true);
						content += "\t * @param " + hump + " the "+hump+"\n";
					}
			}
		}
		content+=
				"\t * @param page the page\n" +
				"\t * @param size the page size\n" +
				"\t */\n" +
				"\t@GET\n" +
				"\t@Produces(MediaType.APPLICATION_JSON)\n" +
				"\tpublic PaginatedAPIResult<"+modelName+"Info> find"+modelName+"s(\n";

		for(JsmColumn c: columns){
			String columnName = c.getColumnName().toLowerCase();
			switch (columnName) {
				case "id":
				case "rvn":
				case "last_modified_at":
				case "created_at":
				case "del_flg":
					continue;
				default:
					if ("nvarchar".equals(c.getDataType())) {
						String hump = getHump(columnName, true);
						content += "\t\t\t\t\t\t\t\t\t\t\t\t\t  @QueryParam(\"" + hump + "\") String "+hump+",\n";
					}
			}
	    }
	    content +=
				"\t\t\t\t\t\t\t\t\t\t\t\t\t  @QueryParam(\"page\") @DefaultValue(\"1\") int page,\n" +
				"\t\t\t\t\t\t\t\t\t\t\t\t\t  @QueryParam(\"size\") @DefaultValue(\"10\") int size)\n" +
				"\t{\n" +
				"\t\treturn new PaginatedAPIResult<>(\n"+
                "\t\t\t\tmapper.mapAsList("+lowModelName+"Manager.find"+modelName+"s(";
		for(JsmColumn c: columns){
			String columnName = c.getColumnName().toLowerCase();
			switch (columnName) {
				case "id":
				case "rvn":
				case "last_modified_at":
				case "created_at":
				case "del_flg":
					continue;
				default:
					if ("nvarchar".equals(c.getDataType())) {
						String hump = getHump(columnName, true);
						content += hump+", ";
					}
			}
		}
		content +=
				"page, size), "+modelName+"Info.class),\n" +
                "\t\t\t\tpage,\n"+
                "\t\t\t\tsize,\n"+
                "\t\t\t\t" + lowModelName + "Manager.count"+modelName+"s(";
		boolean isFirst = true;
        for(JsmColumn c: columns) {
            String columnName = c.getColumnName().toLowerCase();
            switch (columnName) {
                case "id":
                case "rvn":
                case "last_modified_at":
                case "created_at":
                case "del_flg":
                    continue;
                default:
                    if ("nvarchar".equals(c.getDataType())) {
                        String hump = getHump(columnName, true);
                        content += (isFirst? "" : ", ") + hump;
                        isFirst = false;
                    }
            }
        }
        content +=
                "));\n" +
				"\t}\n" +
				"}\n";


		String fileName = StringFormatter.format("%sResource.java", modelName).get();

		FileUtils.getInstance().writeToJava("resource", fileName, content);
	}


	public void generateSharedObject(String modelName, List<JsmColumn> columns)
	{
		String createContent = "package com.conedot.aland.training.sharedObject;\n" +
				"\n" +
				"import com.conedot.aland.training.model.base.BaseEntity;\n" +
				"import java.util.Date;\n" +
				"\n" +
				"public class "+modelName+"Create\n" +
				"\t\textends BaseEntity\n" +
				"{\n";


		for(JsmColumn column: columns){
			if(EXCLUDE_FIELD.contains(getHump(column.getColumnName(), true))){
				continue;
			}
			createContent += "\t/**\n" +
					"\t * the "+getHump(column.getColumnName(), true)+"\n" +
					"\t */\n" +
					"\tprivate "+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+";\n" +
					"\n";
		}

		for(JsmColumn column: columns){
			if(EXCLUDE_FIELD.contains(getHump(column.getColumnName(), true))){
				continue;
			}
			createContent += "\tpublic "+getPropertyType(column.getDataType(), false)+" get"+getHump(column.getColumnName(), false)+"()\n" +
					"\t{\n" +
					"\t\treturn "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n" +
					"\tpublic void set"+getHump(column.getColumnName(), false)+"("+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+")\n" +
					"\t{\n" +
					"\t\tthis."+getHump(column.getColumnName(), true)+" = "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n";
		}

		createContent += "}\n";

		String createFileName = StringFormatter.format("%sCreate.java", modelName).get();

		FileUtils.getInstance().writeToJava("sharedObject", createFileName, createContent);

		//update
		String updateContent = "package com.conedot.aland.training.sharedObject;\n" +
				"\n" +
				"import com.conedot.aland.training.model.base.BaseEntity;\n" +
				"import org.joda.time.DateTime;\n" +
				"\n" +
				"public class "+modelName+"Update\n" +
				"\t\textends BaseEntity\n" +
				"{\n";


		for(JsmColumn column: columns){
			if(EXCLUDE_FIELD.contains(getHump(column.getColumnName(), true))){
				continue;
			}
			updateContent += "\t/**\n" +
					"\t * the "+getHump(column.getColumnName(), true)+"\n" +
					"\t */\n" +
					"\tprivate "+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+" = UNSET_STRING;\n" +
					"\n";
		}

		for(JsmColumn column: columns){
			if(EXCLUDE_FIELD.contains(getHump(column.getColumnName(), true))){
				continue;
			}
			updateContent += "\tpublic "+getPropertyType(column.getDataType(), false)+" get"+getHump(column.getColumnName(), false)+"()\n" +
					"\t{\n" +
					"\t\treturn "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n" +
					"\tpublic void set"+getHump(column.getColumnName(), false)+"("+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+")\n" +
					"\t{\n" +
					"\t\tthis."+getHump(column.getColumnName(), true)+" = "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n";
		}

		updateContent += "}\n";

		String updateFileName = StringFormatter.format("%sUpdate.java", modelName).get();

		FileUtils.getInstance().writeToJava("sharedObject", updateFileName, updateContent);


		//info
		String infoContent = "package com.conedot.aland.training.sharedObject;\n" +
				"\n" +
				"import com.conedot.aland.training.model.base.BaseEntity;\n" +
				"\n" +
				"import java.util.Date;\n" +
				"\n" +
				"public class "+modelName+"Info\n" +
				"\t\textends BaseEntity\n" +
				"{\n";


		for(JsmColumn column: columns){
			infoContent += "\t/**\n" +
					"\t * the "+getHump(column.getColumnName(), true)+"\n" +
					"\t */\n" +
					"\tprivate "+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+";\n" +
					"\n";
		}

		for(JsmColumn column: columns){
			infoContent += "\tpublic "+getPropertyType(column.getDataType(), false)+" get"+getHump(column.getColumnName(), false)+"()\n" +
					"\t{\n" +
					"\t\treturn "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n" +
					"\tpublic void set"+getHump(column.getColumnName(), false)+"("+getPropertyType(column.getDataType(), false)+" "+getHump(column.getColumnName(), true)+")\n" +
					"\t{\n" +
					"\t\tthis."+getHump(column.getColumnName(), true)+" = "+getHump(column.getColumnName(), true)+";\n" +
					"\t}\n" +
					"\n";
		}

		infoContent += "}\n";

		String infoFileName = StringFormatter.format("%sInfo.java", modelName).get();

		FileUtils.getInstance().writeToJava("sharedObject", infoFileName, infoContent);
	}

	public void generateMapper(String tableName, String modelName, List<JsmColumn> columns)
	{
		tableName = tableName.toLowerCase();
		String lowModelName = getLowCaseFirst(modelName);
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
				"<mapper namespace=\""+DAO_PACKAGE+"."+modelName+"Dao\" >\n" +
				"    <resultMap id=\""+lowModelName+"ResultMapper\" type=\""+MODEL_PACKAGE+"."+modelName+"\" >\n";

		for(JsmColumn c: columns){
			if(c.getColumnName().toLowerCase().equals("id")){
				content += "        <id column=\"id\" property=\"id\" jdbcType=\"VARCHAR\" />\n";
			}else {
				content += "        <result column=\""+c.getColumnName().toLowerCase()+"\" property=\""+getHump(c.getColumnName(), true)+"\" jdbcType=\""+getJdbcType(c.getDataType())+"\" />\n";
			}
		}
		content +=
				"    </resultMap>\n" +
				"    <sql id=\"allFields\" >\n";

		for(int i = 0; i < columns.size(); i++){
			JsmColumn c = columns.get(i);
			content += "        "+c.getColumnName().toLowerCase()+""+(i != columns.size()-1 ? ",": "")+"\n";
		}
		content +=
				"    </sql>\n" +
				"\n" +
				"    <select id=\"getAll\" resultMap=\""+lowModelName+"ResultMapper\">\n" +
				"        SELECT\n" +
				"        <include refid=\"allFields\" />\n" +
				"        FROM "+tableName+"\n" +
				"        ORDER BY created_at\n" +
				"    </select>\n" +
				"\n" +
				"    <select id=\"getCount\" parameterType=\"java.util.HashMap\" resultType=\"int\">\n" +
				"        SELECT COUNT(*) as cnt\n" +
				"        FROM "+tableName+"\n" +
				"        WHERE 1=1\n" +
				"        <include refid=\"find"+modelName+"sParams\"/>\n" +
				"    </select>\n" +
				"\n" +
				"    <select id=\"get\" resultMap=\""+lowModelName+"ResultMapper\" parameterType=\"String\" >\n" +
				"        SELECT\n" +
				"        <include refid=\"allFields\" />\n" +
				"        FROM "+tableName+"\n" +
				"        WHERE id = #{id}\n" +
				"    </select>\n" +
				"\n" +
				"    <insert id=\"insert\" parameterType=\""+modelName+"\" >\n" +
				"        INSERT INTO "+tableName+" (\n";
		for(int i = 0; i < columns.size(); i++){
			JsmColumn c = columns.get(i);
			content += "        "+c.getColumnName().toLowerCase()+""+(i != columns.size()-1 ? "," : "")+"\n";
		}

		content+=
				"        )\n" +
				"        VALUES (\n";
		for(int i = 0; i < columns.size(); i++){
			JsmColumn c = columns.get(i);
			content += "        #{"+getHump(c.getColumnName(), true)+",jdbcType="+getJdbcType(c.getDataType())+"}"+(i != columns.size()-1 ? "," : "")+"\n";
		}
		content +=
				"        )\n" +
				"    </insert>\n" +
				"\n" +
				"    <update id=\"update\" parameterType=\""+modelName+"\" >\n" +
				"        UPDATE "+tableName+"\n" +
				"        SET \n";
		for(JsmColumn c: columns){
			if(c.getColumnName().toLowerCase().equals("id")){
				continue;
			}
			if(c.getColumnName().toLowerCase().equals("rvn")){
				content += "        rvn = rvn+1\n";
			}else if(c.getColumnName().toLowerCase().equals("last_modified_at")){
				content += "        last_modified_at = getdate(),\n";
			}else {
				content += "        "+c.getColumnName().toLowerCase()+" = #{"+getHump(c.getColumnName(), true)+",jdbcType="+getJdbcType(c.getDataType())+"},\n";
			}
		}
		content +=
				"        WHERE id = #{id,jdbcType=VARCHAR}\n" +
				"    </update>\n" +
				"\n" +
				"    <sql id=\"find"+modelName+"sParams\">" ;

		for(JsmColumn c: columns){
			String columnName = c.getColumnName().toLowerCase();
			switch (columnName) {
				case "id":
				case "rvn":
				case "last_modified_at":
				case "created_at":
				case "del_flg":
					continue;
				default:
					if ("nvarchar".equals(c.getDataType())) {
						String hump = getHump(columnName, true);
						content +=
								"\n" +
							    "        <if test=\"" + hump + " != null\">\n" +
								"            AND " + columnName + " = #{" + hump + "}\n" +
								"        </if>" ;

					}
			}
		}
		content +=
				"\n    </sql>\n" +
				"    <select id=\"getList\" parameterType=\"java.util.HashMap\" resultMap=\""+lowModelName+"ResultMapper\">\n" +
				"        SELECT * FROM (\n" +
				"            SELECT\n" +
				"            <include refid=\"allFields\" />,\n" +
				"            ROW_NUMBER()\n" +
				"            <choose>\n" +
				"                <when test=\"orderBy != null\">\n" +
				"                    OVER(Order by ${orderBy} )\n" +
				"                </when>\n" +
				"                <otherwise>\n" +
				"                    OVER(Order by created_at )\n" +
				"                </otherwise>\n" +
				"            </choose>\n" +
				"            AS RowId\n" +
				"            FROM "+tableName+"\n" +
				"            WHERE 1=1\n" +
				"            <include refid=\"find"+modelName+"sParams\"/>\n" +
				"        ) A\n" +
				"        WHERE RowId BETWEEN #{pageStart} AND #{pageEnd}\n" +
				"    </select>\n" +
				"\n" +
				"    <delete id=\"purge\">\n" +
				"        DELETE FROM "+tableName+"\n" +
				"    </delete>\n" +
				"\n" +
				"    <delete id=\"delete\" parameterType=\"String\">\n" +
				"        DELETE FROM "+tableName+"\n" +
				"        WHERE id = #{id}\n" +
				"    </delete>\n" +
				"</mapper>";


		String infoFileName = StringFormatter.format("%s.xml", modelName).get();

		FileUtils.getInstance().writeToMapper(infoFileName, content);
	}

	public void generateException(String modelName)
	{
		String lowModelName = getLowCaseFirst(modelName);
		String content = "package "+EXCEPTION_PACKAGE+";\n" +
				"\n" +
				"import com.conedot.aland.training.exception.base.NotFoundException;\n" +
				"\n" +
				"public class "+modelName+"NotFoundException\n" +
				"\textends NotFoundException\n" +
				"{\n" +
				"\tpublic static final int NUMERIC_ERROR_CODE = 404;\n" +
				"\tpublic static final String ERROR_CODE = \"errors."+GROUPS+"."+ARTIFACTS+"."+lowModelName+"_not_found\";\n" +
				"\n" +
				"\tpublic "+modelName+"NotFoundException(String id)\n" +
				"\t{\n" +
				"\t\tsuper(NUMERIC_ERROR_CODE, ERROR_CODE, \"The "+lowModelName+" [{}] can not be found.\", id);\n" +
				"\t}\n" +
				"}\n";


		String infoFileName = StringFormatter.format("%sNotFoundException.java", modelName).get();

		FileUtils.getInstance().writeToJava("exception", infoFileName, content);
	}


	/**
	 * get hump words like "CarrotTable" from "carrot_table"
	 * when firstLowerCase is true, the return should be "carrotTable"
	 *
	 * @param tableName      like "carrot_table", the words split should be "-"
	 * @param firstLowerCase the first alphabet is lower case or not
	 * @return
	 */
	private String getHump(String tableName, boolean firstLowerCase)
	{

		String[] lowCaseTn = tableName.toLowerCase().split("_");
		String modelName = "";
		for (int i = 0; i < lowCaseTn.length; i++)
		{
			String name = lowCaseTn[i];
			if (i == 0 && firstLowerCase)
			{
				modelName += name;
			}
			else
			{
				modelName += name.substring(0, 1).toUpperCase() + name.substring(1);
			}
		}
		return modelName;
	}

	private String getLowCaseFirst(String modelName)
	{
		return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
	}

	private String getPropertyType(String dataType, boolean original)
	{
		String propertyType = "String";
		switch (dataType)
		{
			case "nvarchar":
			case "text":
				propertyType = "String";
				break;
			case "int":
				propertyType = original ? "int": "Integer";
				break;
			case "datetime":
				propertyType = "Date";
				break;
		}
		return propertyType;
	}

	private String getJdbcType(String dataType){
		String propertyType = "VARCHAR";
		switch (dataType)
		{
			case "nvarchar":
			case "text":
				propertyType = "VARCHAR";
				break;
			case "int":
				propertyType = "INTEGER";
				break;
			case "datetime":
				propertyType = "TIMESTAMP";
				break;
		}
		return propertyType;
	}
}
