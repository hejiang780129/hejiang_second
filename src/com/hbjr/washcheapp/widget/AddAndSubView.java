package com.hbjr.washcheapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 
 * @author 
 *
 */
public class AddAndSubView extends LinearLayout
{
	Context context;
	LinearLayout mainLinearLayout;   //涓籚iew锛屽嵆AddAndSubView
	LinearLayout leftLinearLayout;   //鍐呴儴宸iew
	LinearLayout centerLinearLayout;   //涓棿view
	LinearLayout rightLinearLayout;  //鍐呴儴鍙硋iew
	OnNumChangeListener onNumChangeListener;
	Button addButton;
	Button subButton;
	EditText editText;
	int num;          //editText涓殑鏁板��
	int editTextLayoutWidth;  //editText瑙嗗浘鐨勫搴�
	int editTextLayoutHeight;  //editText瑙嗗浘鐨勫搴�
	int editTextMinimumWidth;  //editText瑙嗗浘鐨勬渶灏忓搴�
	int editTextMinimumHeight;  //editText瑙嗗浘鐨勬渶灏忛珮搴�
	int editTextMinHeight;  //editText鏂囨湰鍖哄煙鐨勬渶灏忛珮搴�
	int editTextHeight;  //editText鏂囨湰鍖哄煙鐨勯珮搴�
	

	public AddAndSubView(Context context)
	{
		super(context);
		this.context = context;
		num = 0;
		control();
	}

