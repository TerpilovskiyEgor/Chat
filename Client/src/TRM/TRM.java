package TRM;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class TRM
{
    private static int Port = 1555;
    private static String IPAgres = "127.0.0.1";
    private static Socket MySocket;
    private static DataOutputStream OStream;
    private static DataInputStream IStream;

    public static void Connect()
    {
        try
        {
            MySocket = new Socket(InetAddress.getByName(IPAgres), Port);
            IStream = new DataInputStream(MySocket.getInputStream());
            OStream = new DataOutputStream(MySocket.getOutputStream());
        }
        catch (Exception E)
        {
            JOptionPane.showMessageDialog(null, "Соединение не удалось");
            System.exit(1);
        }
    }

    public static void Send(String Name, String Text)
    {
        try
        {
            OStream.writeUTF(Name + "\n" + Text);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Отправка не удалась");
        }
    }

    public static void ListenANetwork()
    {
        String Text;
        while(true)
        {
            try
            {
                Text = IStream.readUTF();
                GUI.GUI.SetText(Text);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Проблемма с принятием данных");
            }
        }
    }
}