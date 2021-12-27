package com.qa.utils;

import static javax.crypto.Cipher.getInstance;

import com.qa.annotation.Utils;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.ConfigurationException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

@Utils
public class CipherUtils {

  private static Logger log = LogManager.getLogger(CipherUtils.class);

  @Value("${default.cipher:AES/CBC/PKCS5Padding}")
  private String cipherType;

  @Value("used4Soup")
  private String salt;

  @Value("mustNotBeTold")
  private String secret;

  private Cipher getCipher() throws GeneralSecurityException {
    return getInstance(cipherType);
  }

  /**
   * Encrypts a given sensitive {@link String}.
   *
   * @param secret sensitive {@link String} that shall be encrypted
   * @return Encrypted payload for given sensitive {@link String}, never null.
   * @throws GeneralSecurityException when {@link javax.crypto.Cipher} instance or {@link
   *     IvParameterSpec} could not be retrieved
   */
  public String encrypt(final String secret)
      throws ConfigurationException, GeneralSecurityException {
    final SecretKeySpec secretKeySpec =
        createSecretKey(secret.toCharArray(), salt.getBytes());
    final javax.crypto.Cipher ciph = getCipher();
    ciph.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKeySpec);
    final AlgorithmParameters encParams = ciph.getParameters();
    final IvParameterSpec paramSpec = encParams.getParameterSpec(IvParameterSpec.class);
    final byte[] cryptoText = ciph.doFinal(secret.getBytes(StandardCharsets.UTF_8));
    final byte[] iv = paramSpec.getIV();
    return encodeBase64(iv) + ":" + encodeBase64(cryptoText);
  }

  /**
   * Decrypts sensitive {@link String} from given encrypted payload.
   *
   * @param cryptoText encrypted payload
   * @return decrypted sensitive {@link String}
   */
  @SneakyThrows
  public String decrypt(final String cryptoText) {
    SecretKeySpec secretKeySpec;
    try {
      secretKeySpec =
          createSecretKey(secret.toCharArray(), salt.getBytes());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new ConfigurationException(String.format("Unable to create secret key %s", e));
    }
    final String[] parts = cryptoText.split(":");
    if (parts.length != 2) {
      log.warn("Unexpected number of parts in encoded text");
      throw new ConfigurationException(
          "Unable to decrypt because format of encrypted text is incorrect");
    }
    final String iv = parts[0];
    final String secret = parts[1];
    try {
      final javax.crypto.Cipher ciph = getCipher();
      ciph.init(
          javax.crypto.Cipher.DECRYPT_MODE,
          secretKeySpec,
          new IvParameterSpec(decodeBase64(iv)));
      return new String(ciph.doFinal(decodeBase64(secret)));
    } catch (GeneralSecurityException e) {
      throw new ConfigurationException(String.format("Unable to decrypt secret: %s", e));
    }
  }

  /**
   * Encodes a byte array to {@link Base64} encoded {@link String}.
   *
   * @param bytes byte array to encode
   * @return {@link Base64} encoded {@link String} of byte array
   */
  protected String encodeBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Decodes a {@link Base64} encoded {@link String} to byte array.
   *
   * @param encoded {@link Base64} encoded {@link String} to decode
   * @return decoded byte array of {@link Base64} encoded {@link String}
   */
  private byte[] decodeBase64(final String encoded) {
    return Base64.getDecoder().decode(encoded);
  }

  /**
   * Provides {@link SecretKeySpec}.
   *
   * @param password Password {@link String}
   * @param salt Salt {@link String}
   * @return {@link SecretKeySpec}
   * @throws NoSuchAlgorithmException when requested encryption algorithm is not supported
   * @throws InvalidKeySpecException when secret key could not be generated with given {@link
   *     KeySpec}
   */
  private SecretKeySpec createSecretKey(char[] password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
    final KeySpec keySpec = new PBEKeySpec(password, salt, 40000, 128);
    final SecretKey tmpKey = keyFactory.generateSecret(keySpec);
    return new SecretKeySpec(tmpKey.getEncoded(), "AES");
  }
}
