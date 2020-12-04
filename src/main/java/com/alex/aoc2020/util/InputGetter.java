package com.alex.aoc2020.util;

import java.io.*;
import java.net.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputGetter {
    private static final HttpClient client = HttpClient.newBuilder().build();


    public static String getInput(int day, String rootPath) throws IOException, InterruptedException {
        String session = getSession();
        String urlStr = String.format("https://adventofcode.com/2020/day/%d/input", day);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .header("Cookie", String.format("session=%s", session))
                .build();

        rootPath = String.format("%s/%d", rootPath, day);
        generateDirectoryStructure(rootPath);

        String fileoutName = String.format("%s/input.txt", rootPath);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        writeFile(fileoutName, response.body());

        return response.body();

    }

    public static void generateDirectoryStructure(String rootPath) {
        System.out.println(String.format("Generating directory %s", rootPath));
        File directory = new File(rootPath);

        if (! directory.exists()){
            directory.mkdirs();
        }
    }

    private static void writeFile(String PATH, String content){
        try {
            FileWriter myWriter = new FileWriter(PATH);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String getSession() throws IOException {

        InputStream sessionInStream = InputGetter.class.getResourceAsStream("/token.secret");

        assert sessionInStream != null;
        InputStreamReader isReader = new InputStreamReader(sessionInStream);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuilder sb = new StringBuilder();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        return sb.toString();
    }

    public List<String> getInputAsList(int day, String PATH, String splitStr) {
        String[] inputRaw = {};
        try {
            inputRaw = getInput(day, PATH).split(splitStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(inputRaw);
    }

    public List<String> getInputAsList(int day, String PATH) {
        return getInputAsList(day, PATH, "\n");
    }

    public ArrayList<Integer> getInputAsIntArrayList(int day, String PATH, String splitStr){
        ArrayList<Integer> inputInt = new ArrayList<>();
        try {
            String[] inputRaw = getInput(day, PATH).split(splitStr);
            for (String s : inputRaw) {
                inputInt.add(Integer.parseInt(s));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inputInt;
    }

    public ArrayList<Integer> getInputAsIntArrayList(int day, String PATH){
        return getInputAsIntArrayList(day, PATH, "\n");
    }


//    public static void main(String[] args) {
//        try {
//            ArrayList<Integer> s = getInputAsIntArrayList(1, "/Users/ADR/Documents/AOC2020/src/main/java/com/alex/aoc2020/inputs");
//            System.out.println(s);
//
//        }
//        catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }


}
