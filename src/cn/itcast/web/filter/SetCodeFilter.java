package cn.itcast.web.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import cn.itcast.bean.order.DeliverWay;
import cn.itcast.bean.order.OrderState;
import cn.itcast.bean.order.PaymentWay;
import cn.itcast.bean.privilege.SystemPrivilegePK;
import cn.itcast.bean.product.Sex;
import cn.itcast.bean.user.Gender;
import cn.itcast.web.converter.DateConverter;
import cn.itcast.web.converter.DeliverWayConverter;
import cn.itcast.web.converter.GenderConverter;
import cn.itcast.web.converter.OrderStateConverter;
import cn.itcast.web.converter.PaymentWayConverter;
import cn.itcast.web.converter.SexConverter;
import cn.itcast.web.converter.SystemPrivilegePKConverter;

/**
 * Servlet Filter implementation class SetCodeFilter
 */
@WebFilter("/SetCodeFilter")
public class SetCodeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SetCodeFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new SexConverter(), Sex.class);
		ConvertUtils.register(new GenderConverter(), Gender.class);
		ConvertUtils.register(new DeliverWayConverter(), DeliverWay.class);
		ConvertUtils.register(new PaymentWayConverter(), PaymentWay.class);
		ConvertUtils.register(new OrderStateConverter(), OrderState.class);
		ConvertUtils.register(new SystemPrivilegePKConverter(), SystemPrivilegePK.class);
		
	}

}
