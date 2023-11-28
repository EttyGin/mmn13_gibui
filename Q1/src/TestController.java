import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TestController {
	
	@FXML
	private GridPane ansGrid;
	@FXML
	private Text quest;
    @FXML
    private Label ans1;
    @FXML
    private Label ans2;
    @FXML
    private Label ans3;
    @FXML
    private Label ans4;
    @FXML
    private ToggleGroup ans;   
    @FXML
    private Button btn;
    @FXML
    private RadioButton btn1;
    @FXML
    private RadioButton btn2;
    @FXML
    private RadioButton btn3;
    @FXML
    private RadioButton btn4;
    @FXML
    private Text ansStatus;
    
    private CrtTest test;
    private boolean inTest = false;
    
    public void initialize() {
		ansGrid.setVisible(true);
    	btn.setText("לשאלה הבאה");
    	test = new CrtTest();
    	Question();
    }
    
    @FXML
    void nextPressed(ActionEvent event) {
		if (inTest) {
			test.questions.remove(0);
	    	if(test.questions.size()==0) 
	    		end(); //לסכם - הגיע לשאלה האחרונה 
	    	else 
	    		Question(); // לגשת לשאלה הבאה - באמצע מבחן
		}
		else {
			initialize(); //הגיע לכאן אחר
		}
    }

	@FXML
    void ansChosen(ActionEvent event) {
		switchBtns(true);
		String res = "";
		RadioButton crtBtn = (RadioButton)event.getSource();
    	switch (crtBtn.getId()) {
	    	case "btn1":
	    		res = ans1.getText();
	    		break;
	    	case "btn2":
	    		res = ans2.getText();
	    		break;
	    	case "btn3":
	    		res = ans3.getText();
	    		break;
	    	case "btn4":
	    		res = ans4.getText();
	    		break;
    	}
    	
    	if (res.equals(test.getCrt().getCorrectAns())) {
    		res = "תשובה נכונה";
    		test.updateNumOfCrctAns();
    	}
    	else {
    		res = "תשובה לא נכונה";
    	}
    	ansStatus.setText(res);
    }
    
    private void switchBtns(boolean b) {
		btn1.setDisable(b);
		btn2.setDisable(b);
		btn3.setDisable(b);
		btn4.setDisable(b);
		
	}

	private void Question() {
		switchBtns(false);
    	inTest = true;
    	ansStatus.setText("");
    	List<String> options = new ArrayList<>();
    	
    	options.add(test.getCrt().getCorrectAns());
    	options.add(test.getCrt().getAns1());
    	options.add(test.getCrt().getAns2());
    	options.add(test.getCrt().getAns3());
    	
    	Collections.shuffle(options);
    	
    	quest.setText(test.getCrt().getQuest());
    	ans1.setText(options.get(0));
    	ans2.setText(options.get(1));
    	ans3.setText(options.get(2));
    	ans4.setText(options.get(3));
    }
    
    private void end() {
    	    // Get the score
    	int score = test.getNumOfCrctAns();
    	inTest=false;
    	btn.setText("התחלה מחדש");
    	quest.setText("סיימת את המבחן! \n הציון שלך הוא: \n" + score);
    	ansGrid.setVisible(false);
  }
}
