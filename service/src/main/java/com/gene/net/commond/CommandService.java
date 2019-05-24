package com.gene.net.commond;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.cmd.Command;
import com.gene.util.ClassUtil;

public final class CommandService {

	private final Logger LOGGER = LoggerFactory.getLogger(CommandService.class);
	
	
	private static CommandService instnace = new CommandService();
	
	public static CommandService getInst() {
		return instnace;
	}
	/**
	 * 缓存命令对象
	 **/
	private Map<Short, Map<Short, Command>> osCache = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		CommandService.getInst().init();
	}
	
	/**
	 * 初始化命令集合
	 * @throws  
	 * @throws Exception 
	 */
	public void init() throws Exception {
			checkCommand("com.gene.message.ReqCode", "com.gene.message.ResCode");
			Package pack = Command.class.getPackage();
			Set<Class<?>> allClasses = ClassUtil.getClasses(pack);
			for (Class<?> clazz : allClasses) {
				Cmd cmd = clazz.getAnnotation(Cmd.class);
				if (cmd != null) {
					Map<Short, Command> cmdCache = osCache.get(cmd.os());
					if(cmdCache == null) {
						cmdCache = new HashMap<>();
						osCache.put(cmd.os(), cmdCache);
					}
					cmdCache.put(cmd.code(), (Command) clazz.newInstance());
				}
			}
	}

	/**
	 * 缓存中获取命令
	 */
	public Command getCommand(short os, short code) {
		Map<Short, Command> cmdCache = osCache.get(os);
		if(cmdCache == null) {
			LOGGER.error("os cmd is null. os:{}", os);
			return null;
		}
		return cmdCache.get(code);
	}

	/**
	 * 检测协议号是否有重复的
	 */
	private void checkCommand(String ... clazzPaths) throws Exception {
		Map<Short, String> checkedFields = new HashMap<Short, String>();
		for(String path : clazzPaths) {
			Class<?> clazz = Class.forName(path);
			Field[] fields = clazz.getFields();
			for (Field f : fields) {
				short key = f.getShort(f.getType());
				if (checkedFields.containsKey(key)) {
					throw new Exception("协议冲突，类：" + checkedFields.get(key) + " 协议：" + key);
				} else {
					checkedFields.put(key, f.getDeclaringClass().getName());
				}
			}
		}
	}
	
}
