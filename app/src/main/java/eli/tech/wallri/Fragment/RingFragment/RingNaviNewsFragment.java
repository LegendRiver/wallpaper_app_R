package eli.tech.wallri.Fragment.RingFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.Activity.RingActivity.RingCollection;
import eli.tech.wallri.Adapter.Rings.RingNaviFragmentRecyclerViewAdapter;
import eli.tech.wallri.R;
import eli.tech.wallri.Utils.CleanMessageUtil;
import eli.tech.wallri.Utils.ToastUtils;

/**
 * Created by 小雷 on 2017/12/28.
 */

public class RingNaviNewsFragment extends Fragment {

    private RecyclerView mNaviFragmentRecyclerView;
    private List<Integer> list;
    private List<Integer> imageList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.naviringfragment, container, false);
        initView(view);

        RingNaviFragmentRecyclerViewAdapter adapter = new RingNaviFragmentRecyclerViewAdapter(imageList, list, getActivity());
        mNaviFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNaviFragmentRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RingNaviFragmentRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemclic(View view, int data) {
                switch (data) {
                    //给应用打分Mark
                    case 0:
                        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                    //升级
//                    case 1:
//                        break;
                    //收藏
                    case 1:
                        startActivity(new Intent(getActivity(), RingCollection.class));
                        break;
                    //缓存
                    case 2:
                        CleanMessageUtil.clearAllCache(getActivity().getApplicationContext());
                        ToastUtils.showToast(getActivity(),"Success");
                        break;

                }
            }
        });

        return view;
    }

    private void initView(View view) {
        mNaviFragmentRecyclerView = view.findViewById(R.id.NaviRingFragmentRecyclerView);
        list = new ArrayList<Integer>();
        list.add(R.string.Mark);//给应用打分Mark
        list.add(R.string.Collection);  //收藏
        list.add(R.string.ScavengingCaching);//缓存8
        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.star);
        imageList.add(R.drawable.like_gray_save);
        imageList.add(R.drawable.clear_cache);


    }

    int yourChoice;



    private void showThumbsUp() {


        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(getActivity());
        View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.showthumbsup, null);
        final RatingBar mRatingBar = dialog.findViewById(R.id.ratingBar);

        customizeDialog.setTitle(R.string.score);

        customizeDialog.setView(dialog);
        customizeDialog.setPositiveButton(R.string.Determine, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), R.string.Success, Toast.LENGTH_LONG).show();
            }
        });
        customizeDialog.show();
    }


}
