package simetrica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Simetrica {

    public static SecretKey keygenKeyGeneration(int keySize) {

        SecretKey sKey = null;

        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }

        return sKey;
    }

    public static byte[] encryptData(SecretKey sKey, byte[] data) {

        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return encryptedData;
   }
    
    public static byte[] DesencryptData(SecretKey sKey, byte[] data) {

        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return encryptedData;
   }
    
    public static SecretKey passwordKeyGeneration(String text, int keySize) {

        SecretKey sKey = null;

        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {

        try {

            byte[] data = text.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data);
            byte[] key = Arrays.copyOf(hash, keySize/8);
            sKey = new SecretKeySpec(key, "AES");
        }catch(Exception ex){
            System.err.println("Error generant la clau:"+ex);
        }
      }
    
        return sKey;
    }
    
  
      
    public static void main(String[] args) throws FileNotFoundException, IOException{
        

        
        File fichero = new File("//home/tarda/Escriptori/prueba.txt");
        
        FileInputStream f = new FileInputStream(fichero);
        
        byte[] buffer = new byte[(int)fichero.length()];
        
        f.read(buffer);
            
                                        
        byte[] dades = buffer;
        byte[] missatgeXifrat;
        byte[] dadesDesxifrades;
        String missatgeDesxifrat;

        //Xifrar missatge
        SecretKey key = passwordKeyGeneration("cristian", 128);
        
       // SecretKey key = keygenKeyGeneration(128);
       
        
        File fichero2 = new File("//home/tarda/Escriptori/encriptado.txt");
        
        FileOutputStream o = new FileOutputStream(fichero2);   
        
        missatgeXifrat = encryptData(key, dades);
        
        o.write(missatgeXifrat);
          
        
        
        //Descifrar missatge
                        
        dadesDesxifrades = DesencryptData(key, missatgeXifrat);
                
        //Mostrar missatge
        
        missatgeDesxifrat = new String(dadesDesxifrades);        
        System.out.println(missatgeDesxifrat);
        
        
        /*
        
        
        
        File input;
        input.lenght();
        byte[] buffer = new byte[(int)input.lenght()];*/
    }

}
