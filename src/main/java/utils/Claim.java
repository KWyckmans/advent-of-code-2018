package utils;

public class Claim {
    private String id;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean hasOverlap;

    public Claim(String s) {
//        System.out.println("Processing: " + s);
        String[] parts = s.split("@");
        String[] specifications = parts[1].split(":");
        String[] location = specifications[0].split(",");
        String[] dimensions = specifications[1].split("x");

        this.id = parts[0].trim();

        this.x = Integer.valueOf(location[0].trim());
        this.y = Integer.valueOf(location[1]);

        this.width = Integer.valueOf(dimensions[0].trim());
        this.height = Integer.valueOf(dimensions[1]);
    }

    @Override
    public String toString() {
        return "Claim (" + id + "): located at (" + x + "," + y + "), with width: " + width + ", height: " + height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void overlaps(){
        hasOverlap = true;
    }

    public boolean hasOverlap(){
        return hasOverlap;
    }

    public boolean doesNotOverlap(){
        return !hasOverlap;
    }

    public String getId() {
        return this.id;
    }
}
