package com.secretbiology.helpers.general.security;

import javax.crypto.SecretKey;

/**
 * Created by Rohit Suratekar on 05-07-17 for CommonLibraries.
 * All code is released under MIT License.
 * <p>
 * Code from : https://github.com/nelenkov/android-pbe taken with Apache Licence
 */

public class EncryptorHelper {

    public abstract class Encryptor {
        SecretKey key;

        public abstract SecretKey deriveKey(String passpword, byte[] salt);

        public abstract String encrypt(String plaintext, String password);

        public abstract String decrypt(String ciphertext, String password);

        public String getRawKey() {
            if (key == null) {
                return null;
            }

            return Crypto.toHex(key.getEncoded());
        }
    }

    private static final String TAG = EncryptorHelper.class.getSimpleName();


    public final Encryptor PKCS12_ENCRYPTOR = new Encryptor() {

        @Override
        public SecretKey deriveKey(String password, byte[] salt) {
            return Crypto.deriveKeyPkcs12(salt, password);
        }

        @Override
        public String encrypt(String plaintext, String password) {
            byte[] salt = Crypto.generateSalt();
            key = deriveKey(password, salt);
            return Crypto.encryptPkcs12(plaintext, key, salt);
        }

        @Override
        public String decrypt(String ciphertext, String password) {
            return Crypto.decryptPkcs12(ciphertext, password);
        }
    };

    public final Encryptor PBKDF2_ENCRYPTOR = new Encryptor() {

        @Override
        public SecretKey deriveKey(String password, byte[] salt) {
            return Crypto.deriveKeyPbkdf2(salt, password);
        }

        @Override
        public String encrypt(String plaintext, String password) {
            byte[] salt = Crypto.generateSalt();
            key = deriveKey(password, salt);
            return Crypto.encrypt(plaintext, key, salt);
        }

        @Override
        public String decrypt(String ciphertext, String password) {
            return Crypto.decryptPbkdf2(ciphertext, password);
        }
    };

}
