package idv.kaponcer.RoEAnalyze.model.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import idv.kaponcer.RoEAnalyze.DatabaseControl;
import idv.kaponcer.RoEAnalyze.model.bean.Transaction;

public class InsertTran implements Queryable<Transaction> {

	private PreparedStatement preState = null;

	@Override
	public String getSQL() {
		return "insert into Transaction" + "( T_DATE,T_TIME, C_BUY, C_SELL, R_BUY, R_SELL, WORKED)"
				+ "values (?,?,?,?,?,?,?)";
	}

	@Override
	public Transaction query(QueryCommand comm, Map<Enum, String> args) {
		int result = 0;
		Transaction tran = null;

		String dateTime = args.get(Field.DATE)+" "+args.get(Field.TIME);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			java.util.Date date = sdf.parse(dateTime);
			if (DatabaseControl.getDatabaseControl().query(QueryCommand.QUERY, args) == null) {
				preState.setDate(1, new Date(date.getTime()));
				preState.setTime(2, new Time(date.getTime()));
				preState.setFloat(3, Float.valueOf(args.get(Field.C_BUY)));
				preState.setFloat(4, Float.valueOf(args.get(Field.C_SELL)));
				preState.setFloat(5, Float.valueOf(args.get(Field.R_BUY)));
				preState.setFloat(6, Float.valueOf(args.get(Field.R_SELL)));
				preState.setBoolean(7, Boolean.parseBoolean(args.get(Field.WORKED)));
				result = preState.executeUpdate();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DatabaseControl.getDatabaseControl().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return (Transaction) DatabaseControl.getDatabaseControl().query(QueryCommand.QUERY, args);
	}

	@Override
	public void init(PreparedStatement state) {
		preState = state;

	}

}
