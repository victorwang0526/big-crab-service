package com.victor.wang.bigCrab.resource;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * The user resource
 *
 * @author victor.wang
 * @version $Id$
 */
@Component
@Path("/api/uploads")
public class UploadResource
{


	/**
	 * <h3>上传文件</h3>.
	 * <p>上传文件，返回路径.</p>
	 * <br/>
	 * <p>MULTIPART_FORM_DATA</p>
	 * <p>form key: file</p>
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void upload(FormDataMultiPart form)
	{
		List<FormDataBodyPart> fileBodyParts = form.getFields("file");
		List<String> locations = new ArrayList<>();
		if (fileBodyParts != null && fileBodyParts.size() > 0)
		{
			for (FormDataBodyPart body : fileBodyParts)
			{

			}
		}
	}
}
