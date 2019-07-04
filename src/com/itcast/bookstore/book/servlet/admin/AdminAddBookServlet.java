package com.itcast.bookstore.book.servlet.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itcast.bookstore.book.domain.Book;
import com.itcast.bookstore.book.service.BookService;
import com.itcast.bookstore.category.domain.Category;

import cn.itcast.commons.CommonUtils;

/**
 * Servlet implementation class AdminAddBookServlet
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("uft-8");
		response.setContentType("text/html;charset=utf-8");
		DiskFileItemFactory dif = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(dif);
		
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Map<String, String> map = new HashMap<String, String>();
			for (FileItem fileItem : fileItemList) {
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("uft-8"));
				}
			}
			Book book = CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			String savepath = this.getServletContext().getRealPath("/book_img");
			String filename = fileItemList.get(1).getName();
			filename = CommonUtils.uuid() + "_" + filename;
			book.setImage("book_img/" + filename);
			File file = new File(savepath, filename);
			fileItemList.get(1).write(file);
			
			bookService.add(book);
			
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
					.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
