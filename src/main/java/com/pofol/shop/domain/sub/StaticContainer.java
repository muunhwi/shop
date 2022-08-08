package com.pofol.shop.domain.sub;

public interface StaticContainer {
    String[] CATEGORY = {"아우터", "셔츠", "팬츠", "스포츠", "신발", "가방", "악세서리"};
    String[] PANTS_SIZE = {"28사이즈", "30사이즈", "32사이즈", "34사이즈", "36사이즈"};
    String[] SHOES_SIZE = {"250mm", "260mm", "270mm", "280mm", "290mm"};
    String[] BASE_SIZE = {"S", "M", "X", "XL"};
    String[] COLOR = {"black", "white", "navy", "brown", "khaki", "beige", "charcoal", "skyblue"};

    String[] HAT_SIZE = {"58", "60", "62"};
    String[] RING_SIZE = {"17", "20", "23"};
    String[] ACCESSORY_SIZE = {"M", "L", "F"};
    String[] ALL_SIZE = {
            "28사이즈", "30사이즈", "32사이즈", "34사이즈", "36사이즈", "250mm", "260mm",
            "270mm", "280mm", "290mm", "S", "M", "X", "XL",  "58", "60", "62", "17",
            "20", "23", "M", "L", "F"};
}
