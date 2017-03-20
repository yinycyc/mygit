package com.zero.flappybird.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;*/


public class ClassPathConfigReaderContext {
	
	/**
	 * ����������ٶ�
	 */
	public  double BIRD_G;
	
	/**
	 * ��ĳ��ٶ�
	 */
	public  double BIRD_V0;
	
	/**
	 * ���ӵ�ˮƽ�ٶ�
	 */
	public int PILLAR_SPEEDX;
	
	/**
	 * ���ӵĴ�ֱ�ٶ�
	 */
	public int PILLAR_SPEEDY;
	
	/**
	 * �����ƶ��ٶ�
	 */
	public int GROUND_SPEED;
	
	/**
	 * ��ʼ����Ϸ����
	 */
	public ClassPathConfigReaderContext() {
		BIRD_G =  Double.parseDouble(get("bird_g"));
		BIRD_V0 =  Double.parseDouble(get("bird_v0"));
		PILLAR_SPEEDX =  Integer.parseInt(get("pillar_speedx"));
		PILLAR_SPEEDY =  Integer.parseInt(get("pillar_speedy"));
		GROUND_SPEED =  Integer.parseInt(get("ground_speed"));
	}
	
	/**
	 * ͨ��KEY�õ�ֵ
	 * @param arg
	 * @return
	 */
	public  String get(String arg) {
		return getValue(arg, "config.properties");
	}
	
	
	/**
	 * properties �ļ�����
	 * @param key
	 * @param fileName
	 * @return
	 */
	public String getValue(String key,String fileName) {
		String value = null;
		Properties prop = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		try {
			prop.load(is);
			value = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	/**
	 * XML�����ļ�
	 */
	
	/*public static Map<String,String> getElements(String keywords) throws SAXException {
		
		Map<String,String> elements = new TreeMap<String,String>();
		
		File file = new File("src/config.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		
		Iterator it = root.elementIterator();
		
		while(it.hasNext()) {
			Element word = (Element) it.next();	
			if(keywords.equals(word.getName())) {
				List<Element> words = word.elements();
				
				for(Element item:words) {
					if(item.getName().equals("speedx")) {
						elements.put(item.getName(),item.getText());
					}else if(item.getName().equals("speedy")) {
						elements.put(item.getName(),item.getText());
					}else if(item.getName().equals("g")) {
						elements.put(item.getName(),item.getText());
					}else if(item.getName().equals("v0")) {
						elements.put(item.getName(),item.getText());
					}else if(item.getName().equals("speed")) {
						elements.put(item.getName(),item.getText());
					}
				}
			}
		}
		
		return elements;
	}*/
	
	/*public static String get(String arg) {
		String target = null;
		Map<String, String> elements = null;
		try {
			String[] str = arg.split("-");
			String element = str[0];
			target = str[1];
			elements = getElements(element);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return elements.get(target);
	}*/
	
}
