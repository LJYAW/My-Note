package com.sino.base.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class StringEncrypter {

	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	  public static final String DES_ENCRYPTION_SCHEME = "DES";
	  public static final String DEFAULT_ENCRYPTION_KEY = "VLDRSPCKRNICCDJKMSXYYW86";
	  private static final String UNICODE_FORMAT = "UTF8";
	  public static final String LICENSE_CHECK_ENCRYPTION_KEY = "MODELSFREEOFLICENSECHECK";
	  private KeySpec _keySpec;
	  private SecretKeyFactory _keyFactory;
	  private Cipher _cipher;

	  public StringEncrypter(String encryptionScheme)
	    throws StringEncrypter.EncryptionException
	  {
	    this(encryptionScheme, "VLDRSPCKRNICCDJKMSXYYW86");
	  }

	  public StringEncrypter(String encryptionSchema, int salt) throws StringEncrypter.EncryptionException {
	    this(encryptionSchema, salt + "VLDRSPCKRNICCDJKMSXYYW86");
	  }

	  public StringEncrypter(String encryptionScheme, String encryptionKey)
	    throws StringEncrypter.EncryptionException
	  {
	    if (encryptionKey == null) {
	      throw new IllegalArgumentException("encryption key was null");
	    }
	    if (encryptionKey.trim().length() < 24) {
	      throw new IllegalArgumentException("encryption key was less than 24 characters");
	    }

	    try
	    {
	      byte[] keyAsBytes = encryptionKey.getBytes("UTF8");

	      if (encryptionScheme.equals("DESede"))
	        this._keySpec = new DESedeKeySpec(keyAsBytes);
	      else if (encryptionScheme.equals("DES"))
	        this._keySpec = new DESKeySpec(keyAsBytes);
	      else {
	        throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
	      }

	      this._keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
	      this._cipher = Cipher.getInstance(encryptionScheme);
	    }
	    catch (InvalidKeyException e) {
	      throw new StringEncrypter.EncryptionException(e);
	    } catch (UnsupportedEncodingException e) {
	      throw new StringEncrypter.EncryptionException(e);
	    } catch (NoSuchAlgorithmException e) {
	      throw new StringEncrypter.EncryptionException(e);
	    } catch (NoSuchPaddingException e) {
	      throw new StringEncrypter.EncryptionException(e);
	    }
	  }

	  public String encrypt(String unencryptedString)
	    throws StringEncrypter.EncryptionException
	  {
	    if ((unencryptedString == null) || (unencryptedString.trim().length() == 0)) {
	      throw new IllegalArgumentException("unencrypted string was null or empty");
	    }

	    try
	    {
	      SecretKey key = this._keyFactory.generateSecret(this._keySpec);
	      this._cipher.init(1, key);
	      byte[] cleartext = unencryptedString.getBytes("UTF8");
	      byte[] ciphertext = this._cipher.doFinal(cleartext);

	      return new String(Base64.encodeBase64(ciphertext));
	    } catch (Exception e) {
	      throw new StringEncrypter.EncryptionException(e);
	    }
	  }

	  public String decrypt(String encryptedString) throws StringEncrypter.EncryptionException
	  {
	    if ((encryptedString == null) || (encryptedString.trim().length() <= 0)) {
	      throw new IllegalArgumentException("encrypted string was null or empty");
	    }
	    try
	    {
	      SecretKey key = this._keyFactory.generateSecret(this._keySpec);
	      this._cipher.init(2, key);
	      byte[] cleartext = Base64.decodeBase64(encryptedString.getBytes());
	      byte[] ciphertext = this._cipher.doFinal(cleartext);

	      return bytes2String(ciphertext);
	    } catch (Exception e) {
	      throw new StringEncrypter.EncryptionException(e);
	    }
	  }

	  private static String bytes2String(byte[] bytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < bytes.length; ++i) {
	      stringBuffer.append((char)bytes[i]);
	    }
	    return stringBuffer.toString();
	  }

	  public static String encryptString(String encryptionScheme, String stringToEncrypt)
	    throws Exception
	  {
	    return new StringEncrypter(encryptionScheme).encrypt(stringToEncrypt);
	  }

	  public static String decryptString(String encryptionScheme, String stringToDecrypt) throws Exception {
	    return new StringEncrypter(encryptionScheme).decrypt(stringToDecrypt);
	  }

	  public static String DESedeEncrypt(String stringToEncrypt) throws Exception {
	    return new StringEncrypter("DESede").encrypt(stringToEncrypt);
	  }

	  public static String DESedeDecrypt(String stringToDecrypt) throws Exception {
	    return new StringEncrypter("DESede").decrypt(stringToDecrypt);
	  }

	  public static String DESedeEncrypt(String stringToEncrypt, int salt) throws Exception {
	    return new StringEncrypter("DESede", salt).encrypt(stringToEncrypt);
	  }

	  public static String DESedeDecrypt(String stringToDecrypt, int salt) throws Exception {
	    return new StringEncrypter("DESede", salt).decrypt(stringToDecrypt);
	  }

	  public static class EncryptionException extends Exception
	  {
	    public EncryptionException(Throwable t)
	    {
	      super(t);
	    }
	  }
}
