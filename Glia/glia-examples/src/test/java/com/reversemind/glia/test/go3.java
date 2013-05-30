package com.reversemind.glia.test;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.URLDecoder;

/**
 * Date: 5/28/13
 * Time: 9:25 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class go3 implements Serializable {


    public static void save(String fileName, String string) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter( new File(fileName)));
        bw.write(string + "\n");
        bw.flush();
        bw.close();


    }

    public static void main(String... args) throws Exception {

        System.setProperty("http.proxyHost", "10.105.0.217");
        System.setProperty("http.proxyPort", "3128");

        System.setProperty("https.proxyHost", "10.105.0.217");
        System.setProperty("https.proxyPort", "3128");


        HttpHost proxy = new HttpHost("10.105.0.217",3128);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

        HttpGet request = new HttpGet("https://twitter.com/i/profiles/show/splix/timeline/with_replies?include_available_features=1&include_entities=1&max_id=285605679744569344");
        HttpResponse response = client.execute(request);

// Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

        String sl = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
            sl += line;
        }

        sl = new String(sl.getBytes(),"UTF-8");
        String sss = sl
                .replaceAll("\\{1,}","\\")
                .replaceAll("\\\"","'")
                .replaceAll("\\&quot;", "'")
                .replaceAll("&gt;", ">").replaceAll("&lt;","<").replaceAll("&amp;","&").replaceAll("&apos;", "'")
                .replaceAll("\u003c", "<")
                .replaceAll("\u003e",">")
                .replaceAll("\n", " ")
                .replaceAll("\\/","/")
                .replaceAll("\\'","'");

        String sss2 = sss.replaceAll("\\'","'");
        System.out.println(sss);


        save("/opt/_del/go_sl.txt", sl);
        save("/opt/_del/go_sss.txt", sss);
        save("/opt/_del/go_line.txt", line);
        save("/opt/_del/go_sss2.txt", sss2);


        System.out.println("\n\n\n\n\n");
        System.out.println(sss);
        System.out.println("\n\n\n\n\n");
        System.out.println(URLDecoder.decode(sl,"UTF-8"));
        System.out.println(URLDecoder.decode("\u0438\u043d\u043e\u0433\u0434\u0430","UTF-8"));

        System.out.println(URLDecoder.decode("\n            \u003c/span\u003e\n            \u003cb\u003e\n ", "UTF-8"));

    }

}
