package idv.kaponcer.RoEAnalyze;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import idv.kaponcer.RoEAnalyze.model.db.QueryCommand;
import idv.kaponcer.RoEAnalyze.model.db.Queryable;

public class DatabaseControl implements Queryable<Object> {
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:./RoEEntity";
	// 命令物件註冊表,
	private static Map<QueryCommand, Queryable> QueryMap = new HashMap<QueryCommand, Queryable>();

	private static DatabaseControl dbm = null;
	private Connection conn = null;

	/**
	 * Constructor
	 * 
	 */
	private DatabaseControl() {
		try {
			Class.forName(DRIVER);
			File db = new File("./RoEEntity.mv.db");
			// 如果資料庫不存在，就先建一個資料庫
			if (!db.exists()) {
				conn = DriverManager.getConnection(URL);
				Statement statement = conn.createStatement();
				statement.execute(this.getSQL());
				statement.close();
				conn.close();
			}
			this.init(null);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static DatabaseControl getDatabaseControl() {
		if (dbm != null)
			return dbm;
		return new DatabaseControl();
	}

	// 將查詢命令註冊到本類別
	public Queryable<?> register(QueryCommand comm, Queryable<?> query) {
		return QueryMap.put(comm, query);
	}

	// 新增一個Table
	@Override
	public String getSQL() {
		return "create table Transaction" + "(TID INTEGER PRIMARY KEY AUTO_INCREMENT," + " T_DATE DATE, "
				+ " T_TIME TIME, " + " C_BUY REAL," + " C_SELL REAL," + " R_BUY REAL," + " R_SELL REAL,"
				+ " WORKED BOOLEAN)";
	}

	// 每當有命令進來的時候,自動的去指派該命令物件去執行
	@Override
	public Object query(QueryCommand comm, Map<Enum, String> args) {
		try {
			// 如果connect已經關閉,則先去初始化啟動
			if (conn.isClosed()) {
				init(null);
			}
			// 指派給該命令物件去執行
			return (Object) QueryMap.get(comm).query(comm, args);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return this.query(comm, args);
	}

	public void close() throws SQLException {
		conn.close();
	}

	// 連結資料庫,並預先編譯好要查詢的SQL以供之後使用.
	@Override
	public void init(PreparedStatement state) {
		try {
			conn = DriverManager.getConnection(URL);
			for (QueryCommand query : QueryCommand.values()) {
				this.register(query, query.getInstance());
			}

			// 將每個查詢命令的物件,預先編譯好SQL以便後續直接可以進行執行
			for (QueryCommand comm : QueryMap.keySet()) {
				QueryMap.get(comm).init(
						// 預先編譯SQL
						conn.prepareCall(QueryMap.get(comm).getSQL()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}