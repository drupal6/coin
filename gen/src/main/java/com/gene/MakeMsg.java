package com.gene;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import com.huobi.client.model.event.OrderUpdateEvent;

public class MakeMsg {

	public static void main(String[] args) {
		Class<?> clazz = OrderUpdateEvent.class;
		Field[] fileds = clazz.getDeclaredFields();
		System.out.println("message " + clazz.getSimpleName() + "Msg {");
		for(int i = 1; i <= fileds.length; i++) {
			Field f = fileds[i-1];
			System.out.print("\t");
			System.out.print(getField(f));
			System.out.println(" = "+i+";");
		}
		System.out.println("}");
	}
	
	private static String getField(Field f) {
//		System.out.println(type);
		String symbol = getSymbol(f.getType());
		String dataType = getDataType(f.getGenericType(), f.getType());
		
		return symbol + " " + dataType + " " + f.getName();
	}
	
	private static String getSymbol(Class<?> type) {
		if(type.getTypeName().equals(List.class.getName())) {
			return "repeated";
		} else {
			return "optional";
		}
	}
	
	private static String getDataType(Type type, Class<?> clazz) {
		if(clazz.isEnum()) {
			return "string";
		} else if(clazz.isAssignableFrom(long.class)) {
			return "int64";
		} else if(clazz.isAssignableFrom(String.class)) {
			return "string";
		} else if(clazz.isAssignableFrom(int.class)) {
			return "int32";
		} else if(clazz.isAssignableFrom(BigDecimal.class)) {
			return "string";
		} else {
			ParameterizedType t = (ParameterizedType)type;
			if(t.getRawType().getTypeName().equals(List.class.getName())) {
				String subClassName = t.getActualTypeArguments()[0].getTypeName();
				return subClassName.substring(subClassName.lastIndexOf(".") + 1) + "Msg";
				
			} else {
				throw new RuntimeException(type.getTypeName() + " no deel.");
			}
		}
	}
}
