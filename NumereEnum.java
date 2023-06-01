package convertor;

public enum NumereEnum {

    mii(4),
    milioane(7),
    miliarde(10),
    trilioane(13),

    cvadrilion(16);

    NumereEnum(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }


    public static NumereEnum getByNumber(int number) {
        for (NumereEnum myEnum : values()) {
            if (myEnum.getValue() == number) {
                return myEnum;
            }
        }
        throw new IllegalArgumentException("No enum value associated with the given number: " + number);
    }

}
