//package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.listener;
//
//import com.geccocrawler.gecco.GeccoEngine;
//import com.geccocrawler.gecco.listener.SimpleEventListener;
//
//import com.lvyongwenhouzi.bigdata.reptile.ali.data.DataContainer;
//import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model.PageListModel;
//import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model.SecondPage;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
///**
// * @author WeiHui-Z
// * @since 2021-11-04 17:12
// */
//@Slf4j
//@AllArgsConstructor
//public class DataSave extends SimpleEventListener {
//
//	private String file;
//
//	@Override
//	public void onStop(final GeccoEngine ge) {
//		log.info("====>结束爬取，汇总结果开始");
//		List<SecondPage> data = DataContainer.getData();
//
//		StringBuilder sb = new StringBuilder("# 数据库内核月报");
//		sb.append("\n");
//		for (final SecondPage page : data) {
//			sb.append("## ");
//			sb.append(page.getYear()).append("/").append(page.getMonth());
//			sb.append("\n");
//			List<PageListModel> pageListModels = page.getPageListModels();
//			if (pageListModels == null || pageListModels.isEmpty()) {
//				continue;
//			}
//			sb.append("| ");
//			sb.append("文章");
//			sb.append(" |");
//			sb.append("链接");
//			sb.append("|");
//			sb.append("\n");
//			sb.append("| ");
//			sb.append("---------------------------------------------------");
//			sb.append(" |");
//			sb.append("------------");
//			sb.append("|");
//			sb.append("\n");
//			for (final PageListModel model : pageListModels) {
//				sb.append("|");
//				sb.append("[");
//				sb.append(model.getTitle());
//				sb.append("]");
//				sb.append("(");
//				sb.append(model.getUrl());
//				sb.append(")");
//				sb.append("|");
//				sb.append(model.getUrl());
//				sb.append("|");
//				sb.append("\n");
//			}
//			sb.append("\n");
//		}
//		try {
//			FileUtils.write(new File(this.file),sb.toString(),StandardCharsets.UTF_8.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		log.info("====>结束爬取，汇总结果完成");
//	}
//
//}
