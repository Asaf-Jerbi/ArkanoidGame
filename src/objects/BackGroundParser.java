package objects;

//import backgrounds.ColorBackground;
//import backgrounds.ImageBackground;
import game.backgrounds.ColorBackground;
import game.backgrounds.ImageBackground;
import interfaces.BackGround;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
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
 * a ColorsParser class.
 */
public class BackGroundParser {
    /**
     * backGroundFromString - parse background definition and return the specified Background.
     *
     * @param s string
     * @return color
     */
    public BackGround backGroundFromString(String s) {
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
                ColorBackground colorBackground = new ColorBackground();
                Color c = new Color(x, y, z);
                colorBackground.setColor(c);
                return colorBackground;
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
                ColorBackground colorBackground = new ColorBackground();
                for (int i = 0; i < colorNames.length; i++) {
                    if (colorNames[i].equals(colorType)) {
                        colorBackground.setColor(nameColorMap.get(colorType));
                        return colorBackground;
                    }
                }
            }
        } else if (s.startsWith("image")) {
            String imageName = s.substring("image".length() + 1, s.length() - 1);
            Image img = null;
            ImageBackground imageBackground;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
                img = ImageIO.read(is);
                imageBackground = new ImageBackground(img);
                return imageBackground;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ColorBackground();
    }

}
