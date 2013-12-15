package aritzh.ld28.board;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Stats {

    private int upgradesClicked, upgradesLost, upgradesAvailableAtEnd;
    private int litSquaresClicked, unlitSquaresClicked;
    private int totalGameTime = 60;
    private long longestSquare;
    private long shortestSquare;

    public void upgradeClicked(){
        this.upgradesClicked++;
        this.upgradesAvailableAtEnd++;
        this.totalGameTime++;
    }

    public void updateTiming(long time){
        if(this.shortestSquare == 0) this.shortestSquare = time;
        else this.shortestSquare = Math.min(time, this.shortestSquare);

        if(this.longestSquare == 0) this.longestSquare = time;
        else this.longestSquare = Math.max(time, this.longestSquare);
    }

    public void finishBefore(int seconds){
        this.totalGameTime=seconds;
    }

    public void upgradeLost(){
        this.upgradesLost++;
        this.upgradesAvailableAtEnd--;
    }

    public void litSquareClicked(){
        this.litSquaresClicked++;
    }

    public void unlitSquareClicked(){
        this.unlitSquaresClicked++;
    }

    public int getUpgradesClicked() {
        return upgradesClicked;
    }

    public int getUpgradesLost() {
        return upgradesLost;
    }

    public int getUpgradesAvailableAtEnd() {
        return upgradesAvailableAtEnd;
    }

    public int getLitSquaresClicked() {
        return litSquaresClicked;
    }

    public int getUnlitSquaresClicked() {
        return unlitSquaresClicked;
    }

    public int getTotalGameTime() {
        return totalGameTime;
    }

    public long getLongestSquare() {
        return longestSquare;
    }

    public long getShortestSquare() {
        return shortestSquare;
    }

    public int getSuccessPercentage(){
        return (int) ((double)this.getCorrectClicks() / this.getTotalSquaresClicked() * 100);
    }

    public int getScore(){
        return this.getLitSquaresClicked()*20 + this.getUpgradesClicked()*40 - this.unlitSquaresClicked*10;
    }

    public int getTotalSquaresClicked() {
        return this.litSquaresClicked + this.upgradesClicked + this.unlitSquaresClicked;
    }

    public int getCorrectClicks(){
        return this.litSquaresClicked + this.upgradesClicked;
    }
}
