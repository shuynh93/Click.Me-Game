import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FitsLawView extends Application 
{
	private static FitsLawModel model;
	private static FitsLawController controller;
	
	public static void main(String[] args) 
	{
		//construct Model
		model = new FitsLawModel();
		
		//construct Controller
		controller = new FitsLawController( model );
	
		launch( args );
	}
	
	public void start(Stage primaryStage) 
	{
		try {
			VBox root = new VBox(); 
			
			Scene scene = new Scene(root,750,500);
			
			BorderPane border = new BorderPane();
		
			scene.setOnMouseClicked( controller );
			
			/**Main Menu**/
			border.setCenter(MainMenu());
			
			
			/**Game Panel**/
			BorderPane gamePanel = new BorderPane();
			gamePanel.setLeft(gameObjective());
			gamePanel.setCenter( controller.GenerateNewGame() );
			
			
			/**Result Panel**/
			ResultsPanel();

			
			scene.setRoot(border);
			primaryStage.setScene( scene );
			primaryStage.setTitle( "Click.Me" ); 
			primaryStage.show();
			
			model.setGamePane(gamePanel);
			model.setBorder( border );
		} 
		catch( Exception e ) 
		{ 
			e.printStackTrace(); 
		}
	}

	public StackPane MainMenu(){
		/**Main Menu Panel**/
		StackPane mainMenu = new StackPane();
		VBox hb = new VBox(25);
		HBox vb = new HBox(10);
		TextField numClicks = new TextField("1");

		Label promp = new Label("How many rounds would you like to play? (Must be greater than 0)");
		Label title = new Label("Click.Me");

		title.setFont(new Font("Verdana", 95));
		
		Button start = new Button("Start");
		Button tut = new Button("How to Play");
		
		start.addEventHandler( ActionEvent.ACTION, controller );
		tut.addEventHandler( ActionEvent.ACTION, controller );
		
		vb.getChildren().addAll(numClicks, start);
		vb.setAlignment(Pos.CENTER);
		
		hb.getChildren().addAll( title, promp, vb, tut );
		
		hb.setAlignment(Pos.CENTER);
		mainMenu.setAlignment(Pos.CENTER);
		
		mainMenu.getChildren().addAll( hb );
		
		hb.toFront();
		
		HBox games = new HBox();
		games.setMaxHeight(500);
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		ArrayList<Circle> circles = new ArrayList<Circle>();
		
		for(int i = 0; i < 20; i++){
			Random sizeRN = new Random();
			int size = sizeRN.nextInt(20) + 10;
			
			Random positionRN = new Random();
			int position = positionRN.nextInt(4000) + 3000;

			double r = Math.random();
			double g = Math.random();
			double b = Math.random();
			
			Circle circle = new Circle( size, Color.color(r, g, b));
			
			circles.add(circle);
			
			games.getChildren().add(circle);
			
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(position),
					new KeyValue (circle.translateYProperty(), 550)));
			
		}
		games.setAlignment(Pos.TOP_CENTER);
		timeline.play();
	
		mainMenu.getChildren().add( games );
		games.setTranslateY(-50);
		games.setOpacity(0.25);
		games.toBack();
		
		model.setNumClicks( numClicks );
		
		return mainMenu;
	}

	public VBox gameObjective(){
		
		VBox results = new VBox(10);
		Label colorWanted = new Label("Target wanted:");
		Label roundNumber = new Label("Round: 1");
		Label roundsLeft = new Label("Rounds Left: " + model.getTotNumClicks());
		Label success = new Label("Success: 0");
		Label missed = new Label("Missed: 0");
		
		Circle wantedObject = new Circle ( 30.0, Color.BLACK);
		
		roundNumber.setFont(new Font("Verdana", 30));
		results.getChildren().addAll( colorWanted, wantedObject, roundNumber, success, missed, roundsLeft);
		results.setAlignment(Pos.TOP_CENTER);
		
		results.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-color: black;");
		results.setMaxHeight(15);

		
		model.setRoundsLeft( roundsLeft );
		model.setRoundNumber( roundNumber );
		model.setSuccessCount( success );
		model.setMissedCount( missed );
		model.setWantedObject( wantedObject );
		
		return results;
	}
	
	public void ResultsPanel(){
		BorderPane resultPanel = new BorderPane();
		HBox finalResults = new HBox(20);
		HBox legend = new HBox(20);
		VBox finalRes = new VBox(10);

		Label finalScore = new Label("Final Score:");
		Label successLabel = new Label("Success Rate: " + (model.getSuccessClicks()/model.getTotalRounds() *100));
		Label failLabel = new Label("Failure Rate: " + (model.getFailClicks()/model.getTotalRounds() *100));
		
		Label xAxis = new Label("X - Index of Diffculty");
		Label yAxis = new Label("Y - Time taken by the user to make the click (seconds)");
	
		
		legend.getChildren().addAll(xAxis, yAxis);
		legend.setAlignment(Pos.CENTER);
		
		finalResults.getChildren().addAll(finalScore, successLabel, failLabel);
		finalResults.setAlignment(Pos.CENTER);
		
		finalRes.getChildren().addAll(finalResults, legend);
		
		ScatterChart<Number, Number> results = new ScatterChart<Number, Number>( new NumberAxis(), new NumberAxis() );

		results.setLegendVisible( true ); 
		
		resultPanel.setTop(finalRes);
		resultPanel.setCenter(results);
		
		model.setResults( results );
		model.setSuccessLabel( successLabel );
		model.setFailLabel( failLabel );
		model.setResultPanel( resultPanel );
	}
	

}