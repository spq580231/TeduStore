package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ��¼������������û�û�е�¼�ͳ��Է���ĳЩURL�������ض��򵽵�¼ҳ
 */
public class LoginInterceptor
        implements HandlerInterceptor {

    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {
        // ��request�л�ȡsession
        HttpSession session
                = request.getSession();
        // �ж�session���Ƿ����uid
        if (session.getAttribute("uid") != null) {
            // ���ڣ��û��ѵ�¼�������
            return true;
        } else {
            // �����ڣ��û�δ��¼���ض���������
            response.sendRedirect("../web/login.html");
            return false;
        }
    }

    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex)
            throws Exception {
    }

}
