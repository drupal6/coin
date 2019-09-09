package com.gene;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import com.huobi.client.model.Withdraw;

public class MakeBean {

	public static void main(String[] args) {
		Class<?> clazz = Withdraw.class;
		String beanName = lowerFirst(clazz.getSimpleName());
		String msgName = beanName + "Msg";
		Field[] fileds = clazz.getDeclaredFields();
		System.out.println("public static " + clazz.getSimpleName() + " parse" + clazz.getSimpleName() + "(" + clazz.getSimpleName() + "Msg "+beanName+"Msg) {");
		System.out.println("\t"+clazz.getSimpleName()+" "+beanName+" = new "+clazz.getSimpleName()+"();");
		for (int i = 1; i <= fileds.length; i++) {
			Field f = fileds[i - 1];
			System.out.println("\t" + setData(f, beanName, msgName));
		}
		System.out.println("\treturn "+beanName + ";");
		System.out.println("}");
	}

	private static boolean isRepeated(Class<?> type) {
		if(type.getTypeName().equals(List.class.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String setData(Field f, String beanName, String msgName) {
		Type type = f.getGenericType();
		Class<?> clazz = f.getType();
		if (clazz.isEnum()) {
			return beanName + ".set" + upFirst(f.getName()) + "(" + clazz.getSimpleName() + ".valueOf(" +msgName + ".get" +upFirst(f.getName())+"()));";
		} else if (clazz.isAssignableFrom(long.class)) {
			return beanName + ".set" + upFirst(f.getName()) + "(" +msgName + ".get" +upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(String.class)) {
			return beanName + ".set" + upFirst(f.getName()) + "(" +msgName + ".get" +upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(int.class)) {
			return beanName + ".set" + upFirst(f.getName()) + "(" +msgName + ".get" +upFirst(f.getName())+"());";
		} else if (clazz.isAssignableFrom(BigDecimal.class)) {
			return beanName + ".set" + upFirst(f.getName()) + "(new BigDecimal(" +msgName + ".get" +upFirst(f.getName())+"()));";
		} else {
			ParameterizedType t = (ParameterizedType) type;
			if (t.getRawType().getTypeName().equals(List.class.getName())) {
				String subClassName = t.getActualTypeArguments()[0].getTypeName();
				String subMsgName = subClassName.substring(subClassName.lastIndexOf(".") + 1);
				if(false == isRepeated(clazz)) {
					return beanName +".set"+upFirst(f.getName())+"(parse" + subMsgName + "("+msgName+".get"+upFirst(f.getName())+"()));";
				} else {
					String firstSubMsgName = lowerFirst(subMsgName);
					StringBuffer buffer = new StringBuffer();
					buffer.append("List<"+subMsgName+"> "+firstSubMsgName+"s = new ArrayList<>();\r\n");
					buffer.append("\t"+msgName+".get"+upFirst(f.getName())+"List().forEach("+firstSubMsgName +"Msg -> {\r\n");
						buffer.append("\t\t"+firstSubMsgName+"s.add(parse"+subMsgName+"("+firstSubMsgName +"Msg));\r\n");
					buffer.append("\t});\r\n");
					buffer.append("\t"+beanName+".set"+upFirst(f.getName())+"("+firstSubMsgName+"s);");
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
