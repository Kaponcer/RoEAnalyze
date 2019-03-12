package idv.kaponcer.RoEAnalyze;

import org.eclipse.jetty.server.Server;
public class Webserver implements Runnable{

	@Override
	public void run() {
		Server server = new Server(8080);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
