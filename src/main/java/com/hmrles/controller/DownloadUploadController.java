package com.hmrles.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador para manejar las peticiones subir/descargar archivos
 * de  Standalone/Config
 */
@Controller
public class DownloadUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadUploadController.class);	
	private static final String ROOT_PATH = System.getProperty("jboss.server.base.dir");
	private static final String CONFIG = "config";
	private static final String LOG = "log";

	
	/**
	 * Subimos el archivo en la carpeta config o log de la instancia
	 * /jboss/WSTransfInter1/jboss-eap-6.3/standalone 
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file, @RequestParam("carpeta") String carpeta) {

		String nombreCarpeta = carpeta.equals("1")?CONFIG:LOG;
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				
				

				File dir = new File(ROOT_PATH + File.separator + nombreCarpeta);
				if (!dir.exists())
					dir.mkdirs();

				//Creamos un archivo en el servidor
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Ubicacion del archivo en el servidor=" + serverFile.getAbsolutePath());

				return "Tu archivo se subio correctamente=" + name;
			} catch (Exception e) {
				return "Ocurrio una falla al subir el archivo" + name + " => " + e.getMessage();
			}
		} else {
			return "Ocurrio una falla al subir el archivo " + name + " porque el archivo esta vacio.";
		}
	}

	
	
	/**
	 * Descargamos un archivo en especifico de la carpeta config o log
	 * /jboss/WSTransfInter1/jboss-eap-6.3/standalone
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFileHandler(HttpServletResponse response, @RequestParam("name") String nombreArchivo,  @RequestParam("carpeta") String carpeta)
			throws IOException {

		String nombreCarpeta = carpeta.equals("1")?CONFIG:LOG;
		
		File file = new File(ROOT_PATH + File.separator + nombreCarpeta + File.separator + nombreArchivo);

		if (!file.exists()) {
			String errorMessage = "El archivo no existe" + ROOT_PATH + File.separator + nombreCarpeta + File.separator + nombreArchivo;
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("No se detecto el MimeType, se usara el default");
			mimeType = "application/octet-stream";
		}

		System.out.println("MimeType : " + mimeType);

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}


}
