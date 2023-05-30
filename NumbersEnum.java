package convertor;

public enum NumbersEnum {

    thousand(4),
    milion(7),
    bilion(10),
    trilion(13);

    NumbersEnum(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }


    public static NumbersEnum getByNumber(int number) {
        for (NumbersEnum myEnum : values()) {
            if (myEnum.getValue() == number) {
                return myEnum;
            }
        }
        throw new IllegalArgumentException("No enum value associated with the given number: " + number);
    }

}
