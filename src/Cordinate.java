public class Cordinate {
    
    private int y;
    private int x;

    public Cordinate(int givenY, int givenX) {
        this.y = givenY;
        this.x = givenX;
    }

    //palauttaa koordinaatin y (rivi)
    public int getY() {
        return y;
    }

    //palauttaa koordinaatin x (sarake)
    public int getX() {
        return x;
    }


}