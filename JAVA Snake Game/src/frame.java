import javax.swing.*;
import java.awt.*;

public class frame extends JFrame
{
    frame()
    {
        // this is helping us to implement functionality of JFrame class
        // here we are making class panel which will will use JPanel
        this.add(new panel());

        this.setTitle("Snake Game");
        // so that user cant resize window
        this.setResizable(false);

        // this will make size of frame similar according to pc spec and OS type
        this.pack();

        this.setVisible(true);
    }

}
