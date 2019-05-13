/*
 *  수     업 : 인터넷 통신
 *  담당 교수님 : 강남희 
 *	이     름 : 박경선
 *  학     과 : IT미디어공학과 
 *  제출  날짜 : 2019.05.12
 *	개발  환경 : eclipse IDE - version: 4.11.0
 */

package server_socket_TCP;

import java.io.*;
import java.net.*;

public class server_TCP {
	ServerSocket server = null;
	Socket client = null;
	static final int port = 3000; // 상수값으로 port 번호 설정

	// 입력용 Stream
	InputStream is;
	ObjectInputStream ois;

	// 출력용 Stream
	OutputStream os;
	ObjectOutputStream oos;

	// 사용자로 부터 받은 데이터를 담을 데이터
	String receiveData;
	// 대문자로 변경된 데이터
	String changeData;

	server_TCP() {
		try {
			System.out.println("*****  Server Program - TCP *****");
			
			server = new ServerSocket(port); // port 번호: 3000으로 ServerSocket 생성
			System.out.println("[log] bind socket");
			
			System.out.println("[log] listen ");
			// Client 접속이 있을 때까지 대기: 접속하는 순간 Socket을 반환 
			client = server.accept();
			System.out.println("[log] accept connection from client");

			// Client로 부터 수신받은 message를 읽기 위한 입력 Stream 
			is = client.getInputStream();
			ois = new ObjectInputStream(is);
			
			// Client로 부터 수신받은 message를 다시 보내기 위한 출력 Stream 
			os = client.getOutputStream();
			oos = new ObjectOutputStream(os);

			// Client가 보내온 message를 Server가 읽은 후 다시 Client에게 전송함
			while ((receiveData = (String) ois.readObject()) != null) // ObjectInputStream.readObject() 호출
			{
				System.out.println("[log] read  \'" + receiveData +"\'");
				// 사용자로 받은 데이터가 영어 문장인지 아닌지를 판별해줄 정규식.
				if (receiveData.matches("^*[a-zA-Z]*$")) 
				{				
					changeData = "서버에서 받은 변경된 데이터 : " + receiveData.toUpperCase();	
				} else {
					changeData = "영어 문장으로 입력해주세요.";
				}
				oos.writeObject(changeData); 	// ObjectOutputStream.writeObject() 호출
				System.out.println("[log] write \'" + changeData +"\'");
				oos.flush(); 					// 버퍼의 데이터를 효율적으로 전송하기 위한 method
			}
			
			// quit으로 사용자가 더이상 데이터를 주지 않는다면 모든 stream들을 닫아줘야한다.
			is.close();
			ois.close();
			os.close();
			oos.close();
			System.out.println("[log] close socket");
		} catch (Exception e) {
			System.out.println("통신 종료");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new server_TCP();
	}
}