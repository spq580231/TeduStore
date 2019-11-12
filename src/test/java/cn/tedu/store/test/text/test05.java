package cn.tedu.store.test.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class test05 {

    public static void main(String[] args) {
        ArrayList<Integer> random = new ArrayList<>();
        Random a = new Random();

        for (int i = 0; i < 6; i++) {
            int r = a.nextInt(33) + 1;
            random.add(r);

        }
        for (int i = 0; i < random.size(); i++) {
            System.out.println(random.get(i));

        }
    }

}

