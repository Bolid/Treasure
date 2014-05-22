package ru.omdroid.game.ChaosWord.LogicFillingFieldGame;

public class ReplaceNotAllowableSymbol {

    int countCharMas1 = 0;
    int countCharMas2 = 0;
    String alphabet;

    public ReplaceNotAllowableSymbol(String alphabet){
        this.alphabet = alphabet;
    }

    public String deleteCharFromAlphabet(String str, float percent){



        // Следующих букв не более 3
        if (str.equals("О") || str.equals("Е") || str.equals("А")){
            if (percent > 7.9)
                this.alphabet = alphabet.replace(str, "");
        }

        // Следующих букв не более 2
        if (str.equals("Н") || /*){
            if (percent > 4)
                this.alphabet = alphabet.replace(str, "");
        }

        // Следующих букв не более 1
        if (*/str.equals("И") ||
                str.equals("Т") ||
                str.equals("С") ||
                str.equals("Л") ||
                str.equals("В") ||
                str.equals("Р")
                )     {
            if (percent > 3.9)
                this.alphabet = alphabet.replace(str, "");
        }

        // Следующих букв не более 3 в совокупности.
        // Конкретная буква встречается не более одного раза.
        if ((str.equals("К") ||
                str.equals("Д") ||
                str.equals("М") ||
                str.equals("У") ||
                str.equals("П") ||
                str.equals("Я") ||
                str.equals("Ь") ||
                str.equals("Ы") ||
                str.equals("Г") ||
                str.equals("Б") ||
                str.equals("Ч") ||
                str.equals("З"))/* & countCharMas1 < 3*/
                ){

            /*countCharMas1++;
            if (countCharMas1 == 3) {
                this.alphabet = alphabet.replace("К", "");
                this.alphabet = alphabet.replace("Д", "");
                this.alphabet = alphabet.replace("М", "");
                this.alphabet = alphabet.replace("У", "");
                this.alphabet = alphabet.replace("П", "");
                this.alphabet = alphabet.replace("Я", "");
                this.alphabet = alphabet.replace("Ь", "");
                this.alphabet = alphabet.replace("Ы", "");
                this.alphabet = alphabet.replace("Г", "");
                this.alphabet = alphabet.replace("Б", "");
                this.alphabet = alphabet.replace("Ч", "");
                this.alphabet = alphabet.replace("З", "");
            }  */
            this.alphabet = alphabet.replace(str, "");
        }

        // Следующих букв не более 1 раза в совокупности.
        if (str.equals("Ж") ||
                str.equals("Й") ||
                str.equals("Ш") ||
                str.equals("Х") ||
                str.equals("Ю") ||
                str.equals("Э") ||
                str.equals("Ц") ||
                str.equals("Щ") ||
                str.equals("Ф") ||
                str.equals("Ъ")){
            this.alphabet = alphabet.replace("Ж", "");
            this.alphabet = alphabet.replace("Й", "");
            this.alphabet = alphabet.replace("Ш", "");
            this.alphabet = alphabet.replace("Х", "");
            this.alphabet = alphabet.replace("Ю", "");
            this.alphabet = alphabet.replace("Э", "");
            this.alphabet = alphabet.replace("Ц", "");
            this.alphabet = alphabet.replace("Щ", "");
            this.alphabet = alphabet.replace("Ф", "");
            this.alphabet = alphabet.replace("Ъ", "");
        }

        return this.alphabet;
    }

    public String reloadCharToAlphabet(String str){
        for (int i = 0; i < str.length(); i++)
            if (!alphabet.contains(String.valueOf(str.charAt(i)))){    // если буквы нет в алфавите, то возвращаем
                if (String.valueOf(str.charAt(i)).equals("Ж") ||
                        String.valueOf(str.charAt(i)).equals("Й") ||
                        String.valueOf(str.charAt(i)).equals("Ш") ||
                        String.valueOf(str.charAt(i)).equals("Х") ||
                        String.valueOf(str.charAt(i)).equals("Ю") ||
                        String.valueOf(str.charAt(i)).equals("Э") ||
                        String.valueOf(str.charAt(i)).equals("Ц") ||
                        String.valueOf(str.charAt(i)).equals("Щ") ||
                        String.valueOf(str.charAt(i)).equals("Ф") ||
                        String.valueOf(str.charAt(i)).equals("Ъ")){
                    alphabet += "Ж" + "Й" + "Ш" + "Х" + "Ю" + "Э" + "Ц" + "Щ" + "Ф" + "Ъ";
                    return alphabet;
                }
                alphabet += str.charAt(i);
            }
        return alphabet;
    }


}
