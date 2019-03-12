package idv.kaponcer.RoEAnalyze.model.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import idv.kaponcer.RoEAnalyze.DatabaseControl;
import idv.kaponcer.RoEAnalyze.model.bean.Transaction;

public class QueryTran implements Queryable<Transaction> {

	private PreparedStatement preState = null;

	@Override
	public Transaction query(QueryCommand comm, Map<Enum, String> args) {
		ResultSet resultSet = null;
		Transaction tran = null;
		try {
			String date = args.get(Field.DATE)+" "+args.get(Field.TIME);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			java.util.Date datetime = sdf.parse(date);
			
			
			preState.setDate(1, new Date(datetime.getTime()));
			preState.setTime(2, new Time(datetime.getTime()));

			resultSet = preState.executeQuery();
			
			while (resultSet.next()) {
				tran = new Transaction();
				tran.setTID(resultSet.getInt(1));
				tran.setT_Date(resultSet.getDate(2));
				tran.setT_Time(resultSet.getTime(3));
				tran.setC_Buy(resultSet.getFloat(4));
				tran.setC_Sell(resultSet.getFloat(5));
				tran.setR_Buy(resultSet.getFloat(6));
				tran.setR_Sell(resultSet.getFloat(7));
				tran.setWorked(resultSet.getBoolean(8));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					DatabaseControl.getDatabaseControl().close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return tran;
	}

	@Override
	public String getSQL() {
		return "select * from Transaction" + " where T_DATE = ? AND T_TIME = ?";
	}

	@Override
	public void init(PreparedStatement state) {
		this.preState = state;
	}

}
