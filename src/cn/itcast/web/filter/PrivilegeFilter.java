package cn.itcast.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bean.privilege.Employee;
import cn.itcast.utils.WebUtil;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
@WebFilter("/PrivilegeFilter")
public class PrivilegeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PrivilegeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		Employee employee = WebUtil.getEmployee(request);
		if(employee==null){//如果员工未登录，重定向到员工登陆界面
			HttpServletResponse response = (HttpServletResponse)res;
			response.sendRedirect("/employee/logon");
		}else{
			chain.doFilter(req, res);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
