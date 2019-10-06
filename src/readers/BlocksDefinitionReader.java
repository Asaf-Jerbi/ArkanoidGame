package readers;

import interfaces.BlockCreator;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a BlocksDefinitionReader class.
 */
public class BlocksDefinitionReader {
    private Map<String, BlockCreator> blockCreatorMap = new TreeMap<>();
    private Map<String, Integer> widthSpacersMap = new TreeMap<>();
    private List<String> bdefs = new ArrayList<>();
    private List<String> sdefs = new ArrayList<>();
    private List<String> lines = new ArrayList<>();
    private List<String> defaults = new ArrayList<>();
    private Reader readerr;


    /**
     * Read lines.
     */
    public void readLines() {
        Reader reader = this.readerr;
        try {
            String line = ((BufferedReader) reader).readLine();
            while (true) {
                if (line == null) {
                    break;
                }
                if ((!line.isEmpty()) && (!line.startsWith("#"))) {
                    lines.add(line);
                }
                line = ((BufferedReader) reader).readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Separate starts.
     */
    public void separateStarts() {
        for (String line : this.lines) {
            if (line.startsWith("bdef")) {
                this.bdefs.add(line);
            } else if (line.startsWith("default")) {
                this.defaults.add(line);
            } else if (line.startsWith("sdef")) {
                this.sdefs.add(line);
            } else {
                throw new RuntimeException("no default/bdef/sdef String");
            }
        }
    }


    /**
     * Extract creators.
     */
    public void extractCreators() {
        Map<String, String> defaultMap = new TreeMap<>();
        Map<String, String> blocksMap = new TreeMap<>();

        if (!this.defaults.isEmpty()) {
            String[] defBlock = defaults.get(0).split(" ");
            for (int i = 1; i < defBlock.length; i++) {
                String[] defBlockSplit = defBlock[i].split(":");
                defaultMap.put(defBlockSplit[0], defBlockSplit[1]);
            }
        }

        for (int i = 0; i < bdefs.size(); i++) {
            blocksMap.clear();
            String[] whiteSpaceSplitBdefs = bdefs.get(i).split(" ");
            for (int j = 1; j < whiteSpaceSplitBdefs.length; j++) {
                String[] properties = whiteSpaceSplitBdefs[j].split(":");
                blocksMap.put(properties[0], properties[1]);
            }

            String theSymbol = null;
            int theHitPoint = 1;
            int theHeight = 0;
            int theWidth = 0;

            if (blocksMap.containsKey("symbol")) {
                theSymbol = blocksMap.get("symbol");
            }
            if (blocksMap.containsKey("hit_points")) {
                theHitPoint = Integer.parseInt(blocksMap.get("hit_points"));
            } else if (!defaultMap.isEmpty() && defaultMap.containsKey("hit_points")) {
                theHitPoint = Integer.parseInt(defaultMap.get("hit_points"));
            }
            Object[] objectBlockKeys = blocksMap.keySet().toArray();
            String[] blockKeys = new String[objectBlockKeys.length];
            Map<String, String> fillStartsMap = new TreeMap<>();
            for (int j = 0; j < objectBlockKeys.length; j++) {
                blockKeys[j] = (String) objectBlockKeys[j];
                if (blockKeys[j].startsWith("fill")) {
                    fillStartsMap.put(blockKeys[j], blocksMap.get(blockKeys[j]));
                }
            }
            if (blocksMap.containsKey("height")) {
                theHeight = Integer.parseInt(blocksMap.get("height"));
            } else if (!defaultMap.isEmpty() && defaultMap.containsKey("height")) {
                theHeight = Integer.parseInt(defaultMap.get("height"));
            }
            if (blocksMap.containsKey("width")) {
                theWidth = Integer.parseInt(blocksMap.get("width"));
            } else if (!defaultMap.isEmpty() && defaultMap.containsKey("width")) {
                theWidth = Integer.parseInt(defaultMap.get("width"));
            }
            BlockCreator blockBySymbol = new FactoryBlockCreator(theWidth, theHeight, fillStartsMap, theHitPoint);

            blockCreatorMap.put(theSymbol, blockBySymbol);
        }
    }

    /**
     * parseSpacers.
     * <p>
     * return spacers map
     */
    public void parseSpacers() {

        for (int i = 0; i < sdefs.size(); i++) {
            String spaceSymbol = "";
            int spaceWidth = 0;
            Map<String, String> tempSpacersMap = new TreeMap<>();
            String[] spacers;
            spacers = sdefs.get(i).split(" ");
            for (int j = 1; j < spacers.length; j++) {
                String[] afterColonSplit = spacers[j].split(":");
                tempSpacersMap.put(afterColonSplit[0], afterColonSplit[1]);
            }
            if (tempSpacersMap.containsKey("symbol")) {
                spaceSymbol = tempSpacersMap.get("symbol");
            }
            if (tempSpacersMap.containsKey("width")) {
                spaceWidth = Integer.parseInt(tempSpacersMap.get("width"));
            }

            widthSpacersMap.put(spaceSymbol, spaceWidth);
        }
    }


    /**
     * Gets block creator map.
     *
     * @return the block creator map
     */
    public Map<String, BlockCreator> getBlockCreatorMap() {
        return blockCreatorMap;
    }


    /**
     * Gets spacer widths map.
     *
     * @return the spacer widths map
     */
    public Map<String, Integer> getWidthSpacersMap() {
        return widthSpacersMap;
    }


    /**
     * Sets reader.
     *
     * @param r the r
     */
    public void setReader(Reader r) {
        this.readerr = r;
    }


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        blocksDefinitionReader.setReader(reader);
        blocksDefinitionReader.readLines();
        blocksDefinitionReader.separateStarts();
        blocksDefinitionReader.extractCreators();
        blocksDefinitionReader.parseSpacers();
        Map<String, Integer> spacerWidths = new TreeMap<>(blocksDefinitionReader.getWidthSpacersMap());
        Map<String, BlockCreator> blockCreators = new TreeMap<>(blocksDefinitionReader.getBlockCreatorMap());
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }
}
