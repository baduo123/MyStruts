package web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.ProxyXML;

public class test {
	public static void main(String[] args) throws Exception{
	
//		Class clzz = Class.forName("web.LoginAction");
//		Object object =  clzz.getMethod("execute", null).invoke(clzz.newInstance(), null);
//		System.out.println(object=="success");
		
//		List<String[]>list = ProxyXML.readReturnMsg("login");
//		for (String[] strings : list) {
//			for (String string : strings) {
//				System.out.println(string);
//			}
//		}
		Map<String, String[]>map = ProxyXML.readReturnMsg("login");
		Set set = map.keySet();
		for (Object object : set) {
			System.out.println("现在的结果为"+object);
			String[]strs = map.get(object);
			for (String string : strs) {
				System.out.println("存的值："+string);
			}
		}
		
	}
}
