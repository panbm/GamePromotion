package com.gamepro.base.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseBean implements Cloneable, Serializable {


	public BaseBean() {
	}

	public final void setValue(String fieldNm, Object value)
			throws SecurityException, NoSuchFieldException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (value == null) {
			return;
		}
		String methodNm = "set" + Character.toUpperCase(fieldNm.charAt(0))
				+ fieldNm.substring(1, fieldNm.length());
		Field field = getField(fieldNm);
		String paramType = field.getType().getName();
		Method method = null;
		if ("boolean".equals(paramType)) {
			method = getMethod(methodNm, boolean.class);
			boolean realValue = value instanceof String ? Boolean
					.valueOf((String) value) : ((Boolean) value).booleanValue();
			method.invoke(this, realValue);
		} else if ("char".equals(paramType)) {
			method = getMethod(methodNm, char.class);
			char realValue = value instanceof String ? Character.
					valueOf(value.toString().charAt(0)) : ((Character) value).charValue();
			method.invoke(this, realValue);
		} else if ("byte".equals(paramType)) {
			method = getMethod(methodNm, byte.class);
			byte realValue = value instanceof String ? Byte
					.valueOf((String) value) : ((Byte) value).byteValue();
			method.invoke(this, realValue);
		} else if ("short".equals(paramType)) {
			method = getMethod(methodNm, short.class);
			short realValue = value instanceof String ? Short
					.valueOf((String) value) : ((Short) value).shortValue();
			method.invoke(this, realValue);
		} else if ("int".equals(paramType)) {
			method = getMethod(methodNm, int.class);
			int realValue = value instanceof String ? Integer
					.valueOf((String) value) : ((Integer) value).intValue();
			method.invoke(this, realValue);
		} else if ("long".equals(paramType)) {
			method = getMethod(methodNm, long.class);
			long realValue = value instanceof String ? Long
					.valueOf((String) value) : (value instanceof Integer ? Long
					.valueOf((Integer) value) : ((Long) value).longValue());
			method.invoke(this, realValue);
		} else if ("float".equals(paramType)) {
			method = getMethod(methodNm, float.class);
			float realValue = value instanceof String ? Float
					.valueOf((String) value) : ((Float) value).floatValue();
			method.invoke(this, realValue);
		} else if ("double".equals(paramType)) {
			method = getMethod(methodNm, double.class);
			double realValue = value instanceof String ? Double
					.valueOf((String) value) : ((Double) value).doubleValue();
			method.invoke(this, realValue);
		} else if ("java.lang.String".equals(paramType)) {
			method = getMethod(methodNm, String.class);
			method.invoke(this, (String) value);
		}
	}

	public final Object getValue(String fieldNm) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NoSuchFieldException {
		String prefix = "get";
		Field field = getField(fieldNm);
		if ("boolean".equals(field.getType().getName())) {
			prefix = "is";
		}
		String methodNm = prefix + Character.toUpperCase(fieldNm.charAt(0))
				+ fieldNm.substring(1, fieldNm.length());
		Method method = getMethod(methodNm);
		return method.invoke(this);
	}

	private Method getMethod(String methodNm, Class<?>... classNm)
			throws SecurityException, NoSuchMethodException {
		Method method = null;
		try {
			method = this.getClass().getDeclaredMethod(methodNm, classNm);
		} catch (NoSuchMethodException e) {
			method = this.getClass().getSuperclass()
					.getDeclaredMethod(methodNm, classNm);
		}
		return method;
	}

	private Field getField(String fieldNm) throws SecurityException,
			NoSuchFieldException {
		Field field = null;
		try {
			field = this.getClass().getDeclaredField(fieldNm);
		} catch (NoSuchFieldException e) {
			field = this.getClass().getSuperclass().getDeclaredField(fieldNm);
		}
		return field;
	}
	public String[] getFields(){
    	Field[] fields = this.getClass().getDeclaredFields();
    	Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();
    	List<String> fieldName = new ArrayList<String>();
    	if(null != fields){
    		for(Field field:fields){
        		fieldName.add(field.getName());
        	}	
    	}
    	if(null != superFields){
    		for(Field field:superFields){
        		fieldName.add(field.getName());
        	}	
    	}
    	
    	return fieldName.toArray(new String[0]);
	}

}