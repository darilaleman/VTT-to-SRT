/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtt.to.srt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Daril
 */
public class Subtitle {

    public void translate(File[] filenames) throws IOException {
        if (filenames.length == 0) {
            System.out.println("No subtitles found");
            System.exit(0);
        } else {
            System.out.println("Found: " + filenames.length + " subtitles");
            for (File file : filenames) {
                String text = "";
                String aux = "";
                FileReader fileReader = new FileReader(file);
                try (BufferedReader lee = new BufferedReader(fileReader)) {
                    while ((aux = lee.readLine()) != null) {
                        text += aux + "\n";
                    }
                }
                String removeFirst = removeFirst(text);
                String finalSubtitle = vttToSrt(removeFirst);
                File finalFile = new File(FilenameUtils.removeExtension(file.getAbsolutePath()));
                finalFile = new File(finalFile.getCanonicalPath() + ".srt");
                FileUtils.writeStringToFile(finalFile, finalSubtitle);
                System.out.println("Done!!!");

            }

        }

    }

    public void translate(File fileName) throws IOException {
        System.out.println("--------------------------------------------");
        System.out.println("Subtitle found");
        System.out.println(fileName.getAbsolutePath());
        String text = "";
        String aux = "";
        FileReader fileReader = new FileReader(fileName);
        try (BufferedReader lee = new BufferedReader(fileReader)) {
            while ((aux = lee.readLine()) != null) {
                text += aux + "\n";
            }
        }
        String removeFirst = removeFirst(text);
        System.out.println("Processing file...");
        String finalSubtitle = vttToSrt(removeFirst);
        File finalFile = new File(FilenameUtils.removeExtension(fileName.getAbsolutePath()));
        System.out.println("Almost finishing...");
        finalFile = new File(finalFile.getCanonicalPath() + ".srt");
        FileUtils.writeStringToFile(finalFile, finalSubtitle);
        System.out.println("Done!!!");

    }

    public String removeFirst(String text) {
        int pos = -1;
        Pattern patt = Pattern.compile("^[0-9]{2}:[0-9]{2}.[0-9]{3}");
        try (Scanner scanner = new Scanner(text)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher m = patt.matcher(line);
                if (m.find()) {
                    pos = text.indexOf(line);
                    break;
                }

            }
            if (pos != -1) {
                text = text.substring(pos);
            }
        }
        return text;
    }

    public String vttToSrt(String texto) {
        String subtitle = "";
        int count = 1;
        String lastString = "";
        Pattern patt = Pattern.compile("^[0-9]{2}:[0-9]{2}.[0-9]{3}");
        Pattern patt2 = Pattern.compile("/^$/");
        try (Scanner scanner = new Scanner(texto)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher m = patt.matcher(line);
                Matcher m2 = patt2.matcher(line);
                if (m.find()) {
                    lastString = line;
                    line = line.replace('.', ',');
                    line = "00:" + line;
                    line = line.replaceAll("> *", "> 00:");
                    subtitle += count + "\n";
                    subtitle += line + "\n";
                    count++;
                } else {
                    if (m2.find()) {
                        subtitle += line + "\n";
                    } else {
                        subtitle += line + "\n";
                    }
                }
            }

        }

        return subtitle;
    }

}
