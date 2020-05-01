import java.io.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class todolist
{
    String data;
}

public class ToDoListGUI extends Applet implements ActionListener, Runnable{

    Graphics g;
    Graphics2D g2;
    Applet app;
    Image img_home[];
    Button ok;
    TextField tf1;
    Thread th;
    //int repetition = 0;
    
    int listnum_max = 100;
    int i;
    int windowsize_x = 1366, windowsize_y = 768;
    int page = 0, nightmode = 0, todo_add = 0, todo_complete = 0, complete_number = -1;
    int class_number = 0;
    todolist[] list = new todolist[listnum_max];
    
    int setting_icon_x, setting_icon_y, setting_icon_length;
    int night_icon_x, night_icon_y, night_icon_length;
    
    
    public void init() {
        
        //各要素ごとにインスタンス化
        for(int i = 0; i < list.length; i++){
            list[i] = new todolist();
        }
/*        for(i = 0; i < listnum_max; i++){
            list[i] = new todolist();
        }*/
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
            String str1;
            int count = 0;
            while((str1 = br.readLine()) != null){
                list[count].data = str1;
                count++;
            }
            br.close();
            class_number = count;
        }
        catch(IOException e){
            System.out.println("記録がありません");
        }
        
        app = this;
        app.setSize(windowsize_x, windowsize_y);
        g = app.getGraphics();
        g2 = (Graphics2D) g;
        ok = new Button("OK");
        tf1 = new TextField(30);
        //setLayout(new GridLayout(3,1,10,10));
        add(tf1);
        add(ok);
        tf1.addActionListener(this);
        ok.addActionListener(this);
        
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
                    for(i = 0; i < windowsize_y / 60; i++){
                        if(i < class_number){
                            if(25 <= e.getX() && e.getX() <= 25 + 50 && night_icon_length + 20 + (i * 60) <= e.getY() && e.getY() <= night_icon_length + 20 + (i * 60) + 50){
                                todo_complete = 1;
                                complete_number = i;
                                repaint();
                            } 
                        } else{
                        
                        } 
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
        th = new Thread(this);
        th.start();
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
            g.drawLine(0, night_icon_length + 10, windowsize_x, night_icon_length + 10);
            for(i = 0; i < windowsize_y / 60; i++){
                    
                if(i < class_number){
                    g.drawLine(0, night_icon_length + 10 + ((i + 1) * 60), windowsize_x, night_icon_length + 10 + ((i + 1) * 60));
                    if(complete_number == i){
                        g.drawImage(img_home[8], 25, night_icon_length + 20 + (i * 60), app);
                    }else{
                        g.drawImage(img_home[7], 25, night_icon_length + 20 + (i * 60), app);
                    }
                    g.drawString(list[i].data, 80, night_icon_length + 50 + (i * 60));
                } else{
                        
                } 
            }
        try{
            PrintWriter pw = new PrintWriter (new BufferedWriter(new FileWriter("todolist.txt")));
            for(i = 0; i < class_number; i++){
                 pw.println(list[i].data); 
            }
            pw.close();
        }
        catch(IOException e){
            System.out.println("入出力エラー");
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
    public void actionPerformed(ActionEvent e){
        String str = tf1.getText();
        list[class_number].data = str;
        class_number +=1;
        tf1.setText("");
        repaint();
    }
    
    @Override
    public void run(){
        while(true){
            //this.repetition++;
            //repaint();
            if(todo_complete == 1){
                try{
                    todo_complete = 0;
                    Thread.sleep(500);
                    for(i = complete_number; i <= class_number; i++){
                        list[i].data = list[i + 1].data;
                    }
                    complete_number = -1;
                    class_number -= 1;
                    repaint();
                } catch (InterruptedException ex) {
                 Logger.getLogger(ToDoListGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                
            }
            try {
                //条件外の時、高速ループしないように
                Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ToDoListGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
               
        }
    }
}