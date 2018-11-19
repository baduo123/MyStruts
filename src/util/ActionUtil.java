package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionUtil {
	public static HttpServletRequest request;
	private static Map<String,Object> session;
	static{
		if (session==null) {
			session = new HashMap<String,Object>();
		}
	}
	
	public static Map getSession(){
		return session;
		
	}
	
	private static void createSession() {
		if (!session.isEmpty()) {
			HttpSession se = request.getSession();
			Set<String>set = session.keySet();
			for (String	string : set) {
				se.setAttribute(string,session.get(string));
			}
		}
	}
	
	public Object createAction(String uri) throws Exception {
		String classname = ProxyXML.readBean(uri);
		Class clzz = Class.forName(classname);
		return clzz.newInstance();
	}

	public void createParameties(Object obj, HttpServletRequest request, String uri) throws Exception {
		this.request = request;
		Class clzz = obj.getClass();
		Field[] fields = clzz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			Class Type = field.getType();
			String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
			if (Type.isArray()) {
				clzz.getMethod(methodName, Type).invoke(obj, new Object[] { request.getParameterValues(name) });
			}
			if (Type.toString().contains("String")) {
				clzz.getMethod(methodName, Type).invoke(obj, request.getParameter(name));
			} else {
				Object object2 = Type.newInstance();
				createParameties(object2, request, uri);
				clzz.getMethod(methodName, Type).invoke(obj, object2);
			}
		}
	}

	public void returnpage(HttpServletRequest request, Object obj, HttpServletResponse response, String uri)
			throws Exception{
		Class clzz = obj.getClass();
		Method[] methods = clzz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals("execute")) {
				Object result = clzz.getMethod(method.getName(), null).invoke(obj, null);
				Map map = ProxyXML.readReturnMsg(uri);
				Set<String> strs = map.keySet();
				for (String str : strs) {
					if (str.equals(result.toString())) {
						String[] strings = (String[]) map.get(str);
						System.out.println(strings[1]);
						switch (strings[0]) {
						case "forward":
							createSession();
							request.getRequestDispatcher(strings[1]).forward(request, response);
							return;
						case "rediert":
							createSession();
							response.sendRedirect(strings[1]);
							return;
						default:
							break;
						}
					}
				}

			}
		}

	}

}
