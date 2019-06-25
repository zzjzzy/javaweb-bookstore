package com.itcast.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.user.service.UserException;

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
    	
    	System.out.println("这里写发邮件的代码");
    	request.setAttribute("msg", "恭喜，注册成功！请去注册邮箱激活账号！");
    	return "f:/jsps/msg.jsp";
    }

}
