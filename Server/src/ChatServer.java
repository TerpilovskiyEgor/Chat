import java.net.*;
import java.util.*;

public class ChatServer
{
    public static List <CCleent> ClientsOnline = new ArrayList<>();
    private int Port = 1555;
    private ServerSocket SSoket;

    public ChatServer()
    {
        try
        {
            SSoket = new ServerSocket(Port);
        }
        catch (Exception E) { System.out.println("<<ERROR>> Ошибка при создании сокета сервера"); }
    }

    public void CustomerExpectation()
    {
        while(true)
        {
            System.out.println("Ожидаются подключения");
            try
            {
                Socket ClentSocket = SSoket.accept();
                ClientsOnline.add(new CCleent(ClentSocket));
            }
            catch (Exception E) { System.out.println("<<ERROR>> Ошибка при подключении пользователя"); }
        }
    }
}