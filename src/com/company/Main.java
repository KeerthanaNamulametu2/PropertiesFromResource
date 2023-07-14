package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
         Properties defaultProp=new Properties();
         try(InputStream inputStream=Main.class.getResourceAsStream("MyDefaultValues.xml")) {
             defaultProp.loadFromXML(inputStream);
         }
         Properties userProps=new Properties(defaultProp);
         loadUserProps(userProps);
         String welcomemsg=userProps.getProperty("welcomeMessage");
         String farewellmsg=userProps.getProperty("farewellMessage");
         System.out.println(welcomemsg);
         System.out.println(farewellmsg );

      if(userProps.getProperty("isFirstRun").equalsIgnoreCase("Y")){
          userProps.setProperty("welcomeMessage","Welcome back");
          userProps.setProperty("farewellMessage","Things will be familiar now");
          userProps.setProperty("isFirstRun","N");
          saveUserProps(userProps);
      }




        } catch (IOException e) {
            System.out.println("Exception <" + e.getClass().getSimpleName() + "> " + e.getMessage());
        }
    }

    private static Properties loadUserProps(Properties userProps) throws IOException{
        Path userFile = Paths.get("userValues.xml");
        if(Files.exists(userFile)) {
            try(InputStream inputStream = Files.newInputStream(userFile)) {
                userProps.loadFromXML(inputStream);
            }
        }

        return userProps;
    }

    private static void saveUserProps(Properties userProps) throws IOException {
        try(OutputStream outputStream = Files.newOutputStream(Paths.get("userValues.xml"))) {
            userProps.storeToXML(outputStream, null);
        }
    }
}
