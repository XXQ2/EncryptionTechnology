package ScoutaleEncryption;

import java.util.Random;
import java.util.Scanner;

public class Encoder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("平文を入力してください >>> ");
        String plaintext = scanner.nextLine();

        System.out.print("Rを入力してください >>> ");
        int r = scanner.nextInt();

        // 平文の文字数をRで割った時に余りが0でなければ、あまりが0になるまでランダムな文字列を末尾に追加する
        int amari = plaintext.length() % r;

        if(amari >= 1){
            System.out.println("平文の文字数がRで割り切れないため、ランダムな文字列を追加します。");
            int count = r - amari;
            String ramdomString = generateRandomString(count);
            plaintext+=ramdomString;
        }

        System.out.println("暗号化前 : " + plaintext);

        // 平文とRを用いて暗号化を行う
        String ciphertext = encode(plaintext, r);

        // 暗号文を出力する
        System.out.println("暗号化後 : " + ciphertext.toUpperCase());
    }

    /**
     * 暗号化をおこなう
     * @param plaintext 平文
     * @param r R(一巻きの文字数)
     * @return 生成した暗号文
     */
    public static String encode(String plaintext, int r){
        /** 巻き数 */
        int turns = plaintext.length() / r;
        int strNumber = 0;
        StringBuilder result = new StringBuilder();
        String[][] planeArray = new String[turns][r];

        // 二次元配列に平文を詰め込んでいく（以下、平文配列と呼ぶ）
        for(int i=0; i<turns; i++){
            for(int k=0; k<r; k++){
                planeArray[i][k] = plaintext.substring(strNumber, strNumber+1);
                strNumber++;
            }
        }

        String[][] cipherArray = new String[planeArray[0].length][planeArray.length];

        //平文配列の縦横逆の配列を作成（以下、暗号配列と呼ぶ）
        for(int n=0 ; n<planeArray.length ; n++) {
            for(int j=0 ; j<planeArray[0].length ; j++) {
                cipherArray[j][n] = planeArray[n][j];
            }
        }

        // 暗号配列に格納されている文字を順に１つの文字列につないでいく
        for(int m=0 ; m<cipherArray.length ; m++) {
            for(int l=0 ; l<cipherArray[0].length ; l++) {
                result.append(cipherArray[m][l]);
            }
        }

        // 暗号文を返却
        return result.toString();
    }

    /**
     * 指定された文字数文ランダムなアルファベットを生成する
     * @param wordCount 生成する文字数
     * @return ランダムな文字列
     */
    public static String generateRandomString(int wordCount) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(wordCount)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
