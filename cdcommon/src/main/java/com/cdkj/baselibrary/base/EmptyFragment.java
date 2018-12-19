package com.cdkj.baselibrary.base;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragment extends BaseLazyFragment{


//    public EmptyFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
//    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }
}
