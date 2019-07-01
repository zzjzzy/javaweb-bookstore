package com.itcast.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.user.service.UserException;
import com.itcast.bookstore.cart.domain.Cart;
import com.itcast.bookstore.user.domain.User;
import com.itcast.bookstore.user.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public UserServlet() {
        super();
    }
    
    private UserService userService = new UserService();
    
    public String regist(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String username = form.getUsername();
		if (username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空");
		} else if (username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须介于3~10之间");
		}
		
		String password = form.getPassword();
		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if (password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须介于3~10之间");
		}
		
		String email = form.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "邮箱不能为空");
		} else if (!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "邮箱格式不正确");
		}
		
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("user", form);
			return "f:/jsps/user/regist.jsp";
		}
		
    	try {
			userService.regist(form);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);
			return "f:/jsps/user/regist.jsp";
		}
    	
    	Properties props = new Properties();
//    	Inputsteam
    	props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
    	String host = props.getProperty("host");
    	String uname = props.getProperty("uname");
    	String pwd = props.getProperty("pwd");
    	String from = props.getProperty("from");
    	String to = form.getEmail();
    	String subject = props.getProperty("subject");
    	subject = new String(subject.getBytes("ISO-8859-1"), "utf-8");
//    	System.out.println(subject);
    	String content = props.getProperty("content");
    	content = new String(content.getBytes("ISO-8859-1"), "utf-8");
    	content = MessageFormat.format(content, form.getCode());
    	//zzj19930120@163.com
    	Session session = MailUtils.createSession(host, uname, pwd);
    	Mail mail = new Mail(from, to, subject, content);
    	try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	request.setAttribute("msg", "恭喜，注册成功！请去注册邮箱激活账号！");
    	return "f:/jsps/msg.jsp";
    }
    
    public String active(HttpServletRequest request, HttpServletResponse response) {
    	String code = request.getParameter("code");
    	try {
			userService.active(code);
			request.setAttribute("msg", "恭喜，激活成功！");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
    	
    	return "f:/jsps/msg.jsp";
    }
    
    public String login(HttpServletRequest request, HttpServletResponse response) {
    	User form = CommonUtils.toBean(request.getParameterMap(), User.class);
    	try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
    }
    
    public String quit(HttpServletRequest request, HttpServletResponse response) {
    	request.getSession().invalidate();
    	return "r:/index.jsp";
    }

}
