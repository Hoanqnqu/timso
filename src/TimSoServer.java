
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class TimSoServer {

	public static void main(String[] args) {
		new TimSoServer();
	}

	int n = 5;
	int matran[][] = new int[n][n];
	Random rand = new Random();
	Vector<Xuly> cls = new Vector<Xuly>();
	List<Point> dadanh = new ArrayList<Point>();
	List<Integer> playerClicks = new ArrayList<>();
	String number = "";
	int players = 0;

	public TimSoServer() {
		ServerSocket theServer;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matran[i][j] = i * n + j + 1;
			}
		}
		for (int r = 0; r < n * n; r++) {
			int i1 = rand.nextInt(n);
			int j1 = rand.nextInt(n);
			int i2 = rand.nextInt(n);
			int j2 = rand.nextInt(n);
			int tmp = matran[i1][j1];
			matran[i1][j1] = matran[i2][j2];
			matran[i2][j2] = tmp;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				number += (matran[i][j] + " ");
			}
		}

		try {
			theServer = new ServerSocket(5050);
			while (true) {
				Socket soc = theServer.accept();
				Xuly x = new Xuly(soc, this);
				cls.add(x);
				x.start();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}

class Xuly extends Thread {
	TimSoServer server;
	Socket soc;

	public Xuly(Socket soc, TimSoServer server) {
		this.soc = soc;
		this.server = server;

	}

	public void run() {

		try {
			DataInputStream dis = new DataInputStream(soc.getInputStream());
				String s = dis.readUTF();
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			
			String dadanhdau ="";
			
			for(int i=0; i<server.dadanh.size(); i++) {
				dadanhdau += server.dadanh.get(i).x + " " + server.dadanh.get(i).y + " " + server.playerClicks.get(i) + ","; 
			}

			// for (Point p : server.dadanh) {
				
			// 	dadanhdau+=(p.x + " " + p.y + " " + s)+",";
			// }
			if(s.equals("want to join")&&(server.players<5))
			{
				server.players++; 
				dos.writeUTF("initital;"+server.number+";,"+dadanhdau + ";" + server.players);
			}
			else{
				dos.writeUTF("initital;"+server.number+";,"+dadanhdau + ";" + 6);
			}

		} catch (Exception e) {
		}


		 while (true) {
			try {
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				String s = dis.readUTF();
				
				int ix = Integer.parseInt(s.split(" ")[0]);
				int iy = Integer.parseInt(s.split(" ")[1]);
				int player = Integer.parseInt(s.split(" ")[2]);
				server.dadanh.add(new Point(ix, iy));
				server.playerClicks.add(player);
				for (Xuly x : server.cls) {
					try {
						DataOutputStream dos = new DataOutputStream(x.soc.getOutputStream());
						dos.writeUTF(s);

					} catch (Exception e1) {

					}
				}
			} catch (Exception e) {

			}
		}
	}
}
