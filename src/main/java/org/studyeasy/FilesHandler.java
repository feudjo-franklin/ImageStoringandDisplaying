package org.studyeasy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.studyeasy.hibernate.DAO.FilesDAO;
import org.studyeasy.hibernate.entity.Files;

@WebServlet("/FilesHandler")
public class FilesHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String path = "C:/Users/frank/OneDrive/Desktop/images/"; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "listingImages":
				listingImages(request, response);
				break;
			case "viewImage":
				viewImage(request,response);
				break;
			case "deleteImage":
				deleteImage(request,response);
				break;
			default:
				request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch(action) {
			case "filesUpload":
				filesUpload(request, response);
				break;
			case "updateInformation":
				updateInformation(request, response);
				break;
			default:
				request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
	
	private void deleteImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//the logic for file deletion from database
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		Files file = new FilesDAO().getFile(fileId);
		new FilesDAO().deleteFile(fileId);
		//the logic for file deletion from FileSystem
		File fileOnDisc = new File(path+file.getFileName());
		if(fileOnDisc.delete()) {
			System.out.println("File got deleted from filesystem");
		}else {
			System.out.println("File not deleted from filesystem");
		}
		listingImages(request, response);
	}
	
	private void viewImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		Files file = new FilesDAO().getFile(fileId);
		request.setAttribute("file", file);
		request.setAttribute("path", path);
		request.getRequestDispatcher("viewImage.jsp").forward(request, response);
	}
	
	private void updateInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		String label = request.getParameter("label");
		String caption = request.getParameter("caption");
		new FilesDAO().updateInformation(fileId, label, caption);
		listingImages(request, response);
	}

	private void listingImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Files> files = new FilesDAO().listFiles();
		request.setAttribute("files", files);
		request.setAttribute("path", path);
		request.getRequestDispatcher("listFiles.jsp").forward(request, response);
		
	}

	public void filesUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		
		try {
			List<FileItem> images = upload.parseRequest(request);
			for(FileItem image: images) {
				String name = image.getName();
				try {name = name.substring(name.lastIndexOf("\\")+1);}catch(Exception e) {}
				File file = new File(path+name);
				if(!file.exists()) {
					new FilesDAO().addFileDetails(new Files(name));
					image.write(file);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listingImages(request, response);
	}

}
