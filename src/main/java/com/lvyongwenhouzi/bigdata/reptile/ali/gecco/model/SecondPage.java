//package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model;
//
//import com.geccocrawler.gecco.annotation.Gecco;
//import com.geccocrawler.gecco.annotation.HtmlField;
//import com.geccocrawler.gecco.annotation.Request;
//import com.geccocrawler.gecco.annotation.RequestParameter;
//import com.geccocrawler.gecco.request.HttpRequest;
//import com.geccocrawler.gecco.spider.HtmlBean;
//
//import java.util.List;
//
///**
// * @author WeiHui-Z
// * @since 2021-11-04 15:57
// */
//@Gecco(matchUrl = "http://mysql.taobao.org/monthly/{year}/{month}/", timeout = 60 * 1000,
//		pipelines = {"consolePipeline", "mdPipeline"})
//public class SecondPage implements HtmlBean, Comparable<SecondPage> {
//
//	@RequestParameter
//	private String year;
//
//	@RequestParameter
//	private String month;
//
//	@Request
//	private HttpRequest request;
//
//	@HtmlField(cssPath = "#container > div.content.typo > ul > li > h3 > a")
//	private List<PageListModel> pageListModels;
//
//	public String getYear() {
//		return year;
//	}
//
//	public void setYear(final String year) {
//		this.year = year;
//	}
//
//	public String getMonth() {
//		return month;
//	}
//
//	public void setMonth(final String month) {
//		this.month = month;
//	}
//
//	public HttpRequest getRequest() {
//		return request;
//	}
//
//	public void setRequest(final HttpRequest request) {
//		this.request = request;
//	}
//
//	public List<PageListModel> getPageListModels() {
//		return pageListModels;
//	}
//
//	public void setPageListModels(final List<PageListModel> pageListModels) {
//		this.pageListModels = pageListModels;
//	}
//
//	@Override
//	public int compareTo(final SecondPage o) {
//		String other = o.getYear() + o.getMonth();
//		String current = this.getYear() + this.getMonth();
//		return current.compareTo(other);
//	}
//
//}
