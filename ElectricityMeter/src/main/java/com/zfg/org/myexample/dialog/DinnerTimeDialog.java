

package com.zfg.org.myexample.dialog;

import java.util.List;

import android.content.Context;

import com.zfg.org.myexample.model.MapModel;

public class DinnerTimeDialog extends BaseListDialog{

	public DinnerTimeDialog(Context context) {
		super(context);
	}

	@Override
	protected void initData(List<MapModel> data) {
		MapModel model = new MapModel("eat_pre", "餐前");
		data.add(model);
		model = new MapModel("eat_after", "餐后");
		data.add(model);
	}

    

}
