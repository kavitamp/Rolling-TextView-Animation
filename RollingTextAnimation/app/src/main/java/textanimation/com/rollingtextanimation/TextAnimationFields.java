package textanimation.com.rollingtextanimation;

public class TextAnimationFields {


    int textSize = 20;
    String textStyleString = "Bold";
    int maxNumbers = 4;
    long animationDuration = 200, gapBetweenTwoNumbersDuration = 100;

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextStyleString() {
        return textStyleString;
    }

    public void setTextStyleString(String textStyleString) {
        this.textStyleString = textStyleString;
    }

    public int getMaxNumbers() {
        return maxNumbers;
    }

    public void setMaxNumbers(int maxNumbers) {
        this.maxNumbers = maxNumbers;
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public long getGapBetweenTwoNumbersDuration() {
        return gapBetweenTwoNumbersDuration;
    }

    public void setGapBetweenTwoNumbersDuration(long gapBetweenTwoNumbersDuration) {
        this.gapBetweenTwoNumbersDuration = gapBetweenTwoNumbersDuration;
    }
}
