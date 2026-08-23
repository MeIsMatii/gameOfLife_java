import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private boolean isAlive = false;
    private boolean isModifiable = true;

    public void setModifiable(boolean isModifiable) {
        this.isModifiable = isModifiable;
        repaint();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        repaint();
    }

    public Cell() {
        setPreferredSize(new Dimension(60,60));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!isModifiable) {
                    return;
                }
                isAlive = !isAlive;
                repaint();
            }
        });
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isModifiable && isAlive) {
            g.setColor(Color.BLUE);
        } else if(isModifiable && !isAlive) {
            g.setColor(Color.GRAY);
        }
        else if(isAlive) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(0,0,getWidth(),getHeight());
    }
}
