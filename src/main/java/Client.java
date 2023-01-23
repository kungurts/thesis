import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8989;
        Scanner sc = new Scanner(System.in);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("введи искомое слово:");
            String word = sc.nextLine();
            out.println(word);

            String answer = in.readLine();
            String[] answerSplit = answer.split(",");

            for (String a : answerSplit) {
                System.out.println(a);
            }
        }
    }
}
