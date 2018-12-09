import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st = null;
    public static int hour;
    public static int min;
    private static final int REFILL_MIN = 15;
    private static final int EARLY_MIN = 45;

    public static void main(String[] args) throws IOException {
        input();
        solve();
        close();
    }

    private static void solve() throws IOException {
        if (min - EARLY_MIN >= 0) {
            min -= EARLY_MIN;
        } else {
            min += REFILL_MIN;
            hour--;
        }

        if (hour < 0)
            hour = 23;

        bw.write(hour + " " + min);
    }

    private static void input() throws IOException {
        st = getStringTokenizer();
        hour = Integer.parseInt(st.nextToken());
        min = Integer.parseInt(st.nextToken());
    }

    private static StringTokenizer getStringTokenizer() throws IOException {
        return new StringTokenizer(br.readLine(), " ");
    }

    private static void close() throws IOException {
        bw.close();
        br.close();
    }
}
