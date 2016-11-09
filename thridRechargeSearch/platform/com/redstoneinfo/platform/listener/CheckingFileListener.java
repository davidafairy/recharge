package com.redstoneinfo.platform.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.redstoneinfo.platform.biz.dao.AgentDao;
import com.redstoneinfo.platform.biz.dao.CheckingDao;
import com.redstoneinfo.platform.biz.dao.OrderDao;
import com.redstoneinfo.platform.entity.Agent;
import com.redstoneinfo.platform.entity.CheckingFile;
import com.redstoneinfo.platform.entity.OrderHis;
import com.redstoneinfo.platform.utils.DateUtil;

public class CheckingFileListener implements ServletContextListener {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		final OrderDao orderDao = (OrderDao)springContext.getBean("orderDao");
		final AgentDao agentDao = (AgentDao)springContext.getBean("agentDao");
		final CheckingDao checkingDao = (CheckingDao)springContext.getBean("checkingDao");
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				
				while(true) {
					Date currentDate = Calendar.getInstance().getTime();
					currentDate.setHours(0);
					currentDate.setMinutes(0);
					currentDate.setSeconds(0);
					List<Agent> agents = agentDao.listAgents();
					for (Agent agent : agents) {
						
						if ("admin".equals(agent.getLoginName()))
							continue;
						
						String genCycle = DateUtil.getbeforeDate(currentDate);
						
						CheckingFile lastCheckingFile = checkingDao.findLastCheckingFile(agent.getLoginName());
						
						if (lastCheckingFile != null) {
							log.info("对账文件上个周期："+lastCheckingFile.getCheckingFileCycle());
							String checkCycle = lastCheckingFile.getCheckingFileCycle();
							Date lastCycleDate = DateUtil.parserByType(checkCycle, 2);
							Date genDate = DateUtil.getAfterDate(lastCycleDate, 1);
							if (genDate.before(DateUtil.getAfterSecond(currentDate,-3600))) {
								genCycle = DateUtil.getFormatedDateStr(genDate, 3);
							} else {
								genCycle = null;
							}
							
						}
						
						if (null != genCycle) {
							log.info("准备生成对账文件=>agent:"+agent.getName()+";cycle:"+genCycle);
							Map<String,String> condition = new HashMap<String,String>();
							if (!"admin".equals(agent.getLoginName())) {
								condition.put("eq.agentId", String.valueOf(agent.getId()));
							}
							condition.put("ge.rechargeTime", genCycle);
							condition.put("le.rechargeTime", genCycle);
							
							List list = orderDao.listOrderHisListWithCondition(condition);
							
							String fileName = "联通对账文件("+condition.get("ge.rechargeTime")+")-"+agent.getLoginName()+".xls";
							
							CheckingFile cf = new CheckingFile();
							cf.setAgentName(agent.getLoginName());
							cf.setAgentId(agent.getId());
							cf.setCheckingFileCycle(genCycle);
							cf.setFileName(fileName);
							cf.setGenTime(currentDate);
							try {
								genExcelFile(agent,list,fileName);
								cf.setResult(1);
							} catch (Exception e) {
								cf.setResult(2);
							}
							checkingDao.saveCheckingFile(cf);
						}else {
							log.info("没有找到合适的对账文件周期");
						}
					}
					try {
						Thread.currentThread().sleep(600000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}}).start();
		
	}
	
	public String genExcelFile(Agent agent,List list,String fileName) throws Exception {
		OutputStream os = null;
		jxl.write.WritableWorkbook book = null;
		
		String filePath = null;
		
		try {
			
			String tmpFilePath = this.getClass().getResource("/").toURI().getPath().replaceAll("WEB-INF/classes/", "")+"tmpFiles";
			filePath = tmpFilePath + "/" + fileName;
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			
			os = new FileOutputStream(filePath);
			book = jxl.Workbook.createWorkbook(os);
			List successList = new ArrayList();
			List failedList = new ArrayList();
			for (int i=0;i<list.size();i++) {
				OrderHis oh = (OrderHis)list.get(i);
				if (oh.getResult() == 1) {
					successList.add(oh);
				} else {
					failedList.add(oh);
				}
			}
			WritableSheet successSheet = book.createSheet("充值成功记录", 0);
			setColumn(successSheet,"集团编码","充值类型","充值交易时间","小票号","手机号","状态","金额","扣款金额","账户余额");
			for (int j=0;j<successList.size();j++) {
				OrderHis oh = (OrderHis)successList.get(j);
				sheetAddCell(agent.getGroupno(),0,j+1,successSheet);
				sheetAddCell("联通充值",1,j+1,successSheet);
				sheetAddCell(DateUtil.getFormatedDateStr(oh.getRechargeTime(), 2),2,j+1,successSheet);
				sheetAddCell(oh.getFlowNo(),3,j+1,successSheet);
				sheetAddCell(oh.getMobile(),4,j+1,successSheet);
				sheetAddCell("充值成功",5,j+1,successSheet);
				sheetAddCell(String.valueOf(oh.getMoney()/100),6,j+1,successSheet);
				sheetAddCell(String.valueOf(((double)oh.getAmount())/100),7,j+1,successSheet);
				sheetAddCell(String.valueOf(((double)oh.getBalance())/1000),8,j+1,successSheet);
			}
			
			WritableSheet failedSheet = book.createSheet("充值失败记录", 1);
			setColumn(failedSheet,"集团编码","充值类型","充值交易时间","小票号","手机号","状态","金额");
			for (int j=0;j<failedList.size();j++) {
				OrderHis oh = (OrderHis)failedList.get(j);
				sheetAddCell(agent.getGroupno(),0,j+1,failedSheet);
				sheetAddCell("联通充值",1,j+1,failedSheet);
				sheetAddCell(DateUtil.getFormatedDateStr(oh.getRechargeTime(), 2),2,j+1,failedSheet);
				sheetAddCell(oh.getFlowNo(),3,j+1,failedSheet);
				sheetAddCell(oh.getMobile(),4,j+1,failedSheet);
				sheetAddCell("充值失败",5,j+1,failedSheet);
				sheetAddCell(String.valueOf(oh.getMoney()/100),6,j+1,failedSheet);
			}
			book.write();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				book.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return filePath;
	}
	
	private void setColumn(WritableSheet sheet, String... strLine) throws RowsExceededException, WriteException {
		if(strLine.length!=0){
			for(int i = 0;i<strLine.length;i++){
				Label label00 = new Label(i, 0, strLine[i]);
				sheet.addCell(label00);
			}
		}	
	}
	
	//String类型加入表格
	private void sheetAddCell(String content,int cell ,int row,WritableSheet sheet) throws WriteException,
			RowsExceededException {
		if(content!=null){
			Label label = new Label(cell, row, content);
			sheet.addCell(label);
		}
	}

}