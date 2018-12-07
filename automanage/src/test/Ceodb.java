package test;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;

public class Ceodb {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 620;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					screen frame = new screen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void auto() {
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		int min = date.get(Calendar.MINUTE);
		date.set(Calendar.MINUTE,31);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		DBConnection db = new DBConnection();
		timer.scheduleAtFixedRate(db, date.getTime(), 1000 * 60 * 10);
	}
}