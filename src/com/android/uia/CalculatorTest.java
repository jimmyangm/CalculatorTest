package com.android.uia;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;

/***
 * 
 * @author Jimmy
 * UI automation test cases for an android calculator app
 */
public class CalculatorTest extends UiAutomatorTestCase {
	
	
	/**
	 * Initialize test script before each run 
	 * 
	 */
	protected void setUp() throws Exception {
		super.setUp();
		getToTargetAppsUi();
	}
	
	/**
	 * Basic calculation
	 *	Calculate 7 x 8 =
	 *	Calculate + 3 on previous result  
	 *	Verify the final result is 59
	 * @throws UiObjectNotFoundException
	 */
	public void testBasicCals() throws UiObjectNotFoundException{
		
		//Simulate pressing button 7*8=
		UiObject num7 = new UiObject(new UiSelector().className("android.widget.Button").text("7"));
        UiObject num8 = new UiObject(new UiSelector().className("android.widget.Button").text("8"));
        UiObject multiply = new UiObject(new UiSelector().className("android.widget.Button").text("*"));
        UiObject equal = new UiObject(new UiSelector().className("android.widget.Button").text("="));
        
        num7.click();
        multiply.click();
        num8.click();
        equal.click();
       
        //Verify whether the final result is 56
        UiObject resultBox = new UiObject(new UiSelector().className("android.widget.EditText"));
        assertEquals(resultBox.getText(), "56");
        
        //Simulate pressing button +3=
        UiObject plus = new UiObject(new UiSelector().className("android.widget.Button").text("+"));
        UiObject num3 = new UiObject(new UiSelector().className("android.widget.Button").text("3"));
        
        plus.click();
        num3.click();
        equal.click();
        
        //Verify whether the final result is 59
        assertEquals(resultBox.getText(), "59");
        
    }
	
	/**
	 * Change precision 
	 *  Go to menu > Settings > Precision, change the precision to 5
	 *	Back to main activity, calculate 1 / 3 =
	 *	Verify the result is 0.33333
	 * @throws UiObjectNotFoundException
	 */
	public void testPrecisionChg() throws UiObjectNotFoundException{
		
		//Simulate pressing Menu button for android device
		getUiDevice().pressMenu();
		
		//Simulate pressing Settings from a UI pop up menu
		UiObject appSettings = new UiObject(new UiSelector().className("android.widget.TextView").text("Settings"));
		if(appSettings.waitForExists(5000)){
			appSettings.clickAndWaitForNewWindow();
		}
		
		
		//Click on the point which is located around PrecisionSetting section.
		int  h=getUiDevice().getDisplayHeight(); 
        int  w=getUiDevice().getDisplayWidth(); 
        System.out.println( "(h/2,w/2)=" +h/2+ "," +w/2); 
        getUiDevice().click(h/2,w/2);
        
        //Set pop up window box from 10 to 5 and press OK button
        UiObject precisionBox = new UiObject(new UiSelector().className("android.widget.EditText"));
        precisionBox.waitForExists(50000);
        precisionBox.setText("5");
        
		UiObject okBtn = new UiObject(new UiSelector().className("android.widget.Button").text("OK"));
		okBtn.click();
		
		//Back to previous app UI
		getUiDevice().pressBack();
		
        
		//Simulate pressing button 1/3=
		UiObject equal = new UiObject(new UiSelector().className("android.widget.Button").text("="));
    	UiObject num1 = new UiObject(new UiSelector().className("android.widget.Button").text("1"));
    	UiObject num3 = new UiObject(new UiSelector().className("android.widget.Button").text("3"));
    	UiObject divide = new UiObject(new UiSelector().className("android.widget.Button").text("/"));
    	UiObject resultBox = new UiObject(new UiSelector().className("android.widget.EditText"));
    	
    	resultBox.waitForExists(5000);
    	num1.click();
    	divide.click();
    	num3.click();
    	equal.click();

        //Verify the result is 0.33333
    	assertEquals(resultBox.getText(), "0.33333");
     

    }
	
	/**
	 * Trigonometric functions
	 *	Scroll up from bottom to reveal more functions, calculate sin(90)
	 *	Verify the result is 1
	 * @throws UiObjectNotFoundException
	 */
	public void testTrigonometricFunc() throws UiObjectNotFoundException{
		
		//click on slidingDrawer to show up more function such as sin,cos...
		UiObject swtichPanel = new UiObject(new UiSelector().className("android.widget.SlidingDrawer")
									.childSelector(new UiSelector().className("android.widget.LinearLayout")
									.clickable(true)));
        swtichPanel.click();
        
		//click on sin button
        UiObject sin = new UiObject(new UiSelector().className("android.widget.Button").text("sin"));
        sin.waitForExists(5000);
        sin.click();
                
        //get the slidingDRawer back to the original in order to show whole numbers button again
        swtichPanel.click();
		
        //click on button 90=
        UiObject equal = new UiObject(new UiSelector().className("android.widget.Button").text("="));
    	UiObject num9 = new UiObject(new UiSelector().className("android.widget.Button").text("9"));
    	UiObject num0 = new UiObject(new UiSelector().className("android.widget.Button").text("0"));
    	
        equal.waitForExists(5000);
        num9.click();
        num0.click();
        equal.click();
        
        //Verify the result is 1
        UiObject resultBox = new UiObject(new UiSelector().className("android.widget.EditText"));
        assertEquals(resultBox.getText(), "1");
     

    }
	
	/**
	 * Initialize android UI to target app:calculator first Open UI
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	private void getToTargetAppsUi() throws UiObjectNotFoundException, RemoteException{

		//Simulate HOME pressing on the device
        getUiDevice().pressHome();
        getUiDevice().setOrientationNatural();
        
        //Simulate pressing Apps icon for android OS UI.
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        allAppsButton.clickAndWaitForNewWindow();

        //Simulate clicking on APPS tab on application UI where our target app:calculator installed
        UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
        appsTab.click();

        //From uiautomatorviewer£¬the first instance is system default cals£¬The second one is our test target.
        UiObject calcButton = new UiObject(new UiSelector().text("Calculator").instance(1));
        
        //Click Calculator icon to open app
        calcButton.clickAndWaitForNewWindow();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		//restore the device into home status
		getUiDevice().pressHome();
	}

}
