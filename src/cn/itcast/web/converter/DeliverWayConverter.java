package cn.itcast.web.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.order.DeliverWay;

public class DeliverWayConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		if(clazz==String.class){
			return value.toString();
		}
		if(clazz==DeliverWay.class){
			try{
				return DeliverWay.valueOf((String) value);
			}catch (Exception e) {}
		}
		return null;
	}

}
