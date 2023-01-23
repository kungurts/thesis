import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8989;

        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(port, engine);
        server.start();

    }
}