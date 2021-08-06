package cos.mol.ott;

import android.content.Context;

public class TochkaBuilder {

    FastSaver fs;

    public TochkaBuilder(Context context, String game, String naming, String uuid, String gadid){
        fs = new FastSaver(context);
        String tochka = game + "?nmg=" + naming + "&dv_id=" + uuid + "&avr=" + gadid;
        fs.setTochka(tochka);
    }
}
