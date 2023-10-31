package com.yoshida.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientProgram2 {
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		OutputStream os = null;
		try {
			int port = 1234;
			// 接続先を指定
			socket = new Socket("127.0.0.1", port);
			// 出力用のストリームを取得
			os = socket.getOutputStream();
			Scanner scan = new Scanner(System.in);
			while (true) {
				String str = scan.nextLine();
				os.write(str.getBytes());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 接続を閉じる
			socket.close();
			os.close();
		}
	}
}
