package com.yoshida.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram1 {

    public static void main(String[] args) {
        try {
            // 受け入れポートの設定
            ServerSocket serverSocket = new ServerSocket(1234);
            // 接続待ち（プログラムはブロックされる）
            Socket socket = serverSocket.accept();
            // 接続がされたのでデータ入力ストリームを取得
            InputStream io = socket.getInputStream();

            ByteArrayOutputStream data = new ByteArrayOutputStream();
            // データを読み込むための領域を確保
            byte[] buffer = new byte[1024];
            int len;
            // データを読み込む
            while ((len=io.read(buffer))!=-1){
            	// 読み取ったデータの長さを出力
            	System.out.println("len = " + len);
				//長さを指定して出力する
				System.out.println(new String(buffer,0,len));
            }
            // 接続を閉じる
            io.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
