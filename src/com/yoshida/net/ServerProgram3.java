package com.yoshida.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProgram3 implements Runnable {
	static ArrayList<Socket> list = new ArrayList<Socket>();

	public static void main(String[] args) {
		Socket socket;
		var obj = new ServerProgram3();
		try {
			// 受け入れポートの設定
			ServerSocket serverSocket = new ServerSocket(1234);
			while (true) {

				// 接続待ち（プログラムはブロックされる）
				socket = serverSocket.accept();
				// listにソケットを追加する
				list.add(socket);
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
		InputStream io = null;
		Socket socket = null;
		// 接続がされたのでデータ入力ストリームを取得
		try {
			socket = list.get(list.size()-1);
			io = socket.getInputStream();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		// クローズされるまで繰り返す
		while (true) {
			try {
				// データを読み込むための領域を確保
				byte[] buffer = new byte[1024];
				int len;
				// データを読み込む
				while ((len = io.read(buffer)) != -1) {
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
				if(len == -1) {
					list.remove(socket);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
