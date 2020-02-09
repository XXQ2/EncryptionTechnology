package ScoutaleEncryption;

import java.util.Random;
import java.util.Scanner;

/**
 * 追加機能
 * ・自動解析機能
 * 　　１から平文文字列-1までの範囲でデコードを繰り返し、各復号分に対して以下をおこなう。
 * 　　　- 頻出単語をリスト化しそれらが最も多く含まれる復号文を自動で返却する
 * 　　また、一定の割合以上の単語を検出できたRの値も一緒に返却する
 * ・Rの範囲指定
 * ・暗号文の文字数がRで割り切れない時に、割り切れる文字列になるまでランダムな文字列で埋める or エラーを返却する
 * ・入力値なしなどのバリデーションチェック
 */

public class Decoder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("暗号文を入力してください >>> ");
        String cipherText = scanner.nextLine();

        System.out.print("Rを入力してください >>> ");
        int r = scanner.nextInt();

        // 平文の文字数をRで割った時に余りが0でなければ、あまりが0になるまでランダムな文字列を末尾に追加する
        int amari = cipherText.length() % r;

        if(amari >= 1){
            System.out.println("平文の文字数がRで割り切れないため、ランダムな文字列を追加します。");
            int count = r - amari;
            String ramdomString = generateRandomString(count);
            cipherText+=ramdomString;
        }

        System.out.println("復号化前 : " + cipherText.toUpperCase());

        String plaintext = decode(cipherText, r);

        // 復号文を出力する
        System.out.println("復号化後 : " + plaintext.toUpperCase());
    }

    /**
     * 指定の条件で解読した文章を返却します。
     * @param cipherText 暗号文
     * @param r R(一巻きの文字数)
     * @return 解読後の文章
     */
    public static String decode(String cipherText, int r){
        /** 巻き数 */
        int turns = cipherText.length() / r;
        int strNumber = 0;
        StringBuilder result = new StringBuilder();
        String[][] cipherArray = new String[turns][r];

        // 二次元配列に平文を詰め込んでいく（以下、平文配列と呼ぶ）
        for(int i=0; i<turns; i++){
            for(int k=0; k<r; k++){
                cipherArray[i][k] = cipherText.substring(strNumber, strNumber+1);
                strNumber++;
            }
        }

        String[][] planeArray = new String[cipherArray[0].length][cipherArray.length];

        //平文配列の縦横逆の配列を作成（以下、暗号配列と呼ぶ）
        for(int n=0 ; n<cipherArray.length ; n++) {
            for(int j=0 ; j<cipherArray[0].length ; j++) {
                planeArray[j][n] = cipherArray[n][j];
            }
        }

        // 暗号配列に格納されている文字を順に１つの文字列につないでいく
        for(int m=0 ; m<planeArray.length ; m++) {
            for(int l=0 ; l<planeArray[0].length ; l++) {
                result.append(planeArray[m][l]);
            }
        }

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