	/**
	 * 甯﹀垵濮嬫暟鎹疄渚嬪寲
	 * @param context
	 * @param 鍒濆鏁版嵁
	 */
	public AddAndSubView(Context context, int num)
	{
		super(context);
		this.context = context;
		this.num = num;
		control();
	}
	
	
	
	
	public AddAndSubView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		num = 0;
		control();
	}

	/**
	 * 
	 */
	private void control()
	{
		initTextWithHeight();
		initialise();          //瀹炰緥鍖栧唴閮╲iew
		setViewsLayoutParm();  //璁剧疆鍐呴儴view鐨勫竷灞�鍙傛暟
		insertView();            //灏嗗瓙view鏀惧叆linearlayout涓�
		setViewListener();
	}
	
	
	/**
	 * 鍒濆鍖朎ditText瀹介珮鍙傛暟
	 */
	private void initTextWithHeight()
	{
		editTextLayoutWidth = -1;
		editTextLayoutHeight = -1;
		editTextMinimumWidth = -1;
		editTextMinimumHeight = -1;
		editTextMinHeight = -1;
		editTextHeight = -1;
	}
	
	
	/**
	 * 瀹炰緥鍖栧唴閮╒iew
	 */
	private void initialise()
	{
		mainLinearLayout = new LinearLayout(context);
		leftLinearLayout = new LinearLayout(context);
		centerLinearLayout = new LinearLayout(context);
		rightLinearLayout = new LinearLayout(context);
		addButton = new Button(context);
		subButton = new Button(context);
		editText = new EditText(context);
		
		addButton.setText("+");
		subButton.setText("-");
		addButton.setTag("+");
		subButton.setTag("-");
		//璁剧疆杈撳叆绫诲瀷涓烘暟瀛�
		editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		editText.setText(String.valueOf(num));
	}
	
	/**
	 * 璁剧疆鍐呴儴view鐨勫竷灞�鍙傛暟
	 */
	private void setViewsLayoutParm()
	{
		LayoutParams viewLayoutParams = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		
		addButton.setLayoutParams(viewLayoutParams);
		subButton.setLayoutParams(viewLayoutParams);
		editText.setLayoutParams(viewLayoutParams);
		editText.setGravity(Gravity.CENTER);
		setTextWidthHeight();
		
		viewLayoutParams.gravity = Gravity.CENTER;
		centerLinearLayout.setLayoutParams(viewLayoutParams);
		//璁〆ditText涓嶈嚜鍔ㄨ幏寰楃劍鐐�
		centerLinearLayout.setFocusable(true);
		centerLinearLayout.setFocusableInTouchMode(true);
		
		viewLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		viewLayoutParams.weight = 1.0f;
		leftLinearLayout.setLayoutParams(viewLayoutParams);  //鍙傛暟锛氬銆侀珮銆佹瘮閲嶏紝姣旈噸涓�1.0
		rightLinearLayout.setLayoutParams(viewLayoutParams);  //鍙傛暟锛氬銆侀珮銆佹瘮閲嶏紝姣旈噸涓�1.0
		
		viewLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
		mainLinearLayout.setLayoutParams(viewLayoutParams);
		mainLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
	}
	
	
	/**
	 * 璁剧疆EditText瑙嗗浘鍜屾枃鏈尯鍩熷楂�
	 */
	private void setTextWidthHeight()
	{
		float fPx;
		
		//璁剧疆瑙嗗浘鏈�灏忓搴�
		if (editTextMinimumWidth < 0)
		{
			// 灏嗘暟鎹粠dip(鍗砫p)杞崲鍒皃x锛岀涓�鍙傛暟涓烘暟鎹師鍗曚綅锛堟涓篋IP锛夛紝绗簩鍙傛暟涓鸿杞崲鐨勬暟鎹��
			fPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					80f, context.getResources().getDisplayMetrics());
			editTextMinimumWidth = Math.round(fPx);
		}
		editText.setMinimumWidth(editTextMinimumWidth);
		
		//璁剧疆鏂囨湰鍖哄煙楂樺害
		if (editTextHeight > 0)
		{
			if (editTextMinHeight >= 0 && editTextMinHeight > editTextHeight)
			{
				editTextHeight = editTextMinHeight;
			}
			editText.setHeight(editTextHeight);
		}
		
		//璁剧疆瑙嗗浘楂樺害
		if (editTextLayoutHeight > 0)
		{
			if (editTextMinimumHeight > 0  && 
					editTextMinimumHeight > editTextLayoutHeight)
			{
				editTextLayoutHeight = editTextMinimumHeight;
			}
			
			LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
			layoutParams.height = editTextLayoutHeight;
			editText.setLayoutParams(layoutParams);
		}
		
		//璁剧疆瑙嗗浘瀹藉害
		if (editTextLayoutWidth > 0)
		{
			if (editTextMinimumWidth > 0  && 
					editTextMinimumWidth > editTextLayoutWidth)
			{
				editTextLayoutWidth = editTextMinimumWidth;
			}
			
			LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
			layoutParams.width = editTextLayoutWidth;
			editText.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 灏嗗瓙view鏀惧叆linearlayout涓�
	 */
	private void insertView()
	{
		mainLinearLayout.addView(leftLinearLayout, 0);
		mainLinearLayout.addView(centerLinearLayout, 1);
		mainLinearLayout.addView(rightLinearLayout, 2);
		
		leftLinearLayout.addView(addButton);
		centerLinearLayout.addView(editText);
		rightLinearLayout.addView(subButton);

		addView(mainLinearLayout);  //灏嗘暣鍧楄鍥炬坊鍔犺繘褰撳墠AddAndSubView涓�
	}

	/**
	 * 璁剧疆editText涓殑鍊�
	 * @param num
	 */
	public void setNum(int num)
	{
		this.num = num;
		editText.setText(String.valueOf(num));
	}
	
	/**
	 * 鑾峰彇editText涓殑鍊�
	 * @return
	 */
	public int getNum()
	{
		if ( editText.getText().toString() != null )
		{
			return Integer.parseInt(editText.getText().toString());
		}
		else {
			return 0;
		}
	}

	
	/**
	 * 璁剧疆EditText瑙嗗浘鐨勬渶灏忛珮搴�
	 * @param minimumWidth EditText鐨勬渶灏忛珮搴︼紝鍗曚綅px
	 */
	public void setEditTextMinimumWidth(int editTextMinimumWidth)
	{
		//璁剧疆瑙嗗浘鏈�灏忓搴�
		if (editTextMinimumWidth > 0)
		{
			this.editTextMinimumWidth = editTextMinimumWidth;
			editText.setMinimumWidth(editTextMinimumWidth);
		}
		
	}

	/**
	 * 璁剧疆EditText瑙嗗浘鐨勬渶灏忛珮搴�
	 * @param editTextMinimumHeight EditText瑙嗗浘鐨勬渶灏忛珮搴�,鍗曚綅锛歱x
	 */
	public void setEditTextMinimumHeight(int editTextMinimumHeight)
	{
		//璁剧疆瑙嗗浘鏈�灏忛珮搴�
		if (editTextMinimumHeight > 0)
		{
			this.editTextMinimumHeight = editTextMinimumHeight;
			editText.setMinimumHeight(editTextMinimumHeight);
		}
	}

	/**
	 * 璁剧疆EditText鏂囨湰鍖哄煙鐨勬渶灏忛珮搴�
	 * @param editTextMinHeight EditText鏂囨湰鍖哄煙鐨勬渶灏忛珮搴�,鍗曚綅锛歱x
	 */
	public void setEditTextMinHeight(int editTextMinHeight)
	{
		//璁剧疆鏂囨湰鍖哄煙鏈�灏忛珮搴�
		if (editTextMinHeight > 0)
		{
			this.editTextMinHeight = editTextMinHeight;
			editText.setMinHeight(editTextMinHeight);
		}
	}

	/**
	 * 璁剧疆EditText鏂囨湰鍖哄煙鐨勯珮搴�
	 * @param editTextHeight EditText鏂囨湰鍖哄煙鐨勯珮搴�,鍗曚綅锛歱x
	 */
	public void setEditTextHeight(int editTextHeight)
	{
		this.editTextHeight = editTextHeight;
		setTextWidthHeight();
	}

	/**
	 * 璁剧疆EditText瑙嗗浘鐨勫搴�
	 * @param editTextLayoutWidth 璁剧疆EditText瑙嗗浘鐨勫搴�,鍗曚綅px
	 */
	public void setEditTextLayoutWidth(int editTextLayoutWidth)
	{
		this.editTextLayoutWidth = editTextLayoutWidth;
		setTextWidthHeight();
	}

	/**
	 * 璁剧疆EditText瑙嗗浘鐨勯珮搴�
	 * @param editTextLayoutHeight EditText瑙嗗浘鐨勬渶灏忛珮搴︼紝鍗曚綅px
	 */
	public void setEditTextLayoutHeight(int editTextLayoutHeight)
	{
		this.editTextLayoutHeight = editTextLayoutHeight;
		setTextWidthHeight();
	}

	/**
	 * 浠rawable褰㈠紡 璁剧疆鎸夐挳鑳屾櫙鍥�
	 * @param addBtnDrawable 鍔犲彿鑳屾櫙鍥�
	 * @param subBtnDrawable 鍑忓彿鑳屾櫙鍥�
	 */
	public void Drawable(Drawable addBtnDrawable, Drawable subBtnDrawable)
	{
		//涓嶆帹鑽愮敤setBackgroundDrawable锛屾柊API鎺ㄨ崘鐢╯etBackground锛堝湪API 16涓級
		addButton.setBackgroundDrawable(addBtnDrawable);
		subButton.setBackgroundDrawable(subBtnDrawable);
		addButton.setText("");
		subButton.setText("");
	}
	
	/**
	 * 浠ヨ祫婧怰esource褰㈠紡 璁剧疆鎸夐挳鑳屾櫙鍥�
	 * @param addBtnResource 鍔犲彿鑳屾櫙鍥�
	 * @param subBtnResource 鍑忓彿鑳屾櫙鍥�
	 */
	public void setButtonBgResource(int addBtnResource, int subBtnResource)
	{
		addButton.setBackgroundResource(addBtnResource);
		subButton.setBackgroundResource(subBtnResource);
		addButton.setText("");
		subButton.setText("");
	}
	
	/**
	 * 璁剧疆鎸夐挳鑳屾櫙鑹�
	 * @param addBtnColor 鍔犲彿鑳屾櫙鑹�
	 * @param subBtnColor 鍑忓彿鑳屾櫙鑹�
	 */
	public void setButtonBgColor(int addBtnColor, int subBtnColor)
	{
		addButton.setBackgroundColor(addBtnColor);
		subButton.setBackgroundColor(subBtnColor);
	}
	
	/**
	 * 璁剧疆EditText鏂囨湰鍙樺寲鐩戝惉
	 * @param onNumChangeListener
	 */
	public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener)
	{
		this.onNumChangeListener = onNumChangeListener;
	}
	
	
	/**
	 * 璁剧疆鏂囨湰鍙樺寲鐩稿叧鐩戝惉浜嬩欢
	 */
	private void setViewListener()
	{
		addButton.setOnClickListener(new OnButtonClickListener());
		subButton.setOnClickListener(new OnButtonClickListener());
		editText.addTextChangedListener(new OnTextChangeListener());
	}
	
	
	/**
	 * 鍔犲噺鎸夐挳浜嬩欢鐩戝惉鍣�
	 * @author ZJJ
	 *
	 */
	class OnButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			String numString = editText.getText().toString();
			if (numString == null || numString.equals(""))
			{
				num = 0;
				editText.setText("0");
			} else
			{
				if (v.getTag().equals("+"))
				{
					if (++num < 0)  //鍏堝姞锛屽啀鍒ゆ柇
					{
						num--;
						Toast.makeText(context, "璇疯緭鍏ヤ竴涓ぇ浜�0鐨勬暟瀛�",
								Toast.LENGTH_SHORT).show();
					} else
					{
						editText.setText(String.valueOf(num));
						
						if (onNumChangeListener != null)
						{
							onNumChangeListener.onNumChange(AddAndSubView.this, num);
						}
					}
				} else if (v.getTag().equals("-"))
				{
					if (--num < 0)  //鍏堝噺锛屽啀鍒ゆ柇
					{
						num++;
						Toast.makeText(context, "璇疯緭鍏ヤ竴涓ぇ浜�0鐨勬暟瀛�",
								Toast.LENGTH_SHORT).show();
					} else
					{
						editText.setText(String.valueOf(num));
						if (onNumChangeListener != null)
						{
							onNumChangeListener.onNumChange(AddAndSubView.this, num);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * EditText杈撳叆鍙樺寲浜嬩欢鐩戝惉鍣�
	 * @author ZJJ
	 *
	 */
	class OnTextChangeListener implements TextWatcher
	{

		@Override
		public void afterTextChanged(Editable s)
		{
			String numString = s.toString();
			if(numString == null || numString.equals(""))
			{
				num = 0;
				if (onNumChangeListener != null)
				{
					onNumChangeListener.onNumChange(AddAndSubView.this, num);
				}
			}
			else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 0)
				{
					Toast.makeText(context, "璇疯緭鍏ヤ竴涓ぇ浜�0鐨勬暟瀛�",
							Toast.LENGTH_SHORT).show();
				} else
				{
					//璁剧疆EditText鍏夋爣浣嶇疆 涓烘枃鏈湯绔�
					editText.setSelection(editText.getText().toString().length());
					num = numInt;
					if (onNumChangeListener != null)
					{
						onNumChangeListener.onNumChange(AddAndSubView.this, num);
					}
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
			
		}
		
	}
	
	
	public interface OnNumChangeListener
	{
		/**
		 * 杈撳叆妗嗕腑鐨勬暟鍊兼敼鍙樹簨浠�
		 * @param view 鏁翠釜AddAndSubView
		 * @param num 杈撳叆妗嗙殑鏁板��
		 */
		public void onNumChange(View view, int num);
	}
	
}
