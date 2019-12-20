package presentation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentHelper {

    private Activity ActivityInUse;

    public FragmentHelper(Activity activity)
    {
        ActivityInUse = activity;
    }

    public void replaceFragment(Fragment destFragment, int var1)
    {
        FragmentManager fragmentManager = ((AppCompatActivity)ActivityInUse).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(var1, destFragment);
        fragmentTransaction.commit();
    }

    public void closeFragment(Fragment fragment)
    {
        android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)ActivityInUse).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }
}
