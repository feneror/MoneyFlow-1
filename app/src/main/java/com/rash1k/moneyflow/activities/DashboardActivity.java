package com.rash1k.moneyflow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rash1k.moneyflow.R;
import com.rash1k.moneyflow.adapters.DashboardPagerAdapter;
import com.rash1k.moneyflow.dialogs.AddNewExpenseDialog;
import com.rash1k.moneyflow.dialogs.AddNewIncomeDialog;
import com.rash1k.moneyflow.util.Prefs;

public class DashboardActivity extends AppCompatActivity implements
        View.OnClickListener, TabLayout.OnTabSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    private int tabPosition = 0;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        DashboardPagerAdapter dashboardPagerAdapter = new DashboardPagerAdapter(this,
                getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.vpDashboard);
        viewPager.setAdapter(dashboardPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDashBoard);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (tabPosition) {

            case DashboardPagerAdapter.FRAGMENT_CASH_FLOW:
                getMenuInflater().inflate(R.menu.main_cashflow, menu);
                break;
            case DashboardPagerAdapter.FRAGMENT_EXPENSES:
                getMenuInflater().inflate(R.menu.menu_expenses, menu);
                break;
            case DashboardPagerAdapter.FRAGMENT_INCOMES:
                getMenuInflater().inflate(R.menu.menu_incomes, menu);
                break;
        }

        Log.d(Prefs.LOG_TAG, "onPrepareOptionsMenu: " + menu.size());
        return true;
    }

       @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_list_expenses:
                startActivity(new Intent(this, ExpensesActivity.class));
                break;
            case R.id.item_list_incomes:
                startActivity(new Intent(this, IncomesActivity.class));
                break;

            case R.id.item_user_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (tabPosition) {
            case DashboardPagerAdapter.FRAGMENT_EXPENSES:
                AddNewExpenseDialog addNewExpenseDialog = new AddNewExpenseDialog();
                addNewExpenseDialog.show(getSupportFragmentManager(), "addExpense");
                break;
            case DashboardPagerAdapter.FRAGMENT_INCOMES:
                AddNewIncomeDialog addNewIncomeDialog = new AddNewIncomeDialog();
                addNewIncomeDialog.show(getSupportFragmentManager(), "addIncome");
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabPosition = tab.getPosition();
        switch (tabPosition) {
            case DashboardPagerAdapter.FRAGMENT_CASH_FLOW:
                toolbar.setTitle(R.string.app_name);
                fab.setVisibility(View.GONE);
                break;
            case DashboardPagerAdapter.FRAGMENT_EXPENSES:
                toolbar.setTitle(R.string.title_tab_expenses);
                fab.setVisibility(View.VISIBLE);
                break;
            case DashboardPagerAdapter.FRAGMENT_INCOMES:
                toolbar.setTitle(R.string.title_tab_incomes);
                fab.setVisibility(View.VISIBLE);
                break;
        }
        viewPager.setCurrentItem(tabPosition);
      invalidateOptionsMenu();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
