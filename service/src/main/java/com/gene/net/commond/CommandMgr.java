package com.gene.net.commond;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.util.ClassUtil;

public final class CommandMgr {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandMgr.class);
	
	/**
	 * 缓存命令对象
	 **/
	private static Map<Short, Command> cmdCache = new HashMap<Short, Command>();
	
	/**
	 * 初始化命令集合
	 */
	public static boolean init() {
		try {
			if(!checkCommand()){
				return false ;
			}
			
			Package pack = CommandMgr.class.getPackage();
			Set<Class<?>> allClasses = ClassUtil.getClasses(pack);

			for (Class<?> clazz : allClasses) {
				try {
					Cmd cmd = clazz.getAnnotation(Cmd.class);
					if (cmd != null) {
						if (cmdCache.get(cmd.code()) != null) {
							String name = cmdCache.get(cmd.code()).getClass().getName();
							LOGGER.error("cmd code error, code : " + cmd.code() + " exist :" + name + ", new : " + clazz.getName());
							return false;
						}
						cmdCache.put(cmd.code(), (Command) clazz.newInstance());
						continue;
					}

				} catch (Exception e) {
					LOGGER.error("load command fail, command name : " + clazz.getName(), e);
					e.printStackTrace();
				}
			}

			LOGGER.info("cmdCache size : " + cmdCache.size());
			
			return true;
		} catch (Exception e) {
			LOGGER.error("命令管理器解析错误", e);
			return false;
		}
	}

	/**
	 * 缓存中获取命令
	 */
	public static Command getCommand(short code) {
		return cmdCache.get(code);
	}

	/**
	 * 检测协议号是否有重复的
	 */
	private static boolean checkCommand() {
		try {
			Class<?> clazz = Class.forName("com.ly.code.PBCode");
			Map<Short, String> checkedFields = new HashMap<Short, String>();
			Field[] fields = clazz.getFields();
			for (Field f : fields) {
				short key = f.getShort(f.getType());
				if (checkedFields.containsKey(key)) {
					LOGGER.error("协议冲突，类：" + checkedFields.get(key) + " 协议：" + key);
					return false;
				} else {
					checkedFields.put(key, f.getDeclaringClass().getName());
				}
			}
		} catch (Exception e) {
			LOGGER.error("检测协议时出错", e);
			return false;
		}
		return true;
	}
	
}
