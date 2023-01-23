import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private int port;
    private BooleanSearchEngine engine;

    public Server(int port, BooleanSearchEngine engine) {
        this.port = port;
        this.engine = engine;
    }

    public void start() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Starting server at " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    String word = in.readLine();
                    List<PageEntry> answer = engine.search(word);

                    List<String> answerJson =
                            answer.stream()
                            .map(pageEntry -> gson.toJson(pageEntry))
                            .collect(Collectors.toList());

                    out.println(answerJson);
                }
            }
        }
    }
}
