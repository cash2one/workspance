/**
 * 
 */
package com.zz91.top.app.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Task;
import com.taobao.api.internal.util.AtsUtils;
import com.taobao.api.request.TopatsResultGetRequest;
import com.taobao.api.response.TopatsResultGetResponse;
import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.domain.SysTask;
import com.zz91.top.app.persist.SysTaskMapper;

/**
 * 扫描店铺 => AccessToken 最晚 trade 时间 + 最晚 Task end_time => task start and end time
 * create task
 * 
 * @author mays
 * 
 */
@Service
public class ScanTaskJob {

	@Resource
	private SysTaskMapper sysTaskMapper;
	@Resource
	private TopConfig topConfig;
	

	final static int LIMIT = 100;
	final static String METHOD = "taobao.topats.trades.sold.get";
	final static String DEST_DIR="/usr/data/top_download";

	public void doTask() {
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>scan task: "
//				+ DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 扫描任务(非处理完的任务)
		// unstart: start
		// undone:检查,并更新状态
		// DONE:下载ZIP－＞读取文件->写入DB

		int fromId = 0;
		List<SysTask> list = null;
		TaobaoClient client = new DefaultTaobaoClient(topConfig.getUrl(),
				topConfig.getAppKey(), topConfig.getAppSecret());
		do {
			list = sysTaskMapper.queryByTask(METHOD, fromId, LIMIT);

			for (SysTask t : list) {
				fromId=t.getId();
				TopatsResultGetRequest req = new TopatsResultGetRequest();
				req.setTaskId(Long.valueOf(t.getTaskId()));
				try {
					TopatsResultGetResponse rsp = client.execute(req);
					if (rsp.isSuccess()
							&& rsp.getTask().getStatus().equals("done")) {

						Task task = rsp.getTask();
						String url = task.getDownloadUrl();
						File taskFile = AtsUtils.download(url, new File(
								DEST_DIR+"/result")); // 下载文件到本地
						File resultFile = new File(DEST_DIR+"/unzip",
								task.getTaskId() + ""); // 解压后的结果文件夹
						
						List<File> files = AtsUtils.unzip(taskFile, resultFile); // 解压缩并写入到指定的文件夹

						// 遍历解压到的文件列表并读取结果文件进行解释 …
						for(File file:files){
							// TODO 读取文件写入数据库
							// TODO 更新 task 状态
						}

					} else {
						continue ;
					}
				
				} catch (ApiException e) {
					e.printStackTrace();
					continue ;
				} catch (IOException e) {
					e.printStackTrace();
					continue ;
				}
			}
		} while (list != null && list.size() > 0);
	}

}
