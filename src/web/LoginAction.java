package web;

import java.util.Map;

import dto.User;
import util.ActionUtil;

public class LoginAction {
	private User user;
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String execute(){
		Map map = ActionUtil.getSession();
		if (user.getName().equals("admin")&&user.getPwd().equals("123")) {
			map.put("user",user);
			return "success";
		}else {
			map.put("msg", "密码或者用户名错误，请重新登录");
			return "false";
		}
	}
	
	
}
