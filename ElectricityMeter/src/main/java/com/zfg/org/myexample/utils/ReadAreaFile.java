package com.zfg.org.myexample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * 用于读取 全国的地区文件，文件area已经根据level分组，再根据id排序
 * 
 * @author
 * @createDate 2014年7月16日
 * 
 */
public class ReadAreaFile {

	private Context context;
	private List<Address> listProvince;
	private List<Address> listCity;

	private final String FILE_PROVINCE = "province.txt";
	private final String FILE_CITY = "city.txt";
	private final String FILE_AREA = "area.txt";

	public ReadAreaFile(Context context) {
		this.context = context;
	}

	/**
	 * 获取省份的list集合
	 * 
	 * @return 省份的list集合
	 */
	public List<Address> readProvince() {
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		if (listProvince == null) {// 省份不用每次都加载
			try {
				listProvince = new ArrayList<Address>();
				in = context.getAssets().open(this.FILE_AREA);
				bufferReader = new BufferedReader(new InputStreamReader(in,
						"UTF-8"));
				// 跳入第二行，第一行是标题
				bufferReader.readLine();
				// 加载“请选择”文字
				if ((str = bufferReader.readLine()) != null) {
					supstr = str.split(",");
					Address address = new Address();
					address.setId(Integer.parseInt(supstr[0]));
					address.setName(supstr[1]);
					listProvince.add(address);
				}
				while ((str = bufferReader.readLine()) != null) {
					supstr = str.split(",");
					// 当level是2时 结束
					if (supstr[2].equals("2")) {
						break;
					}
					Address address = new Address();
					address.setId(Integer.parseInt(supstr[0]));
					address.setName(supstr[1]);
					address.setLevel(Integer.parseInt(supstr[2]));
					address.setParentId(Integer.parseInt(supstr[3]));
					listProvince.add(address);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeBuffer(bufferReader);
				closeInputStream(in);
			}
		}
		return listProvince;
	}

	/**
	 * 根据省的id 找到 省里面的市
	 * 
	 * @param provinceId
	 */
	public List<Address> readCity(int provinceId) {
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		try {
			listCity = new ArrayList<Address>();
			in = context.getAssets().open(this.FILE_AREA);
			bufferReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			// 跳入第二行，第一行是标题
			bufferReader.readLine();
			// 加载“请选择”文字
			if ((str = bufferReader.readLine()) != null) {
				supstr = str.split(",");
				Address address = new Address();
				address.setId(Integer.parseInt(supstr[0]));
				address.setName(supstr[1]);
				listCity.add(address);
			}
			while ((str = bufferReader.readLine()) != null) {
				supstr = str.split(",");
				// level为2，parentId为provinceId
				if (supstr[2].equals("2")
						&& supstr[3].equals(String.valueOf(provinceId))) {
					Address address = new Address();
					address.setId(Integer.parseInt(supstr[0]));
					address.setName(supstr[1]);
					address.setLevel(Integer.parseInt(supstr[2]));
					address.setParentId(Integer.parseInt(supstr[3]));
					listCity.add(address);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBuffer(bufferReader);
			closeInputStream(in);
		}
		return listCity;
	}

	public Map<String, String> readArea() {
		Map<String,String> ares = new HashMap<String, String>();
		InputStream in = null;
		BufferedReader bufferReader = null;
		try {
			in = context.getAssets().open(this.FILE_AREA);
			bufferReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			// 跳入第二行，第一行是标题
			bufferReader.readLine();
			// 加载“请选择”文字
			String str;
			while ((str = bufferReader.readLine()) != null) {
				String[] splite = str.split(",");
				ares.put(splite[0], splite[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBuffer(bufferReader);
			closeInputStream(in);
		}
		return ares;
	}

	/**
	 * 根据地区的id，得到地区的名字
	 * 
	 * @param Address的id
	 * @return
	 */
	public String getNameById(int id) {
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		String name = "";
		try {
			in = context.getAssets().open(this.FILE_AREA);
			bufferReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			// 跳入第二行，见地区文件area.txt
			bufferReader.readLine();

			while ((str = bufferReader.readLine()) != null) {
				supstr = str.split(",");
				if (supstr[0].equals(String.valueOf(id))) {
					return name = supstr[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBuffer(bufferReader);
			closeInputStream(in);
		}
		return name;
	}

	/**
	 * 根据省份的id，得到省份的名字
	 * 
	 * @param Address的id
	 * @return
	 */
	public String getNameByProvinceId(int id) {
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		Address address;
		String name = "";
		if (listProvince != null) {
			for (int i = 0; i < listProvince.size(); i++) {
				address = listProvince.get(i);
				if (address.getId() == id) {
					return name = address.getName();
				}
			}
		} else {
			try {
				in = context.getAssets().open(this.FILE_AREA);
				bufferReader = new BufferedReader(new InputStreamReader(in,
						"UTF-8"));
				// 跳入第二行，见地区文件area.txt
				bufferReader.readLine();

				while ((str = bufferReader.readLine()) != null) {
					supstr = str.split(",");
					if (supstr[0].equals(String.valueOf(id))) {
						return name = supstr[1];
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeBuffer(bufferReader);
				closeInputStream(in);
			}
		}
		return name;
	}

	/**
	 * 根据省份的名字得到它的id，用户上传服务器
	 * 
	 * @param 省份的name
	 * @return 省份id
	 */
	public int findProvinceIdByName(String name) {
		// 文件定义第一条数据id为-1. -->-1,不限
		int id = -1;
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		if (listProvince != null) {
			id = findIdByname(name, listProvince);
			return id;
		} else {
			try {
				in = context.getAssets().open(this.FILE_AREA);
				bufferReader = new BufferedReader(new InputStreamReader(in,
						"UTF-8"));
				// 跳到第二行，见地区文件area.txt
				bufferReader.readLine();

				while ((str = bufferReader.readLine()) != null) {
					supstr = str.split(",");
					if (supstr[1].equals(name)) {
						return id = Integer.parseInt(supstr[0]);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeBuffer(bufferReader);
				closeInputStream(in);
			}
		}
		return id;
	}

	/**
	 * 根据地区的名字得到它的id，用户上传服务器
	 * 
	 * @param 地区的name
	 * @return 地区id
	 */
	public int findAreaIdByName(String name) {
		// 文件定义第一条数据id为-1. -->-1,不限
		int id = -1;
		InputStream in = null;
		BufferedReader bufferReader = null;
		String str = "";
		String[] supstr;
		try {
			in = context.getAssets().open(this.FILE_AREA);
			bufferReader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			// 跳到第二行，见地区文件area.txt
			bufferReader.readLine();

			while ((str = bufferReader.readLine()) != null) {
				supstr = str.split(",");
				if (supstr[1].equals(name)) {
					return id = Integer.parseInt(supstr[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBuffer(bufferReader);
			closeInputStream(in);
		}
		return id;
	}

	/**
	 * 把list对象转化为string数组，方便放入adapter
	 * 
	 * @param list
	 */
	public String[] getAddressArray(List<Address> list) {
		String[] item = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Address address = list.get(i);
			item[i] = address.getName();
		}
		return item;
	}

	/**
	 * 在获取到的Address list里面查找对应的id
	 * 
	 * @param name
	 * @param list
	 * @return 地区的id
	 */
	public int findIdByname(String name, List<Address> list) {
		int id = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name)) {
				id = list.get(i).getId();
				break;
			}
		}
		return id;
	}

	/**
	 * 关闭BufferReader
	 * 
	 * @param BufferReader
	 * 
	 */
	public void closeBuffer(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭InputStream
	 * 
	 * @param in
	 */
	public void closeInputStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class Address {
		private int id;
		private String name;
		private int level;
		private int parentId;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		@Override
		public String toString() {
			return this.getName();
		}
	}
}
