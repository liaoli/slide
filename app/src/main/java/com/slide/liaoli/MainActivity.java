package com.slide.liaoli;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Toast;

import com.slide.view.BinarySlidingMenu;
import com.slide.view.BinarySlidingMenu.OnMenuOpenListener;
import com.slide.view.DepthPageTransformer;

public class MainActivity extends FragmentActivity implements BlankFragment.OnFragmentInteractionListener
{
	private BinarySlidingMenu mMenu;
	private List<String> mDatas = new ArrayList<String>();
	private List<Fragment> fragments = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mMenu = (BinarySlidingMenu) findViewById(R.id.id_menu);
		mMenu.setOnMenuOpenListener(new OnMenuOpenListener()
		{
			@Override
			public void onMenuOpen(boolean isOpen, int flag)
			{
				if (isOpen)
				{
					Toast.makeText(getApplicationContext(),
							flag == 0 ? "LeftMenu Open" : "RightMenu Open",
							Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getApplicationContext(),
							flag == 0 ? "LeftMenu Close" : "RightMenu Close",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		// 初始化数据
		for (int i = 'A'; i <= 'Z'; i++)
		{
			mDatas.add((char) i + "");
		}
		// 设置适配器



		fragments.add(BlankFragment.newInstance("#330f7f","fragment_1"));

		fragments.add(BlankFragment.newInstance("#0f0f33","fragment_2"));

		ViewPager pager = (ViewPager) findViewById(R.id.pager);

		pager.setCurrentItem(1);

		pager.setPageTransformer(false,new DepthPageTransformer());

		pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
			}

			@Override
			public int getCount() {
				return fragments.size();
			}
		});
	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}
}
