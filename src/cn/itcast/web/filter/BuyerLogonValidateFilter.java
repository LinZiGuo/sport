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

import org.apache.commons.codec.binary.Base64;

import cn.itcast.bean.user.Buyer;
import cn.itcast.utils.WebUtil;

/**
 * Servlet Filter implementation class BuyerLogonValidateFilter
 */
@WebFilter("/BuyerLogonValidateFilter")
public class BuyerLogonValidateFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BuyerLogonValidateFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		Buyer buyer = WebUtil.getBuyer(request);
		if(buyer == null){
			String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
			String directUrl = new String(Base64.encodeBase64(url.getBytes()));
			HttpServletResponse response = (HttpServletResponse)res;
			response.sendRedirect("/user/logon?directUrl="+ directUrl);
		}else{
			chain.doFilter(req, res);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
