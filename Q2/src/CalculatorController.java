import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class CalculatorController {

	public enum Action {EMPTY, ADD, SUB, MULT, DIV};

    @FXML
    private Label resultArea;

    @FXML
    private Pane pane;
    private Button[] btns = new Button[17];
    private double oprnd1, oprnd2;
    private Action action = Action.EMPTY;
    private boolean hasPoint = false, clearScreen = false;
    final int BTN_WIDTH = 75;
    final int BTN_HEIGHT = 66;

    
    public void initialize() {
    	handleButtons();
    }
    
	private void handleButtons() {  	
    	int col = 1;
    	for (int i=0; i < 11; i++) {
    		btns[i] = new Button(((i==10)? ".": i)+"");
    		btns[i].setFont(new Font(28)); 
    		btns[i].setPrefSize(((i==0) ? 2: 1) * BTN_WIDTH, BTN_HEIGHT);   
    		btns[i].setLayoutX(((i==0) ? 0: (i-1)%3) * BTN_WIDTH);
    		btns[i].setLayoutY(((i==0) ? 4: col) * BTN_HEIGHT);
      		btns[i].setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent e) {
    				handleButtonsActions(e);
    			}
    		});
    		pane.getChildren().add(btns[i]);
    		if (i>0 && i%3==0) 
    			col++;
    	}
		btns[10].setLayoutX(2 * BTN_WIDTH);

    }
    
	private void handleButtonsActions(ActionEvent event) {
	    Button clickedBtn = (Button) event.getSource();
	    updateRes(clickedBtn);

	}

	private void updateRes(Button clickedBtn) {
	    String btnText = clickedBtn.getText();
	    if (!(btnText.equals(".")&&(hasPoint))) {
	    String crtText = resultArea.getText();
	    if (!clearScreen) 
	    	resultArea.setText(crtText + btnText);
	    else {
	    	resultArea.setText(btnText);
	    	clearScreen=false;
	    }
	    if (btnText.equals(".")) hasPoint= true;
	    }	
	}

    @FXML
    void add(ActionEvent event) {
	    action = Action.ADD;   
	    next();
    }

    @FXML
    void div(ActionEvent event) {
	    action = Action.DIV;   
	    next();
    }

    @FXML
    void mult(ActionEvent event) {
	    action = Action.MULT;   
	    next();
    }

    @FXML
    void sub(ActionEvent event) {
	    action = Action.SUB;
	    next();
    }
    
    @FXML
    void resPressed(ActionEvent event) {
	    oprnd2 =Double.parseDouble(resultArea.getText());
	    double res = 0;
		switch (action) {
		    case ADD:
		    	res = oprnd1+oprnd2;
		        break;
		    case SUB:
		    	res = oprnd1-oprnd2;
		        break;
		    case MULT:
		    	res = oprnd1*oprnd2;
		        break;
		    case DIV:
		    	res = oprnd1/oprnd2;
		        break;
		    default:
		        break;
		}
		resultArea.setText(res + "");
    }

    private void next () {
	    oprnd1 =Double.parseDouble(resultArea.getText());
	    clearScreen=true;
	    hasPoint=false;
    }
    
    @FXML
    void reverseSign(ActionEvent event) {
    	String crtText = resultArea.getText();
    	if (crtText.length()>0) {
    		if (crtText.charAt(0)=='-') 
    			crtText=crtText.substring(1);
    		else 
    			crtText = "-" + crtText;
    		resultArea.setText(crtText);
    	}
    }
    
    @FXML
    void acPressed(ActionEvent event) {
        oprnd1=0;
        oprnd2=0;
        action = Action.EMPTY;
        hasPoint = false;
        clearScreen = false;
		resultArea.setText("");
    }
}