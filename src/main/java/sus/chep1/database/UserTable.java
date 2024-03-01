package sus.chep1.database;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserTable {
    private static Map<String, User> users = new HashMap<>();

    public static String toSHA256(String input)  throws IOException
    {
        try {
            if (input == null) {
                return null;
            }
            if (input.equals("")){
                return "";
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            String stringHash = new String(messageDigest);
            return stringHash;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptThisString(String input)  throws IOException
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encryptWitEcb(String filenamePlain, String filenameEnc, byte[] key) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        try (FileInputStream fis = new FileInputStream(filenamePlain);
             BufferedInputStream in = new BufferedInputStream(fis);
             FileOutputStream out = new FileOutputStream(filenameEnc);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            byte[] ibuf = new byte[1024];
            int len;
            while ((len = in.read(ibuf)) != -1) {
                byte[] obuf = cipher.update(ibuf, 0, len);
                if (obuf != null)
                    bos.write(obuf);
            }
            byte[] obuf = cipher.doFinal();
            if (obuf != null)
                bos.write(obuf);
        }
    }

    public static void decryptWithEcb(String filenameEnc, String filenameDec, byte[] key) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        try (FileInputStream in = new FileInputStream(filenameEnc);
             FileOutputStream out = new FileOutputStream(filenameDec)) {
            byte[] ibuf = new byte[1024];
            int len;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            while ((len = in.read(ibuf)) != -1) {
                byte[] obuf = cipher.update(ibuf, 0, len);
                if (obuf != null) {
                    out.write(obuf);
                }
            }
            byte[] obuf = cipher.doFinal();
            if (obuf != null)
                out.write(obuf);
        }
    }

    public static void writeFile( String folder, String path ) throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException {

        //afea1183ba729d6d5d4f3b703efa19aa

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] key1 = encryptThisString("Grisha Chepurnoy").getBytes("UTF-8");

        SecretKey secretKey = new SecretKeySpec(key1, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        File file = new File( folder );
        if ( !file.exists() ) {
            if ( file.mkdir() ) {
                System.out.println( "Directory is created!" );
            } else {
                System.out.println( "Failed to create directory!" );
            }
        }

        BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));

        String data = "";
        //String data1 = "{\nADMIN\n1\nfalse\nfalse\n}\n";

        for(Map.Entry<String, User> entry : users.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();

            data += "{\n";
            data += value.getLogin() + "\n";
            data += value.getPass() + "\n";
            data += value.getBlockStatus() + "\n";
            data += value.getPassLimitStatus() + "\n";
            data += "}\n" ;

            /*writer.write("{\n");
            writer.write(value.getLogin() + "\n");
            writer.write(value.getPass() + "\n") ;
            writer.write(value.getBlockStatus() + "\n" );
            writer.write(value.getPassLimitStatus() + "\n" );
            writer.write("}\n" );*/
        }
        byte[] result = cipher.doFinal(data.getBytes());
        String a = Base64.getEncoder().encodeToString(result);

        /*cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String a = Base64.getEncoder().encodeToString(result);
        byte[] decryptedMessageBytes = cipher.doFinal(Base64.getDecoder().decode(a));
        String c = new String(decryptedMessageBytes);*/

        writer.write(a);
        writer.flush();
        writer.close();

        //encryptWitEcb(path, folder+"\\lol.enc", key1);
    }

    public static boolean isFileEmpty(){
        String folder = "C:\\Users\\Xiaomi\\Desktop";
        String path = folder+"\\data.txt";

        BufferedReader reader = null;
        String a = "";
        boolean correct = false;

        try {
            reader = new BufferedReader
                    (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String text;

            while ( ( text = reader.readLine() ) != null ) {
                a += text;
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( reader != null ) {
                    reader.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        return (a.length() == 0);
    }

    public static String getTextFromFile(String input )  throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException{
        String folder = "C:\\Users\\Xiaomi\\Desktop";
        String path = folder+"\\data.txt";


        BufferedReader reader = null;
        boolean correct = false;
        String a = "";

        try {
            reader = new BufferedReader
                    (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String text;

            while ( ( text = reader.readLine() ) != null ) {
                a += text;
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( reader != null ) {
                    reader.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        byte[] key1 = encryptThisString(input).getBytes("UTF-8");

        SecretKey secretKey = new SecretKeySpec(key1, "AES");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] result = cipher.doFinal(Base64.getDecoder().decode(a));

        String b = new String(result);


        return b;
    }

    public static boolean readFile(String input) throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException{

        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {
            String text;

            User user = new User(null, null);
            int index = 0;

            Scanner scanner = new Scanner(getTextFromFile(input));
            while (scanner.hasNextLine()) {
                text = scanner.nextLine();
                if (text.equals("{")) {
                    index++;
                }else if(text.equals("}")){
                    index = 0;
                    if (user.getLogin() != null && user.getPass() != null){
                        users.put(user.getLogin(), user);
                    }else{
                        return false;
                    }
                    user = new User(null, null);
                    continue;
                }
                else {
                    switch (index) {
                        case 1:
                            user.setLogin(text);
                            index++;
                            break;
                        case 2:
                            user.setPass(text);
                            index++;
                            break;

                        case 3:
                            user.setBlockStatus(Boolean.parseBoolean(text));
                            index++;
                            break;

                        case 4:
                            user.setPassLimitStatus(Boolean.parseBoolean(text));
                            break;
                    }
                }
            }
            scanner.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( reader != null ) {
                    reader.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        return isAdminHere();
    }

    public static void readFile1() throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException{

        String folder = "C:\\Users\\Xiaomi\\Desktop";
        String path = folder+"\\data.txt";

        byte[] key = "afea1183ba729d6d5d4f3b703efa19aa".getBytes("UTF-8");
        decryptWithEcb(folder+"\\lol.enc", path, key);

        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path), "UTF-8"));
            String text;

            User user = new User(null, null);
            int index = 0;

            while ( ( text = reader.readLine() ) != null ) {
                if (text.equals("{")) {
                    index++;
                }else if(text.equals("}")){
                    index = 0;
                    if (user.getLogin() != null && user.getPass() != null){
                        users.put(user.getLogin(), user);
                    }
                    user = new User(null, null);
                    continue;
                }
                else {
                    switch (index) {
                        case 1:
                            user.setLogin(text);
                            index++;
                            break;
                        case 2:
                            user.setPass(text);
                            index++;
                            break;

                        case 3:
                            user.setBlockStatus(Boolean.parseBoolean(text));
                            index++;
                            break;

                        case 4:
                            user.setPassLimitStatus(Boolean.parseBoolean(text));
                            break;
                    }
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( reader != null ) {
                    reader.close();
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

    }


    public static void updateFile() {
        String folder = "C:\\Users\\Xiaomi\\Desktop";
        String path = folder+"\\data.txt";
        try {
            writeFile(folder, path);
        } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean addUser(User user) {
        String login = user.getLogin();

        if (!users.containsKey(login) && user.getLogin() != null && user.getLogin().trim().length() > 0 ) {
            users.put(user.getLogin(), user);
            updateFile();
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkPass(String login, String password) {
        User user = users.get(login);

        if (user != null && password.equals(user.getPass())) {
            return true;
        } else {
            return false;
        }
    }

    public static void changePass(String login, String password, String new_password) {
        if(checkPass(login,password) == true){
            users.get(login).setPass(new_password);
            updateFile();
        } else {
            System.out.println("UserTable.changePass: " + login + " password is not changed");
        }
    }
    public static boolean isUserNew(String login) {
        User user = users.get(login);

        if (user != null) {
            return user.isNew();
        } else {
            return false;
        }
    }

    public static boolean isUserPassLimited(String login) {
        User user = users.get(login);

        if (user != null) {
            return user.getPassLimitStatus();
        } else {
            return false;
        }
    }

    public static boolean isPassAfford(String pass, String login){
        if(pass == null || pass == ""){
            if (login != null){
                pass = users.get(login).getPass();
            }else{
                return false;
            }
        }
        boolean startWithSymbol = false;
        boolean contWithDigit = false;
        boolean lastsWithSymbol = false;

        for (int i = 0; i<pass.length(); i++){
            if (Character.isLetter(pass.charAt(i))){
                if (!startWithSymbol){
                    startWithSymbol = true;
                }else if (contWithDigit){
                    lastsWithSymbol = true;
                }
            }else if ( Character.isDigit(pass.charAt(i))){
                if (startWithSymbol){
                    if (!contWithDigit){
                        contWithDigit = true;
                    }else if (lastsWithSymbol){
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }

        return startWithSymbol && contWithDigit && lastsWithSymbol;
    }

    // Получение всех юзеров, но без паролей
    public static ArrayList<User> getUsers() {
        ArrayList<User> users_wp = new ArrayList<>();
        for (User user : users.values()) {
            if (!user.getLogin().equals("ADMIN")) {
                User user_wp = new User(user);
                user_wp.setPass(null);
                users_wp.add(user_wp);
            }
        }
        if (users_wp.size() == 0){
            System.out.println("UserTable.getUsers: No users found");
        }
        return users_wp;
    }

    // Получение юзера без пароля
    public static User getUser(String login) {
        if (login != "ADMIN") {
            User user = new User(users.get(login));
            if (user != null){
                user.setPass(null);
            }
            return user;
        } else {
            System.out.println("UserTable.getUser: No user found");
            return null;
        }
    }

    public static boolean isAdminHere() {
        User user = users.get("ADMIN");
        return (user!=null);
    }


    public static boolean setStatus(String login, boolean iB, boolean pL) {
        User user = users.get(login);
        if(login != "ADMIN" && user != null){
            user.setBlockStatus(iB);
            user.setPassLimitStatus(pL);
            updateFile();
            return true;
        }else{
            System.out.println("UserTable.setStatus: No user found");
            return false;

        }
    }




}
