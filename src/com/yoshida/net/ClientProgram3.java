package com.yoshida.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientProgram3 implements Runnable {
	Socket socket = null;
	InputStream io;

	public static void main(String[] args) throws IOException {
		OutputStream os = null;
		ClientProgram3 obj = null;

		try {
			int port = 1234;
			// 接続先を指定
			obj = new ClientProgram3();
			obj.socket = new Socket("127.0.0.1", port);
			// 受信用
			obj.io = obj.socket.getInputStream();
			// 受信用スレッドの生成
			Thread thread = new Thread(obj);
			thread.start();
			// 出力用のストリームを取得
			os = obj.socket.getOutputStream();
			Scanner scan = new Scanner(System.in);
			while (true) {
				String str = scan.nextLine();
				os.write(str.getBytes());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 接続を閉じる
			obj.socket.close();
			os.close();
		}
	}

	@Override
	public void run() {
		// データ受信用スレッド
		while (true) {
			try {
				ByteArrayOutputStream data = new ByteArrayOutputStream();
				// データを読み込むための領域を確保
				byte[] buffer = new byte[1024];
				int len;
				// データを読み込む
				while ((len = this.io.read(buffer)) != -1) {
					// 読み取ったデータの長さを出力
					System.out.println("len = " + len);
					// 長さを指定して文字列化する
					System.out.println(new String(buffer,0,len));
				}
				// データの受信が完了したら接続を閉じる

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			this.io.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
