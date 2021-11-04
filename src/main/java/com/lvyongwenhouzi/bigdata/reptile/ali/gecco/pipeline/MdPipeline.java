package com.lvyongwenhouzi.bigdata.reptile.ali.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.lvyongwenhouzi.bigdata.reptile.ali.data.DataContainer;
import com.lvyongwenhouzi.bigdata.reptile.ali.gecco.model.SecondPage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WeiHui-Z
 * @since 2021-11-04 17:09
 */
@Slf4j
@PipelineName("mdPipeline")
public class MdPipeline implements Pipeline<SecondPage> {

	@Override
	public void process(final SecondPage bean) {
		DataContainer.addData(bean);
	}

}
