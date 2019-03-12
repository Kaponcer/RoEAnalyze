package idv.kaponcer.RoEAnalyze.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import idv.kaponcer.RoEAnalyze.DatabaseControl;
import idv.kaponcer.RoEAnalyze.model.bean.Transaction;
import idv.kaponcer.RoEAnalyze.model.db.QueryCommand;
import idv.kaponcer.RoEAnalyze.model.db.Queryable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

	DatabaseControl dbc = null;

	/**
	 * 資料庫創建測試
	 */
	@Test
	public void task_000_CreateDB() {
		dbc = DatabaseControl.getDatabaseControl();
		Assert.assertNotEquals(null, dbc);
	}

	/**
	 * 插入資料測試
	 */
	@Test
	public void task_001_InsertData() {
		Map<Enum, String> insertTestData = new HashMap<Enum, String>();
		insertTestData.put(Queryable.Field.DATE, "2019/03/04");
		insertTestData.put(Queryable.Field.TIME, "09:03:52");
		insertTestData.put(Queryable.Field.C_BUY, "30.38");
		insertTestData.put(Queryable.Field.C_SELL, "31.07");
		insertTestData.put(Queryable.Field.R_BUY, "30.75");
		insertTestData.put(Queryable.Field.R_SELL, "30.85");
		insertTestData.put(Queryable.Field.WORKED, "true");
		
		dbc = DatabaseControl.getDatabaseControl();
		Transaction touple = (Transaction) dbc.query(QueryCommand.INERT, insertTestData);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		assertEquals(Boolean.valueOf("false"), touple == null);
		assertEquals("2019/03/04", dateFormat.format(touple.getT_Date()));
		assertEquals("09:03:52", timeFormat.format(touple.getT_Time()));
		assertEquals("30.38", touple.getC_Buy().toString());
		assertEquals("31.07", touple.getC_Sell().toString());
		assertEquals("30.75", touple.getR_Buy().toString());
		assertEquals("30.85", touple.getR_Sell().toString());
		assertEquals(Boolean.valueOf("true"), touple.isWorked());
	}

	/**
	 * 查詢測試
	 */
	@Test
	public void task_002_1Query() {
		dbc = DatabaseControl.getDatabaseControl();
		this.queryAll();
		this.queryUseID();
		this.queryUseDateTime();
	}

	private void queryAll() {
		
	}
	
	private void queryUseID() {

	}

	private void queryUseDateTime() {

	}

	/**
	 * 更新測試
	 */
	@Test
	public void task_003_Update() {
		dbc = DatabaseControl.getDatabaseControl();

	}
	
	/**
	 * 刪除測試
	 */
	@Test
	public void task_004_Delete() {
		dbc = DatabaseControl.getDatabaseControl();

	}
}
