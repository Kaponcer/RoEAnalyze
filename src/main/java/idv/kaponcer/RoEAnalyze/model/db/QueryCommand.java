package idv.kaponcer.RoEAnalyze.model.db;

import java.lang.reflect.InvocationTargetException;

public enum QueryCommand {
	INERT("InsertTran"), QUERY("QueryTran");

	private String InstanceName;

	QueryCommand(String instance) {
		InstanceName = instance;
	}

	public Queryable getInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		return (Queryable) Class.forName("idv.kaponcer.RoEAnalyze.model.db." + InstanceName).getConstructor()
				.newInstance(null);
		

	}

}
