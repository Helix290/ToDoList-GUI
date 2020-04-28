import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;


public class ToDoListGUI extends Applet {

    Graphics g;
    Graphics2D g2;
    Applet app;
    Image img_home[];
    public void init() {
        app = this;
        //windows size 変数にして変更できるようにする
        app.setSize(1366, 768);
        g = app.getGraphics();
        g2 = (Graphics2D) g;
        //画像読み込み
        int img_homenumber = 4;
        img_home = new Image[img_homenumber];
        for (int i = 1; i <= img_homenumber; i++) {
            img_home[i - 1] = app.getImage(app.getCodeBase(), "images/Home" + i + ".png");
        }
        //グラフィクスの読み込みを遅滞なく行わせるため
        Image pr = createImage(500,500);
        for (int i = 1; i < img_homenumber; i++) {
        Graphics gpr = pr.getGraphics();
        gpr.drawImage(img_home[i], 0, 0, app);
        }
        this.addMouseListener(
                new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //クリック時の動作
            }        
        });
    }
    public void paint(Graphics g) {
        Font f = new Font("Serif", Font.BOLD, 12);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("ホーム画面",50, 50);
    }
}
