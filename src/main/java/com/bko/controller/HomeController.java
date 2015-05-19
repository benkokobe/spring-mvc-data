package com.bko.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bko.domain.Member;
import com.bko.domain.User;
import com.bko.persistence.UserDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value = "/home")
	public String sayHello (Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "home";
	}
	@RequestMapping(value = "/generate2", method = RequestMethod.GET)
	public ModelAndView drList(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("username"));
		User user = userDao.get(userId);
		ModelAndView model = new ModelAndView("drPatchList");
		model.addObject("user", user);
		return model;		
	}

	@RequestMapping("/")
	public ModelAndView handleRequest() throws Exception {
		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("UserList");
		model.addObject("userList", listUsers);
		return model;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newUser() {
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", new User());
		return model;		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userDao.get(userId);
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", user);
		return model;		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		userDao.delete(userId);
		return new ModelAndView("redirect:/");		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute User user) {
		userDao.saveOrUpdate(user);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/member/{name}", method = RequestMethod.GET)
    public String getMember(@PathVariable String name, ModelMap model) {
  
        /*Member member = new Member();
        member.setName(name);
        member.setEmail("ben@gmail.com");
        model.addAttribute("member", member);  
        */
        List<User> listUsers = userDao.list();
        //ModelAndView model = new ModelAndView("UserList");
		model.addAttribute("userList", listUsers);
        return "UserList";

  
    }
	
}
