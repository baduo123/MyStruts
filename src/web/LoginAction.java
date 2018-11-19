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
			map.put("msg", "��������û������������µ�¼");
			return "false";
		}
	}
	
	
}
