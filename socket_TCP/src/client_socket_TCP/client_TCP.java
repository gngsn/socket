/*
 *  수     업 : 인터넷 통신
 *  담당 교수님 : 강남희 
 *	이     름 : 박경선
 *  학     과 : IT미디어공학과 
 *  제출  날짜 : 2019.05.12
 *	개발  환경 : eclipse IDE - version: 4.11.0
 */

package client_socket_TCP;
import java.io.*;
import java.net.*;

class client_TCP
{
   Socket client = null;
   String ipAddress; //접속을 요청할 Server의 IP 주소를 저장할 변수
   static final int port = 3000; //접속을 요청할 Server의 port 번호와 동일하게 지정
 
   BufferedReader read;
 
   //입력용 Stream
   InputStream is;
   ObjectInputStream ois;

   //출력용 Stream
   OutputStream os;
   ObjectOutputStream oos;
 
   //서버로 보내줄 데이터
   String sendData;
   //서버로부터 받은 변경된 데이터
   String receiveData;

   client_TCP(String ip)
   {
	 //생성자의 IP Address를 ipAddress 맴버변수에 저장
      ipAddress = ip; 

      try
      {
    	  System.out.println("*****  Client Program - TCP *****");
    	  
         // 접속할 Server의 IP Address와 port 번호 정보가 있는 Socket 생성 
         client = new Socket(ipAddress,port);
         
         // Client Socket이 생성되면, Server의 accept()가 수행된다

         // 키보드로부터 message를 읽어올 입력 Stream
         read = new BufferedReader(new InputStreamReader(System.in));

         // Server로 message를 송신하기 위한 출력 Stream
         os = client.getOutputStream();
         oos = new ObjectOutputStream(os);

         // Server로 보낸 message를 수신받기 위한 입력  Stream 
         is = client.getInputStream();
         ois = new ObjectInputStream(is);

         System.out.print("\n영어 문장을 입력해주세요 : ");
         
         // 키보드로부터 message를 입력 받아 Server로 전송한 후 다시 받아서 출력 
         while ((sendData = read.readLine()) != null)
         {
            oos.writeObject(sendData); 				//ObjectOutputStream.writeObject() 호출
            oos.flush(); 							//버퍼의 데이터를 효율적으로 전송하기 위한 method
            
            if (sendData.equals("quit")) 		// quit를 입력하면 사용자 입력을 종료
			{
            	break;
			}
            
            receiveData = (String)ois.readObject(); //ObjectInputStream.readObject() 호출
            
            System.out.println(">> " +receiveData);
            System.out.print("\n영어 문장을 입력해주세요 : ");
         }
         is.close();
         ois.close();
         os.close();
         oos.close();
         client.close(); 							//Socket 닫기
         System.out.print("\n통신 종료");
      }
      catch (Exception e)
      {
         System.out.println("통신 Error");
         System.exit(0);
      }
   }
   public static void main(String[] args) 
   {
      new client_TCP("127.0.0.1");					 //Server Program이 실행되는 컴퓨터의 IP Address를 입력
   }
}