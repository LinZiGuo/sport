package cn.itcast.web.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.order.PaymentWay;

public class PaymentWayConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		if(clazz==String.class){
			return value.toString();
		}
		if(clazz==PaymentWay.class){
			try{
				return PaymentWay.valueOf((String) value);
			}catch (Exception e) {}
		}
		return null;
	}

}
