package cn.itcast.web.converter;

import org.apache.commons.beanutils.Converter;

import cn.itcast.bean.user.Gender;

public class GenderConverter implements Converter {

	@Override
	public Object convert(Class clazz, Object value) {
		if(value instanceof Gender) return value;
		try{
			return Gender.valueOf((String) value);
		}catch (Exception e) {}
		return null;
	}

}
