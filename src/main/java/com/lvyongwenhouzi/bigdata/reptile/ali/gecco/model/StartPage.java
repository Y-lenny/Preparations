//package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model;
//
//import com.geccocrawler.gecco.annotation.Gecco;
//import com.geccocrawler.gecco.annotation.HtmlField;
//import com.geccocrawler.gecco.spider.HtmlBean;
//
//import java.util.List;
//
///**
// * @author WeiHui-Z
// * @since 2021-11-04 15:57
// */
//@Gecco(matchUrl = "http://mysql.taobao.org/monthly/", timeout = 60 * 1000,
//		pipelines = {"consolePipeline", "listPipeline"})
//public class StartPage implements HtmlBean {
//
//	@HtmlField(cssPath = "a.main")
//	private List<PageListModel> pageListModels;
//
//	public List<PageListModel> getPageListModels() {
//		return pageListModels;
//	}
//
//	public void setPageListModels(final List<PageListModel> pageListModels) {
//		this.pageListModels = pageListModels;
//	}
//
//}
