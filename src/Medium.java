public class Medium extends Difficulty {
    public Medium() {
    //There are 3 mice, 3 milk while the delay for mice=800 and milk = 1500 for Medium level.
        miceCount    = 3;
        milkCount    = 3;
        miceInterval = 800;
        milkInterval = 1500;
    }

    @Override
    public String getDifficultyLevel(){
        return "medium";
    }
}
