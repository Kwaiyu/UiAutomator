package com.example.android.testing.uiautomator.BasicSample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class UiTest {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String BASIC_SAMPLE_PACKAGE = "com.example.android.testing.uiautomator.BasicSample";
    private static final String API_DEMO_PACKAGE = "io.appium.android.apis";
    private static final String ACTIVITY_NAME = "com.example.android.testing.uiautomator.BasicSample/com.example.uiautomator.MainActivity";
    private static final String ACTIVITY_NAME_01 = "io.appium.android.apis/.ApiDemos";
    private UiDevice mDevice;
    private static final String STRING_TO_BE_TYPED = "Uiautomator";

    private String getLauncherPackageName(){
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    @Before
//    public void startByIntent(){
////      真机：可启动API Demos app。 Basic Uiautomator app不能启动
////      模拟器：都可启动
//        mDevice = UiDevice.getInstance(getInstrumentation());
//        mDevice.pressHome();
//        final String launcherPackage = getLauncherPackageName();
//        assertThat(launcherPackage, notNullValue());
//        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
////        真机
////        Context context = getInstrumentation().getContext();
////        模拟器
//        Context context = getApplicationContext();
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);   // need clear out any previous instances
//        context.startActivity(intent);
//        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
//    }
    public void startByAM(){
//      真机：可启动API Demos app。 可启动Basic Uiautomator app，但测试结束后在后台
//      模拟器：都可启动
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        try {
            mDevice.executeShellCommand("am start -n " + ACTIVITY_NAME_01);
            mDevice.wait(Until.hasObject(By.pkg(API_DEMO_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//    @Test
//    public void testChangeText_sameActivity() {
//        // Type text and then press the button.
//        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "editTextUserInput"))
//                .setText(STRING_TO_BE_TYPED);
//        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "changeTextBt"))
//                .click();
//
//        // Verify the test is displayed in the Ui
//        UiObject2 changedText = mDevice
//                .wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "textToBeChanged")),
//                        5000 /* wait 5000ms */);
//        assertThat(changedText.getText(), is(equalTo(STRING_TO_BE_TYPED)));
//    }
//    @Test
//    public void testChangeText_newActivity() {
//        // Type text and then press the button.
//        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "editTextUserInput"))
//                .setText(STRING_TO_BE_TYPED);
//        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "activityChangeTextBtn"))
//                .click();
//
//        // Verify the test is displayed in the Ui
//        UiObject2 changedText = mDevice
//                .wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "show_text_view")),
//                        5000 /* wait 5000ms */);
//        assertThat(changedText.getText(), is(equalTo(STRING_TO_BE_TYPED)));
//    }
    @Test
    public void uiScrollableDemo () throws UiObjectNotFoundException {
        UiObject2 element = mDevice.findObject(By.text("App"));
        UiScrollable scroll  = new UiScrollable( new UiSelector()
                .scrollable(true));
        element.clickAndWait(Until.newWindow(),2000);
        scroll.getChildByText(new UiSelector().resourceId("android:id/text1"),
                "Text-To-Speech", true);
        UiObject2 element2 = mDevice.findObject(By.text("Text-To-Speech"));
        element2.clickAndWait(Until.newWindow(), 2000);
        UiObject2 element3 = mDevice.findObject(By.text("Again"));
        element3.click();
    }
}