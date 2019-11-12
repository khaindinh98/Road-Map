//	import java.io.*;
//	public class Map{
//		public void paintComponent(Graphics g) {
//			try {
//				//graph = new ShortestPath("input/test.txt");
//				File f = new File("input/abc/");
//				String[] files = f.list();
//				int k = 0;
//				images = new String[row][col];
//				for (int i = 0; i < col; i++) {
//					for (int j = 0; j < row; j++) {
//						String t = "input/abc/" + files[k];
//						images[j][i] = t;
//						k += 1;
//						//System.out.println()
//					}
//				}
//				g.setColor(Color.white); //colors the window
//				g.fillRect(0, 0, getWidth(), getHeight());
//				int colframe = 8;
//				int rowframe = 5;
//				System.out.println("8" + this.x + " " + this.y);
//
//				int xframe = Math.abs(x);
//				int yframe = Math.abs(y);
//				System.out.println("9" + xframe + " " + yframe);
//				int t = 0, k = 0;
//				for (t = 0; t <= widthImage; t += 256) {
//					if (t >= xframe) {
//						break;
//					}
//				}
//				for (k = 0; k <= heightImage; k += 256) {
//					if (k >= yframe) {
//						break;
//					}
//				}
//				System.out.println("10" + t + " " + k);
//
//				drawFrame(g, colframe, rowframe, t - 256, k - 256);
//			}
//			} catch (Exception e) {
//				System.out.println("Fail");
//			}
//		}
//		public void drawFrame(Graphics g, int colFrame, int rowFrame, int xframe, int yframe) {
//			System.out.println("6" + xframe + " " + yframe);
//			for (int i = (xframe / 256) - extend; i < (xframe / 256) + colFrame + extend; i += 1) {
//				for (int j = (yframe / 256) - extend; j < (yframe / 256) + rowFrame + extend; j += 1) {
//					if (i >= 0 && i < col && j >= 0 && j < row) {
//						Image image = new ImageIcon(images[j][i]).getImage();
//						g.setColor(Color.red);
//						g.drawImage(image, i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale, null);
//						//g.drawRect(i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale);
//						System.out.println("7" + i + " " + j);
//					}
//				}
//
//			}
//		}
//	}
