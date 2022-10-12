
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class TimSoOffline extends JFrame implements MouseListener {
	public static void main(String[] args) {
		new TimSoOffline();
	}
	int n = 5;
	int s = 100;
	int os = 50;
	int matran[][] = new int[n][n];
	Random rand = new Random();
	List<Point> dadanh = new ArrayList<Point>();

	public TimSoOffline() {
		this.setSize(n * s + 2 * os, n * s + 2 * os);
		this.setTitle("Caro");
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
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
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.YELLOW);
		for (int i = 0; i < dadanh.size(); i++) {
			int ix = dadanh.get(i).x;
			int iy = dadanh.get(i).y;
			int x = os + ix * s;
			int y = os + iy * s;
			g.fillRect(x, y, s, s);;
		}
		
		g.setColor(Color.BLACK);
		for (int i = 0; i <= n; i++) {
			g.drawLine(os, os + i * s, os + n * s, os + i * s);
			g.drawLine(os + i * s, os, os + i * s, os + n * s);
		}
		g.setFont(new Font("arial", Font.BOLD, s/3));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				String str = matran[i][j]+"";
				if (matran[i][j]<10) str = "00" + str;
				else if (matran[i][j]<100) str = "0" + str;
				int x = os + i * s + s / 2 - s / 4;
				int y = os + j * s + s / 2 + s / 4 - s/8;
				g.drawString(str, x, y);
			}
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
		if (matran[ix][iy]!=dadanh.size()+1) return;
		dadanh.add(new Point(ix, iy));
		this.repaint();
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
