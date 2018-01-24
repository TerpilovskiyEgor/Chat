package GUI;

import TRM.TRM;
import javax.swing.*;

public class GUI extends Thread
{
    private static JFrame Frame;
    private static JButton ButtonSend;
    private static JTextArea TextAInput, TextAUotput;
    private static JMenuBar MenuB;



    public static String NickName;

    public GUI()
    {
        Frame = new JFrame("Client");
        Initialization();
        NickName = JOptionPane.showInputDialog("Введие ваш ник");
        TRM.Connect();
        Frame.setVisible(true);
        this.start();
    }
    private static void Initialization()
    {
        //Initialization frame
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLocationRelativeTo(null);
        Frame.setLayout(null);
        Frame.setResizable(false);
        Frame.setSize(400,600);

        //Initialization TextAUotput
        TextAUotput = new JTextArea();
        TextAUotput.setLineWrap(true);
        TextAUotput.setWrapStyleWord(true);
        JScrollPane SB_TextAUotput = new JScrollPane(TextAUotput);
        SB_TextAUotput.setSize(360,420);
        SB_TextAUotput.setLocation(10,10);
        Frame.add(SB_TextAUotput);

        //Initialization TextAInput
        TextAInput = new JTextArea();
        TextAInput.setLineWrap(true);
        JScrollPane SB_TextAInput = new JScrollPane(TextAInput);
        SB_TextAInput.setSize(280, 100);
        SB_TextAInput.setLocation(10,445);
        Frame.add(SB_TextAInput);

        //Initialization ButtonSend
        ButtonSend = new JButton("Отправить");
        ButtonSend.setSize(75,75);
        ButtonSend.setLocation(300,460);
        ButtonSend.addActionListener(e -> {if(e.getSource() == ButtonSend) {ButtonSend_Clic();}});
        Frame.add(ButtonSend);

        //Initialization MenuBar
        MenuB = new JMenuBar();
        MenuB.add(new JMenu("Подключится"));
        MenuB.add(new JMenu("Отключится"));
        Frame.setJMenuBar(MenuB);
    }

    private static void ButtonSend_Clic()
    {
        TRM.Send(NickName, TextAInput.getText() + "\n\n");
        TextAUotput.append(NickName + "\n" + TextAInput.getText() + "\n\n");
        TextAInput.setText("");
    }

    public static void SetText(String Text)
    {
        TextAUotput.append(Text);
    }

    @Override
    public void run()
    {
        TRM.ListenANetwork();
    }
}