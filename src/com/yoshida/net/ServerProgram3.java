package com.yoshida.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProgram3 implements Runnable {
	Socket socket;
	InputStream io;
	static ArrayList<Socket> list = new ArrayList<Socket>();

	public static void main(String[] args) {
		try {
			// 受け入れポートの設定
			ServerSocket serverSocket = new ServerSocket(1234);
			while (true) {
				var obj = new ServerProgram3();
				// 接続待ち（プログラムはブロックされる）
				obj.socket = serverSocket.accept();
				// 接続がされたのでデータ入力ストリームを取得
				obj.io = obj.socket.getInputStream();
				// listにソケットを追加する
				list.add(obj.socket);
				// スレッドを開始する
				var thread = new Thread(obj);
				thread.start(); 

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// クローズされるまで繰り返す
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
					//長さを指定して出力する
					System.out.println(new String(buffer,0,len));
					// 接続しているソケット全体に送り返す
					for(var output: list) {
						OutputStream os = output.getOutputStream();
						// 長さを指定してソケットストリームに出力する。
						os.write(buffer,0,len);
					}
				}
				// データの受信が完了したら接続を閉じる
				this.io.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				list.remove(this.socket);
				break;
			}
		}
	}

}
