package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model.PageListModel;
import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model.StartPage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@PipelineName("listPipeline")
public class ListPipeline implements Pipeline<StartPage> {

	@Override
	public void process(StartPage startPage) {
		List<PageListModel> pageListModels = startPage.getPageListModels();

		for (PageListModel pageListModel : pageListModels) {
			String title = pageListModel.getTitle();
			String url = pageListModel.getUrl();

			log.info("====>开始爬取月份的文章列表，title：{} ， URL：{}", title, url);

			HttpRequest subRequest = new HttpGetRequest(url);
			DeriveSchedulerContext.into(subRequest);
		}
	}

}
