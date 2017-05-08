package edu.umd.cs.securecalculator;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calculator extends AppCompatActivity {

	private final String SDK_VERSION = "1";
	private final int MENUITEM_CLOSE = 300;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;

	// Lifecycle variables
	private List<String> lifeCycleLog;
	private boolean userLeftApp = false;
	private String leftTime = "";
	private String returnTime = "";

	// User variables
	private String directoryID = "";
	private String classID = "";

	/*
	 * Edit Text and Button object initialization for simple calculator design.
	 */
	private EditText txtCalc = null;
	private Button btnZero = null;
	private Button btnOne = null;
	private Button btnTwo = null;
	private Button btnThree = null;
	private Button btnFour = null;
	private Button btnFive = null;
	private Button btnSix = null;
	private Button btnSeven = null;
	private Button btnEight = null;
	private Button btnNine = null;
	private Button btnPlus = null;
	private Button btnMinus = null;
	private Button btnMultiply = null;
	private Button btnDivide = null;
	private Button btnEquals = null;
	private Button btnC = null;
	private Button btnDecimal = null;
	private Button btnMC = null;
	private Button btnMR = null;
	private Button btnMM = null;
	private Button btnMP = null;
	private Button btnBS = null;
	private Button btnPerc = null;
	private Button btnSqrRoot = null;
	private Button btnPM = null;
	private Button expSQ = null;
	private Button expEX = null;
	private Button nbtn1 = null;
	private Button nbtn2 = null;
	private Button nbtn3 = null;
	private Button nbtn4 = null;
	private Button nbtn5 = null;
	private Button nbtn6 = null;
	private Button nbtn7 = null;

	private double num = 0;
	private double memNum = 0;
	private int operator = 1;
	// 0 = nothing, 1 = plus, 2 = minus, 3 =
	// multiply, 4 = divide
	private boolean readyToClear = false;
	private boolean hasChanged = false;

	public static final String DIRECTORY_ID_EXTRA = "directoryID";
	public static final String CLASS_ID_EXTRA = "classID";

	//FireBase
	private FirebaseDatabase fireDB;
	private DatabaseReference database;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black);
		setContentView(R.layout.calculator);

		// Get user information from intent
		Intent intent = getIntent();
		directoryID =  String.valueOf(intent.getExtras().get(DIRECTORY_ID_EXTRA));
		classID = String.valueOf(intent.getExtras().get(CLASS_ID_EXTRA));


        // Init Firebase DB
        fireDB = FirebaseDatabase.getInstance();
        database = fireDB.getReference();

		modifyUserStatus("OK");

		lifeCycleLog = new ArrayList<String>();
		lifeCycleLog.add("onCreate: Calculator view created.");



		this.setTitle(" ");

		initControls();
		initScreenLayout();
		reset();



	}

	private void initScreenLayout() {

		/*
		 * The following three command lines you can use depending upon the
		 * emulator device you are using.
		 */

		// 320 x 480 (Tall Display - HVGA-P) [default]
		// 320 x 240 (Short Display - QVGA-L)
		// 240 x 320 (Short Display - QVGA-P)

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels
		// +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);

		int height = dm.heightPixels;
		int width = dm.widthPixels;

		if (height < 400 || width < 300) {
			txtCalc.setTextSize(20);
		}

		if (width < 300) {
			btnMC.setTextSize(18);
			btnMR.setTextSize(18);
			btnMP.setTextSize(18);
			btnMM.setTextSize(18);
			btnBS.setTextSize(18);
			btnDivide.setTextSize(18);
			btnPlus.setTextSize(18);
			btnMinus.setTextSize(18);
			btnMultiply.setTextSize(18);
			btnEquals.setTextSize(18);
			btnPM.setTextSize(18);
			btnPerc.setTextSize(18);
			btnC.setTextSize(18);
			btnSqrRoot.setTextSize(18);
			btnNine.setTextSize(18);
			btnEight.setTextSize(18);
			btnSeven.setTextSize(18);
			btnSix.setTextSize(18);
			btnFive.setTextSize(18);
			btnFour.setTextSize(18);
			btnThree.setTextSize(18);
			btnTwo.setTextSize(18);
			btnOne.setTextSize(18);
			btnZero.setTextSize(18);
			btnDecimal.setTextSize(18);
			expSQ.setTextSize(18);
			expEX.setTextSize(18);
			nbtn1.setTextSize(18);
			nbtn2.setTextSize(18);
			nbtn3.setTextSize(18);
			nbtn4.setTextSize(18);
			nbtn5.setTextSize(18);
			nbtn6.setTextSize(18);
			nbtn7.setTextSize(18);
		}

		txtCalc.setTextColor(Color.WHITE);
		txtCalc.setBackgroundColor(Color.BLACK);
		txtCalc.setKeyListener(null);

		btnZero.setTextColor(Color.WHITE);
		btnOne.setTextColor(Color.WHITE);
		btnTwo.setTextColor(Color.WHITE);
		btnThree.setTextColor(Color.WHITE);
		btnFour.setTextColor(Color.WHITE);
		btnFive.setTextColor(Color.WHITE);
		btnSix.setTextColor(Color.WHITE);
		btnSeven.setTextColor(Color.WHITE);
		btnEight.setTextColor(Color.WHITE);
		btnNine.setTextColor(Color.WHITE);
		btnPM.setTextColor(Color.WHITE);
		btnDecimal.setTextColor(Color.WHITE);

		btnMP.setTextColor(Color.WHITE);
		btnMM.setTextColor(Color.WHITE);
		btnMR.setTextColor(Color.WHITE);
		btnMC.setTextColor(Color.WHITE);
		btnBS.setTextColor(Color.WHITE);
		btnC.setTextColor(Color.WHITE);
		btnPerc.setTextColor(Color.WHITE);
		btnSqrRoot.setTextColor(Color.WHITE);
		btnDivide.setTextColor(Color.WHITE);
		btnPlus.setTextColor(Color.WHITE);
		btnMinus.setTextColor(Color.WHITE);
		btnMultiply.setTextColor(Color.WHITE);
		btnEquals.setTextColor(Color.WHITE);
		expSQ.setTextColor(Color.WHITE);
		expEX.setTextColor(Color.WHITE);

		nbtn1.setTextColor(Color.WHITE);
		nbtn2.setTextColor(Color.WHITE);
		nbtn3.setTextColor(Color.WHITE);
		nbtn4.setTextColor(Color.WHITE);
		nbtn5.setTextColor(Color.WHITE);
		nbtn6.setTextColor(Color.WHITE);
		nbtn7.setTextColor(Color.WHITE);

	}

	private void initControls() {
		txtCalc = (EditText) findViewById(R.id.txtCalc);
		btnZero = (Button) findViewById(R.id.btnZero);
		btnOne = (Button) findViewById(R.id.btnOne);
		btnTwo = (Button) findViewById(R.id.btnTwo);
		btnThree = (Button) findViewById(R.id.btnThree);
		btnFour = (Button) findViewById(R.id.btnFour);
		btnFive = (Button) findViewById(R.id.btnFive);
		btnSix = (Button) findViewById(R.id.btnSix);
		btnSeven = (Button) findViewById(R.id.btnSeven);
		btnEight = (Button) findViewById(R.id.btnEight);
		btnNine = (Button) findViewById(R.id.btnNine);
		btnPlus = (Button) findViewById(R.id.btnPlus);
		btnMinus = (Button) findViewById(R.id.btnMinus);
		btnMultiply = (Button) findViewById(R.id.btnMultiply);
		btnDivide = (Button) findViewById(R.id.btnDivide);
		btnEquals = (Button) findViewById(R.id.btnEquals);
		btnC = (Button) findViewById(R.id.btnC);
		btnDecimal = (Button) findViewById(R.id.btnDecimal);
		btnMC = (Button) findViewById(R.id.btnMC);
		btnMR = (Button) findViewById(R.id.btnMR);
		btnMM = (Button) findViewById(R.id.btnMM);
		btnMP = (Button) findViewById(R.id.btnMP);
		btnBS = (Button) findViewById(R.id.btnBS);
		btnPerc = (Button) findViewById(R.id.btnPerc);
		btnSqrRoot = (Button) findViewById(R.id.btnSqrRoot);
		btnPM = (Button) findViewById(R.id.btnPM);
		expSQ = (Button) findViewById(R.id.expSQ);
		expEX = (Button) findViewById(R.id.expEX);
		nbtn1 = (Button) findViewById(R.id.nbtn1);
		nbtn2 = (Button) findViewById(R.id.nbtn2);
		nbtn3 = (Button) findViewById(R.id.nbtn3);
		nbtn4 = (Button) findViewById(R.id.nbtn4);
		nbtn5 = (Button) findViewById(R.id.nbtn5);
		nbtn6 = (Button) findViewById(R.id.nbtn6);
		nbtn7 = (Button) findViewById(R.id.nbtn7);

		nbtn1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(12);

			}

		});

		nbtn2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(7);

			}

		});

		nbtn3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(8);

			}

		});

		nbtn4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(9);

			}

		});

		nbtn5.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(10);

			}

		});

		nbtn6.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(11);

			}

		});

		nbtn7.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(13);

			}

		});

		btnZero.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(0);

			}

		});
		expSQ.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(Math.cbrt(Double.parseDouble(txtCalc
						.getText().toString()))));
			}

		});
		expEX.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(6);
			}

		});
		btnOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(1);
			}
		});
		btnTwo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(2);
			}
		});
		btnThree.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(3);
			}
		});
		btnFour.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(4);
			}
		});
		btnFive.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(5);
			}
		});
		btnSix.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(6);
			}
		});
		btnSeven.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(7);
			}
		});
		btnEight.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(8);
			}
		});
		btnNine.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleNumber(9);
			}
		});
		btnPlus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(1);
			}
		});
		btnMinus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(2);
			}
		});
		btnMultiply.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(3);
			}
		});
		btnDivide.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(4);
			}
		});
		btnEquals.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleEquals(0);
			}
		});
		btnC.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				reset();
			}
		});
		btnDecimal.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleDecimal();
			}
		});
		btnPM.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handlePlusMinus();
			}
		});
		btnMC.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = 0;
			}
		});
		btnMR.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(memNum));
			}
		});
		btnMM.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = memNum
						- Double.parseDouble(txtCalc.getText().toString());
				operator = 0;
			}
		});
		btnMP.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				memNum = memNum
						+ Double.parseDouble(txtCalc.getText().toString());
				operator = 0;
			}
		});
		btnBS.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				handleBackspace();
			}
		});
		btnSqrRoot.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(Math.sqrt(Double.parseDouble(txtCalc
						.getText().toString()))));
			}
		});
		btnPerc.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setValue(Double.toString(num
						* (0.01 * Double.parseDouble(txtCalc.getText()
								.toString()))));
			}
		});

		txtCalc.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int i, android.view.KeyEvent e) {
				if (e.getAction() == KeyEvent.ACTION_DOWN) {
					int keyCode = e.getKeyCode();

					// txtCalc.append("["+Integer.toString(keyCode)+"]");

					switch (keyCode) {
					case KeyEvent.KEYCODE_0:
						handleNumber(0);
						break;

					case KeyEvent.KEYCODE_1:
						handleNumber(1);
						break;

					case KeyEvent.KEYCODE_2:
						handleNumber(2);
						break;

					case KeyEvent.KEYCODE_3:
						handleNumber(3);
						break;

					case KeyEvent.KEYCODE_4:
						handleNumber(4);
						break;

					case KeyEvent.KEYCODE_5:
						handleNumber(5);
						break;

					case KeyEvent.KEYCODE_6:
						handleNumber(6);
						break;

					case KeyEvent.KEYCODE_7:
						handleNumber(7);
						break;

					case KeyEvent.KEYCODE_8:
						handleNumber(8);
						break;

					case KeyEvent.KEYCODE_9:
						handleNumber(9);
						break;

					case 43:
						handleEquals(1);
						break;

					case KeyEvent.KEYCODE_EQUALS:
						handleEquals(0);
						break;

					case KeyEvent.KEYCODE_MINUS:
						handleEquals(2);
						break;

					case KeyEvent.KEYCODE_PERIOD:
						handleDecimal();
						break;

					case KeyEvent.KEYCODE_C:
						reset();
						break;

					case KeyEvent.KEYCODE_SLASH:
						handleEquals(4);
						break;

					case KeyEvent.KEYCODE_DPAD_DOWN:
						return false;
					}
				}

				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.calculator,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent called = getIntent();
		String cID = called.getStringExtra(CLASS_ID_EXTRA);
		String dID = called.getStringExtra(DIRECTORY_ID_EXTRA);
		switch (item.getItemId()) {
			case R.id.menu_item_log_out:
				dbInteraction.updateStatus(cID, dID, FireDatabaseConstants.LOG_OUT_STATUS);
				finish();
				return true;
			case R.id.menu_item_request_help:
				dbInteraction.updateStatus(cID, dID, FireDatabaseConstants.HELP_STATUS);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void handleEquals(int newOperator) {
		if (hasChanged) {
			switch (operator) {
			case 1:
				num = num + Double.parseDouble(txtCalc.getText().toString());
				break;
			case 2:
				num = num - Double.parseDouble(txtCalc.getText().toString());
				break;
			case 3:
				num = num * Double.parseDouble(txtCalc.getText().toString());
				break;
			case 4:
				num = num / Double.parseDouble(txtCalc.getText().toString());
				break;
			case 5:
				num = Math.pow(num, 2);
				break;
			case 6:
				num = Math.pow(num,
						Double.parseDouble(txtCalc.getText().toString()));
				break;
			case 7:
				num = num
						+ Math.sin(Double.parseDouble(txtCalc.getText()
								.toString()));
				break;
			case 8:
				num = num
						+ Math.cos(Double.parseDouble(txtCalc.getText()
								.toString()));
				break;
			case 9:
				num = num
						+ Math.tan(Double.parseDouble(txtCalc.getText()
								.toString()));
				break;
			case 10:
				num = Math
						.log(Double.parseDouble(txtCalc.getText().toString()));
				break;
			case 11:
				double loge = Math.log(Double.parseDouble(txtCalc.getText()
						.toString()));
				num = Math.exp(loge);
				break;
			case 12:
				num = Math.PI;
				break;
			case 13:
				num = Math.E;
				break;
			}

			String txt = Double.toString(num);
			txtCalc.setText(txt);
			txtCalc.setSelection(txt.length());

			readyToClear = true;
			hasChanged = false;
		}

		operator = newOperator;
	}

	private void handleNumber(int num) {
		if (operator == 0)
			reset();

		String txt = txtCalc.getText().toString();
		if (readyToClear) {
			txt = "";
			readyToClear = false;
		} else if (txt.equals("0"))
			txt = "";

		txt = txt + Integer.toString(num);

		txtCalc.setText(txt);
		txtCalc.setKeyListener(null);
		txtCalc.setSelection(txt.length());

		hasChanged = true;
	}

	private void setValue(String value) {
		if (operator == 0)
			reset();

		if (readyToClear) {
			readyToClear = false;
		}

		txtCalc.setText(value);
		txtCalc.setSelection(value.length());

		hasChanged = true;
	}

	private void handleDecimal() {
		if (operator == 0)
			reset();

		if (readyToClear) {
			txtCalc.setText("0.");
			txtCalc.setSelection(2);
			readyToClear = false;
			hasChanged = true;
		} else {
			String txt = txtCalc.getText().toString();

			if (!txt.contains(".")) {
				txtCalc.append(".");
				hasChanged = true;
			}
		}
	}

	private void handleBackspace() {
		if (!readyToClear) {
			String txt = txtCalc.getText().toString();
			if (txt.length() > 0) {
				txt = txt.substring(0, txt.length() - 1);
				if (txt.equals(""))
					txt = "0";

				txtCalc.setText(txt);
				txtCalc.setSelection(txt.length());
			}
		}
	}

	private void handlePlusMinus() {
		if (!readyToClear) {
			String txt = txtCalc.getText().toString();
			if (!txt.equals("0")) {
				if (txt.charAt(0) == '-')
					txt = txt.substring(1, txt.length());
				else
					txt = "-" + txt;

				txtCalc.setText(txt);
				txtCalc.setSelection(txt.length());
			}
		}
	}

	private void reset() {
		num = 0;
		txtCalc.setText("0");
		txtCalc.setSelection(1);
		operator = 1;
	}

	// LOG LIFECYCLE METHODS
	@Override
	public void onStart() {
		super.onStart();
		lifeCycleLog.add("onStart: Calculator visible to user.");

		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		if(!isConnected){
			lifeCycleLog.add("Internet not connected");
		}


	}

	@Override
	public void onRestart() {
		super.onRestart();
		lifeCycleLog.add("onRestart: User returns to SecureCalculator.");

		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		if(!isConnected){
			lifeCycleLog.add("Internet not connected");
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if (userLeftApp) {
			userLeftApp = false;

			returnTime = getCurrentTime();
			lifeCycleLog.add("onResume: User has returned to the app at " + returnTime + ".");
		}
		else {
			lifeCycleLog.add("onResume: User can interact with app.");
		}

		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		if(!isConnected){
			lifeCycleLog.add("Internet not connected");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		lifeCycleLog.add("onPause: User is leaving SecureCalculator.");
	}

	@Override
	public void onStop() {
		super.onStop();

		userLeftApp = true;
		leftTime = getCurrentTime();

		modifyUserStatus("onStop");

		lifeCycleLog.add("onStop: User has left SecureCalculator at " + leftTime + ".");

		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		if(!isConnected){
			lifeCycleLog.add("Internet not connected");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		modifyUserStatus("LOGGED_OUT");

		lifeCycleLog.add("onDestroy: User has quit the app.");
	}

	// Gets the current time and returns it in the format: hour:minute:second.millisecond
	private String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		System.out.println(sdf.format(cal.getTime()).toString());
		return(sdf.format(cal.getTime()));
	}

	// Modifies the users status in the database - called in lifecycle methods
	public void modifyUserStatus(String status) {
		dbInteraction.updateStatus(classID, directoryID, status);
	}

}
