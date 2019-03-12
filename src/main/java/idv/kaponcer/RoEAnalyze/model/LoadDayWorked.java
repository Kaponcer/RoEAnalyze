package idv.kaponcer.RoEAnalyze.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import idv.kaponcer.RoEAnalyze.DatabaseControl;
import idv.kaponcer.RoEAnalyze.model.bean.Transaction;
import idv.kaponcer.RoEAnalyze.model.db.QueryCommand;
import idv.kaponcer.RoEAnalyze.model.db.Queryable;

/***
 * 
 * @author kaponcer
 *
 *         爬指定日期營業時間的美金匯率 https://rate.bot.com.tw/xrt/quote/yyyy-MM-dd/USD
 */
public class LoadDayWorked implements Crawlable {

	private String date = null;
	public String tableTitle;
	public List<Transaction> transList = new LinkedList<Transaction>();

	@Override
	public void Crawled() {
		try {
			Document doc = Jsoup.connect("https://rate.bot.com.tw/xrt/quote/" + date + "/USD").get();
			Element table = doc.select("table").get(0);
			Elements rows = table.select("tbody").select("tr");
			Iterator<Element> iterator = rows.listIterator();
			while (iterator.hasNext()) {
				Elements clounms = iterator.next().select("td");
				DatabaseControl dbc = DatabaseControl.getDatabaseControl();
				Map<Enum, String> insertData = new HashMap<Enum, String>();
				
				if(clounms.size()<=1)
					break;
				String[] datetime = clounms.get(0).html().split(" ");
				insertData.put(Queryable.Field.DATE, datetime[0]);
				insertData.put(Queryable.Field.TIME, datetime[1]);
				insertData.put(Queryable.Field.C_BUY, clounms.get(2).html());
				insertData.put(Queryable.Field.C_SELL, clounms.get(3).html());
				insertData.put(Queryable.Field.R_BUY, clounms.get(4).html());
				insertData.put(Queryable.Field.R_SELL, clounms.get(5).html());
				insertData.put(Queryable.Field.WORKED, "true");
				Transaction data = (Transaction) dbc.query(QueryCommand.INERT, insertData);
				transList.add(data);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定日期
	 */
	@Override
	public void setData(Map<Crawlable.DataField, String> data) {
		date = data.get(Crawlable.DataField.DATE);

	}

}
