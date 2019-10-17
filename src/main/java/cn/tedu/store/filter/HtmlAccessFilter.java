package cn.tedu.store.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HTML���ʹ�����
 */
public class HtmlAccessFilter implements Filter {
	/**
	 * ������������ֱ�ӷ��ʵ�ҳ���б�
	 */
	private List<String> whiteList = new ArrayList<String>();
	
	public void init(FilterConfig arg0) throws ServletException {
		// ȷ��������
		whiteList.add("index.html");
		whiteList.add("product.html");
		whiteList.add("register.html");
		whiteList.add("login.html");
		whiteList.add("footerTemplate.html");
		whiteList.add("leftTemplate.html");
		whiteList.add("topTemplate.html");
		
		// ���
		System.out.println("�����¼��ҳ���б�");
		for (String page : whiteList) {
			System.out.println(page);
		}
	}

	public void doFilter(ServletRequest arg0, 
			ServletResponse arg1, 
			FilterChain filterChain)
			throws IOException, ServletException {
		// ��ȡ��ǰҳ��
		HttpServletRequest request 
			= (HttpServletRequest) arg0;
		String uri = request.getRequestURI();
		int beginIndex = uri.lastIndexOf("/") + 1;
		String fileName = uri.substring(beginIndex);
		System.out.println("��ǰ����ҳ�棺" + fileName);
		
		// �жϵ�ǰ���ʵ����ĸ�ҳ��
		// ����������¼��ҳ�棬ֱ�ӷ��У����磺login.html
		if (whiteList.contains(fileName)) {
			System.out.println("\t�����¼��ֱ�ӷ���");
			// ����ִ�й�������
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		// �������Ҫ��¼��ҳ�棬�ж�session���������л��ض���
		HttpSession session
			= request.getSession();
		if (session.getAttribute("uid") != null) {
			// Session����uid����ʾ�ѵ�¼��ֱ�ӷ���
			System.out.println("\t�Ѿ���¼��ֱ�ӷ���");
			// ����ִ�й�������
			filterChain.doFilter(arg0, arg1);
			return;
		}
		
		// ִ�е��˴�����ʾ��ǰҳ�治�ڰ������У���δ��¼��������
		// ���صı����ǣ��ض��򵽵�¼ҳ
		System.out.println("\t���ص�ǰҳ�棬���ض��򵽵�¼ҳ��");
		HttpServletResponse response
			= (HttpServletResponse) arg1;
		response.sendRedirect("login.html");
	}

	public void destroy() {
	}
}






