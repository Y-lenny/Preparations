package com.lvyongwenhouzi.bigdata.reptile.ali;

import com.geccocrawler.gecco.GeccoEngine;
import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.listener.DataSave;

/**
 * @author WeiHui-Z
 * @since 2021-11-04 19:22
 */
public class ApplicationMain {

	private static final String filePath = "/Users/11114396/IdeaProjects/Preparations/reader/mysql.taobao.org.md";

	public static void main(String[] args) {
		GeccoEngine.create()
				.setEventListener(new DataSave(filePath))
				.classpath("com.lvyongwenhouzi.bigdata.reptile.ali.gecco")
				.seed("http://mysql.taobao.org/monthly/")
				.loop(false)
				.thread(20)
				.run();
	}

}
