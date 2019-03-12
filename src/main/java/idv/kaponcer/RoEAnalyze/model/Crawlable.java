package idv.kaponcer.RoEAnalyze.model;

import java.util.Map;

/**
 * 
 * @author kaponcer 可爬蟲的介面
 *
 */
public interface Crawlable {
	public enum DataField {
		DATE
	}

	/**
	 * 爬蟲執行
	 */
	public void Crawled();

	/**
	 * 查詢參數
	 */
	public void setData(Map<Crawlable.DataField, String> data);
}
