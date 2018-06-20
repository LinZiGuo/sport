package cn.itcast.web.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.order.OrderState;

public class OrderStateConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		if(clazz==String.class){
			return value.toString();
		}
		if(clazz==OrderState.class){
			try{
				return OrderState.valueOf((String) value);
			}catch (Exception e) {}
		}
		return null;
	}

}
