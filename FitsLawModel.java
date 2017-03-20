import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class FitsLawModel {

	private boolean sceneME;
	private BorderPane border;
	private BorderPane gamePanel;
	private BorderPane resultPanel;
	private StackPane mainMenu;
	private Circle wantedObject; 
	private Timeline timeline;

	private ScatterChart<Number, Number> results;
	private Series<Number, Number> seriesFails = new XYChart.Series<Number,Number>(); 
	private Series<Number, Number> seriesSuccess = new XYChart.Series<Number,Number>(); 
	
	private ArrayList<Circle> circles;
	private ArrayList<Color> colorFills;
	private ArrayList<Integer> successChecker = new ArrayList<Integer>();
	private ArrayList<Double> totalTime = new ArrayList<Double>();
	private ArrayList<Double> xValues = new ArrayList<Double>();
	private ArrayList<Double> distance = new ArrayList<Double>();
	private ArrayList<Double> yPositions = new ArrayList<Double>();
	private ArrayList<Double> xPositions = new ArrayList<Double>();
	private ArrayList<Double> targetWidth = new ArrayList<Double>();
	private ArrayList<Duration> times = new ArrayList<Duration>();
	
	private TextField numClicks;
	int totalRounds = 1;

	private Label failLabel;
	private Label successLabel;
	private Label roundNumber;
	private Label roundsLeft;
	private Label success;
	private Label missed;
	private int totNumClicks = 1;
	private int roundOn = 0;

	private int successClicks = 0;
	private int failClicks = 0;

	@SuppressWarnings("unchecked")
	public void updateGame() {
		this.roundNumber.setText( "Round: " + this.roundOn);
		this.roundsLeft.setText( "Rounds Left: " + (this.totNumClicks - 1) );
		this.success.setText("Success: " + this.successClicks);
		this.missed.setText("Misssd: " + this.failClicks);
		if(this.totNumClicks == 0){
			this.border.setCenter(resultPanel);
			timeline.pause();

			this.successLabel.setText("Success Rate: " + (double) this.successClicks/this.totalRounds * 100 + "%");
			this.failLabel.setText("Failure Rate: " + (double) this.failClicks/this.totalRounds * 100 + "%");
			
			calculateID();
			
			for(int i = 0; i < this.totalRounds; i++){

				
				if(this.successChecker.get(i) == 1){
					this.seriesSuccess.getData().add(new XYChart.Data<Number,Number>(this.xValues.get(i), this.totalTime.get(i)));
				}
				else if(this.successChecker.get(i) == 0){
					this.seriesFails.getData().add(new XYChart.Data<Number,Number>(this.xValues.get(i), this.totalTime.get(i)));
				}
			}
			this.seriesSuccess.setName("Success");
			this.seriesFails.setName("Failures");
			
			this.results.getData().addAll(this.seriesSuccess, this.seriesFails);
			
		}
	}
	
	public void calculateID(){
		for(int i = 0; i < this.totalRounds; i++){
			double distance = Math.hypot((xPositions.get(i)-(xPositions.get(i+1))), (yPositions.get(i)-(yPositions.get(i+1))));
			this.distance.add(distance);
			
			Double time = this.times.get(i).toSeconds();			
			this.totalTime.add(time);
			
		}

		for(int i = 0; i <this.totalRounds; i++){
			double ID = Math.log((distance.get(i)/this.targetWidth.get(i)) + 1);
			this.xValues.add(ID);
		}
		

	}
	
	public void positions(double x, double y, double targetWidth){
		this.xPositions.add(x);
		this.yPositions.add(y);
		
		this.targetWidth.add(targetWidth);
	}

	public void addSuccessChecker(int checker) {
		this.successChecker.add(checker);
	}
	
	public int getTotalRounds() {
		return totalRounds;
	}

	public void setTotalRounds(int totalRounds) {
		this.totalRounds = totalRounds;
	}
	
	public int getRoundOn() {
		return roundOn;
	}

	public void setRoundOn(int roundOn) {
		this.roundOn = roundOn;
	}
	
	public void setSceneME(boolean value) {
		this.sceneME = value;
	}

	public boolean getSceneMe() {
		return this.sceneME;
	}

	public TextField getNumClicks() {
		return this.numClicks;
	}

	public void setNumClicks(TextField numClicks) {
		this.numClicks = numClicks;
	}

	public int getSuccessClicks() {
		return successClicks;
	}

	public void setSuccessClicks(int successClicks) {
		this.successClicks = successClicks;
	}

	public int getFailClicks() {
		return failClicks;
	}

	public void setFailClicks(int failClicks) {
		this.failClicks = failClicks;
	}

	public void setTotNumClicks(int totNumClicks) {
		this.totNumClicks = totNumClicks;
	}
	
	public int getTotNumClicks() {
		return this.totNumClicks;
	}

	public void setBorder(BorderPane border) {
		this.border = border;
	}
	
	public BorderPane getBorder() {
		return this.border;
	}

	public void setGamePane(BorderPane gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public BorderPane getGamePane() {
		return this.gamePanel;
	}

	public void setRoundNumber(Label roundNumber) {
		this.roundNumber = roundNumber;
	}

	public void setSuccessCount(Label success) {
		this.success = success;
		
	}

	public void setMissedCount(Label missed) {
		this.missed = missed;
	}

	public void setResultPanel(BorderPane resultPanel) {
		this.resultPanel = resultPanel;
	}

	public Circle getWantedObject() {
		return this.wantedObject;
	}
	
	public void setWantedObject(Circle wantedObject) {
		
		this.wantedObject = wantedObject;
	}

	public void setTimeLine(Timeline timeline) {
		this.timeline = timeline;
	}

	public Timeline getTimeLine() {
		return this.timeline;
	}
	
	public void setColorFill(ArrayList<Color> colorFills) {
		this.colorFills = colorFills;
	}

	public void setRoundsLeft(Label roundsLeft) {
		this.roundsLeft = roundsLeft;
	}
	
	
	public void addTimes(Duration time){
		this.times.add(time);
	}

	public void setCircles(ArrayList<Circle> circles) {
		this.circles = circles;
	}
	
	public ArrayList<Circle> getCircles() {
		return this.circles;
	}

	public void setObjectGoal() {
		Random num = new Random();
		int object = num.nextInt(12);
		
		this.wantedObject.setFill(colorFills.get(object));
	}

	public void setSuccessLabel(Label successLabel) {
		this.successLabel = successLabel;
	}

	public void setFailLabel(Label failLabel) {
		this.failLabel = failLabel;
	}

	public void setResults(ScatterChart<Number, Number> results) {
		this.results = results;
	}

	public StackPane getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(StackPane mainMenu) {
		this.mainMenu = mainMenu;
	}
}
