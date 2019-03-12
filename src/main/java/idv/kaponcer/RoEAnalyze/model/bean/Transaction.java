package idv.kaponcer.RoEAnalyze.model.bean;

import java.sql.Time;
import java.sql.Date;

public class Transaction {
	private Integer TID = null;
	private Date T_Date = null;
	private Time T_Time =null;
	private Float C_Buy = null;
	private Float C_Sell = null;
	private Float R_Buy = null;
	private Float R_Sell = null;
	private Boolean Worked = null;

	public Integer getTID() {
		return TID;
	}

	public void setTID(int tID) {
		TID = tID;
	}

	public Float getC_Buy() {
		return C_Buy;
	}

	public void setC_Buy(float f) {
		C_Buy = f;
	}

	public Float getC_Sell() {
		return C_Sell;
	}

	public void setC_Sell(float c_Sell) {
		C_Sell = c_Sell;
	}

	public Float getR_Buy() {
		return R_Buy;
	}

	public void setR_Buy(float r_Buy) {
		R_Buy = r_Buy;
	}

	public Float getR_Sell() {
		return R_Sell;
	}

	public void setR_Sell(float r_Sell) {
		R_Sell = r_Sell;
	}

	public Boolean isWorked() {
		return Worked;
	}

	public void setWorked(boolean worked) {
		Worked = worked;
	}

	public Date getT_Date() {
		return T_Date;
	}

	public void setT_Date(Date date) {
		T_Date = date;
	}

	public Time getT_Time() {
		return T_Time;
	}

	public void setT_Time(Time t_Time) {
		T_Time = t_Time;
	}

}
