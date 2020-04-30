import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

class todolist
{
    int view_number;
    char [] data = new char[100];
}

public class ToDoListGUI extends Applet {

    Graphics g;
    Graphics2D g2;
    Applet app;
    Image img_home[];
    
    int listnum_max = 100;
    int i;
    int windowsize_x = 1366, windowsize_y = 768;
    int page = 0, nightmode = 0, todo_add = 0;
    int class_number = 0;
    
    int setting_icon_x, setting_icon_y, setting_icon_length;
    int night_icon_x, night_icon_y, night_icon_length;
    
    
    public void init() {
        todolist[] list = new todolist[listnum_max];
        //各要素ごとにインスタンス化
        for(int i = 0; i < list.length; i++){
            list[i] = new todolist();
        }
        
        //ファイル読み込み
        
        app = this;
        app.setSize(windowsize_x, windowsize_y);
        g = app.getGraphics();
        g2 = (Graphics2D) g;
        
        setting_icon_x = windowsize_x - 50;
        setting_icon_y = 0;
        setting_icon_length = 50;
        night_icon_x = windowsize_x - 110;
        night_icon_y = 0;
        night_icon_length = 50;
        
        //画像読み込み
        int img_homenumber = 9;
        img_home = new Image[img_homenumber];
        for (int i = 0; i < img_homenumber; i++) {
            img_home[i] = app.getImage(app.getCodeBase(), "images/Home" + i + ".png");
        }
        //グラフィクスの読み込みを遅滞なく行わせるため
        Image pr = createImage(500,500);
        for (int i = 0; i < img_homenumber; i++) {
        Graphics gpr = pr.getGraphics();
        gpr.drawImage(img_home[i], 0, 0, app);
        }
        
        this.addMouseListener(
                new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(page == 0){
                    if(setting_icon_x <= e.getX() && e.getX() <= windowsize_x && setting_icon_y <= e.getY() && e.getY() <= setting_icon_y + setting_icon_length){
                        page = 1;
                        repaint();
                    }
                    if(night_icon_x <= e.getX() && e.getX() <= night_icon_x + night_icon_length && night_icon_y <= e.getY() && e.getY() <= night_icon_y + night_icon_length){
                        if(nightmode == 0) nightmode = 1;
                        else if(nightmode == 1) nightmode = 0;
                        repaint();
                    }
                    if(windowsize_x - 170 <= e.getX() && e.getX() <= windowsize_x - 120 && 0 <= e.getY() && e.getY() <= 50){
                        if(todo_add == 0) todo_add = 1;
                        else todo_add = 0;
                        repaint();
                    }
                }
                else if(page == 1){
                    if(setting_icon_x <= e.getX() && e.getX() <= windowsize_x && setting_icon_y <= e.getY() && e.getY() <= setting_icon_y + setting_icon_length){
                        page = 0;
                        repaint();
                    }
                    if(night_icon_x <= e.getX() && e.getX() <= night_icon_x + night_icon_length && night_icon_y <= e.getY() && e.getY() <= night_icon_y + night_icon_length){
                        if(nightmode == 0) nightmode = 1;
                        else if(nightmode == 1) nightmode = 0;
                        repaint();
                    }
                }
            }        
        });
    }
    public void paint(Graphics g) {
        if(page == 0){
            g.drawImage(img_home[0], setting_icon_x, setting_icon_y, app);
                if(nightmode == 0){
                    g.drawImage(img_home[5], night_icon_x, night_icon_y, app);
                    app.setBackground(Color.white);
                    Font f = new Font("Serif", Font.BOLD, 25);
                    g.setFont(f);
                    g.setColor(Color.BLACK);
                }
                else if(nightmode == 1){
                    g.drawImage(img_home[6], night_icon_x, night_icon_y, app);
                    app.setBackground(new Color(50, 50, 50));
                    Font f = new Font("Serif", Font.BOLD, 25);
                    g.setFont(f);
                    g.setColor(Color.WHITE);  
                }
                g.drawString("ToDoList",15, 30);
                if(todo_add == 0){
                    g.drawImage(img_home[7], windowsize_x - 170, 0, app);
                    g.drawString("(タスクを追加)", windowsize_x - 350, 38);
                } else{
                    g.drawImage(img_home[8], windowsize_x - 170, 0, app);
                }
                for(i = 0; i < windowsize_y / 60; i++){
                    g.drawLine(0, night_icon_length + 10 + (i * 60), windowsize_x, night_icon_length + 10 + (i * 60));
                }
                
        }
        else if (page == 1){
            g.drawImage(img_home[4], setting_icon_x, setting_icon_y, app);
            if(nightmode == 0){
                g.drawImage(img_home[5], night_icon_x, night_icon_y, app);
                app.setBackground(Color.white);
                Font f = new Font("Serif", Font.BOLD, 25);
                g.setFont(f);
                g.setColor(Color.BLACK);
                g.drawString("ToDoList - 設定",15, 30);
            }
            else if(nightmode == 1){
                g.drawImage(img_home[6], night_icon_x, night_icon_y, app);
                app.setBackground(new Color(50, 50, 50));
                Font f = new Font("Serif", Font.BOLD, 25);
                g.setFont(f);
                g.setColor(Color.WHITE);
                g.drawString("ToDoList - 設定",15, 30);
            }
            
        } 
    }
}
