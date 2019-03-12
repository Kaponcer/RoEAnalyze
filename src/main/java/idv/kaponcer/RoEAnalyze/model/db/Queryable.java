package idv.kaponcer.RoEAnalyze.model.db;

import java.sql.PreparedStatement;
import java.util.Map;

public interface Queryable<T> {

	public enum Field {
		TID, DATE, TIME, C_BUY, C_SELL, R_BUY, R_SELL, WORKED
	}

	// 執行查詢的主體
	public T query(QueryCommand comm, Map<Enum, String> args);

	// 要執行查詢的SQL
	public String getSQL();

	// 查詢類別的初始化
	public void init(PreparedStatement state);

}
