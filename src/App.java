import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String name = null, phoneNumber = null, tagihanStr = null;
        boolean validName = false, validPhoneNumber = false, validTagihan = false;
        int tagihan = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Invoice");
        System.out.println("=======");

        while(!validName) {
            try {
                System.out.print("Input nama: ");
                name = sc.nextLine();
                if(name == null || name.isEmpty())  throw new IllegalArgumentException("Nama tidak boleh kosong");
                if(!name.matches("[a-zA-Z\\s]*")) throw new IllegalArgumentException("Nama tidak boleh mengandung angka atau simbol");
                validName = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (!validPhoneNumber) {
            try {
                System.out.print("Input no Hp: ");
                phoneNumber = sc.nextLine();
                if(phoneNumber == null || phoneNumber.isEmpty()) throw new IllegalArgumentException("No Hp tidak boleh kosong");
                if (!phoneNumber.matches("\\d+")) throw new IllegalArgumentException("No Hp tidak boleh mengandung abjad atau simbol");
                if (!phoneNumber.matches("08\\d{10}|628\\d{10}")) throw new IllegalArgumentException("Jumlah digit salah");
                validPhoneNumber = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (!validTagihan) {
            try {
                System.out.print("Input tagihan: ");
                tagihanStr = sc.nextLine();
                if(tagihanStr == null || tagihanStr.isEmpty()) throw new IllegalArgumentException("Tagihan tidak boleh kosong");
                if(!tagihanStr.matches("-?\\d+")) throw new IllegalArgumentException("Tagihan tidak boleh mengandung abjad atau simbol");
                tagihan = Integer.parseInt(tagihanStr);
                if(tagihan < 0) throw new IllegalArgumentException("Tagihan tidak boleh negatif");
                
                validTagihan = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } /*catch (java.util.InputMismatchException e) {
                System.out.println("Tagihan tidak boleh mengandung abjad atau simbol");
                sc.nextLine(); 
            }*/
        }

        System.out.println("Invoice");
        System.out.println("=======");

        String firstName = "";
        String middleName = "";
        String lastName = "";

        String[] nameParts = name.split(" ");
        if (nameParts.length == 1) {
            firstName = nameParts[0];
            middleName = "";
            lastName = "";
        } else if (nameParts.length == 2) {
            firstName = nameParts[0];
            middleName = "";
            lastName = nameParts[1];
        } else if (nameParts.length >= 3) {
            lastName = nameParts[nameParts.length - 1];
            middleName = nameParts[nameParts.length - 2];
            firstName = String.join(" ", java.util.Arrays.copyOfRange(nameParts, 0, nameParts.length - 2));
        }

        System.out.println("Nama depan: " + capitalize(firstName));
        System.out.println("Nama tengah: " + capitalize(middleName));
        System.out.println("Nama belakang: " + capitalize(lastName));

        if (phoneNumber.startsWith("0")) {
            phoneNumber = phoneNumber.substring(1);
        } else if (phoneNumber.startsWith("62")) {
            phoneNumber = phoneNumber.substring(2);
        }

        phoneNumber = "+62 " + phoneNumber;
        phoneNumber = phoneNumber.substring(0, 7) + "-" + phoneNumber.substring(7, 11) + "-" + phoneNumber.substring(11);
        System.out.println("Nomor handphone yang diformat: " + phoneNumber);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String tagihanRupiah = formatRupiah.format(tagihan);
        tagihanRupiah = tagihanRupiah.replace("Rp", "Rp ");
        System.out.printf("Tagihan Anda: %s %n", tagihanRupiah);

        String kodeBP = "BP";
        String kodeNama = getKodeNama(name);
        String kodeHP = getKodeHP(phoneNumber);
        String kodeTagihan = getKodeTagihan(tagihan);
        String nomorInvoice = kodeBP + "-" + kodeNama + "-" + kodeHP + "-" + kodeTagihan;
        System.out.println("Nomor Invoice: " + nomorInvoice);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }
        return String.join(" ", words);
    }

    public static String getKodeNama(String nama) {
        String[] kata = nama.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : kata) {
            sb.append(s.charAt(0));
        }
        return sb.toString().toUpperCase();
    }

    public static String getKodeHP(String noHP) {
        return noHP.substring(noHP.length() - 3);
    }

    // public static String getKodeTagihan(int nominal) {
    //     return String.format("%03d", nominal);
    // }

    public static String getKodeTagihan(int nominal) {
        String strNominal = Integer.toString(nominal);
        if (strNominal.length() > 3) {
            strNominal = strNominal.substring(0, 3);
        } else {
            strNominal = String.format("%03d", nominal);
        }
        return strNominal;
    }
}
