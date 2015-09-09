
package interfacePerformance.util;

import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class RpcTextClient {
	
	private String host;
	private String port;
	
	private boolean isConnected;
	
	private Socket socket;
	private InputStream receiver;
	private OutputStream sender;
	
	ConcurrentLinkedQueue<String> response;

	//构造函数
	public RpcTextClient(String host,String port) {
		this.host = host;
		this.port = port;
		isConnected = false;
		
		socket = new Socket();
		response = new ConcurrentLinkedQueue<String>();
		
	}		
	
	/*
	 * 连接socket
	 */
	public void connect() throws UnknownHostException, IOException{
		try {
			socket = new Socket(host, Integer.parseInt(port));
			isConnected = true;
			sender = socket.getOutputStream();
			receiver = socket.getInputStream();
			
			new Thread(new ReceiveThread(), "Receive Thread").start();
		} catch (ConnectException e) {
			throw new ConnectException("connect failed");
		}
	}
	
	/*
	 * 发送字符串数据
	 */
	public void send(String data) throws IOException {
		
//		System.out.println(data);
		
		if(isConnected){
			try {
				//向服务器发送数据
				sender.write(data.getBytes());
			
			} catch (IOException e) {
				throw new IOException("send data failed", e);
			}
		}
	}
	
	/*
	 * 获取socket输入流
	 */
	public String getResponse(long timeout) {
		final long endTime = timeout + System.currentTimeMillis();
		String res = null;
		
		while(System.currentTimeMillis() < endTime){
			res = response.poll();
			if(res == null) {
				continue;
			}
			else {
				break;
			}
		}
		
		return (res);
	}
	
	/*
	 * 关闭线程，关闭socket
	 */
	public void close() {
		stopReceiveThread();
		closeSocket();
	}
	
	private void stopReceiveThread() {
		mStopReceive = true;
	}
	
	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	volatile boolean mStopReceive = false;
	
	private final class ReceiveThread implements Runnable {

		@Override
		public void run() {
			while(!mStopReceive) {
				try {
					receive();
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/*
	 * 接收返回值
	 */
	private void receive() throws IOException {
		try {
			//接收服务器端返回的数据
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(receiver,"utf-8"));

			String mString = null;
			//循环读取返回值直到为空
			while ((mString = bufferedReader.readLine()) != null) {
				response.offer(mString);
			}
					
		} catch (IOException e) {
			if("socket closed".equalsIgnoreCase(e.getMessage())){
				//关闭时，因为read方法还在阻塞中，所以要抛出这个异常，这是正常的，所以不处理
			}
			else {
				e.printStackTrace();
			}
		}
	}
}
