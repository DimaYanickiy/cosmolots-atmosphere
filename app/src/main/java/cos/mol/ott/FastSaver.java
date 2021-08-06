package cos.mol.ott;

import android.content.Context;

import com.appizona.yehiahd.fastsave.FastSave;

public class FastSaver{

    public FastSaver(Context context){
        FastSave.init(context);
    }

    public boolean getLaunch(){
        return FastSave.getInstance().getBoolean("1", true);
    }
    public boolean getFlyer(){
        return FastSave.getInstance().getBoolean("2", true);
    }
    public boolean getReference(){
        return FastSave.getInstance().getBoolean("3", true);
    }
    public String getTochka(){
        return FastSave.getInstance().getString("4", "");
    }
    public int getPoints(){
        return FastSave.getInstance().getInt("5", 20000);
    }

    public void setLaunch(boolean launch){
        FastSave.getInstance().saveBoolean("1", launch);
    }
    public void setFlyer(boolean flyer){
        FastSave.getInstance().saveBoolean("2", flyer);
    }
    public void setReference(boolean reference){
        FastSave.getInstance().saveBoolean("3", reference);
    }
    public void setTochka(String tochka){
        FastSave.getInstance().saveString("4", tochka);
    }
    public void setPoints(int points){
        FastSave.getInstance().saveInt("5", points);
    }

    public String getAd(){
        return FastSave.getInstance().getString("6", "");
    }
    public void setAd(String ad){
        FastSave.getInstance().saveString("6", ad);
    }
}
