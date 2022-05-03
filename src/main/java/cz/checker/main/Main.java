package cz.checker.main;

import org.apache.commons.io.FileUtils;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static boolean isEqual(File output, File output1) {
        try {
            return FileUtils.contentEquals(output, output1);
        } catch (IOException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public static void main(String[] arg) throws IOException {

        Scanner sc = new Scanner(System.in, "UTF-8");
        System.out.println("Zadej adresu url: ");
        String urlvs = sc.nextLine();

        URL url = new URL(urlvs);
        URLConnection con = url.openConnection();
        Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
        Matcher m = p.matcher(con.getContentType());

        String charset = m.matches() ? m.group(1) : "UTF-8";
        Reader r = new InputStreamReader(con.getInputStream(), charset);
        StringBuilder buf = new StringBuilder();

        while (true) {
            int ch = r.read();
            if (ch < 0)
                break;
            buf.append((char) ch);
        }
        String str = buf.toString();

        File output = new File("htmlold.txt");
        FileWriter writer = new FileWriter(output);

        writer.write(str);
        writer.flush();
        writer.close();

        System.out.println("Stránka zapsána!");
        System.out.println(" ");
        System.out.println("Po kolika sekundách kontrolovat stránku?");
        int cas = Integer.parseInt(sc.nextLine());

        try {
            while (true) {
                System.out.println(new Date());
                Thread.sleep(cas * 1000);

                URL url2 = new URL(urlvs);
                URLConnection con2 = url2.openConnection();
                Pattern p2 = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
                Matcher m2 = p2.matcher(con.getContentType());

                String charset2 = m2.matches() ? m2.group(1) : "UTF-8";
                Reader r2 = new InputStreamReader(con2.getInputStream(), charset);
                StringBuilder buf2 = new StringBuilder();

                while (true) {
                    int ch = r2.read();
                    if (ch < 0)
                        break;
                    buf2.append((char) ch);
                }
                String str2 = buf2.toString();


                File output1 = new File("htmlnew.txt");
                FileWriter writer1 = new FileWriter(output1);

                writer1.write(str2);
                writer1.flush();
                writer1.close();

                boolean equal = isEqual(output, output1);

                if (equal) {
                    System.out.println("Žádná změna.");
                    continue;

                } else {
                    try {
                        System.out.println("Stránka změněna!");
                        File yourFile = new File("/Volumes/MacDisk2 1/IntelliJ/git/page_checker/beep.wav");
                        AudioInputStream stream;
                        AudioFormat format;
                        DataLine.Info info;
                        Clip clip;

                        stream = AudioSystem.getAudioInputStream(yourFile);
                        format = stream.getFormat();
                        info = new DataLine.Info(Clip.class, format);
                        clip = (Clip) AudioSystem.getLine(info);
                        clip.open(stream);
                        clip.start();
                        Thread.sleep(1000);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
