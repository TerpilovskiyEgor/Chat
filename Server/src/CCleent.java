import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

class CCleent extends Thread
{
    private long ID;
    Socket CSocket;
    DataInputStream IStream;
    DataOutputStream OStream;

    public CCleent(Socket CSocket)
    {
        this.ID = new Random().nextLong();
        if(ID < 0) {ID *= -1;}

        this.CSocket = CSocket;
        try
        {
            IStream = new DataInputStream(this.CSocket.getInputStream());
            OStream = new DataOutputStream(this.CSocket.getOutputStream());
            this.start();
        }
        catch (Exception E) { System.out.println("<<ERROR>> Ошибка при создании потоков ввода/вывода"); }
    }

    @Override
    public void run()
    {
        boolean LoopFlag = true;

        String Text;
        while(LoopFlag)
        {
            try
            {
                Text = IStream.readUTF();

                for (CCleent Client: ChatServer.ClientsOnline)
                {
                    if (!this.equals(Client))
                    {
                        Client.Send(Text);
                    }
                }
            }
            catch (Exception E)
            {
                System.out.println("<<ERROR>> Имя потока: " + Thread.currentThread().getName() + " Ошибка получения данных");
                System.out.println("<<ERROR>> Имя потока: " + Thread.currentThread().getName() + " Отключение пользователя");
                for (int i = 0; i < ChatServer.ClientsOnline.size(); i++)
                {
                    if (this.equals(ChatServer.ClientsOnline.get(i)))
                    {
                        ChatServer.ClientsOnline.remove(ChatServer.ClientsOnline.get(i));
                        LoopFlag = false;
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        CCleent O = (CCleent) obj;
        return (this.ID == O.getID());
    }

    public void Send(String Text)
    {
        try
        {
            OStream.writeUTF(Text);
            OStream.flush();
        }
        catch (Exception E) { System.out.println("<<ERROR>> Имя потока: " + Thread.currentThread().getName() + " Ошибка отправки данных"); }
    }

    public long getID()
    {
        return ID;
    }
}