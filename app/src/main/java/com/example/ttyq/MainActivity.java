package com.example.ttyq;


import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText chongzhijine;
    private EditText zengsongjine;
    private EditText tongdanjia;
    private EditText kaneizongjine;
    private EditText shizhetongshu;
    private EditText shijitongdanjia;
    private Button jisuan;
    private Button qingkong;
    private Button help;

    private boolean i = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chongzhijine = findViewById(R.id.czje);
        zengsongjine = findViewById(R.id.zsje);
        tongdanjia = findViewById(R.id.tdj);
        kaneizongjine = findViewById(R.id.knzje);
        shizhetongshu = findViewById(R.id.szts);
        shijitongdanjia = findViewById(R.id.sjtdj);
        jisuan = findViewById(R.id.js);
        qingkong = findViewById(R.id.qingkong);
        help = findViewById(R.id.help);

        //计算模块
        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = chongzhijine.getText().toString();
                String z = zengsongjine.getText().toString();
                String t = tongdanjia.getText().toString();
                if (c.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.toast1, Toast.LENGTH_SHORT).show();
                }else if (z.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.toast2, Toast.LENGTH_SHORT).show();
                }else  if (t.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.toast3, Toast.LENGTH_SHORT).show();
                }else{
                    kaneizongjine.setText(getKaneizongjine(chongzhijine,zengsongjine));
                    shizhetongshu.setText(getShizhetoushu(chongzhijine,zengsongjine,tongdanjia));
                    shijitongdanjia.setText(getShijitongdanjia(chongzhijine,zengsongjine,tongdanjia));
                }
            }
        });

        //清空模块
        qingkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //除桶单价外全部清空
                clean(chongzhijine,zengsongjine,kaneizongjine,shizhetongshu,shijitongdanjia);
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder实例化AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.mipmap.icon);
                builder.setTitle(R.string.help);
                builder.setMessage(R.string.shuoming);

                builder.show();

            }
        });
        //设置状态栏沉浸
        setStatusBarBgColor(getResources().getColor(R.color.white));


//主方法结束
    }

    //计算卡内总金额，返回String
    private String getKaneizongjine(EditText czje,EditText zsje)
    {
        //充值金额转int
        String s_cz = czje.getText().toString();
        int cz = Integer.parseInt(s_cz);
        //赠送金额转int
        String s_zs = zsje.getText().toString();
        int zs = Integer.parseInt(s_zs);

        //卡内总金额=充值金额+赠送金额
        int s = cz + zs;
        String sum = String.valueOf(s);

        return sum;
    }

    //计算实折桶数，返回String(小数点后两位)
    private String getShizhetoushu(EditText czje,EditText zsje,EditText tdj)
    {
        //充值金额转int
        String s_cz = czje.getText().toString();
        int cz = Integer.parseInt(s_cz);
        //赠送金额转int
        String s_zs = zsje.getText().toString();
        int zs = Integer.parseInt(s_zs);
        //桶单价转int
        float t = Float.valueOf(tdj.getText().toString());
        //实折桶数=（充值金额+赠送金额）/桶单价
        float s = (cz + zs)/t;
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
        String sum = myformat.format(s);

        return sum;
    }

    //计算实际桶单价，返回String(小数点后两位)
    private String getShijitongdanjia(EditText czje,EditText zsje,EditText tdj)
    {
        //充值金额转int
        String s_cz = czje.getText().toString();
        int cz = Integer.parseInt(s_cz);
        //赠送金额转int
        String s_zs = zsje.getText().toString();
        int zs = Integer.parseInt(s_zs);
        //桶单价转int
        float t = Float.valueOf(tdj.getText().toString());

        //桶数
        float ts = (cz+zs)/t;

        //实际桶单价=充值金额/实折桶数
        float s = cz/ts;
        //取小数点后两位
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
        String sum = myformat.format(s);

        return sum;
    }

    //用于清空除桶单价外所有值
    private void clean(EditText czje,EditText zsje,EditText knzje,EditText szts,EditText sjtdj)
    {
        czje.setText("");
        zsje.setText("");
        knzje.setText("");
        szts.setText("");
        sjtdj.setText("");
    }

    //设置状态栏沉浸的同时使用下面isLightColor方法使状态栏反色
    public void setStatusBarBgColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

}
