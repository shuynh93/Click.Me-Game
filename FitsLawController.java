import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FitsLawController implements EventHandler<Event>{

	private FitsLawModel model;
	
	public FitsLawController(FitsLawModel model){
		this.model = model;
	}


	@Override
	public void handle(Event event) {

		if(event.getSource().toString().contains("Circle"))
		{
			double circleWidth;
			Circle selectedObject = (Circle)event.getSource();
			this.model.addTimes(this.model.getTimeLine().getCurrentTime());
			if(selectedObject.getFill().toString().equals(this.model.getWantedObject().getFill().toString()) ){
				circleWidth = Math.PI * selectedObject.getRadius();
				this.model.setSceneME( false );
				this.model.setSuccessClicks(this.model.getSuccessClicks() + 1);
				this.model.setTotNumClicks( this.model.getTotNumClicks() - 1 );
				this.model.addSuccessChecker(1);
				this.model.positions( selectedObject.getLayoutX(), selectedObject.getLayoutY(), circleWidth);
				this.model.updateGame();
			}else{
				
				for(Circle c: this.model.getCircles()){
					if(this.model.getWantedObject().getFill().toString().equals(c.getFill().toString())){
						circleWidth = Math.PI * c.getRadius();
						this.model.positions( c.getLayoutX(), c.getLayoutY(), circleWidth);
					}
				}
				this.model.addSuccessChecker(0);
				this.model.setSceneME( false );
				this.model.setFailClicks(this.model.getFailClicks() + 1);
				this.model.setTotNumClicks( this.model.getTotNumClicks() - 1 );
				this.model.updateGame();
			}
			
		
			this.model.getGamePane().setCenter(GenerateNewGame());
			this.model.setObjectGoal();
			this.model.getTimeLine().play();
			
		
		}
		else if(event.getSource().toString().contains("Scene"))
		{
			
			if(this.model.getSceneMe())
			{
				
				double circleWidth;
				for(Circle c: this.model.getCircles()){
					if(this.model.getWantedObject().getFill().toString().equals(c.getFill().toString())){
						circleWidth = Math.PI * c.getRadius();
						this.model.positions( c.getLayoutX(), c.getLayoutY(), circleWidth);
					}
				}
				
				
				this.model.addSuccessChecker(0);
				this.model.addTimes(this.model.getTimeLine().getCurrentTime());
				this.model.setFailClicks(this.model.getFailClicks() + 1);
				this.model.setTotNumClicks( this.model.getTotNumClicks() - 1 );
				this.model.updateGame();
				
			}
			else
			{
				this.model.setSceneME( true );
			}
			this.model.setRoundOn(this.model.getRoundOn() + 1);
			this.model.getGamePane().setCenter(GenerateNewGame());
			this.model.setObjectGoal();
			this.model.getTimeLine().play();
		}
		
		else if(event.getSource().toString().contains("Button"))
		{
			Button button = (Button)event.getSource();
			
			switch(button.getText()){
			case "Start":
				if(!this.model.getNumClicks().getText().equalsIgnoreCase("") || 
						this.model.getNumClicks().getText().equalsIgnoreCase("0")){
					
					this.model.setTotNumClicks(Integer.parseInt(this.model.getNumClicks().getText()));
					this.model.setTotalRounds(Integer.parseInt(this.model.getNumClicks().getText()));
					this.model.getBorder().setTop(null);
					this.model.getGamePane().setCenter(GenerateNewGame());
					this.model.getBorder().setCenter(this.model.getGamePane());
					this.model.getTimeLine().play();
					this.model.setObjectGoal();
					
				    

					this.model.positions( button.getLayoutX(), button.getLayoutY(), button.getWidth());
				}
				break;
			case "How to Play": 
				final Stage tutorial = new Stage();
				tutorial.setTitle("How to Play");
                
                VBox tutPopup = new VBox(20);
              
                tutPopup.getChildren().add(new Text("The objective of this game is to selected the object with the \n"
                		+ "same color as the one that is the goal target. \n\n"
                		+ "Instructions: \n"
                		+ "1. Type in how many rounds you would like to play \n"
                		+ "2. Select the moving circle with the color that is wanted"));
                Scene dialogScene = new Scene(tutPopup, 400, 200);
               
                tutorial.setScene(dialogScene);
                tutorial.show();
                this.model.updateGame();
				break;
			}
			
		}
	
	}
	
	public HBox GenerateNewGame(){
		HBox game = new HBox();
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		game.setMaxHeight(500);

		ArrayList<Circle> circles = new ArrayList<Circle>();
		ArrayList<Color> colorFills = new ArrayList<Color>();
		
		for(int i = 0; i < 13; i++){
			Random sizeRN = new Random();
			int size = sizeRN.nextInt(20) + 10;
			
			Random positionRN = new Random();
			int position = positionRN.nextInt(4000) + 3000;

			double r = Math.random();
			double g = Math.random();
			double b = Math.random();
			
			Circle circle = new Circle( size, Color.color(r, g, b));
			circle.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
			
			circles.add(circle);
			colorFills.add(Color.color(r, g, b));

			game.setTranslateY(-50);
			game.getChildren().add( circle );
			
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(position),
					new KeyValue (circle.translateYProperty(), 550)));
			
		}
		
		this.model.setCircles( circles );
		this.model.setTimeLine( timeline );
		this.model.setColorFill( colorFills );

		return game;
	}

}
