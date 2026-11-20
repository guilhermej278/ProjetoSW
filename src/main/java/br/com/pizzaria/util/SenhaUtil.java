package br.com.pizzaria.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class SenhaUtil {

    private static final int ITERACOES = 120000;
    private static final int TAMANHO_CHAVE = 256;
    private static final int TAMANHO_SALT = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    private SenhaUtil() {
    }

    public static String gerarHash(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        byte[] salt = new byte[TAMANHO_SALT];
        RANDOM.nextBytes(salt);
        byte[] hash = derivar(senha.toCharArray(), salt, ITERACOES);
        return ITERACOES + "$" + Base64.getEncoder().encodeToString(salt)
                + "$" + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificar(String senha, String senhaArmazenada) {
        if (senha == null || senhaArmazenada == null) {
            return false;
        }
        try {
            String[] partes = senhaArmazenada.split("\\$");
            if (partes.length != 3) {
                return false;
            }
            int iteracoes = Integer.parseInt(partes[0]);
            byte[] salt = Base64.getDecoder().decode(partes[1]);
            byte[] hashSalvo = Base64.getDecoder().decode(partes[2]);
            byte[] hashInformado = derivar(senha.toCharArray(), salt, iteracoes);
            return MessageDigest.isEqual(hashSalvo, hashInformado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derivar(char[] senha, byte[] salt, int iteracoes) {
        PBEKeySpec spec = new PBEKeySpec(senha, salt, iteracoes, TAMANHO_CHAVE);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível gerar o hash da senha.", e);
        } finally {
            spec.clearPassword();
        }
    }
}
