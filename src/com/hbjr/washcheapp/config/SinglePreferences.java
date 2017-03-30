package com.hbjr.washcheapp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SinglePreferences {
	// Setting����
	private static SinglePreferences instance;
	// ����SharedPreferences�ӿ�
	private SharedPreferences sp;

	/**
	 * ˽�й��췽��
	 */
	private SinglePreferences() {
	}

	/**
	 * ��ȡSetting����
	 * 
	 * @return Setting����
	 */
	public synchronized static SinglePreferences getInstance() {
		if (instance == null) {
			instance = new SinglePreferences();
		}
		return instance;
	}

	/**
	 * ��ȡSharedPreferences����
	 * 
	 * @param context
	 *            ������
	 */
	public SinglePreferences initSP(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		}
		return instance;
	}

	/**
	 * д������
	 * 
	 * @param key
	 *            Keyֵ
	 * @param value
	 *            ���������
	 */
	public void writeSpData(String key, Object value) {
		Editor editor = sp.edit();
		if (value instanceof Boolean) {// Boolean
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {// String
			editor.putString(key, (String) value);
		} else if (value instanceof Long) {// Long
			editor.putLong(key, (Long) value);
		} else if (value instanceof Integer) {// Integer
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Float) {// Float
			editor.putFloat(key, (Float) value);
		}
		// �ύ����
		editor.commit();
	}

	/**
	 * ��ȡ����
	 * 
	 * @param key
	 *            Keyֵ
	 * @param defValue
	 *            Ĭ��ֵ
	 * @return ����
	 */
	public Object redeSpData(String key, Object defValue) {
		if (defValue instanceof Boolean) {// Boolean
			return sp.getBoolean(key, (Boolean) defValue);
		} else if (defValue instanceof String) {// String
			return sp.getString(key, (String) defValue);
		} else if (defValue instanceof Long) {// Long
			return sp.getLong(key, (Long) defValue);
		} else if (defValue instanceof Integer) {// Integer
			return sp.getInt(key, (Integer) defValue);
		} else if (defValue instanceof Float) {// Float
			return sp.getFloat(key, (Float) defValue);
		} else {
			return null;
		}
	}
}
