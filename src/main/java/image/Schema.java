package image;

public class Schema implements TextColorSchema {

    static final String DENSITY =
            //"@QB#NgWM8RDHdOKq9$6khEPXwmeZaoS2yjufF]}{tx1zv7lciL/\\|?*>r^;:_\"~,'.-`";
             "#$@%*+'";

    @Override
    public char convert(int color) {
        // поскольку количество символов меньше 255, будем использовать проценты
        int charValue = (int) Math.round(DENSITY.length() / 255.0 * color);
        charValue = Math.max(charValue, 0);
        charValue = Math.min(charValue, DENSITY.length() - 1);
        return DENSITY.charAt(charValue);
    }
}
