package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * @author WeiHui-Z
 * @since 2021-11-04 15:57
 */
public class PageListModel implements HtmlBean {

	@Text
	@HtmlField(cssPath="a")
	private String title;

	@Href
	@HtmlField(cssPath="a")
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}


}
