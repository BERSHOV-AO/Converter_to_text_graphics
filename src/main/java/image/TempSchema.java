package image;

public class TempSchema implements TextColorSchema {

    static final String COLOR_INTENSITY =
            "@QB#NgWM8RDHdOKq9$6khEPXwmeZaoS2yjufF]}{tx1zv7lciL/\\|?*>r^;:_\"~,'.-`";

    @Override
    public char convert(int color) {
        int charValue = (int) Math.round(COLOR_INTENSITY.length() / 255.0 * color);
        charValue = Math.max(charValue, 0);
        charValue = Math.min(charValue, COLOR_INTENSITY.length() - 1);
        return COLOR_INTENSITY.charAt(charValue);
    }
}
