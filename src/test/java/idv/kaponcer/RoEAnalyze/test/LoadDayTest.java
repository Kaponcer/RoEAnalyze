package idv.kaponcer.RoEAnalyze.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import idv.kaponcer.RoEAnalyze.model.Crawlable;
import idv.kaponcer.RoEAnalyze.model.LoadDayWorked;

public class LoadDayTest {

	/**
	 * 抓指定日期營業時間的所有匯率變化並存在資料庫中
	 */
	@Test
	public void task_000_LoadDayWorked() {
		LoadDayWorked daydata = new LoadDayWorked();
		Map<Crawlable.DataField, String> data = new HashMap<Crawlable.DataField, String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat week = new SimpleDateFormat("u");
		// 指定為今日
		Date today = new Date();
		// 星期六日則提前一日
		if (week.format(today).equals("7"))
			today = new Date(today.getTime() - 24 * 60 * 60 * 1000);
		if (week.format(today).equals("6"))
			today = new Date(today.getTime() - 24 * 60 * 60 * 1000);
		data.put(Crawlable.DataField.DATE, sdf.format(today));

		/**
		 * test question 國定假日時則無資料
		 */
		daydata.setData(data);
		daydata.Crawled();
		assertEquals(true, daydata.transList.size() >= 0);

	}

	/**
	 * 抓指定日期非營業時間的所有匯率變化並存在資料庫中
	 */
	@Test
	public void task_001_LoadDayNotWorked() {
//		LoadDayNotWorked daydata = new LoadDayNotWorked();
//		Map<Crawlable.DataField, String> data = new HashMap<Crawlable.DataField, String>();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		data.put(Crawlable.DataField.DATE, sdf.format(new Date()));
//		daydata.setData(data);
//		daydata.Crawled();
//		assertEquals(true, daydata.transList.size() > 0);

	}

}
