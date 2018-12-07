package test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.TimerTask;

import javax.swing.JOptionPane;



public class DBConnection extends TimerTask {
		int min; int tim; String day; String place; String noplace; int cut;
		int air[] = new int[4];
		int checkair[][] = new int [4][4];
		private Connection con;
		private PreparedStatement pstmt;
		private ResultSet rs;
		DBmanage dBmanage = new DBmanage();
		public DBConnection() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3307/hansung","아이디","비밀번호");
				System.out.println("DB연결 성공");
			}
			catch(Exception e) {
				System.out.println("DB 연결오류"+e.getMessage());
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance( );
			if(calendar.get(Calendar.DAY_OF_WEEK)==1) {
				day = "일";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==2) {
				day = "월";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==3) {
				day = "화";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==4) {
				day = "수";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==5) {
				day = "목";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==6) {
				day = "금";
			}else if(calendar.get(Calendar.DAY_OF_WEEK)==7) {
				day = "토";
			}
			if(calendar.get(Calendar.HOUR_OF_DAY)==8) {
				tim = 0;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==9) {
				tim = 1;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==10) {
				tim = 2;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==11) {
				tim = 3;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==12) {
				tim = 4;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==13) {
				tim = 5;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==14) {
				tim = 6;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==15) {
				tim = 7;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==16) {
				tim = 8;
			}else if(calendar.get(Calendar.HOUR_OF_DAY)==17) {
				tim = 9;
			}
			min = calendar.get(Calendar.MINUTE);
			try {
				String SQL = "SELECT * FROM schedule WHERE day = ?";
				pstmt = con.prepareStatement(SQL);
				pstmt.setString(1,day);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					place=""; noplace = "";
					String time = rs.getString("time");
					int change = Integer.parseInt(time.substring(1, 2));
					int change2 = Integer.parseInt(time.substring(time.length()-2, time.length()-1));
					if(min==30) {
						if((tim+1)==change||(tim+1)==change+1||(tim+1)==change2) {
							place = rs.getString("place");	
						}
						else {
							noplace = rs.getString("place");
							}
					}
					else {
						if((tim)==change||(tim)==change+1||(tim)==change2) {
							place = rs.getString("place");
							System.out.println(place);
						}
					}
						
					for(int i=0; i<4; i++) { //받아오는 변수 초기화 함 해주고
						air[i]=0;
					}
					
					if(!place.equals("")) {
					if(place.equals("공학관 B105")) { //받아와서 변수에 넣고
						for(int i=0; i<4; i++) {	
						air[i] = message(i+1);
						}
					}
					else if(place.equals("탐구관 B101")) {
						for(int i=0; i<4; i++) {	
							air[i] = message(i+5);
							}
					}
					
					if(screen.check==1) {
						checkair[0][0] = 900; //조도 1000~700까지 너무높아도 안됨
						checkair[0][1] = 80;//미세먼지 80이상이면 나쁨
						checkair[0][2] = 25; // 학교 기준 온도 등등 정해주기 
						checkair[0][3] = 40;//40퍼 습도이상이면 작동
					}
					else if(screen.check==2){
						checkair[0][0] = 1500; //조도 2000~800까지 너무높아도 안됨
						checkair[0][1] = 80;//미세먼지 80이상이면 나쁨
						checkair[0][2] = 23; // 학교 기준 온도 등등 정해주기 
						checkair[0][3] = 50;//60퍼 습도이상이면 작동
					}else if(screen.check==3){
						checkair[0][0] = 900; //조도 1000~700까지 너무높아도 안됨
						checkair[0][1] = 80;//미세먼지 80이상이면 나쁨
						checkair[0][2] = 25; // 학교 기준 온도 등등 정해주기 
						checkair[0][3] = 40;//40퍼 습도이상이면 작동
					}
					//현재 온도를 비교해봄
					if(700>air[0] || air[0]>checkair[0][0]) {
						dBmanage.ipsearch(place, air[0],checkair[0][0], 0);
					}
					if(air[1]>checkair[0][1]) {
						dBmanage.ipsearch(place, air[1],checkair[0][1] ,1);
					}
					if(air[2] != checkair[0][2]) {
						dBmanage.ipsearch(place, air[2],checkair[0][2],2);
					}
					if(air[3]>checkair[0][3]) {
						dBmanage.ipsearch(place, air[3],checkair[0][3],3);
					}
				}
					else if(!noplace.equals("")) {
						dBmanage.turnoff(noplace);
					}
				
				}	
			}catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "에러커넥션에서"+e.getMessage(), "에러 메시지", JOptionPane.WARNING_MESSAGE);
			}{
				
			}
		}
		public int message(int place) throws Exception {
			URL url = null;
			String target = "https://api.thingspeak.com/channels/544668/fields/"+place+".json?results=1";
			HttpURLConnection con = (HttpURLConnection) new URL(target).openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while((temp = br.readLine()) != null) {
				temp = temp.substring(temp.indexOf("entry_id"));
				temp = temp.substring(temp.indexOf("field"));
				temp = temp.substring(temp.indexOf(":")+2,temp.length()-4);
				cut = Integer.parseInt(temp);
			}
			con.getInputStream();
			br.close();
			return cut;
		}
}
