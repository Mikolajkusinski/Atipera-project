package com.example.Atiperaproject.service;


import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class GithubService {

    public String getRepoInfo(String name, String fileType) throws IOException {
        String http = "https://api.github.com/users/"+name+"/repos";
        String acceptHeader = "Accept: application/"+fileType;
        if(!fileType.equals("json")){
            JSONObject wrongFileTypeMessage = new JSONObject();
            wrongFileTypeMessage.put("status:","406");
            wrongFileTypeMessage.put("message","Nothing but JSON wouldn't be accepted!");
            return wrongFileTypeMessage.toString();

        }
        ProcessBuilder commands = new ProcessBuilder("curl", "--request",  "GET", "--url", http, "--header", acceptHeader ,"--header", "Authorization: Bearer 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");
        Process process = commands.start();

        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null){
            if(line.contains("Not Found")){
                JSONObject userNotFoundMessage = new JSONObject();
                userNotFoundMessage.put("status:","404");
                userNotFoundMessage.put("message","User not found!");
                return userNotFoundMessage.toString();
            }
            if(line.contains("full_name") || line.contains("login")) {
                int index1 = line.indexOf("/")+1;
                int index2 = line.indexOf(",")-1;
                String subline = line.substring(index1,index2);
                response.append("<br><br>").append(line);

                getBranchInfo(name,fileType,response,subline);

            }
        }

        process.destroy();




        return response.toString();
    }

    public StringBuilder getBranchInfo(String name, String acceptHeader, StringBuilder response, String subline) throws IOException {

        String http2 = "https://api.github.com/repos/"+name+"/"+subline+"/branches";
        ProcessBuilder commands = new ProcessBuilder("curl", "--request",  "GET", "--url", http2, "--header", acceptHeader,"--header", "Authorization: Bearer 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");

        Process process = commands.start();

        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null){
            if(line.contains("name") || line.contains("sha")){
                response.append("<br>").append(line);
            }
        }
        process.destroy();


        return response;
    }
}
