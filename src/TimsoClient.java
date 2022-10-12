
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class TimsoClient extends JFrame implements MouseListener, Runnable {

	public static void main(String[] args) {
		new TimsoClient();
	}

	int n = 5;
	int s = 100;
	int os = 50;
	DataInputStream dis;
	DataOutputStream dos;
	int matran[][] = new int[n][n];
	List<Point> dadanh = new ArrayList<Point>();
	List<Integer> playerClicks = new ArrayList<>();
	Color[] colors = {Color.GREEN, Color.BLUE, Color.CYAN, Color.RED, Color.PINK};
	int player;

	public TimsoClient() {
		this.setSize(n * s + 2 * os, n * s + 2 * os);
		this.setTitle("Caro");
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);

		try {
			Socket soc = new Socket("localhost", 5050);
			dis = new DataInputStream(soc.getInputStream());
			dos = new DataOutputStream(soc.getOutputStream());
		} catch (Exception e) {

		}
		new Thread(this).start();
		this.setVisible(true);
	}
    //day la comment cho vui cua nhat hoang
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (int i = 0; i < dadanh.size(); i++) {
			int ix = dadanh.get(i).x;
			int iy = dadanh.get(i).y;
			int x = os + ix * s;
			int y = os + iy * s;
			g.setColor(colors[playerClicks.get(i)-1]);
			g.fillRect(x, y, s, s);
		}

		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, os + i * s, os + n * s, os + i * s);
			g.drawLine(os + i * s, os, os + i * s, os + n * s);
		}
		g.setFont(new Font("arial", Font.BOLD, s / 3));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				String str = matran[i][j] + "";
				if (matran[i][j] < 10)
					str = "00" + str;
				else if (matran[i][j] < 100)
					str = "0" + str;
				int x = os + i * s + s / 2 - s / 4;
				int y = os + j * s + s / 2 + s / 4 - s / 8;
				g.drawString(str, x, y);
			}
		}
	}

	public void run() {
		try {
			while (true) {
				String s = dis.readUTF();
				System.out.println(s);
				if (s.contains("initital")) {
					String numbers = s.split(";")[1];
					player = Integer.parseInt(s.split(";")[3]);
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < n; j++) {
							matran[i][j] = Integer.parseInt(numbers.split(" ")[i * n + j]);
						}
					}
					String dadanhdau = s.split(";")[2];
					String[] arrayDaDanh = dadanhdau.split(",");
					for (int i = 1; i < arrayDaDanh.length; i++) {
						int x = Integer.parseInt(arrayDaDanh[i].split(" ")[0]);
						int y = Integer.parseInt(arrayDaDanh[i].split(" ")[1]);
						int player = Integer.parseInt(arrayDaDanh[i].split(" ")[2]);
						dadanh.add(new Point(x, y));
						playerClicks.add(player);
					}

					this.repaint();
				} 
				else {
					// s = dis.readUTF(); Loi ngu
					int x = Integer.parseInt(s.split(" ")[0]);
					int y = Integer.parseInt(s.split(" ")[1]);
					int player = Integer.parseInt(s.split(" ")[2]);
					dadanh.add(new Point(x, y));
					playerClicks.add(player);
					this.repaint();

				}
			}

		} catch (Exception e) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();
		if (x < os || x >= os + n * s)
			return;
		if (y < os || y >= os + n * s)
			return;
		int ix = (x - os) / s;
		int iy = (y - os) / s;
		for (Point d : dadanh) {
			if (ix == d.x && iy == d.y)
				return;
		}
		if (matran[ix][iy] != dadanh.size() + 1)
			return;
		// Gui toa do len server
		if(player <= 5) {
			try {
				dos.writeUTF(ix + " " + iy + " " + player);
			} catch (Exception e1) {
	
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}