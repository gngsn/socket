/*
 *  수     업 : 인터넷 통신
 *  담당 교수님 : 강남희 
 *	이     름 : 박경선
 *  학     과 : IT미디어공학과 
 *  제출  날짜 : 2019.05.12
 *	개발  환경 : eclipse IDE - version: 4.11.0
 */

package server_socket_UDP;

import java.net.*;
import java.io.*;

public class server_UDP {
	public server_UDP(int port) {
		System.out.println("*****  Server Program - UDP *****");
		String changeData;
		
		try {
			// 지정된 포트와 바인
			DatagramSocket ds = new DatagramSocket(port);
			System.out.println("[log] bind socket");
			
			while (true) {
				byte buffer[] = new byte[512];
				// 패킷을 받아 데이터 추출 
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ds.receive(dp);
				String receiveData = new String(dp.getData());
				System.out.println("[log] receive data - \'" + receiveData + "\'");

				// 사용자로 받은 데이터가 영어 문장인지 아닌지를 판별해줄 정규식.
				if(receiveData.matches("^quit*\0*$")){
					break;
				} else if (receiveData.matches("^[a-zA-Z]*\0*$")) {
					changeData = "서버에서 받은 변경된 데이터 : " + receiveData.toUpperCase();
					// 전달해줄 데이터를 byte[]타입으로 지정 
					dp.setData(changeData.getBytes());
				} else {
					changeData = "영어 문장으로 입력해주세요.";
					dp.setData(changeData.getBytes());
				}

				// 받아온 DatagramPacket의 정보로 ip를 추출
				InetAddress ia = dp.getAddress();
				port = dp.getPort();
				// 데이터를 전송할 패킷을 만들고 전송 
				dp = new DatagramPacket(dp.getData(), dp.getData().length, ia, port);
				ds.send(dp);
				System.out.println("[log] send data - \'" + changeData + "\'");
			}
			ds.close();
			System.out.println("통신 종료");
		} catch (IOException ioe) {
			System.out.println("통신 종료");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception {
		new server_UDP(3000);
	}
}
