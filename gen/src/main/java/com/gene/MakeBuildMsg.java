package com.gene;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import com.huobi.client.model.UnitPrice;

public class MakeBuildMsg {

	public static void main(String[] args) {
		Class<?> clazz = UnitPrice.class;
		String beanName = lowerFirst(clazz.getSimpleName());
		String builderName = beanName + "Builder";
		Field[] fileds = clazz.getDeclaredFields();
		System.out.println("public static " + clazz.getSimpleName() + "Msg build" + clazz.getSimpleName() + "Msg(" + clazz.getSimpleName() + " "+beanName+") {");
		System.out.println("\t"+clazz.getSimpleName()+"Msg.Builder "+builderName+" = "+clazz.getSimpleName()+"Msg.newBuilder();");
		for (int i = 1; i <= fileds.length; i++) {
			Field f = fileds[i - 1];
			System.out.println("\t" + setData(f, beanName, builderName));
		}
		System.out.println("\treturn "+builderName+".build();");
		System.out.println("}");
	}

	private static boolean isRepeated(Class<?> type) {
		if(type.getTypeName().equals(List.class.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String setData(Field f, String beanName, String builderName) {
		Type type = f.getGenericType();
		Class<?> clazz = f.getType();
		if (clazz.isEnum()) {
			return builderName +".set"+upFirst(f.getName())+"("+beanName+".get"+upFirst(f.getName())+"().toString());";
		} else if (clazz.isAssignableFrom(long.class)) {
			return builderName +".set"+upFirst(f.getName())+"("+beanName+".get"+upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(String.class)) {
			return builderName +".set"+upFirst(f.getName())+"("+beanName+".get"+upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(int.class)) {
			return builderName +".set"+upFirst(f.getName())+"("+beanName+".get"+upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(BigDecimal.class)) {
			return builderName +".set"+upFirst(f.getName())+"("+beanName+".get"+upFirst(f.getName())+"().toString());";
		} else {
			ParameterizedType t = (ParameterizedType) type;
			if (t.getRawType().getTypeName().equals(List.class.getName())) {
				String subClassName = t.getActualTypeArguments()[0].getTypeName();
				String subMsgName = subClassName.substring(subClassName.lastIndexOf(".") + 1);
				if(false == isRepeated(clazz)) {
					return builderName +".set"+upFirst(f.getName())+"(build" + subMsgName + "Msg("+beanName+".get"+upFirst(f.getName())+"()));";
				} else {
					String firstSubMsgName = lowerFirst(subMsgName);
					StringBuffer buffer = new StringBuffer();
					buffer.append(beanName + ".get"+subMsgName+"s().forEach("+firstSubMsgName+" -> {\r\n");
					buffer.append("\t\t"+builderName+".add"+subMsgName+"s(build"+subMsgName+"Msg("+firstSubMsgName+"));");
					buffer.append("\r\n");
					buffer.append("\t});");
					return buffer.toString();
				}
			} else {
				throw new RuntimeException(type.getTypeName() + " no deel.");
			}
		}
	}

	public static String lowerFirst(String oldStr) {
		return oldStr.substring(0, 1).toLowerCase() + oldStr.substring(1);
	}
	
	public static String upFirst(String oldStr) {
		return oldStr.substring(0, 1).toUpperCase() + oldStr.substring(1);
	}
}
