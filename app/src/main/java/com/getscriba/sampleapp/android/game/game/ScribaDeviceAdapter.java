package com.getscriba.sampleapp.android.game.game;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getscriba.sampleapp.android.game.R;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alannaogrady on 08/03/2018.
 */

public class ScribaDeviceAdapter extends BaseAdapter implements Parcelable {

    private ArrayList<ScribaStylusDevice> mScribaDevices = new ArrayList<>();
    private Context mContext;
    private boolean mClickEnabled = false;

    public ScribaDeviceAdapter(final Context context) {
        this.mContext = context;
    }

    protected ScribaDeviceAdapter(Parcel in) {
        mScribaDevices = in.createTypedArrayList(ScribaStylusDevice.CREATOR);
    }

    public static final Creator<ScribaDeviceAdapter> CREATOR = new Creator<ScribaDeviceAdapter>() {
        @Override
        public ScribaDeviceAdapter createFromParcel(Parcel in) {
            return new ScribaDeviceAdapter(in);
        }

        @Override
        public ScribaDeviceAdapter[] newArray(int size) {
            return new ScribaDeviceAdapter[size];
        }
    };

    public void update(final List<ScribaStylusDevice> results) {
        if (results.size() != mScribaDevices.size()) {
            this.mScribaDevices = new ArrayList<>(results);
            notifyDataSetChanged();
        }
    }

    public void clearDevices() {
        mScribaDevices.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mScribaDevices.isEmpty() ? 1 : mScribaDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mScribaDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return mClickEnabled;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.name = view.findViewById(R.id.device_name);
            holder.tick = view.findViewById(R.id.tick);
            view.setTag(holder);
        }

        if (mScribaDevices.isEmpty()) {
            final ViewHolder holder = (ViewHolder) view.getTag();
            holder.name.setText("");
            holder.tick.setVisibility(View.GONE);
            mClickEnabled = false;
        } else {
            final ScribaStylusDevice device = (ScribaStylusDevice) getItem(position);
            if (device != null) {
                final ViewHolder holder = (ViewHolder) view.getTag();
                final String name = device.name();
                holder.name.setText(name != null ? name : mContext.getString(R.string.not_available));
                if (device.getState() == ScribaStylusDevice.ScribaDeviceState.SCRIBA_DEVICE_STATE_CONNECTED) {
                    holder.tick.setVisibility(View.VISIBLE);
                } else {
                    holder.tick.setVisibility(View.GONE);
                }
                mClickEnabled = true;
            }
        }
        return view;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mScribaDevices);
    }

    private class ViewHolder {
        private TextView name;
        private ImageView tick;
    }
}
