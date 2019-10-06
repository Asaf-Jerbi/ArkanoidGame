package objects;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.Color.black;
import static java.awt.Color.cyan;
import static java.awt.Color.gray;
import static java.awt.Color.lightGray;
import static java.awt.Color.orange;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import static java.awt.Color.yellow;
import static java.awt.Color.blue;
import static java.awt.Color.green;
import static java.awt.Color.red;

/**
 * a ColorParser class.
 */
public class ColorParser {
    /**
     * colorFromString - parse color definition and return the specified color.
     *
     * @param s string
     * @return color
     */
    public Color colorFromString(String s) {
        if (s.startsWith("color")) {
            if (s.contains("RGB")) {
                String regex = "(\\d*,\\s*\\d*,\\s*\\d*)";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(s);
                m.find();
                String rgb = m.group(1);
                String[] singleDigits = rgb.split(",");
                int x = Integer.parseInt(singleDigits[0]);
                int y = Integer.parseInt(singleDigits[1]);
                int z = Integer.parseInt(singleDigits[2]);
                return new Color(x, y, z);
            } else {
                Color[] colors = {black, blue, cyan, gray, lightGray, green,
                        orange, pink, red, white, yellow};
                String[] colorNames = {"black", "blue", "cyan", "gray", "lightGray", "green",
                        "orange", "pink", "red", "white", "yellow"};
                Map<String, Color> nameColorMap = new TreeMap<>();
                for (int i = 0; i < colorNames.length; i++) {
                    nameColorMap.put(colorNames[i], colors[i]);
                }
                String colorType = s.substring("color".length() + 1, s.length() - 1);
                for (int i = 0; i < colorNames.length; i++) {
                    if (colorNames[i].equals(colorType)) {
                        return nameColorMap.get(colorType);
                    }
                }
            }
        }
        return Color.black;
    }
}
