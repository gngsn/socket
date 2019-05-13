/*
 *  수     업 : 인터넷 통신
 *  담당 교수님 : 강남희 
 *	이     름 : 박경선
 *  학     과 : IT미디어공학과 
 *  제출  날짜 : 2019.05.12
 *	개발  환경 : eclipse IDE - version: 4.11.0
 */

package client_socket_UDP;

import java.net.*;
import java.io.*;

public class client_UDP {

	private String str;
	private BufferedReader file;
	private static int SERVERPORT = 3000;

	public client_UDP(String ip, int port) {
		try {
			System.out.println("*****  Client Program - UDP *****");

			InetAddress ia = InetAddress.getByName(ip);
			// 전송할 수 있는 UDP 소켓 생성
			DatagramSocket ds = new DatagramSocket(port);

			System.out.print("\n영어 문장을 입력해주세요 : ");
			// 사용자의 입력을 받아드릴 bufferReader 객체의 인스턴스를 만듦.
			file = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				// 사용자의 입력을 받음
				str = file.readLine();
				// String -> byte 배열로 변경
				byte buffer[] = str.getBytes();

				// 데이터 패킷에 넣어서 서버에 전송
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ia, SERVERPORT);
				ds.send(dp);

				//quit을 입력했다면 통신 종료 
				if (str.equals("quit")) {
					break;
				}
				
				buffer = new byte[512];
				// 변경된 데이터를 받음
				dp = new DatagramPacket(buffer, buffer.length);
				ds.receive(dp);
				System.out.println(new String(dp.getData()).trim());
				System.out.print("\n영어 문장을 입력해주세요 : ");
			}
			// 소켓을 닫음 
			ds.close();
			System.out.print("통신 종료");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new client_UDP("localhost", 2000);
	}
};