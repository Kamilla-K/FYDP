package com.fydp.myoralvillage;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserSettings thisUser = new UserSettings();
    List<String> userNames = new ArrayList<>();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean newProfile = true;
    String lastImageClicked = "admin";
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseFile();
        DrawProfiles();
    }

    public void DrawProfiles() {

        if(12-userNames.size()>1) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.unclaimedProfiles1);
            LinearLayout.LayoutParams llParams =  (LinearLayout.LayoutParams) ll.getLayoutParams();
            llParams.weight = 0.3f;
            ll.setLayoutParams(llParams);
        }
        if(12-userNames.size()>7) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.unclaimedProfiles2);
            LinearLayout.LayoutParams llParams =  (LinearLayout.LayoutParams) ll.getLayoutParams();
            llParams.weight = 0.3f;
            ll.setLayoutParams(llParams);
        }
        if(userNames.size()>5) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.claimedProfiles2);
            LinearLayout.LayoutParams llParams =  (LinearLayout.LayoutParams) ll.getLayoutParams();
            llParams.weight = 0.3f;
            ll.setLayoutParams(llParams);
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.claimedProfiles1);
        LinearLayout.LayoutParams llParams =  (LinearLayout.LayoutParams) ll.getLayoutParams();
        llParams.weight = 0.3f;
        ll.setLayoutParams(llParams);

        int claimedCount = 0;
        int unclaimedCount = 0;

        for(int i = 1; i < 12; i++) {
            String filename = "user"+i;
            if(userNames.size()>0) {
                if(userNames.contains(filename)) {
                        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());
                        claimedCount++;

                        String imgview_name = "claimedProfile" + claimedCount;

                        int res_id = getResources().getIdentifier(imgview_name, "id", getPackageName());
                        ImageView iv = (ImageView) findViewById(res_id);
                        iv.setImageResource(img_id);
                        iv.setAlpha(0.5f);
                        iv.setVisibility(View.VISIBLE);
                        iv.setTag(filename);

                    } else {
                        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());
                        unclaimedCount++;

                        String imgview_name = "unclaimedProfile" + unclaimedCount;

                        int res_id = getResources().getIdentifier(imgview_name, "id", getPackageName());
                        ImageView iv = (ImageView) findViewById(res_id);
                        iv.setImageResource(img_id);
                        iv.setVisibility(View.VISIBLE);
                        iv.setTag(filename);
                    }

            } else {
                int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());
                unclaimedCount++;

                String imgview_name = "unclaimedProfile" + unclaimedCount;

                int res_id = getResources().getIdentifier(imgview_name, "id", getPackageName());
                ImageView iv = (ImageView) findViewById(res_id);
                iv.setImageResource(img_id);
                iv.setVisibility(View.VISIBLE);
                iv.setTag(filename);
            }
        }
        int adminId = userNames.size()+1;
        String filename = "admin";
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());

        String imgview_name = "claimedProfile"+adminId;
        int res_id = getResources().getIdentifier(imgview_name, "id", getPackageName());
        ImageView iv = (ImageView) findViewById(res_id);
        iv.setImageResource(img_id);
        iv.setAlpha(0.5f);
        iv.setVisibility(View.VISIBLE);
        iv.setTag(filename);
    }

    public void hasBeenClicked(View v) {
        ImageView iv = (ImageView) v;
        String userString = (String) iv.getTag();
        if (userString.equals(lastImageClicked)) {
            clickCount++;
        } else {
            clickCount = 1;
            lastImageClicked = userString;
        }
        if(userString.equals("admin")) {
            if(clickCount==10) {
                thisUser.userName = userString;
                getDataThroughFile();
                if(thisUser.userName.equals("admin")) {
                    thisUser.userId = -1;
                    for(int i = 0; i < thisUser.demosViewed.length; i++) {
                        thisUser.demosViewed[i] = false;
                    }
                    for(int i = 0; i < thisUser.availableLevels.length; i++) {
                        thisUser.availableLevels[i] = true;
                    }
                    for(int i = 0; i < thisUser.activityProgress.length; i++) {
                        thisUser.activityProgress[i] = true;
                    }
                } else if(newProfile) {
                    thisUser.userId = userNames.size();
                    WriteFile();
                }
                Intent intent = new Intent(this, GameMenuActivity.class);
                intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
                intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
                intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
                intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
                intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
                startActivity(intent);
                finish();
            }
        } else {
            thisUser.userName = userString;
            getDataThroughFile();
            if(thisUser.userName.equals("admin")) {
                thisUser.userId = -1;
                for(int i = 0; i < thisUser.demosViewed.length; i++) {
                    thisUser.demosViewed[i] = true;
                }
                for(int i = 0; i < thisUser.availableLevels.length; i++) {
                    thisUser.availableLevels[i] = true;
                }
                for(int i = 0; i < thisUser.activityProgress.length; i++) {
                    thisUser.activityProgress[i] = true;
                }
            } else if(newProfile) {
                thisUser.userId = userNames.size();
                WriteFile();
            }
            Intent intent = new Intent(this, GameMenuActivity.class);
            intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
            intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
            intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
            intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
            intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
            startActivity(intent);
            finish();
        }
    }

    public void ParseFile() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(userSettingsFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] thisLine = line.split(",");
                userNames.add(thisLine[0]);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataThroughFile() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(userSettingsFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] thisLine = line.split(",");
                userNames.add(thisLine[0]);
                if(thisUser.userName.equals(thisLine[0])) {
                    setUserData(thisLine);
                    newProfile = false;
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(String[] data) {
        thisUser.userId = Integer.parseInt(data[1]);
        for(int i = 0; i < thisUser.demosViewed.length; i++) {
            thisUser.demosViewed[i] = Boolean.parseBoolean(data[i+2]);
        }
        for(int i = 0; i < thisUser.availableLevels.length; i++) {
            thisUser.availableLevels[i] = Boolean.parseBoolean(data[i+11]);
        }
        for(int i = 0; i < thisUser.activityProgress.length; i++) {
            thisUser.activityProgress[i] = Boolean.parseBoolean(data[i+14]);
        }
    }

    public void WriteFile() {
        try
        {
            if (!root.exists()) {
                root.mkdirs();
            }
            File userSettingsFile = new File(root, "usersettings.txt");

            if (!thisUser.userName.equals("admin")) {
                FileWriter writer = new FileWriter(userSettingsFile, true);
                writer.append(thisUser.userName + ",");
                writer.append(String.valueOf(thisUser.userId));
                for (int i = 0; i < thisUser.demosViewed.length; i++) {
                    writer.append("," + thisUser.demosViewed[i]);
                }
                for (int i = 0; i < thisUser.availableLevels.length; i++) {
                    writer.append("," + thisUser.availableLevels[i]);
                }
                for (int i = 0; i < thisUser.activityProgress.length; i++) {
                    writer.append("," + thisUser.activityProgress[i]);
                }
                writer.append("\n");
                writer.flush();
                writer.close();
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
