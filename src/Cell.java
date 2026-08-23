import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
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

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if(!isModifiable) {
                    return;
                }
                boolean isLeftPressed = (e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0;
                boolean isRightPressed = (e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0;

                if(isLeftPressed) {
                    isAlive = true;
                } if (isRightPressed) {
                    isAlive = false;
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!isModifiable) {
                    return;
                }
                boolean isLeftPressed = (e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0;
                boolean isRightPressed = (e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0;
                if (isLeftPressed) {
                    isAlive = true;
                    repaint();
                } else if(isRightPressed) {
                    isAlive = false;
                    repaint();
                }
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
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
